package com.neerajlal.testproject.contentproviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.neerajlal.testproject.database.DBOperations;
import com.neerajlal.testproject.utils.IConstants;

public class ScoreProvider extends ContentProvider implements IConstants{
    public static final String AUTHORITY = "com.neerajlal.testproject.contentproviders";
    private static final String BASE_PATH = "scores";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private ScoreObserver mObserver;
    DBOperations mDatabase;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(LOG_TAG, "Handler called!");
            return false;
        }
    });

    public ScoreProvider() {
    }

    public ScoreProvider(Context context) {
        mDatabase = new DBOperations(context);
        mObserver = new ScoreObserver(handler, context);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rows = mDatabase.delete(selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, mObserver, false);
        }
        return rows;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mDatabase.create(values);
        if (id != -1) {
            getContext().getContentResolver().notifyChange(uri, mObserver, false);
        }
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new DBOperations(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = mDatabase.read(selection, selectionArgs);

        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rows = mDatabase.update(values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, mObserver, false);
        }
        return rows;
    }
}
