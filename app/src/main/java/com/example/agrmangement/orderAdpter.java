package com.example.agrmangement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class orderAdpter extends ArrayAdapter<orderSetData> {

    Context context;
    int resource;
    List<orderSetData> orderSetData;

    public orderAdpter(Context context, int resource, List<orderSetData> orderSetData) {
        super(context, resource, orderSetData);
        this.orderSetData = orderSetData;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);

        TextView OrName = view.findViewById(R.id.Name);
        TextView qty = view.findViewById(R.id.qty);
        TextView price = view.findViewById(R.id.price);

        ImageView image=view.findViewById(R.id.Image);
        TextView payOut = view.findViewById(R.id.pay);
        final com.example.agrmangement.orderSetData orderSetDataNew = orderSetData.get(position);
        OrName.setText(orderSetDataNew.getName());
        qty.setText(orderSetDataNew.getQuantity());
        price.setText(orderSetDataNew.getPrice()+"Frw");
        payOut.setText(orderSetDataNew.getAmount()+"Frw");
        Picasso.get().load(orderSetDataNew.getImage()).into(image);

        return view;
    }
}

