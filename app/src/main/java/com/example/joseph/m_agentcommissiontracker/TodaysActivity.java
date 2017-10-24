package com.example.joseph.m_agentcommissiontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodaysActivity extends AppCompatActivity {
    float mTotalWithAmount=0;
    float mTotalDepoAmount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays);
        readRecords();

    }
    public void readRecords() {
        ArrayList<Transaction> transListfromDb = new TransTableController(this).read();
        ArrayList<String> transList = new ArrayList<>();
        ListView transListView = (ListView) findViewById(R.id.todaysList);
        ArrayAdapter<String> transListAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, transList);
        if(transListfromDb.size()>0){
            for(Transaction trans : transListfromDb){
                float tAmount = trans.amount;
                String tType = trans.transType;
                String tDate = trans.transDate;
                String combined = "" + tAmount + "-"+tType+"-"+ tDate;
                transList.add(combined);
                if (tType.equals("withdraw")) {
                    mTotalWithAmount = mTotalWithAmount + tAmount;
                }
                else if(tType.equals("deposit")){
                    mTotalDepoAmount =mTotalDepoAmount + tAmount;
                }
            }
        }
        else {
            transList.clear();
            transList.add("No records");
        }
        transListView.setAdapter(transListAdapter);
        TextView amountDepoTxtView = (TextView) findViewById(R.id.amount_deposit_id);
        TextView amountWithTxtView = (TextView) findViewById(R.id.amount_withdrawn_id);
        amountDepoTxtView.setText(mTotalDepoAmount+" Kshs");
        amountWithTxtView.setText(mTotalWithAmount+" Kshs");


    }

}
