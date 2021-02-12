package com.example.agrmangement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class signUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextInputEditText fullnameTxetInpupEditText=(TextInputEditText) findViewById(R.id.user);
        final TextInputEditText emailTxetInpupEditText=(TextInputEditText) findViewById(R.id.pwd);
        final TextInputEditText usernameTxetInpupEditText=(TextInputEditText)findViewById(R.id.username);
        final TextInputEditText passwordTxetInpupEditText=(TextInputEditText)findViewById(R.id.password);
        final TextInputEditText phoneTxetInpupEditText=(TextInputEditText)findViewById(R.id.tel);
        final Button signUpButton=(Button) findViewById(R.id.buttonSignUp);
        final TextView textViewlogin=(TextView) findViewById(R.id.loginText);
        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress);
        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname,email,username,password,tel;
                fullname= String.valueOf(fullnameTxetInpupEditText.getText());
                email= String.valueOf(emailTxetInpupEditText.getText());
                username= String.valueOf(usernameTxetInpupEditText.getText());
                password= String.valueOf(passwordTxetInpupEditText.getText());
                tel=String.valueOf(phoneTxetInpupEditText.getText());

                if(!fullname.equals("") || !email.equals("") || !username.equals("") || !password.equals("") || !tel.equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[5];
                        field[0] = "fullname";
                        field[1] = "email";
                        field[2] = "username";
                        field[3] = "password";
                        field[4] = "tel";
                        //Creating array for data
                        String[] data = new String[5];
                        data[0] = fullname;
                        data[1] = email;
                        data[2] = username;
                        data[3] = password;
                        data[4] = tel;
                        PutData putData = new PutData("http://192.168.43.41/android/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if(result.toString().equals("Sign Up Success"))
                                {

                                    Intent intent=new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        //End Write and Read data with URL
                    }
                });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}