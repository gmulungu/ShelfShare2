package com.varsitycollege.shelfshare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newCategory extends AppCompatActivity {

    private EditText categoryTitle;
    private EditText categoryGoal;
    DatabaseReference categoryDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        categoryTitle = (EditText) findViewById(R.id.txtCategoryTitle);
        categoryGoal = (EditText) findViewById(R.id.txtCategoryGoal);

        //Firebase Database reference
        categoryDbRef = FirebaseDatabase.getInstance().getReference().child("Categories");
    }

    public void saveCategory(View view) {
        insertCategoryData();
    }

    private void insertCategoryData() {

        String spinnerCategory = categoryTitle.getText().toString().trim();
        String goal = categoryGoal.getText().toString().trim();

        //Data validation
        if (spinnerCategory.isEmpty()){
            categoryTitle.setError("Title is Required");
            categoryTitle.requestFocus();
            return;
        }

        if (goal.isEmpty()){
            categoryGoal.setError("Goal is Required");
            categoryGoal.requestFocus();
            return;
        }

        //Sending data to Firebase Database
        Categories categories = new Categories(spinnerCategory, goal);
        categoryDbRef.push().setValue(categories);

        startActivity(new Intent(newCategory.this, MainActivity.class));
    }
}