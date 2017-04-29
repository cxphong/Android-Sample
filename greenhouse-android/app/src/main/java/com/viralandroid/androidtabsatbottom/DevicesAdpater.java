package com.viralandroid.androidtabsatbottom;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dinhtho on 06/04/2017.
 */


class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> implements View.OnClickListener {

    private static final String TAG = "DevicesAdapter";

    private List<Device> devicesList;
    private MyCallback myCallback;
    private LinearLayout layout_item;

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: "+view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MyViewHolder";
        public TextView name, mac;
        public View view1;

        public MyViewHolder(View view) {
            super(view);
            this.view1 = view;
            Log.i(TAG, "MyViewHolder: "+view1);
            name = (TextView) view.findViewById(R.id.tv_device_name);
            layout_item=(LinearLayout)view.findViewById(R.id.layout_item);
        }
    }

    public DevicesAdapter(List<Device> devicesList,MyCallback myCallback) {
        this.devicesList = devicesList;
        this.myCallback=myCallback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_row, parent, false);
        int height = parent.getMeasuredHeight() / 5;
        Log.i(TAG, "onCreateViewHolder: "+parent.getMeasuredHeight());
       // itemView.setMinimumHeight(height);

        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,height));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Device device = devicesList.get(position);
        holder.name.setText(device.getDevice().getName());
        holder.view1.setTag(position);

        layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCallback.callbackCall(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }



}
 interface MyCallback {
    void callbackCall(int position);
}
interface ScrollListener{void scrollendlistenter(); }