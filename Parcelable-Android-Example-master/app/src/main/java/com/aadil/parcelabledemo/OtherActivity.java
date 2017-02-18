package com.aadil.parcelabledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OtherActivity extends AppCompatActivity {

    TextView name;
    TextView uniqueID;
    TextView marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StudentInfo studentInfo = (StudentInfo) getIntent().getParcelableExtra("studentInfo");

        name = (TextView) findViewById(R.id.name);
        uniqueID = (TextView) findViewById(R.id.id);
        marks = (TextView) findViewById(R.id.marks);

        name.setText(studentInfo.getName());
        uniqueID.setText(studentInfo.getUniqueID());
        marks.setText(Integer.toString(studentInfo.getMarks()));
    }
}
