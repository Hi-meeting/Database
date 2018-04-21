package com.gachon.ham.database;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        final EditText ed1 = findViewById(R.id.ed1);
        final EditText ed2 = findViewById(R.id.ed2);

        Button btnInsert  = findViewById(R.id.btnInsert);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnLoad = findViewById(R.id.btnLoad);

        /*
         * Layout
         * */
        //insert
        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        //clear
        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ed1.setText("");
                ed2.setText("");
            }
        });

        //load
        btnLoad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Cursor c = mDbOpenHelper.getColumn(1);
                ed1.setText(c.getString(1));
            }
        });

        /* Database */
        // DB Create and Open
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

        mDbOpenHelper.insertColumn("1","dongmin", "1", "question", "Hello my name is dongmin and I have a question!");




    }
}
