package com.example.jerusalem;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.jerusalem.adapters.ViewPagerAdapter;
import com.example.jerusalem.model.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class PicturesActivity extends AppCompatActivity {
    ViewPager viewPager;
    FirebaseFirestore firestore;
    List<Model> list;
    ViewPagerAdapter adapter;
    android.app.AlertDialog dialog;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("أجمل الصور داخل القدس");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

        firestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        dialog = new SpotsDialog.Builder().setContext(this).build();
        list = new ArrayList<>();
        viewPager = findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getApplicationContext(), list);
        viewPager.setAdapter(adapter);
        RetrieveData();


    }
    private void RetrieveData(){
        dialog.show();
        CollectionReference coll =  firestore.collection("Model").document("jd0w1S0Xhlobeysw45MY").collection("Photos");
        Task<QuerySnapshot> t =  coll.get();
        t.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot docSnap:queryDocumentSnapshots.getDocuments()){
                        Model a =  docSnap.toObject(Model.class);
                        list.add(a);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                }
            }
        });

        t.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext() , e.getMessage()+"" , Toast.LENGTH_LONG).show();
            }
        });

    }

}