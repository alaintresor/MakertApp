package com.example.agrmangement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class singleProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Product Details");
        setSupportActionBar(toolbar);

        //get User Id and status

        final String userId = getIntent().getStringExtra("userId");
        final String status = getIntent().getStringExtra("status");

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), product.class);
                intent.putExtra("userId", userId);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });

        final List<catSetData> catSetData;
        catSetData = new ArrayList<>();

        final GridView listView = (GridView) findViewById(R.id.MyList);
//        final ListView listView = (ListView) findViewById(R.id.list);
        final ImageView imagePro = (ImageView) findViewById(R.id.imagepro);
        final TextView namePro = (TextView) findViewById(R.id.name);
        final TextView productDlts = (TextView) findViewById(R.id.productDlts);
        final TextView available = (TextView) findViewById(R.id.available);
        final TextView qtyInput = (TextView) findViewById(R.id.qty);
        final TextView UP = (TextView) findViewById(R.id.Uprice);
        final TextView availableFrom=(TextView)findViewById(R.id.availableFrom);
        Button addCart = (Button) findViewById(R.id.addToCart);
        Button order = (Button) findViewById(R.id.ordering);


//        Get data form product activity

        final String proId = getIntent().getStringExtra("proId");
        final String name = getIntent().getStringExtra("name");
        final String image = getIntent().getStringExtra("image");
        final String description = getIntent().getStringExtra("description");
        final String avble = getIntent().getStringExtra("qty");
        final String price = getIntent().getStringExtra("price");
        final String category = getIntent().getStringExtra("category");
        final String availableDate=getIntent().getStringExtra("availableDate");

        //Order product
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!qtyInput.getText().toString().equals("")) {

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
                            data[1] = proId;
                            data[2] = qtyInput.getText().toString();
                            data[3] = price;

                            PutData putData = new PutData("http://192.168.43.114/androidN/ordering.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    qtyInput.setText("");
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();


                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please proved qty", Toast.LENGTH_LONG).show();
                }
            }
        });


        //add to cart
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!qtyInput.getText().toString().equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters

                            String[] field = new String[7];
                            field[0] = "userId";
                            field[1] = "proId";
                            field[2] = "proImage";
                            field[3] = "proName";
                            field[4] = "proPrice";
                            field[5] = "proQty";
                            field[6] = "availableQty";
                            field[7]="availableDate";
                            //Creating array for data
                            String[] data = new String[7];
                            data[0] = userId;
                            data[1] = proId;
                            data[2] = image;
                            data[3] = name;
                            data[4] = price;
                            data[5] = qtyInput.getText().toString();
                            data[6] = avble;
                            data[7]=availableDate;
                            PutData putData = new PutData("http://192.168.43.114/androidN/addToCart.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    qtyInput.setText("");
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();


                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "please prove qty", Toast.LENGTH_LONG).show();
                }
            }
        });


        namePro.setText(name);
        productDlts.setText(description);
        available.setText(avble);
        UP.setText(price);
        Picasso.get().load(image).into(imagePro);
        availableFrom.setText(availableDate);


//        Similar Products
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[3];
                field[0] = "status";
                field[1] = "category";
                field[2] = "proId";

                //Creating array for data
                String[] data = new String[3];
                data[0] = status;
                data[1] = category;
                data[2] = proId;
                PutData putData = new PutData("http://192.168.43.114/androidN/similarProducts.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
//                        //progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray array = new JSONArray(putData.getResult());

                            for (int i = 0; i < array.length(); i++) {
                                int a = array.length();
                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String qty = object.getString("qty");
                                String price = object.getString("price");
                                String description = object.getString("description");
                                String availableDate = object.getString("availableDate");
                                String image = object.getString("image");
                                String category = object.getString("category");
                                catSetData.add(new catSetData(name, description, image, id, qty, price, category, availableDate));
//

                            }
                            catAdpter catAdpter = new catAdpter(getApplicationContext(), R.layout.gridviewitem, catSetData);
                            listView.setAdapter(catAdpter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id, name, image, description, qty, price, category;
                id = catSetData.get(i).getId();
                name = catSetData.get(i).getCatName();
                image = catSetData.get(i).getImage();
                qty = catSetData.get(i).getQty();
                price = catSetData.get(i).getPrice();
                description = catSetData.get(i).description;
                category = catSetData.get(i).getCategory();

                Intent intent = new Intent(getApplicationContext(), singleProduct.class);
                intent.putExtra("userId", userId);
                intent.putExtra("status", status);
                intent.putExtra("proId", id);
                intent.putExtra("name", name);
                intent.putExtra("image", image);
                intent.putExtra("description", description);
                intent.putExtra("qty", qty);
                intent.putExtra("price", price);
                intent.putExtra("description", description);
                intent.putExtra("category", category);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shop_cart:
                Intent intent = new Intent(singleProduct.this, cart.class);
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