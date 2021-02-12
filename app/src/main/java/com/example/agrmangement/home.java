package com.example.agrmangement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    int counter=0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final List<catSetData> catSetData;
        catSetData = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MarketApplication");

        setSupportActionBar(toolbar);

        //get User Id
        final String userId=getIntent().getStringExtra("userId");

        Button available=(Button)findViewById(R.id.available);
        Button toBook=(Button)findViewById(R.id.toBook);
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(home.this,product.class);
                intent.putExtra("userId",userId);
                intent.putExtra("status","available");
                startActivity(intent);


            }
        });

        toBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(home.this,product.class);
                intent.putExtra("userId",userId);
                intent.putExtra("status","unavailable");
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
        switch (item.getItemId()){
            case R.id.shop_cart:
                Intent intent = new Intent(getApplicationContext(),cart.class);
                final String userId=getIntent().getStringExtra("userId");
                intent.putExtra("userId",userId);
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
