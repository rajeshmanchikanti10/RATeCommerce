package com.example.ratecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ratecommerce.ViewHolder.ProductViewHolder;
import com.example.ratecommerce.model.Products;
import com.example.ratecommerce.prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlinx.coroutines.channels.ReceiveChannel;

public class HomeAcitivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        navigationView=findViewById(R.id.nav_view);


        View headerView=navigationView.getHeaderView(0);
        TextView userNameTextView =headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);
        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        recyclerView=findViewById(R.id.recycler_menu);

       // navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_acitivity, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id=item.getItemId();
                if(id==R.id.nav_cart)
                {

                }
                else if(id==R.id.nav_categories)
                {

                }
                else if(id==R.id.nav_orders)
                {

                }
                else if(id==R.id.nav_logout)
                {
                    Intent intent=new Intent(HomeAcitivity.this,MainActivity.class);
                    //startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                    finish();
                    Toast.makeText(HomeAcitivity.this,"Hello user we are going to mainpage!",Toast.LENGTH_LONG).show();
                }
                else if(id==R.id.nav_settings)
                {

                }
                return true;
            }
        });
        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(databaseReference,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                holder.textProductName.setText(model.getPname());
                holder.textProductDescription.setText(model.getDescription());
                holder.textProductPrice.setText("Price"+model.getPrice()+"$");
                Picasso.get().load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}