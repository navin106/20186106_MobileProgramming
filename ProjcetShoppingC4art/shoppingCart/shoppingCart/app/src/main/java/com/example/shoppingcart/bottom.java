package com.example.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.Model.products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.example.shoppingcart.viewHolder.productViewHolder;

//////////////////////////////
///////////////////////////////////////////////////////
//implements BottomNavigationView.OnNavigationItemSelectedListener


public class bottom extends AppCompatActivity  {
    /////////////////////////////
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ///////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
     ///////////////////////////////////////////

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("ProductCollection/");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    //////////////////////////////////////////////////////////////




    }




    ////////////////////////////////////////////
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, productViewHolder> adapter = new FirebaseRecyclerAdapter<Products, productViewHolder>(options) {
            @NonNull
            @Override
            public  productViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ViewGroup parent = viewGroup;
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                productViewHolder holder = new  productViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull  productViewHolder holder, final int position, @NonNull final Products model) {
                holder.txtProductName.setText(model.getName());
                holder.txtProductPrice.setText("price : $"+model.getPrice().toString());
                holder.txtProductID.setText("product id : "+model.getProductId().toString());
                Picasso.with(bottom.this).load("http://msitmp.herokuapp.com"+model.getProductPicUrl()).into(holder.productImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(bottom.this, productDetails.class);
                        intent.putExtra("pid", position + "");
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onBackPressed() {
        //no code
    }

}