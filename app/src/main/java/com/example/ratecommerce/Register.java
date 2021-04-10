package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button RegisterButton;
    private EditText Input_Name,Input_phone_number,Input_password;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Reference to  all views
        RegisterButton=findViewById(R.id.Register_btn);
        Input_Name=findViewById(R.id.Register_Name_input);
        Input_phone_number=findViewById(R.id.Register_phone_number_input);
        Input_password=findViewById(R.id.Register_password_input);
        loadingBar=new ProgressDialog(this);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createAccount();
            }
        });
    }
    private void createAccount(){
        String inputname=Input_Name.getText().toString();
        String inputphonenumber=Input_phone_number.getText().toString();
        String inputpassword=Input_password.getText().toString();
        if(TextUtils.isEmpty(inputname))
            Input_Name.setError("Name is required!");

        if(TextUtils.isEmpty(inputphonenumber))
            Input_phone_number.setError("Phone Number is required!");
        if(TextUtils.isEmpty(inputpassword))
            Input_password.setError("Password is required!");
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateUser(inputname,inputphonenumber,inputpassword);
        }
    }
    private void validateUser(String name,String Phonenumber,String password)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("users").child(Phonenumber).exists()))
                {
                    HashMap<String,Object> userobjects=new HashMap<>();
                    userobjects.put("Name",name);
                    userobjects.put("Phonenumber",Phonenumber);
                    userobjects.put("password",password);
                    RootRef.child("users").child(Phonenumber).updateChildren(userobjects).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Register.this,"Your account has been created!",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent =new Intent(Register.this,Login_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Register.this,"Network error ! please try again!",Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }
                else{
                    Toast.makeText(Register.this,"This"+Phonenumber+"already exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Register.this,"Please try using another number",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(Register.this,Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}