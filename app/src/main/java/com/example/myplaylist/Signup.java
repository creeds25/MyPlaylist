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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private TextView MyPlayList,Signup;
    private EditText fullname, Age, Email, password2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        MyPlayList = (TextView) findViewById(R.id.textView2);
        MyPlayList.setOnClickListener(this);
        Signup = (Button) findViewById(R.id.Signup);
        Signup.setOnClickListener(this);
        fullname = (EditText) findViewById(R.id.fullname);
        Age = (EditText) findViewById(R.id.Age);
        Email = (EditText) findViewById(R.id.Email);
        password2 = (EditText) findViewById(R.id.password2);

    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.textView2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.Signup:
                Signup();
                break;
        }

    }

    private void Signup() {
        String email = Email.getText().toString().trim();
        String password = password2.getText().toString().trim();
        String fullName = fullname.getText().toString().trim();
        String age = Age.getText().toString().trim();
        if(fullName.isEmpty()){
            fullname.setError("Enter your full name");
            fullname.requestFocus();
            return;
        }

        if(age.isEmpty()){
            Age.setError("Age is required");
            Age.requestFocus();
            return;
        }
        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
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
        if(password.length() < 6){
            password2.setError("Min Password length should be 6 characters!");
            password2.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                             User user = new User(fullName, age, email);
                             FirebaseDatabase.getInstance().getReference("Users")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()) {
                                                 Toast.makeText(Signup.this, "User is successfully registered", Toast.LENGTH_LONG).show();
                                                 //redirect to login layout!
                                             } else{
                                                 Toast.makeText(Signup.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     });
                        }else{
                        Toast.makeText(Signup.this, "Failed to register!", Toast.LENGTH_LONG).show();
                    }

                }
                });


    }
    }



