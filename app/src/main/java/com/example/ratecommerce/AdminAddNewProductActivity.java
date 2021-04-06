package com.example.ratecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String category,ProductName,ProductDescription,ProductPrice;
    private Button AdddNewproductButton;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    int GalleryPick=1;
    private Uri ImageUri;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        AdddNewproductButton=findViewById(R.id.Add_newproductbtn);
        InputProductImage=findViewById(R.id.select_product_image);
        InputProductName=findViewById(R.id.product_name);
        InputProductDescription=findViewById(R.id.product_description);
        InputProductPrice=findViewById(R.id.product_price);
        category=getIntent().getExtras().get("category").toString();
        Toast.makeText(AdminAddNewProductActivity.this,category,Toast.LENGTH_LONG).show();
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }


        });
        AdddNewproductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }
    private void ValidateProductData(){
        ProductName=InputProductName.getText().toString();
        ProductDescription=InputProductDescription.getText().toString();
        ProductPrice=InputProductPrice.getText().toString();
        if(ImageUri==null)
            Toast.makeText(AdminAddNewProductActivity.this,"Product Image is required!",Toast.LENGTH_LONG).show();
        if(TextUtils.isEmpty(ProductName))
            InputProductName.setError("Product name is manadatory for Adding product!");
        if(TextUtils.isEmpty(ProductDescription))
            InputProductDescription.setError("Product Description is manadatory for Adding Product!");
        if(TextUtils.isEmpty(ProductPrice))
            InputProductPrice.setError("Product price is compulsory for Adding new product!");
        else
             i=0;

    }
    private void OpenGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }
}