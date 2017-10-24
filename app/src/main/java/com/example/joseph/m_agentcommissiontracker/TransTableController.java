package com.example.joseph.m_agentcommissiontracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 06/10/2017.
 */

public class TransTableController extends DbConnHandler {
    public TransTableController(Context context) {
        super(context);
    }

    public boolean create(Transaction transaction) {

        ContentValues values = new ContentValues();

        values.put("transAmount", transaction.amount);
        values.put("transType", transaction.transType);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("transactions", null, values) > 0;
        db.close();

        return createSuccessful;
    }
    public int todaysTransCount() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM transactions" ;//WHERE transDate = date('now')
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        //int recordCount =1;
        return recordCount;

    }
    public int weekTransCount(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM transactions";//WHERE transDate = date('now')
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        //int recordCount =1;
        return recordCount;
    }
   public ArrayList<Transaction> read(){
       ArrayList<Transaction> transList = new ArrayList<>();
       String sql = "SELECT * FROM transactions ORDER BY id DESC";

       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(sql, null);

       if (cursor.moveToFirst()) {
           do {

              // int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
               String tAmount = cursor.getString(cursor.getColumnIndex("transAmount"));
               String tType = cursor.getString(cursor.getColumnIndex("transType"));
               String tDate = cursor.getString(cursor.getColumnIndex("transDate"));
               Transaction transaction = new Transaction();
               transaction.amount = Float.parseFloat(tAmount);
               transaction.transType =tType;
               transaction.transDate = tDate;
               //add to arrayList
               transList.add(transaction);
           } while (cursor.moveToNext());
       }

       cursor.close();
       db.close();

       /*transList.add("transaction 1");
       transList.add("transaction 1");
       transList.add("transaction 1");
       transList.add("transaction 1");*/
       return transList;
   }



}