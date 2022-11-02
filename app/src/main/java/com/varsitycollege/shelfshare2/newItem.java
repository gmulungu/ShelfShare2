package com.varsitycollege.shelfshare2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class newItem extends AppCompatActivity {

    private Spinner categories;
    private EditText txtItemName;
    private EditText txtItemDesc;
    DatabaseReference categoryDbRef;
    List<String> Categories;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    DatabaseReference itemDbRef;
    String date;
    private Button photoButton;
    private ImageView imgSave;
    Bitmap captureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        categories = (Spinner) findViewById(R.id.spnCategories);
        Categories = new ArrayList<>();
        initDatePicker();
        dateButton = (Button) findViewById(R.id.btnDate);
        txtItemName = (EditText) findViewById(R.id.txtItemName);
        txtItemDesc = (EditText) findViewById(R.id.txtItemDesc);

        photoButton = (Button) findViewById(R.id.btnPhoto);
        imgSave = (ImageView) findViewById(R.id.imgSave);

        //Getting camera permissions
        if (ContextCompat.checkSelfPermission(newItem.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(newItem.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        //Taking photo
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


        //Firebase Database reference
        itemDbRef = FirebaseDatabase.getInstance().getReference().child("Items");

        //Populating spinner with the categories in the Firebase Database
        categoryDbRef = FirebaseDatabase.getInstance().getReference();
        categoryDbRef.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                String spinnerNew = dataSnapshot1.child("category").getValue(String.class);
                Categories.add(spinnerNew);
            }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(newItem.this, android.R.layout.simple_spinner_item, Categories);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categories.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        }
        //Setting ImageView to photo taken by user
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100) {
                captureImage = (Bitmap) data.getExtras().get("data");
                imgSave.setImageBitmap(captureImage);
            }
        }

    //Initializing date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        //Setting date variables
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    //Turning date into string
    private String makeDateString(int day, int month, int year) {
        return year + "-" + month + "-" + day;
    }


    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void saveItem(View view) {
        insertItemData();
        finish();
    }

    //Sending data to Firebase Database
    private void insertItemData() {

        String itemName = txtItemName.getText().toString().trim();
        String itemDesc = txtItemDesc.getText().toString().trim();
        String selectedCategory = categories.getSelectedItem().toString();


        Items items = new Items(itemName, itemDesc, date, selectedCategory);

        itemDbRef.push().setValue(items);
    }
}




