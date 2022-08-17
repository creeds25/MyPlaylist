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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView create_account;
    private EditText Email, password1;
    private Button Login;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_account = (TextView) findViewById(R.id.create_account);
        create_account.setOnClickListener(this);

        Login = (Button) findViewById(R.id.Login);
        Login.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.Email);
        password1 = (EditText) findViewById(R.id.password1);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account:
                startActivity(new Intent(this, Signup.class));
                break;

            case R.id.Login:
                userLogin();
                break;

        }

    }

    private void userLogin() {
        String email = Email.getText().toString().trim();
        String Password = password1.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            password1.setError("Password is required");
            password1.requestFocus();
            return;
        }
        if(Password.length()<6){
            password1.setError("Min password length is 6 characters!");
            password1.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {
                        if(user.isEmailVerified()){
                        //redirect to user profile

                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        }else {
                            user.sendEmailVerification();
                            Toast.makeText(MainActivity.this, "Check your email to verify", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{

                        Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                 }
            };
        });
    }
}