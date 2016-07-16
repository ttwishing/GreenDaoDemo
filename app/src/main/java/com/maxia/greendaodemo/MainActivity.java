package com.maxia.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maxia.greendaodemo.util.OpenDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenDbHelper openDbHelper = new OpenDbHelper();
    }
}
