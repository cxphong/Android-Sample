package com.example.dinhtho.newapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

/**
 * Created by dinhtho on 30/04/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.Holder> {

    private ArrayList<Image> imageList;
    private Activity activity;
    private MyClickListener myClickListener;

    public MyRecyclerViewAdapter(Activity activity,MyClickListener myClickListener,ArrayList<Image> imageList) {
        this.imageList = imageList;
        this.activity=activity;
        this.myClickListener=myClickListener;
    }


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Image image=imageList.get(position);
//        Glide.with(activity).load(image.getPathImage()).centerCrop().into(holder.imageView);

        Glide.with(activity).load(image.getPathImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imageView.setImageDrawable(circularBitmapDrawable);
            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.onItemClick(position);
            }
        });


    }

    public void deleteItem(int index) {
        imageList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position);
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}

