package com.example.agrmangement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);

        //get user ID
        final String userId = getIntent().getStringExtra("userId");

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
//                Toast.makeText(product.this, "tool bar", Toast.LENGTH_SHORT).show();
            }
        });


        final TextView payOutTextView = (TextView) findViewById(R.id.payOut);
        final TextView isEmpty = (TextView) findViewById(R.id.isEmpty);
        final TextView title = (TextView) findViewById(R.id.tatolTilte);

        final List<setCartData> setCartData;
        setCartData = new ArrayList<>();
        final ListView listView = (ListView) findViewById(R.id.cartList);
        final Button order = (Button) findViewById(R.id.cartOrdering);
        final View divider = (View) findViewById(R.id.divider3);


        final int[] payOut = {0};


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "userId";

                //Creating array for data
                String[] data = new String[1];
                data[0] = userId;
                PutData putData = new PutData("http://192.168.43.114/androidN/myCart.php", "POST", field, data);
                if (putData.startPut()) {
                    String result = null;
                    if (putData.onComplete()) {
                        // progressBar.setVisibility(View.GONE);
                        result = putData.getResult();
                        if (!result.toString().equals("Your Cart is empty")) {
                            try {
                                JSONArray array = new JSONArray(result);

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject object = array.getJSONObject(i);
                                    String id = object.getString("id");
                                    String proId = object.getString("proId");
                                    String proImage = object.getString("proImage");
                                    String proPrice = object.getString("proPrice");
                                    String proQty = object.getString("proQty");
                                    String proName = object.getString("proName");
                                    String inStock = object.getString("inStock");
                                    payOut[0] = payOut[0] + (parseInt(proPrice) * parseInt(proQty));

                                    setCartData.add(new setCartData(id, proId, proImage, proName, "u", "qty", proPrice, proQty, inStock));

                                }
                                cartAdpter cartAdpter = new cartAdpter(getApplicationContext(), R.layout.cart_item, setCartData);
                                listView.setAdapter(cartAdpter);
                                payOutTextView.setText(payOut[0] + " Frw");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Check your connection", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            isEmpty.setVisibility(View.VISIBLE);
                            order.setVisibility(View.GONE);
                            payOutTextView.setVisibility(View.GONE);
                            divider.setVisibility(View.GONE);
                            title.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                    }

                }
            }
            //End Write and Read data with URL

        });


        //order all form cart
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] done = new int[1];
                if (!setCartData.isEmpty()) {
                    for (int i = 0; i < setCartData.toArray().length; i++) {
                        final setCartData setCartDataNew = setCartData.get(i);
                        final String c_proId = setCartDataNew.getProId();
                        final String c_proQty = setCartDataNew.getQty();
                        final String c_price = setCartDataNew.getPrice();

                        Handler handler = new Handler(Looper.getMainLooper());

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                String[] field = new String[4];
                                field[0] = "userId";
                                field[1] = "proId";
                                field[2] = "proQty";
                                field[3] = "proPrice";

                                //Creating array for data
                                String[] data = new String[4];
                                data[0] = userId;
                                data[1] = c_proId;
                                data[2] = c_proQty;
                                data[3] = c_price;

                                PutData putData = new PutData("http://192.168.43.114/androidN/ordering.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();

                                        if (result.toString().equals("Product Ordered well!!")) {
                                            done[0] = 1;
                                        } else {
                                            done[0] = 0;
                                        }


                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[1];
                            field[0] = "userId";

                            //Creating array for data
                            String[] data = new String[1];
                            data[0] = userId;
                            PutData putData = new PutData("http://192.168.43.114/androidN/resetCart.php", "POST", field, data);
                            if (putData.startPut()) {
                                String result = null;
                                if (putData.onComplete()) {
                                    result = putData.getResult();
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                        //End Write and Read data with URL

                    });
                }

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shop_cart:
                Intent intent = new Intent(cart.this, cart.class);
                final String userId = getIntent().getStringExtra("userId");
                intent.putExtra("userId", userId);
                startActivity(intent);
                break;
            case R.id.order:
                Intent intent1 = new Intent(getApplicationContext(),Orders.class);
                final String userId1=getIntent().getStringExtra("userId");
                intent1.putExtra("userId",userId1);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}