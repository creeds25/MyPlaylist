package com.example.myplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    private TextView Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Signup = (TextView) findViewById(R.id.signup);
        Signup.setOnClickListener(this);
    }
}