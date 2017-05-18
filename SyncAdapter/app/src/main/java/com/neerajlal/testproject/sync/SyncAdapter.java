package com.neerajlal.testproject.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neerajlal.testproject.contentproviders.ScoreProvider;
import com.neerajlal.testproject.database.DBOperations;
import com.neerajlal.testproject.network.HttpConnector;
import com.neerajlal.testproject.utils.IConstants;
import com.neerajlal.testproject.vos.ScoreVO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by n33raj on 07-03-2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter implements IConstants {

    private final AccountManager mAccountManager;
    private ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        Log.d(LOG_TAG, "onPerformSync start for account : " + account.name);
        try {
            // Get scores from local
            Cursor localStash = mContentResolver.query(ScoreProvider.CONTENT_URI, null, null, null, null);
            List<ScoreVO> localList = new ArrayList<>();
            if (localStash != null) {
                while (localStash.moveToNext()) {
                    localList.add(ScoreVO.fromCursor(localStash));
                }
                localStash.close();
            }
            Log.d(IConstants.LOG_TAG, "Local size : " + localList.size());

            // Get scores from remote
            String response = HttpConnector.doPost(GET_URL, null);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ScoreVO>>() {
            }.getType();
            List<ScoreVO> remoteList = gson.fromJson(response, listType);
            Log.d(IConstants.LOG_TAG, "Remote size : " + remoteList.size());

            // See what Local scores are missing on Remote
            List<ScoreVO> scoresToRemote = new ArrayList<>();
            for (ScoreVO localItem : localList) {
                if (!remoteList.contains(localItem) || localItem.getDirty() == 1 || localItem.getDirty() == 2)
                    scoresToRemote.add(localItem);
            }

            // See what Remote shows are missing on Local
            List<ScoreVO> scoresToLocal = new ArrayList<>();
            for (ScoreVO remoteItem : remoteList) {
                if (!localList.contains(remoteItem) || remoteItem.getDirty() == 3 || remoteItem.getDirty() == 4)
                    scoresToLocal.add(remoteItem);
            }

            if (scoresToRemote.size() == 0) {
                Log.d(LOG_TAG, "No local changes to update server");
            } else {
                Log.d(LOG_TAG, "Updating remote server with local changes");

                // Updating remote scores
                String param = gson.toJson(scoresToRemote);
                response = HttpConnector.doPost(PUT_URL, param);
                if (response.equals("-1")) {
                    Log.e(LOG_TAG, "Update error!");
                } else {
                    Log.d(LOG_TAG, "Update success");
                }
                //Delete items marked for deletion
                for (ScoreVO localItem : scoresToRemote) {
                    if (localItem.getDirty() == 1) {
                        String selection = DBOperations.DBConstants.COL_ID + " != ?";
                        String[] selectionArgs = {"" + localItem.get_id()};
                        if (provider.delete(ScoreProvider.CONTENT_URI, selection, selectionArgs) > 0)
                            syncResult.stats.numDeletes++;
                    } else if (localItem.getDirty() == 4) {
                        String selection = DBOperations.DBConstants.COL_ID + " != ?";
                        String[] selectionArgs = {"" + localItem.get_id()};
                        ContentValues cvs = new ContentValues();
                        cvs.put(DBOperations.DBConstants.COL_DIRTY, 0);
                        if (provider.update(ScoreProvider.CONTENT_URI, cvs, selection, selectionArgs) > 0)
                            syncResult.stats.numUpdates++;
                    }
                }
            }

            if (scoresToLocal.size() == 0) {
                Log.d(LOG_TAG, "No server changes to update local database");
            } else {
                Log.d(LOG_TAG, "Updating local database with remote changes");

                // Updating local scores
                int i = 0;
                ContentValues scoresToLocalValues[] = new ContentValues[scoresToLocal.size()];
                for (ScoreVO localScore : scoresToLocal) {
                    if (localScore.getDirty() == 0) {
                        scoresToLocalValues[i++] = localScore.getContentValues();
                        syncResult.stats.numInserts++;
                    } else if (localScore.getDirty() == 3) {
                        String selection = DBOperations.DBConstants.COL_ID + " != ?";
                        String[] selectionArgs = {"" + localScore.get_id()};
                        if(provider.delete(ScoreProvider.CONTENT_URI, selection, selectionArgs) > 0)
                            syncResult.stats.numDeletes++;
                    } else if (localScore.getDirty() == 4) {
                        String selection = DBOperations.DBConstants.COL_ID + " != ?";
                        String[] selectionArgs = {"" + localScore.get_id()};
                        ContentValues cvs = localScore.getContentValues();
                        cvs.remove(DBOperations.DBConstants.COL_DIRTY);
                        cvs.put(DBOperations.DBConstants.COL_DIRTY, 0);
                        if(provider.update(ScoreProvider.CONTENT_URI, cvs, selection, selectionArgs) > 0)
                            syncResult.stats.numUpdates++;
                    }
                }
                provider.bulkInsert(ScoreProvider.CONTENT_URI, scoresToLocalValues);
            }

            Log.e(LOG_TAG, "onPerformSync end for account : " + account.name);
        } catch (Exception e) {
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        }

    }
}
