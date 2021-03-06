package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratecommerce.ViewHolder.CartViewHolder;
import com.example.ratecommerce.ViewHolder.ProductViewHolder;
import com.example.ratecommerce.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class cartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button proceedtobuybtn;
    private TextView totalamounttxt;
    private String totalprice;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        totalamounttxt=findViewById(R.id.cart_price_text);
        bundle=getIntent().getExtras();
        totalprice= getIntent().getExtras().get("carttotalprice").toString();
        //Toast.makeText(cartActivity.this,""+totalprice,Toast.LENGTH_LONG).show();
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        proceedtobuybtn=findViewById(R.id.cart_next_btn);
        proceedtobuybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cartActivity.this,ConfirmorderActivity.class);
                intent.putExtra("Total Price",totalprice);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        //totalamounttxt.setText("Total Amount "+totalprice+"$");
        totalamounttxt.setText("Total Amount "+totalprice+"$");
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =new FirebaseRecyclerOptions.Builder<Cart>().setQuery(databaseReference.child("user view").
                child(Prevalent.currentOnlineUser.getPhonenumber()).child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
            holder.TextProductQuantity.setText("Quantity= "+model.getQuantity());
            holder.TextProductPrice.setText("Price "+model.getPrice()+"$");
            holder.TextproductName.setText(model.getPname());
            //totalprice+=Integer.parseInt(model.getPrice())*Integer.parseInt(model.getQuantity());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence options[]=new CharSequence[]
                            {
                                    "Edit","Remove"
                            };
                    AlertDialog.Builder builder=new AlertDialog.Builder(cartActivity.this);
                    builder.setTitle("Cart Options:");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0)
                            {
                                Intent intent=new Intent(cartActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                            else
                            {
                                databaseReference.child("user view").child(Prevalent.currentOnlineUser.getPhonenumber()).child("Products").child(model.getPid()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(cartActivity.this,"Removed product successfully!",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(cartActivity.this,HomeAcitivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    builder.show();
                }
            });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}