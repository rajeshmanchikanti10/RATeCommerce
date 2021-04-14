package com.example.ratecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ratecommerce.ViewHolder.ProductViewHolder;
import com.example.ratecommerce.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class searchActivity extends AppCompatActivity {
private Button btn;
private EditText inputtext;
private RecyclerView list;
private String stringinputtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        inputtext=findViewById(R.id.search_product_name);
        btn=findViewById(R.id.search_btn);
        list=findViewById(R.id.search_recycler_list);
        list.setLayoutManager(new LinearLayoutManager(this));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringinputtext=inputtext.getText().toString();
                if(inputtext.equals(null))
                    inputtext.setError("please provide product name to serach");
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(databaseReference.orderByChild("description").startAt(stringinputtext),Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                holder.textProductName.setText(model.getPname());
                holder.textProductDescription.setText(model.getDescription());
                holder.textProductPrice.setText("Price "+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent intent = new Intent(searchActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getTime());
                            //Toast.makeText(searchActivity.this, model.getTime(), Toast.LENGTH_LONG).show();
                            startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        list.setAdapter(adapter);
        adapter.startListening();
    }
}