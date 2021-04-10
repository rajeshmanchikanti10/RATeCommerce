package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextDirectionHeuristic;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ratecommerce.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminManageAcitivity extends AppCompatActivity {
    private EditText pname,price,description;
    private Button modifybtn,deletebtn;
    private ImageView productImage;
    private String pid="";
    private DatabaseReference productref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_acitivity);
        pname=findViewById(R.id.admin_manage_productname);
        price=findViewById(R.id.admin_manage_productprice);
        description=findViewById(R.id.adminmanageproduct_description);
        deletebtn=findViewById(R.id.admin_productdeletebtn);
        modifybtn=findViewById(R.id.admin_modify_btn);
        productImage=findViewById(R.id.adminmanage_product_image);
        pid=getIntent()
                .getExtras().get("pid").toString();
        productref= FirebaseDatabase.getInstance().getReference().child("Products");
        displayspecificproduct();
        modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applychanges();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteproduct();
            }
        });

    }
    private void deleteproduct(){
        CharSequence options[]=new CharSequence[]
                {
                        "Confirm","Cancel"
                };
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminManageAcitivity.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0)
                {
                    productref.child(pid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AdminManageAcitivity.this, "Deleted product successfuly!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AdminManageAcitivity.this,AdminconfigureActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
        builder.show();

    }
    private void applychanges(){
        String txtname=pname.getText().toString();
        String txtprice=price.getText().toString();
        String txtdes=description.getText().toString();
        if(TextUtils.isEmpty(txtname))
            pname.setError("Please enter modified name to apply changes!");
        if(TextUtils.isEmpty(txtprice))
            price.setError("Please enter modified price to apply changes!");
        if(TextUtils.isEmpty(txtdes))
            description.setError("Please enter modified description to apply changes!");
        else
        {
            HashMap<String,Object> productmap=new HashMap<String,Object>();
            productmap.put("pid",pid);
            productmap.put("description",txtdes);
            productmap.put("price",txtprice);
            productmap.put("pname",txtname);
            productref.child(pid).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(AdminManageAcitivity.this,"chages updated successfully!",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AdminManageAcitivity.this,AdminconfigureActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    private void displayspecificproduct(){
        productref.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Products product=snapshot.getValue(Products.class);
                    pname.setText(product.getPname());
                    description.setText(product.getDescription());
                    price.setText(product.getPrice()+"$");
                    Picasso.get().load(product.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}