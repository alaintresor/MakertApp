package com.example.agrmangement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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


public class Orders extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        final String userId = getIntent().getStringExtra("userId");

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);
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


        final TextView body=(TextView)findViewById(R.id.isEmpty);
        final ListView orderList=(ListView)findViewById(R.id.orderList);
        final Button Upload=(Button)findViewById(R.id.Upload);
        final TextView payout=(TextView) findViewById(R.id.payOut);
         final TextView title=(TextView) findViewById(R.id.tatolTilte);

         Upload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(getApplicationContext(),UploadReceipt.class);
                 intent.putExtra("userId",userId);
                 startActivity(intent);
             }
         });
        final List<orderSetData> orderSetData;
        orderSetData = new ArrayList<>();

        final View divider = (View) findViewById(R.id.divider3);

        final int[] payOut = {0};


        final Handler handler = new Handler(Looper.getMainLooper());
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
                PutData putData = new PutData("http://192.168.43.114/androidN/myOrder.php", "POST", field, data);
                if (putData.startPut()) {
                    String result = null;
                    if (putData.onComplete()) {
                        // progressBar.setVisibility(View.GONE);
                        result = putData.getResult();
                        if (!result.toString().equals("Your Order is empty")) {
                            try {
                                JSONArray array = new JSONArray(result);

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject object = array.getJSONObject(i);
                                    String id = object.getString("id");
                                    String t_price = object.getString("t_price");
                                    String u_price = object.getString("u_price");
                                    String proImage = object.getString("proImage");
                                    String proQty = object.getString("proQty");
                                    String proName = object.getString("proName");
                                    payOut[0] = payOut[0] + (parseInt(u_price) * parseInt(proQty));

                                    orderSetData.add(new orderSetData(id,proName,proQty,u_price,t_price,proImage));

                                }
                                orderAdpter orderAdpter = new orderAdpter(getApplicationContext(), R.layout.order_item, orderSetData);
                                orderList.setAdapter(orderAdpter);
                                payout.setText(payOut[0] + " Frw");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            body.setVisibility(View.VISIBLE);
                            Upload.setVisibility(View.GONE);
                            payout.setVisibility(View.GONE);
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
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shop_cart:
                Intent intent = new Intent(Orders.this, cart.class);
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