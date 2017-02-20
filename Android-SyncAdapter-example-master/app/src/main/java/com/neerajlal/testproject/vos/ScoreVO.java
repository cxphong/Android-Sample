package com.neerajlal.testproject.vos;

import android.content.ContentValues;
import android.database.Cursor;

import com.neerajlal.testproject.database.DBOperations;

/**
 * Created by n33raj on 07-03-2016.
 */
public class ScoreVO {
    int _id;
    String name;
    String score;
    int dirty;

    public ScoreVO() {
    }

    public ScoreVO(int _id, String name, String score, int dirty) {
        this._id = _id;
        this.name = name;
        this.score = score;
        this.dirty = dirty;
    }

    // Create a Score object from a cursor
    public static ScoreVO fromCursor(Cursor curTvShows) {
        int id = curTvShows.getInt(curTvShows.getColumnIndex(DBOperations.DBConstants.COL_ID));
        String name = curTvShows.getString(curTvShows.getColumnIndex(DBOperations.DBConstants.COL_NAME));
        String score = curTvShows.getString(curTvShows.getColumnIndex(DBOperations.DBConstants.COL_SCORE));
        int dirty = curTvShows.getInt(curTvShows.getColumnIndex(DBOperations.DBConstants.COL_DIRTY));

        return new ScoreVO(id, name, score, dirty);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getDirty() {
        return dirty;
    }

    public void setDirty(int dirty) {
        this.dirty = dirty;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("name", name);
        cv.put("score", score);
        cv.put("dirty", dirty);
        return cv;
    }

    @Override
    public String toString() {
        return "{ id : " + _id + ", name : " + name + ", score = " + score + ", dirty = " + dirty + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreVO scoreVO = (ScoreVO) o;
        if (_id != scoreVO._id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + _id;
        return result;
    }
}
