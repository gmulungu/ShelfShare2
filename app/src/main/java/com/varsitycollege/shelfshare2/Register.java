package com.varsitycollege.shelfshare2;

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

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView Banner;
    private EditText FirstName, LastName, EmailAddress2, Password2;
    private Button RegisterUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        RegisterUser = (Button) findViewById(R.id.btnRegisterUser);
        RegisterUser.setOnClickListener(this);

        FirstName = (EditText) findViewById(R.id.txtFirstName);
        LastName = (EditText) findViewById(R.id.txtLastName);
        EmailAddress2 = (EditText) findViewById(R.id.txtEmailAddress2);
        Password2 = (EditText) findViewById(R.id.txtPassword2);

        Banner = (TextView) findViewById(R.id.txtShelfShare);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtShelfShare:
                startActivity(new Intent(this, MainMenu.class));
                break;
            case R.id.btnRegisterUser:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String Email = EmailAddress2.getText().toString().trim();
        String Password = Password2.getText().toString().trim();
        String firstName = FirstName.getText().toString().trim();
        String lastName = LastName.getText().toString().trim();

        //Data validation
        if (firstName.isEmpty()){
            FirstName.setError("First Name is Required");
            FirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()){
            LastName.setError("Last Name is Required");
            LastName.requestFocus();
            return;
        }

        if (Email.isEmpty()){
            EmailAddress2.setError("Email Address is Required");
            EmailAddress2.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            EmailAddress2.setError("Please provide valid email");
            EmailAddress2.requestFocus();
            return;
        }

        if (Password.isEmpty()){
            Password2.setError("Password is Required");
            Password2.requestFocus();
            return;
        }

        //Validates whether email address is valid and sends user data to Firebase Databse
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(firstName, lastName, Email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Register.this, "User has not been registered!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                    }
                });
    }
}