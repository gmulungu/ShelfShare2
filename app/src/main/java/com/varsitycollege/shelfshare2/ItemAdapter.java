package com.varsitycollege.shelfshare2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter  extends ArrayAdapter<Items> {
    public ItemAdapter(Context context, List<Items> items){
        super(context, 0, items);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        Items items = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items, parent, false);

        //Formatting data to be displayed in ListView
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemDesc = convertView.findViewById(R.id.itemDesc);
        TextView date = convertView.findViewById(R.id.datePicked);
        TextView category = convertView.findViewById(R.id.category);

        itemName.setText(items.getItemName());
        itemDesc.setText(items.getItemDesc());
        date.setText(items.getDatePicked());
        category.setText(items.getCategory());

        return convertView;
    }
}
