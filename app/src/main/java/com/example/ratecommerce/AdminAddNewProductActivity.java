package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.google.android.gms.measurement.AppMeasurementJobService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String category,ProductName,ProductDescription,ProductPrice,saveCurrentDate,saveCurrentTime;
    private Button AdddNewproductButton;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDescription,InputProductPrice;
    int GalleryPick=1;
    private Uri ImageUri;
    private  String productRandomKey,downloadImageUrl;
    private StorageReference ProudctImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;
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
        loadingBar=new ProgressDialog(this);
        ProductRef=FirebaseDatabase.getInstance().getReference().child("Products");
        ProudctImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");
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

                //validate inputs
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
        {
            StoreProductInformation();
            loadingBar.setTitle("Adding New Product");
            loadingBar.setMessage("Please wait ,while adding the new product.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }

    }
    private  void StoreProductInformation()
    {
        /*Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentDate.format(calendar.getTime());*/
        DateFormat dateFormat = new SimpleDateFormat("MM dd,yyyy HH:mm:ss a");

        Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal.getTime()));
        productRandomKey=""+dateFormat.format(cal.getTime());

        //store image uri in firebase database;

        StorageReference filePath=ProudctImageRef.child(ImageUri.getLastPathSegment()+productRandomKey+".jpeg");
        final UploadTask uploadTask=filePath.putFile(ImageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this,"Product Image uploaded Successfully",Toast.LENGTH_LONG).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw  task.getException();

                        }
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadImageUrl=uri.toString();
                            }
                        });
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AdminAddNewProductActivity.this,"Got Image URL successfullySuccessfully",Toast.LENGTH_LONG).show();
                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
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
            //Toast.makeText(AdminAddNewProductActivity.this,ImageUri,Toast.LENGTH_LONG).show();
            InputProductImage.setImageURI(ImageUri);
        }
    }
    private  void saveProductInfoToDatabase()
    {
        HashMap<String,Object> productmap=new HashMap<String,Object>();
        productmap.put("pid",productRandomKey);
        productmap.put("time",productRandomKey);
        productmap.put("description",ProductDescription);
        productmap.put("image",downloadImageUrl);
        productmap.put("category",category);
        productmap.put("price",ProductPrice);
        productmap.put("pname",ProductName);
        ProductRef.child(productRandomKey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    loadingBar.dismiss();
                    Intent intent=new Intent(AdminAddNewProductActivity.this,AdminconfigureActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AdminAddNewProductActivity.this, "Product added successfully!", Toast.LENGTH_LONG).show();
                }
                else {
                    loadingBar.dismiss();
                    String message=task.getException().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Error"+message,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}