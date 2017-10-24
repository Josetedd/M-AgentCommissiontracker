package com.example.joseph.m_agentcommissiontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RelativeLayout addTransRL, todaysTransRL;
    float mAmount = 0;
    String mTransType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTransRL = (RelativeLayout) findViewById(R.id.add_transRL);
        todaysTransRL = (RelativeLayout) findViewById(R.id.todays_transRL);


        addTransRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get rootView contex
                final Context context = view.getRootView().getContext();
                // inflate add trans form
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addTransView = inflater.inflate(R.layout.add_trans, null, false);
                //create input views objects
                final EditText newTrans = (EditText) addTransView.findViewById(R.id.editTextTrans);
                final RadioGroup transTypeGroup = (RadioGroup) addTransView.findViewById(R.id.trans_type);
                final RadioButton rbWithdraw = (RadioButton) addTransView.findViewById(R.id.withdrawRBtn);
                final RadioButton rbDeposit = (RadioButton) addTransView.findViewById(R.id.depositRBtn);

                // display the add trans form in a dialog
                new AlertDialog.Builder(context)
                        .setView(addTransView)
                        .setTitle("Add Transaction")
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mAmount = Float.valueOf(newTrans.getText().toString());
                                        //dialog.cancel();
                                        if (rbWithdraw.isChecked()) {
                                            mTransType = "withdraw";
                                            ///Toast.makeText(MainActivity.this, "Withdraw", Toast.LENGTH_SHORT).show();
                                        } else if (rbDeposit.isChecked()) {
                                            //Toast.makeText(MainActivity.this, "Deposit", Toast.LENGTH_SHORT).show();
                                            mTransType = "deposit";
                                        }
                                        //Toast.makeText(MainActivity.this, mTransType, Toast.LENGTH_SHORT).show();
                                        Transaction transaction = new Transaction();
                                        transaction.amount = mAmount;
                                        transaction.transType = mTransType;
                                        boolean createSuccessful = new TransTableController(context).create(transaction);
                                        if (createSuccessful) {
                                            Toast.makeText(context, "Transaction successfully added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "not added", Toast.LENGTH_LONG).show();
                                        }
                                        count();

                                    }

                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();


                //Toast.makeText(MainActivity.this, "I was Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        // open a new view todays transactions activity
        todaysTransRL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TodaysActivity.class);
                startActivity(i);
            }
        });

        count();


    }

    public void count() {
        int todaysCount = new TransTableController(this).todaysTransCount();
        TextView todaysCountTextView = (TextView) findViewById(R.id.today_count);
        todaysCountTextView.setText("" + todaysCount);


    }
}
