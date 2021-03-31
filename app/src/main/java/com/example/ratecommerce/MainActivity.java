package com.example.ratecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button login,joinnow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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