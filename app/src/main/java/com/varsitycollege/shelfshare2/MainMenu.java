package com.varsitycollege.shelfshare2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private Button register, login;
    private EditText EmailAddress, Password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        EmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
        Password = (EditText) findViewById(R.id.txtPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = EmailAddress.getText().toString().trim();
        String password = Password.getText().toString().trim();

        //Login validation
        if (email.isEmpty()){
            EmailAddress.setError("Email is Required");
            EmailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EmailAddress.setError("Please Provide a Vaild Email Address");
            EmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Password.setError("Password is Required");
            Password.requestFocus();
            return;
        }

        //User authentication using Firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //redirect to categories
                    startActivity(new Intent(MainMenu.this, MainActivity.class));
                }else{
                    Toast.makeText(MainMenu.this, "Failed to login. Please check user credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}