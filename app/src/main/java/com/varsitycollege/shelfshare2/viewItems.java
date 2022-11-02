package com.varsitycollege.shelfshare2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class viewItems extends AppCompatActivity {

    private Spinner categories;
    DatabaseReference categoryDbRef;
    DatabaseReference itemsDbRefs;
    List<String> Categories;
    List<String> itemsList;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        categories = (Spinner) findViewById(R.id.spnCategories2);
        Categories = new ArrayList<>();
        itemsList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewItems);

        //Populating ListView with data from Firebase Database
        itemsDbRefs = FirebaseDatabase.getInstance().getReference();
        itemsDbRefs.child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String itemName = "Item Name: " + dataSnapshot1.child("itemName").getValue(String.class);
                    String itemDesc = "Item Description: " + dataSnapshot1.child("itemDesc").getValue(String.class);
                    String date = "Date Acquired: " + dataSnapshot1.child("datePicked").getValue(String.class);
                    String category = "Category: " + dataSnapshot1.child("category").getValue(String.class);
                    Items newItems = new Items(itemName, itemDesc, date, category);
                    Items.itemsArrayList.add(newItems);
                }
                ItemAdapter itemAdapter2 = new ItemAdapter(getApplicationContext(), Items.itemsArrayList);
                listView.setAdapter(itemAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Populating spinner with data from Firebase Database
        categoryDbRef = FirebaseDatabase.getInstance().getReference();
        categoryDbRef.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    String spinnerNew = dataSnapshot1.child("category").getValue(String.class) + " goal: " + dataSnapshot1.child("goal").getValue(String.class);
                    Categories.add(spinnerNew);
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(viewItems.this, android.R.layout.simple_spinner_item, Categories);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categories.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}


