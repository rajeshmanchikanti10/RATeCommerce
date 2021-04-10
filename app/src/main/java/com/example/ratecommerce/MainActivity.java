package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ratecommerce.model.Users;
import com.example.ratecommerce.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
private Button login,joinnow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp1=getSharedPreferences("Login", MODE_PRIVATE);

        String phonenumber=sp1.getString("phonenumber", null);
        String pass = sp1.getString("password", null);
        if(!phonenumber.equals("") && !pass.equals(""))
        {  ProgressDialog loadingbar;
            loadingbar=new ProgressDialog(this);
            loadingbar.setTitle("login");
            loadingbar.setMessage("Please wait");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
            DatabaseReference userref= FirebaseDatabase.getInstance().getReference();
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("users").child(phonenumber).exists())
                    {
                        Users userdata = snapshot.child("users").child(phonenumber).getValue(Users.class);
                        Intent intent = new Intent(MainActivity.this, HomeAcitivity.class);
                        startActivity(intent);
                        Prevalent.currentOnlineUser=userdata;
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.main_login_btn);
        joinnow=findViewById(R.id.main_join_now_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent=new Intent(MainActivity.this,Login_Activity.class);
                startActivity(login_intent);

            }
        });
        joinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent=new Intent(MainActivity.this,Register.class);
                startActivity(register_intent);
            }
        });
    }
}