package com.example.myplaylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView MyPlayList;
    private Button Signup;
    private EditText name, Age, Email, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        MyPlayList = (TextView) findViewById(R.id.Signup);
        MyPlayList.setOnClickListener(this);
        Signup = (Button) findViewById(R.id.Signup);
        Signup.setOnClickListener(this);
        name = (EditText) findViewById(R.id.fullname);
        Age = (EditText) findViewById(R.id.Age);
        Email = (EditText) findViewById(R.id.Email);
        password2 = (EditText) findViewById(R.id.password2);

    }

    @Override
    public void onClick(@NonNull View v) {
        switch(v.getId()){
            case R.id.textView2:
                startActivity(new Intent(this, MainActivity.class));
            break;
            case R.id.Signup:
                Signup();
                break;
        }

}

    private void Signup() {
        String email=Email.getText().toString().trim();
        String password=password2.getText().toString().trim();
        String Name=name.getText().toString().trim();
        String age=Age.getText().toString().trim();

        if(Name.isEmpty()){
            name.setError("Enter your full name");
        }

        if(age.isEmpty()){
            Age.setError("Age is required");
        }
        if(email.isEmpty()){
            Email.setError("Email is required");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please provide valid email");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            password2.setError("Password is required");
            password2.requestFocus();
            return;
        }

    }
    }