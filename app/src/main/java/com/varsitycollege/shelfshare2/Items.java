package com.varsitycollege.shelfshare2;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Items {

    public static ArrayList<Items> itemsArrayList = new ArrayList<>();

    String ItemName;
    String ItemDesc;
    String DatePicked;
    String Category;

    public Items(String itemName, String itemDesc, String datePicked, String category) {
        ItemName = itemName;
        ItemDesc = itemDesc;
        DatePicked = datePicked;
        Category = category;

    }

    public String getItemName() {
        return ItemName;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public String getDatePicked() {
        return DatePicked;
    }

    public String getCategory() {
        return Category;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public void setDatePicked(String datePicked) {
        DatePicked = datePicked;
    }

    public void setCategory(String category) {
        Category = category;
    }


}
