package com.example.ratecommerce;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import io.paperdb.Paper;
import kotlinx.coroutines.channels.ReceiveChannel;

public class HomeAcitivity extends AppCompatActivity  {
    CircleImageView profileimage;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private int totalprice=0;
    private  String type="";
    DatabaseReference cartref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        navigationView=findViewById(R.id.nav_view);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        type=getIntent().getExtras().get("Admin").toString();

        View headerView=navigationView.getHeaderView(0);
        TextView userNameTextView =headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);

        if(!type.equals("Admin")) {
            Toast.makeText(HomeAcitivity.this,Prevalent.currentOnlineUser.getPhonenumber(),Toast.LENGTH_SHORT).show();
            cartref=FirebaseDatabase.getInstance().getReference().child("Cart List").child("user view").child(Prevalent.currentOnlineUser.getPhonenumber()).child("Products");
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            //(Prevalent.currentOnlineUser.getImage()!=null)
           Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("Admin")) {
                    totalprice = 0;
                    cartref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Toast.makeText(HomeAcitivity.this,"count: "+snapshot.getChildrenCount(),Toast.LENGTH_LONG).show();
                            for (DataSnapshot childsnap : snapshot.getChildren()) {
                                Cart cartitem = childsnap.getValue(Cart.class);
                                totalprice += Integer.valueOf(cartitem.getQuantity()) * Integer.valueOf(cartitem.getPrice());
                            }
                            //Toast.makeText(HomeAcitivity.this,""+totalprice,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(HomeAcitivity.this, cartActivity.class);
                            intent.putExtra("carttotalprice", totalprice);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
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

                    if(!type.equals("Admin")) {
                        totalprice = 0;
                        cartref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //Toast.makeText(HomeAcitivity.this,"count: "+snapshot.getChildrenCount(),Toast.LENGTH_LONG).show();
                                for (DataSnapshot childsnap : snapshot.getChildren()) {
                                    Cart cartitem = childsnap.getValue(Cart.class);
                                    totalprice += Integer.valueOf(cartitem.getQuantity()) * Integer.valueOf(cartitem.getPrice());
                                }
                                //Toast.makeText(HomeAcitivity.this,""+totalprice,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(HomeAcitivity.this, cartActivity.class);
                                intent.putExtra("carttotalprice", "" + totalprice);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
                else if(id==R.id.nav_categories)
                {

                }

                else if(id==R.id.nav_logout)
                {
                    if(!type.equals("Admin")) {
                        Paper.book().destroy();
                        Intent intent = new Intent(HomeAcitivity.this, MainActivity.class);
                        //startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(HomeAcitivity.this, "Hello user we are going to mainpage!", Toast.LENGTH_LONG).show();
                    }
                }
                else if(id==R.id.nav_settings)
                {
                    Intent intent=new Intent(HomeAcitivity.this,SettingsActivity.class);
                    startActivity(intent);
                    finish();
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
                holder.textProductPrice.setText("Price"+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type!=null && type.equals("Admin"))
                        {
                            Intent intent = new Intent(HomeAcitivity.this, AdminManageAcitivity.class);
                            intent.putExtra("pid", model.getTime());
                            Toast.makeText(HomeAcitivity.this, model.getTime(), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(HomeAcitivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getTime());
                            Toast.makeText(HomeAcitivity.this, model.getTime(), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}