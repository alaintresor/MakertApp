package com.example.agrmangement;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static java.lang.Integer.parseInt;

public class cartAdpter extends ArrayAdapter<setCartData> {
    Context context;
    int resource;
    List<setCartData> setCartData;

    public cartAdpter(Context context, int resource, List<setCartData> setCartData) {
        super(context, resource, setCartData);
        this.setCartData = setCartData;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);
        TextView name = view.findViewById(R.id.cartName);
        TextView close = view.findViewById(R.id.close);
        ImageView img = view.findViewById(R.id.cartImage);
        TextView price = view.findViewById(R.id.cartPrice);
        TextView availableDate=view.findViewById(R.id.avD);
        final TextView cartQty = view.findViewById(R.id.cartQty);
        Button add = view.findViewById(R.id.add);
        Button reduce = view.findViewById(R.id.reduce);
        final TextView payOut = view.findViewById(R.id.payOut);
        final setCartData setCartDataNew = setCartData.get(position);
        name.setText(setCartDataNew.getName());
        price.setText(setCartDataNew.getPrice() + "Frw");
        cartQty.setText(setCartDataNew.getQty());
        Picasso.get().load(setCartDataNew.getImage()).into(img);
        availableDate.setText("Available: "+setCartDataNew.getAvailableDate());
        //add to qty
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (setCartDataNew.addToQty()) {
                    cartQty.setText(setCartDataNew.getQty());
                } else {
                    Toast.makeText(getContext(), "You reach max Quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (setCartDataNew.reduceToQty()) {
                    cartQty.setText(setCartDataNew.getQty());
                } else {
                    Toast.makeText(getContext(), "You reach min Quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //remove product from cart
        final String itemId = setCartDataNew.getId();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(getContext(),"It worked",Toast.LENGTH_SHORT).show();


                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[1];
                        field[0] = "itemId";

                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = itemId;
                        PutData putData = new PutData("http://192.168.43.246/android/remove_from_cart.php", "POST", field, data);
                        if (putData.startPut()) {
                            String result = null;
                            if (putData.onComplete()) {
                                result = putData.getResult();
                                setCartData.remove(position);

                                notifyDataSetChanged();


                            } else {
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                    //End Write and Read data with URL

                });
            }
        });
        return view;
    }
}
