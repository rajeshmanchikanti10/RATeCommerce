package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ratecommerce.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.temporal.ValueRange;

public class ConfirmorderActivity extends AppCompatActivity {
    private EditText name,phonenumber,address;
    private Button confirmorderbrtn;
    String txtname,txtphonenumber,txtaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        name=findViewById(R.id.confirmorder_name);
        phonenumber=findViewById(R.id.confirmorder_phonenumber);
        address=findViewById(R.id.confirmorder_address);
        confirmorderbrtn=findViewById(R.id.confirmorder_btn);
        txtname=""+Prevalent.currentOnlineUser.getName().toString();
        txtphonenumber=""+Prevalent.currentOnlineUser.getPhonenumber().toString();
        txtaddress=Prevalent.currentOnlineUser.getAddress().toString();
        name.setText(txtname);
        phonenumber.setText(txtphonenumber);



        Toast.makeText(ConfirmorderActivity.this,getIntent().getExtras().get("Total Price").toString(),Toast.LENGTH_LONG
        ).show();
        confirmorderbrtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateData();
            }
        });
    }
    private void ValidateData(){
        String txtname=name.getText().toString();
        String txtphonenumber=phonenumber.getText().toString();
        String txtaddress=address.getText().toString();
        if(TextUtils.isEmpty(txtname))
            name.setError("Please  enter your name");
        if(TextUtils.isEmpty(txtphonenumber))
            phonenumber.setError("Please enter your phone number");
        if(TextUtils.isEmpty(txtaddress))
            address.setError("Please enter your address");
        else
        {

        }
    }

}