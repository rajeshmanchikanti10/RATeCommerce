package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratecommerce.prevalent.Prevalent;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
  private   CircleImageView imageView;
private TextView updateprofilebtn;
private EditText name,phonenumber,address;
private Button update,close;
private Uri imageUri;
private  String myUrl="",checker="";
private StorageReference storageReference;
private StorageTask uploadtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        updateprofilebtn=findViewById(R.id.settings_updateprofile_btn);
        name=findViewById(R.id.settings_name);
        phonenumber=findViewById(R.id.settings_phonenumber);
        address=findViewById(R.id.settings_address);
        update=findViewById(R.id.settings_updatebtn);
        close=findViewById(R.id.settings_close);
        imageView=findViewById(R.id.settings_profile_image);
        storageReference= FirebaseStorage.getInstance().getReference();
        Displayuserinfo();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,HomeAcitivity.class);
                startActivity(intent);
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked"))
                {
                    updateincludingprofileandinfo();
                }
                else
                {
                    updateonlyuserinfo();
                }
            }
        });
        updateprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();
            imageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(SettingsActivity.this,"Error try Again!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));


        }
    }


    private void updateincludingprofileandinfo() {
        if(TextUtils.isEmpty(name.getText().toString()))
        {
            name.setError("Name is Mandatory");
        }
        if(TextUtils.isEmpty(phonenumber.getText().toString()))
            phonenumber.setError("Phone number is Mandatory");
        if(TextUtils.isEmpty(address.getText().toString()))
            address.getText().toString();
        else if(checker.equals("clicked"))
        {
            uploadimage();
        }
    }
    private  void uploadimage()
    {
        ProgressDialog loader=new ProgressDialog(this);
        loader.setTitle("update profile");
        loader.setMessage("Please wait,while updating profile");
        loader.setCanceledOnTouchOutside(false);
        loader.show();
        if(imageUri!=null)
        {
            StorageReference fileref=storageReference.child(Prevalent.currentOnlineUser.getPhonenumber()+"jpeg");
            uploadtask=fileref.putFile(imageUri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                        throw task.getException();
                    return  fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadeduri=task.getResult();
                        myUrl=downloadeduri.toString();
                        DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child("users");
                        HashMap<String ,Object> usermap=new HashMap<String,Object>();
                        usermap.put("Name",name.getText().toString());
                        usermap.put("Phonenumber",phonenumber.getText().toString());
                        usermap.put("Address",address.getText().toString());
                        usermap.put("image",myUrl);
                        userref.child(Prevalent.currentOnlineUser.getPhonenumber()).updateChildren(usermap);
                        loader.dismiss();
                        startActivity(new Intent(SettingsActivity.this,HomeAcitivity.class));
                        Toast.makeText(SettingsActivity.this,"Profile info updated successfully",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SettingsActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                        startActivity(new Intent(SettingsActivity.this,HomeAcitivity.class));
                        finish();
                    }
                }

            });
        }
        else
        {
            Toast.makeText(SettingsActivity.this,"Image is not selected!",Toast.LENGTH_SHORT).show();
        }

    }
    private  void updateonlyuserinfo(){
        DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child("users");
        HashMap<String ,Object> usermap=new HashMap<String,Object>();
        usermap.put("Name",name.getText().toString());
        usermap.put("Phonenumber",phonenumber.getText().toString());
        usermap.put("Address",address.getText().toString());
        userref.child(Prevalent.currentOnlineUser.getPhonenumber()).updateChildren(usermap);
        startActivity(new Intent(SettingsActivity.this,HomeAcitivity.class));
        Toast.makeText(SettingsActivity.this,"Profile info updated successfully",Toast.LENGTH_LONG).show();
        finish();

    }




    private void Displayuserinfo(){
        DatabaseReference userref= FirebaseDatabase.getInstance().getReference().child("users").child(Prevalent.currentOnlineUser.getPhonenumber());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("image").exists())
                {
                    String image=snapshot.child("image").getValue().toString();
                    String txtname=snapshot.child("Name").getValue().toString();
                    String txtphonenumber=snapshot.child("Phonenumber").getValue().toString();
                    String txtAddress=snapshot.child("Address").getValue().toString();

                    name.setText(txtname);
                    phonenumber.setText(txtphonenumber);
                    address.setText(txtAddress);
                    Picasso.get().load(image).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}