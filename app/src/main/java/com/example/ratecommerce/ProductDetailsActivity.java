package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ratecommerce.model.Products;
import com.example.ratecommerce.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productprice,productDescription,productName;
    private String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        addToCartBtn=findViewById(R.id.pd_add_to_cart_btn);
        numberButton=findViewById(R.id.number_btn);
        productImage=findViewById(R.id.product_image_details);
        productName=findViewById(R.id.prodct_name_details);
        productDescription=findViewById(R.id.product_description_details);
        productprice=findViewById(R.id.product_price_details);
        pid=getIntent().getStringExtra("pid");
        getProductDetails(pid);
        addToCartBtn.setOnClickListener(this);

    }
    private  void getProductDetails(String pid)
    {
        DatabaseReference ProductRef=FirebaseDatabase.getInstance().getReference().child("Products");
        ProductRef.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Products product=snapshot.getValue(Products.class);
                    productName.setText(product.getPname());
                    productDescription.setText(product.getDescription());
                    productprice.setText(product.getPrice()+"$");
                    Picasso.get().load(product.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(ProductDetailsActivity.this,pid,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        addToCartList();
    }
    private void addToCartList()
    {   String currenttime;
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Cart List");
        String price= productprice.getText().toString();
        price=price.substring(0,price.length()-1);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat time=new SimpleDateFormat("DD MM,YYY HH:MM:SS a");
        currenttime=time.format(calendar.getTime());
        final HashMap<String,Object> cart=new HashMap<>();
        cart.put("Pid",pid);
        cart.put("time",currenttime);
        cart.put("Pname",productName.getText().toString());
        cart.put("description",productDescription.getText().toString());
        cart.put("price",price);
        cart.put("quantity",numberButton.getNumber());
        databaseReference.child("user view").child(Prevalent.currentOnlineUser.getPhonenumber())
                .child("Products").child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    databaseReference.child("Admin view").child(Prevalent.currentOnlineUser.getPhonenumber())
                            .child("Products").child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ProductDetailsActivity.this,"Added to Cart Successfully!",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(ProductDetailsActivity.this,HomeAcitivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });


    }
}