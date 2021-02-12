package com.example.agrmangement;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class start extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.start);

    final Handler handler=new Handler();
    final Runnable r=new Runnable() {
        @Override
        public void run() {
//            ConnectivityManager cm= (ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
//            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()){
                start1();
//            }else{
//                start2();
//            }

        }
    };
    handler.postDelayed(r,3000);
}

    private void start2() {
       // Intent intent =new Intent(getApplicationContext(),networkerror.class);
        //startActivity(intent);
        Toast.makeText(this, "check your connection", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void start1() {
        Intent intent =new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();
    }
}
