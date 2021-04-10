package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratecommerce.model.Users;
import com.example.ratecommerce.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class Login_Activity extends AppCompatActivity {
    private Button loginButton;
    private ProgressDialog loadingbar;
    EditText Input_phonenumber,Input_password;
    TextView admintextbtn,notadmintextbtn;
    String UserType="users";
    SharedPreferences sp;
    SharedPreferences.Editor Ed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        Input_phonenumber=findViewById(R.id.login_phone_number_input);
        Input_password=findViewById(R.id.login_password_input);
        loginButton=findViewById(R.id.login_btn);
        admintextbtn=findViewById(R.id.iam_admin_text);
        notadmintextbtn=findViewById(R.id.iam_not_admin_text);
        loadingbar=new ProgressDialog(this);
        Paper.init(this);
        admintextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Admin Login");
                notadmintextbtn.setVisibility(View.VISIBLE);
                admintextbtn.setVisibility(View.INVISIBLE);
                UserType="Admins";
                /*HashMap<String,Object> userobjects=new HashMap<>();
                final DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference();
                userobjects.put("Name","Manchikanti Rajesh");
                userobjects.put("Phonenumber","9966924599");
                userobjects.put("password","12345678");
                databaseReference.child("Admins").child("9966924599").updateChildren(userobjects);*/
            }
        });
        notadmintextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                admintextbtn.setVisibility(View.INVISIBLE);
                notadmintextbtn.setVisibility(View.VISIBLE);
                UserType="users";
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    //Function for login activity
    private void login(){
        String PhoneNumber=Input_phonenumber.getText().toString();
        String password=Input_password.getText().toString();
        if(TextUtils.isEmpty(PhoneNumber))
            Input_phonenumber.setError("Phone Number is required!");
        if(TextUtils.isEmpty(password))
            Input_password.setError("Password is required!");
        else {
                //If Fields are not emmpty do the functionality
                loadingbar.setTitle("login");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                ValidateUser(PhoneNumber,password);

        }
    }
    private void ValidateUser(String PhoneNumber,String password)
    {

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(UserType).child(PhoneNumber).exists()) {

                    Users userdata = snapshot.child(UserType).child(PhoneNumber).getValue(Users.class);
                   //Toast.makeText(Login_Activity.this,"userdata "+userdata.getPhonenumber(),Toast.LENGTH_SHORT).show();
                    if (userdata.getPhonenumber().equals(PhoneNumber)) {
                        if (userdata.getPassword().equals(password)) {

                                if(UserType.equals("Admins"))
                                {
                                    loadingbar.dismiss();

                                    Toast.makeText(Login_Activity.this, "Welcome Admin!,your are Logged in successfully!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login_Activity.this, AdminconfigureActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(UserType.equals("users"))
                                {
                                    Paper.book().write(Prevalent.UserPhoneKey,PhoneNumber);
                                    Paper.book().write(Prevalent.UserPasswordKey,password);
                                    loadingbar.dismiss();
                                    Toast.makeText(Login_Activity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login_Activity.this, HomeAcitivity.class);
                                    startActivity(intent);
                                    Prevalent.currentOnlineUser=userdata;
                                    finish();
                                }


                        }
                        else{

                            Toast.makeText(Login_Activity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }


                } else {

                    Toast.makeText(Login_Activity.this, "The account with" + PhoneNumber + " doesn't exists ", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}