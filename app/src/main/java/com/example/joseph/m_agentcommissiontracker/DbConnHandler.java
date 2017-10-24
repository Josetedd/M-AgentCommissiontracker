package com.example.joseph.m_agentcommissiontracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Joseph on 06/10/2017.
 * This class contains methods for creating Db and Tables
 */

public class DbConnHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "mACTDatabase";

    public DbConnHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE transactions " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "transAmount REAL, " +
                "transType TEXT, "+ "transDate DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')) ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS transactions";
        db.execSQL(sql);

        onCreate(db);
    }

}
