package com.example.jerusalem;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class MapActivity extends AppCompatActivity {
    StorageReference storageReference;
    FirebaseStorage storage;
    TouchImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        imageView = findViewById(R.id.mapImageView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("خارطة القدس");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

        RetrieveImage();

    }

    private  void RetrieveImage(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://jerusalem-e8f4a.appspot.com/al_Aqsa_Tahdeed_Internet450.jpg");

        Task<Uri> task  =  storageReference.getDownloadUrl();

        task.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Picasso.get().load(task.getResult().toString()).into(imageView);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext() , e.getMessage()+"" , Toast.LENGTH_LONG).show();
            }
        });
    }
}