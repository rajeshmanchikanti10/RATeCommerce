package com.example.ratecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminconfigureActivity extends AppCompatActivity {
    private ImageView tshirts,womentshirts,sportstshirts,sweathers;
    private ImageView hats,glasses,purses_bags,shoes;
    private  ImageView  phones,laptops,watches,books;
    private Button LogoutBtn,ModifyproductsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminconfigure);
        tshirts=findViewById(R.id.t_shirts);
        womentshirts=findViewById(R.id.female_dresses);
        sportstshirts=findViewById(R.id.sports);
        sweathers=findViewById(R.id.sweather);
        hats=findViewById(R.id.hats);
        glasses=findViewById(R.id.glasses);
        purses_bags=findViewById(R.id.purses_bags);
        shoes=findViewById(R.id.shoes);
        phones=findViewById(R.id.mobiles);
        laptops=findViewById(R.id.laptops);
        watches=findViewById(R.id.watches);
        books=findViewById(R.id.books);
        LogoutBtn=findViewById(R.id.admin_logout);
        ModifyproductsBtn=findViewById(R.id.Manage_product_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        ModifyproductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,HomeAcitivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });
        womentshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","womentshirts");
                startActivity(intent);
            }
        });
        sportstshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category"," sportstshirts");
                startActivity(intent);
            }
        });


        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sweathers");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","hats");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category"," glasses");
                startActivity(intent);
            }
        });
        purses_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","purses_bags");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });

        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category"," phones");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","laptops");
                startActivity(intent);
            }
        });
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","books");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminconfigureActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });


    }

}