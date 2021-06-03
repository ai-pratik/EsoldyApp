package com.example.esoldy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esoldy.Model.Products;
import com.example.esoldy.Model.Users;
import com.example.esoldy.Prevalent.Prevalent;
import com.example.esoldy.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
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

public class NavgiationhomeActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUsers;
    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navgiationhome);


        Toolbar toolbar = findViewById(R.id.toolbar);
        firebaseAuth= FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        ProductRef=FirebaseDatabase.getInstance().getReference().child("Products");



        toolbar.setTitle("Home");


        setSupportActionBar(toolbar);
        //for disabling tiitle
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(NavgiationhomeActivity.this,Cart_activity.class);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        //user authentication system is changed
        TextView userNameTextView=headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);
        TextView useremailTextView=headerView.findViewById(R.id.user_email);

       // DatabaseReference Username1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.OnlineUser.getName());

        userNameTextView.setText(Prevalent.OnlineUser.getName());
       useremailTextView.setText(Prevalent.OnlineUser.getPhone());
        Picasso.get().load(Prevalent.OnlineUser.getImage()).placeholder(R.drawable.user).into(profileImageView);

        recyclerView=findViewById(R.id.recyclermenu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);








       navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
      // NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navgiationhome, menu);
        return true;
    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductRef,Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {
                        holder.txtProductname.setText(model.getProduct_Name());
                        holder.txtProductdescription.setText(model.getDescription());
                        holder.txtProductprice.setText("Price = "+model.getPrice_INR() + "₹");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(NavgiationhomeActivity.this,productDetailsActivity.class);
                                intent.putExtra("PID",model.getPID());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

            recyclerView.setAdapter(adapter);
            adapter.startListening();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.main_search_icon)
        {//search icon code
            return true;
        }
        else if (id==R.id.main_noification_icon)
        {//notification
            return true;
        }
        else if(id==R.id.cart)
        {//cart
            Intent intent =new Intent(NavgiationhomeActivity.this,Cart_activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //final NavigationView navigationView = findViewById(R.id.nav_view);
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_cart)
        {
            Intent intent =new Intent(NavgiationhomeActivity.this,Cart_activity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_orders)
        {

        }
        else if(id==R.id.nav_categories)
        {

        }
        else if(id==R.id.nav_seller)
        {
            Intent intent=new Intent(NavgiationhomeActivity.this,adminpanelactivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_AddEwasteCheckout)
        {

        }
        else if(id==R.id.nav_FactsAndBlogs)
        {
            Intent intent=new Intent(NavgiationhomeActivity.this,factsandblogs.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_settings)
        {
            Intent intent=new Intent(NavgiationhomeActivity.this,testsettings.class);
            startActivity(intent);


        }
        else if(id==R.id.nav_help)
        {
            Intent intent=new Intent(NavgiationhomeActivity.this,uploadblogs.class);
            startActivity(intent);

        }
        else if(id==R.id.signout)
        {
            deleteAppData();

        }
        else if(id==R.id.Moresoon)
        {

        }
        else if(id==R.id.bug_report)
        {

        }

        DrawerLayout drawer=findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;





    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    /*@Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }*/


    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUsername() {

        String id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference username = databaseUsers.child(id).child("fullname");

        username.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                String name = mutableData.getValue(String.class);
                System.out.println(name);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }

        });




    }
}