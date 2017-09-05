package com.example.mengfanlin.h2oreserve.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DBManager
{
    public static final String DATABASE_NAME = "water.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_TABLE_FRIENDSHIP = "CREATE TABLE " + DBStructure.ReportId.TABLE_NAME + " ( "
            + "`" + DBStructure.ReportId.COLUMN_REPORT_ID + "` INTEGER PRIMARY KEY);";

    private SQLiteDatabase db;
    private AppSQLiteOpenHelper helper;
    private String[] projection = {
            DBStructure.ReportId.COLUMN_REPORT_ID
    };

    public DBManager(Context context)
    {
        this.helper = new AppSQLiteOpenHelper(context);
    }

    public void deleteAll()
    {
        this.db.delete(DBStructure.ReportId.TABLE_NAME, null, null);
    }

    /**
     * Delete a report id in SQLite
     * @param id
     */
    public void deleteReportId(int id)
    {
        String selection = DBStructure.ReportId.COLUMN_REPORT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete(DBStructure.ReportId.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Get all the reports in SQLite for testing
     * @return
     */
    public ArrayList<String> getAllReportIds()
    {
        Cursor c = db.query(DBStructure.ReportId.TABLE_NAME, projection,
                null, null, null, null, null);
        ArrayList<String> ids = new ArrayList();
        if (c.moveToFirst()) {
            do
            {
                Log.e("Column 0 is", c.getString(0));
                ids.add(c.getString(0));
            } while (c.moveToNext());
        }
        Log.e("DBManager got ids", ids.toString());
        c.close();
        return ids;
    }


    /**
     * Insert an id
     * @param id
     * @return
     */
    public long insertReportId(int id)
    {
        ContentValues values = new ContentValues();
        values.put(DBStructure.ReportId.COLUMN_REPORT_ID, id);
        return this.db.insert(DBStructure.ReportId.TABLE_NAME, null, values);
    }

    /**
     * open
     * @return
     */
    public DBManager open()
    {
        this.db = this.helper.getWritableDatabase();
        return this;
    }

    /**
     * close
     */
    public void close()
    {
        this.helper.close();
    }

    /**
     * Open Helper
     */
    private static class AppSQLiteOpenHelper
            extends SQLiteOpenHelper
    {
        public AppSQLiteOpenHelper(Context context)
        {
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase paramSQLiteDatabase)
        {
            paramSQLiteDatabase.execSQL(SQL_CREATE_TABLE_FRIENDSHIP);
        }

        public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int oldVersion, int newVersion) {}
    }
}