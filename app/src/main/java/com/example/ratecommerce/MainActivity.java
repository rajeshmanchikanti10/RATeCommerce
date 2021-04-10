package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ratecommerce.model.Users;
import com.example.ratecommerce.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.chrono.MinguoChronology;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
private Button login,joinnow;
private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp1=getSharedPreferences("Login", MODE_PRIVATE);


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
        Paper.init(this);
        String phonenumber= Paper.book().read(Prevalent.UserPhoneKey);
        String pass = Paper.book().read(Prevalent.UserPasswordKey);
        if(phonenumber!="" && pass!="")
        {

            if(!TextUtils.isEmpty(phonenumber) && !TextUtils.isEmpty(pass)) {
               AllowUser(phonenumber,pass);
                loadingbar = new ProgressDialog(this);
                loadingbar.setTitle("Already logged in");
                loadingbar.setMessage("Please wait..");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

            }

        }
    }

    private void AllowUser(String phonenumber, String pass) {



        DatabaseReference userref = FirebaseDatabase.getInstance().getReference();
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("users").child(phonenumber).exists()) {
                    Users userdata = snapshot.child("users").child(phonenumber).getValue(Users.class);
                    if(userdata.getPhonenumber().equals(phonenumber) && userdata.getPassword().equals(pass)) {
                        Intent intent = new Intent(MainActivity.this, HomeAcitivity.class);
                        Toast.makeText(MainActivity.this,"Logged in Successfully!",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        Prevalent.currentOnlineUser = userdata;
                        finish();
                        loadingbar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Invalid username or password!",Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}