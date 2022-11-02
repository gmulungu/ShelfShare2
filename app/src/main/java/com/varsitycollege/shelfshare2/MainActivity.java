package com.varsitycollege.shelfshare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newCategory(View view) {
        startActivity(new Intent(MainActivity.this, newCategory.class));
    }

    public void newItem(View view) {

        startActivity(new Intent(MainActivity.this, newItem.class));
    }

    public void viewItems(View view) {
        startActivity(new Intent(MainActivity.this, viewItems.class));
    }
}