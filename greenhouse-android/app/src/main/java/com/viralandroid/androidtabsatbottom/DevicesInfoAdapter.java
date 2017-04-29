package com.viralandroid.androidtabsatbottom;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.viralandroid.androidtabsatbottom.MainActivity.activity;

/**
 * Created by dinhtho on 07/04/2017.
 */






public class DevicesInfoAdapter extends RecyclerView.Adapter<DevicesInfoAdapter.Holder> {

    private static final String TAG = "DevicesInfoAdapter";
    private ArrayList<DeviceInfo> mDataset;
    private MyClickListener myClickListener;

    public DevicesInfoAdapter(ArrayList<DeviceInfo> myDataset) {
        mDataset = myDataset;
    }


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_devices_info, parent, false);
        int height = parent.getMeasuredHeight() / 5;
        Log.i(TAG, "onCreateViewHolder: "+parent.getMeasuredHeight());
//        v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,height));
        Holder holder = new Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        DeviceInfo deviceInfo = mDataset.get(position);

        holder.tv_gh.setText(deviceInfo.getGh());
        holder.tv_model.setText(deviceInfo.getModel());
        holder.tv_year.setText(deviceInfo.getYear());
//            holder.tv_pic.setText(deviceInfo.getPic());
        Log.i(TAG, "onBindViewHolder: " + deviceInfo.getPic());

        Glide.with(activity).load(deviceInfo.getPic()).centerCrop().into(holder.imageView_pic);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        //public void onItemClick(int position, View v);
    }

    public class Holder extends RecyclerView.ViewHolder {
        //TextView label;
        TextView tv_gh, tv_model, tv_year;
        ImageView imageView_pic;

        public Holder(View itemView) {
            super(itemView);
            tv_gh = (TextView) itemView.findViewById(R.id.tv_gh);
            tv_model = (TextView) itemView.findViewById(R.id.tv_model);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            imageView_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }
}


//public  class DevicesInfoAdapter extends RecyclerView.Adapter<DevicesInfoAdapter.MyViewHolder> {
//
//    private String LOG_TAG = "MyRecyclerViewAdapter";
//    private ArrayList<DeviceInfo> mDataset;
//    private MyClickListener myClickListener;
//
//    public DevicesInfoAdapter(ArrayList<DeviceInfo> myDataset) {
//        mDataset = myDataset;
//    }
//
//
//    public void setOnItemClickListener(MyClickListener myClickListener) {
//        this.myClickListener = myClickListener;
//    }
//
////    @Override
////    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View itemView = LayoutInflater.from(parent.getContext())
////                .inflate(R.layout.recyclerview_devices_info, parent, false);
////        int height = parent.getMeasuredHeight() / 4;
////
////
////        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,height));
////        DataObjectHolder dataObjectHolder = new DataObjectHolder(itemView);
////        return dataObjectHolder;
////
////
////
////    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recyclerview_devices_info, parent, false);
//        int height = parent.getMeasuredHeight() / 5;
//        // itemView.setMinimumHeight(height);
//
//        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,height));
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        DeviceInfo deviceInfo = mDataset.get(position);
////        holder.tv_gh.setText("1");
////        holder.tv_model.setText("1");
////        holder.tv_year.setText("1");
////        holder.tv_pic.setText("1");
//        holder.tv_gh.setText(deviceInfo.getGh());
//        holder.tv_model.setText(deviceInfo.getModel());
//        holder.tv_year.setText(deviceInfo.getYear());
//        holder.tv_pic.setText(deviceInfo.getPic());
//
//    }
//
//
//    public void deleteItem(int index) {
//        mDataset.remove(index);
//        notifyItemRemoved(index);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataset.size();
//    }
//
//    public interface MyClickListener {
//        //public void onItemClick(int position, View v);
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        //TextView label;
//        TextView tv_gh, tv_model, tv_year, tv_pic;
//
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            //label = (TextView) itemView.findViewById(R.id.textView);
//            tv_gh = (TextView) itemView.findViewById(R.id.tv_gh);
//            tv_model = (TextView) itemView.findViewById(R.id.tv_model);
//            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
//            tv_pic = (TextView) itemView.findViewById(R.id.tv_pic);
//
//
//        }
//
//
//    }
//}

