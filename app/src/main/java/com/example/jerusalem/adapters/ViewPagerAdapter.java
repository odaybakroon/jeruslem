package com.example.jerusalem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.jerusalem.R;
import com.example.jerusalem.model.Model;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    List<Model> list ;
    Context context;
    public ViewPagerAdapter(Context context , List<Model> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CoordinatorLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item ,container,false);

        TouchImageView imageView = view.findViewById(R.id.imageView2);

        Picasso.get().load(list.get(position).getImageview()).into(imageView);

        container.addView(view);
        return view;
    }
}
