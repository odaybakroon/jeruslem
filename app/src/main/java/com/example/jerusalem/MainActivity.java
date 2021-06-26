package com.example.jerusalem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.example.jerusalem.adapters.ModelAdapter;
import com.example.jerusalem.model.Model;
import com.example.jerusalem.notifications.FirebasePushNotificationsClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.type.Color;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ModelAdapter adapter ;
    ArrayList<Model> modelArrayList ;
    FirebaseFirestore firestore;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("القدس في قلوبنا");

        FirebaseMessaging.getInstance().subscribeToTopic("Jerusalem")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()){
                            msg = "Failed";
                        }

                    }
                });


        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        switchCompat = findViewById(R.id.switchCompat);
        sharedPreferences = getSharedPreferences("night" , 0);
        Boolean value = sharedPreferences.getBoolean("night_mode" , true);
        if (value){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);
                    recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode" , true);
                    editor.commit();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked(false);
                    recyclerView.setBackgroundColor(getResources().getColor(R.color.black));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode" , false);
                    editor.commit();
                }
            }
        });


        navigationView = findViewById(R.id.nav_view);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        modelArrayList = new ArrayList<>();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_news:
                        Intent browserIntent = new Intent(getApplicationContext()  , NewsActivity.class);
                        startActivity(browserIntent);
                        break;
                    case R.id.nav_history:
                        Intent historyIntent = new Intent(getApplicationContext()  , HistoryActivity.class);
                        startActivity(historyIntent);
                        break;
                    case R.id.nav_Dome:
                        Intent DomeIntent = new Intent(getApplicationContext()  , DomeInfoActivity.class);
                        startActivity(DomeIntent);
                        break;
                    case R.id.nav_pictures:
                        Intent picturesIntent = new Intent(getApplicationContext()  , PicturesActivity.class);
                        startActivity(picturesIntent);

                        break;
                    case R.id.nav_map:
                        Intent mapIntent = new Intent(getApplicationContext()  , MapActivity.class);
                        startActivity(mapIntent);
                        break;
                    case R.id.nav_boraq:
                        Intent boraqIntent = new Intent(getApplicationContext()  , BoraqActivity.class);
                        startActivity(boraqIntent);
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });
        RetrieveData();

    }


    private void RetrieveData(){
        i++;
        firestore.collection("Model").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        Log.d("ttt", snapshot.getId() + " => " + snapshot.getData());
                        Model model =  new Model(snapshot.getString("imageview") , snapshot.getString("title"));
                        if (i == 1){
                            modelArrayList.add(model);
                        }
                    }
                    adapter = new ModelAdapter(modelArrayList , getApplicationContext());
                    recyclerView.setAdapter(adapter);


                } else {
                    Log.w("ttt", "Error getting documents.", task.getException());
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt" , "failure");
                Toast.makeText(getApplicationContext() , e.getMessage()+"" , Toast.LENGTH_LONG).show();
            }
        });

    }
}