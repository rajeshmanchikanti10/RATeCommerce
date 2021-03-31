package com.example.ratecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    private Button Register;
    private EditText Input_Name,Input_phone_number,Input_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register=findViewById(R.id.Register_btn);
        Input_Name=findViewById(R.id.Register_Name_input);
        Input_phone_number=findViewById(R.id.Register_phone_number_input);
        Input_password=findViewById(R.id.Register_password_input);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}