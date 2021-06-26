package com.example.jerusalem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HistoryActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView , text1 , text2 , text3 , text4,text5,text6,text7,text8 ;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    Spinner spinner;
    Spinner spinner2;
    StorageReference storageReference;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        firestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("تاريخ القدس عبر العصور");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

        imageView = findViewById(R.id.image_card1);
        textView = findViewById(R.id.text_card2);
        text1 = findViewById(R.id.text_card3);
        text2 = findViewById(R.id.text_card4);
        text3 = findViewById(R.id.text_card5);
        text4 = findViewById(R.id.text_card6);
        text5 = findViewById(R.id.text_card7);
        text6 = findViewById(R.id.text_card8);
        text7 = findViewById(R.id.text_card9);
        text8 = findViewById(R.id.text_card10);




        int position =  getIntent().getIntExtra("id" , 1);


   RetrieveData1();

    }

    private void RetrieveData1(){
        firestore.collection("Model").document("FWERB6ihSwMQiD2C8V0d")
               .collection("History").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        Log.d("ddd", snapshot.getId() + " => " + snapshot.getData());
                        String img =  snapshot.getString("imageview");

                        textView.setText(snapshot.getString("title"));
                        text1.setText(snapshot.getString("title1"));
                        text2.setText(snapshot.getString("title2"));
                        text3.setText(snapshot.getString("title3"));
                        text4.setText(snapshot.getString("title4"));
                        text5.setText(snapshot.getString("title5"));
                        text6.setText(snapshot.getString("title6"));
                        text7.setText(snapshot.getString("title7"));
                        Picasso.get().load(img).into(imageView);


                    }

                } else {
                    Log.w("ttt", "Error getting documents.", task.getException());
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