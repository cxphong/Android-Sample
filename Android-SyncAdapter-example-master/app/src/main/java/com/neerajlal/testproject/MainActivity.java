package com.neerajlal.testproject;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.neerajlal.testproject.accounts.AccountManagerUtil;
import com.neerajlal.testproject.contentproviders.ScoreObserver;
import com.neerajlal.testproject.contentproviders.ScoreProvider;
import com.neerajlal.testproject.database.DBOperations;
import com.neerajlal.testproject.frags.ListFragment;
import com.neerajlal.testproject.frags.NewFragment;
import com.neerajlal.testproject.utils.IConstants;

public class MainActivity extends AppCompatActivity implements IConstants {

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(LOG_TAG, "Handler called!");
            return false;
        }
    });
    private Account mAccount;
    private ContentResolver mResolver;
    private Object handleSyncObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResolver = getContentResolver();

        //fillDummy();
        loadListFragment();

        // Create the dummy account
        mAccount = AccountManagerUtil.getInstance().getAccount(this);

        mResolver.registerContentObserver(ScoreProvider.CONTENT_URI, true, new ScoreObserver(handler, this));
        ContentResolver.setSyncAutomatically(mAccount, ScoreProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, ScoreProvider.AUTHORITY, new Bundle(), 300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleSyncObserver = ContentResolver.addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE |
                ContentResolver.SYNC_OBSERVER_TYPE_PENDING, new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int which) {
                refreshSyncStatus();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handleSyncObserver != null) {
            ContentResolver.removeStatusChangeListener(handleSyncObserver);
        }
    }


    private void fillDummy() {
        if (getContentResolver().query(ScoreProvider.CONTENT_URI, null, null, null, null).getCount() == 0) {
            for (int i = 0; i < 10; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "name" + i);
                contentValues.put("score", i * i);
                getContentResolver().insert(ScoreProvider.CONTENT_URI, contentValues);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            loadDetailFragment();
            return true;
        } else if (id == R.id.action_delete) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBOperations.DBConstants.COL_DIRTY, 1);
            Toast.makeText(this, "Marked " + getContentResolver().update(ScoreProvider.CONTENT_URI, contentValues, null, null) + " rows for deletion!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_refresh) {
            manualSync();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadListFragment() {
        ListFragment fragment = ListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment, "ListFragment")
                .commit();
    }

    private void loadDetailFragment() {
        NewFragment fragment = NewFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment, "NewFragment")
                .addToBackStack("NewFragment")
                .commit();
    }

    private void manualSync() {
        Bundle settingsBundle = new Bundle();
        //Forces a manual sync
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        //Forces the sync to start immediately.
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccount, ScoreProvider.AUTHORITY, settingsBundle);
    }

    private void refreshSyncStatus() {
        String status;
        if (ContentResolver.isSyncActive(mAccount, ScoreProvider.AUTHORITY))
            status = "Status: Syncing..";
        else if (ContentResolver.isSyncPending(mAccount, ScoreProvider.AUTHORITY))
            status = "Status: Pending..";
        else
            status = "Status: Idle";

        Log.d(LOG_TAG, "refreshSyncStatus> " + status);
    }

}
