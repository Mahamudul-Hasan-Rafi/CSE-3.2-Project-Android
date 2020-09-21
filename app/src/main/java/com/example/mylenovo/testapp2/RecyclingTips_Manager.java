package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclingTips_Manager extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    NavigationView nav;
    private  MyAdapter myAdapter;
    private List<Upload> uploadList;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private ProgressBar progressBar;
    EditText img_name, req_products;
    ImageView img;
    Button save_button, choose_button;
    private Uri imageUri;
    Dialog dialog;

    public static final int IMAGE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling_tips__manager);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progress_circular);

        floatingActionButton = findViewById(R.id.add_product);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecyclingTips_Manager.this);
                builder.setMessage("Do you want to add a new product?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showAddDialog(RecyclingTips_Manager.this);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        uploadList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");
        storageReference = FirebaseStorage.getInstance().getReference("Upload");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploadList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload upload = dataSnapshot1.getValue(Upload.class);
                    uploadList.add(upload);
                }
                myAdapter = new MyAdapter(RecyclingTips_Manager.this, uploadList);
                recyclerView.setAdapter(myAdapter);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        toolbar = findViewById(R.id.tlbar2);

        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentLight));
        }

        toolbar.setTitle("Recycling Tips Manager");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#000000"));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        nav = findViewById(R.id.nav1);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Admin_Home: {
                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    }

                    case R.id.WasteManager: {
                        Intent i = new Intent(getApplicationContext(), WasteProduct_Manager.class);
                        startActivity(i);
                        finish();

                        break;
                    }

                    case R.id.StaffManager: {
                        Intent i = new Intent(getApplicationContext(), Staff_Manager.class);
                        startActivity(i);
                        finish();

                        break;
                    }

                    case R.id.RecyclingTips: {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }

                        break;
                    }
                }
                return true;
            }
        });
    }

    public void showAddDialog(Activity activity){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_recycling_tips);

        int width=(int)(activity.getResources().getDisplayMetrics().widthPixels*1);
        int height=(int)(activity.getResources().getDisplayMetrics().widthPixels*1.5);

        dialog.getWindow().setLayout(width, height);

        dialog.show();

        img_name = dialog.findViewById(R.id.img_name);
        req_products = dialog.findViewById(R.id.req_products);
        img = dialog.findViewById(R.id.img);
        save_button = dialog.findViewById(R.id.save_button);
        choose_button = dialog.findViewById(R.id.choose_button);

        choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void openImageChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            Picasso.with(dialog.getContext()).load(imageUri).into(img);
        }
    }

    public String getFileExtension(Uri ImageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(ImageUri));
    }

    public void saveData()
    {
        final String img_nm = img_name.getText().toString().trim();
        final String rq_prdcts = req_products.getText().toString().trim();

        if(img_nm.isEmpty()){
            img_name.setError("Enter the product name");
            img_name.requestFocus();

            return;
        }

        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();

                        Upload upload = new Upload(img_nm, downloadUrl.toString(), rq_prdcts);
                        databaseReference.child(img_nm).setValue(upload);
                        Toast.makeText(RecyclingTips_Manager.this, "Uploaded Successfuly!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(i);
        finish();
    }
}
