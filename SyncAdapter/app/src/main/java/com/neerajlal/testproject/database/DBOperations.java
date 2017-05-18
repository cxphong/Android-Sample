package com.neerajlal.testproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by n33raj on 06-03-2016.
 */
public class DBOperations implements DatabaseErrorHandler {

    private final String DB_NAME = "ContactSync.db";
    private final int DB_VERSION = 1;
    private final String TBL_SCORE = "score_tbl";

    Context context;
    private DataBaseHelper helper;

    public DBOperations(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context, DB_NAME, null, DB_VERSION, this);
    }

    public long create(ContentValues cv) {
        SQLiteDatabase wrDB = helper.getWritableDatabase();
        try {
            return wrDB.insertOrThrow(TBL_SCORE, null, cv);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Cursor read(String selection, String[] selectionArgs) {
        SQLiteDatabase wrDB = helper.getReadableDatabase();
        return wrDB.query(TBL_SCORE, null, selection, selectionArgs, null, null, null);
    }

    public int update(ContentValues cv, String whereClause, String[] whereArgs) {
        SQLiteDatabase wrDB = helper.getWritableDatabase();
        return wrDB.update(TBL_SCORE, cv, whereClause, whereArgs);
    }

    public int delete(String selection, String[] selectionArgs) {
        SQLiteDatabase wrDB = helper.getWritableDatabase();
        return wrDB.delete(TBL_SCORE, selection, selectionArgs);
    }

    @Override
    public void onCorruption(SQLiteDatabase dbObj) {
        Toast.makeText(context, "DB Corrupted!!", Toast.LENGTH_LONG).show();
    }

    private class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBConstants.CREATE_TBL_SCORE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DBConstants.DELETE_TBL_SCORE);
            onCreate(db);
        }
    }



    public class DBConstants {
        /*
            dirty field values :
                0 = clean
                1 = deleted locally
                2 = updated locally
                3 = deleted remotely
                4 = updated remotely
        */
        public static final int DIRTY_CLEAN = 0;
        public static final int DIRTY_LOCALLY_DELETED = 1;
        public static final int DIRTY_LOCALLY_UPDATED = 2;
        public static final int DIRTY_REMOTELY_DELETED = 3;
        public static final int DIRTY_REMOTELY_UPDATED = 4;

        public static final String COL_ID = "_id";
        public static final String COL_NAME = "name";
        public static final String COL_SCORE = "score";
        public static final String COL_DIRTY = "dirty";
        public static final String DELETE_TBL_SCORE = "DELETE TABLE IF EXISTS score_tbl;";
        public static final String CREATE_TBL_SCORE = "CREATE TABLE score_tbl (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_SCORE + " TEXT, " +
                COL_DIRTY + " INTEGER" +
                ");";
    }

}
