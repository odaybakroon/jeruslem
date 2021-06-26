package com.example.jerusalem.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jerusalem.BoraqActivity;
import com.example.jerusalem.DomeInfoActivity;
import com.example.jerusalem.HistoryActivity;
import com.example.jerusalem.MapActivity;
import com.example.jerusalem.NewsActivity;
import com.example.jerusalem.PicturesActivity;
import com.example.jerusalem.R;
import com.example.jerusalem.model.Model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {

   List<Model> modelList ;
   Context context;
    public ModelAdapter(List<Model> modelList , Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item , parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = modelList.get(position);
        Picasso.get().load(model.getImageview()).into(holder.imageView);
        holder.titel.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){

                    case 0:
                        Intent browserIntent = new Intent(context  , NewsActivity.class);
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(browserIntent);
                        break;
                    case 1:
                         Intent historyintent = new Intent(context , HistoryActivity.class);
                         historyintent.putExtra("id" , position);
                         historyintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         context.startActivity(historyintent);

                        // Activity activity = (Activity) context;
                       //  activity.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                        break;
                    case 2:
                        Intent infointent = new Intent(context , DomeInfoActivity.class);
                        infointent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(infointent);

                        break;
                    case 3:
                        Intent intent = new Intent(context , PicturesActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    case 4:
                        Intent mapintent = new Intent(context , MapActivity.class);
                        mapintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(mapintent);

                        break;
                    case 5:

                        Intent boraqintent = new Intent(context , BoraqActivity.class);
                        boraqintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(boraqintent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_card1);
            titel = itemView.findViewById(R.id.show);

        }


    }
}
