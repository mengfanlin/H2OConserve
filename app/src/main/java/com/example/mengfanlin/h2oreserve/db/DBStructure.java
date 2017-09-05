package com.example.mengfanlin.h2oreserve.db;

import android.provider.BaseColumns;

/**
 * Created by mengfanlin on 4/9/17.
 */

/**
 * SQLite structure
 */
public class DBStructure
{
    public static abstract class ReportId
            implements BaseColumns
    {
        public static final String TABLE_NAME = "myReport";
        public static final String COLUMN_REPORT_ID = "reportId";

    }
}
