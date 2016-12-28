package info.androidhive.recyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idevicesinc.sweetblue.BleManager;

import java.util.List;

// Chuyển class này vô bên trong MainActivity.java
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {


    private List<Device> devicesList;




    private static BleManager.DiscoveryListener.DiscoveryEvent e;
    public static BleManager.DiscoveryListener.DiscoveryEvent getE() {
        return e;
    }

    public void setE(BleManager.DiscoveryListener.DiscoveryEvent e) {
        this.e = e;
    }



    private BleManager.DiscoveryListener.DiscoveryEvent selectedDevice(List<BleManager.DiscoveryListener.DiscoveryEvent> eventList,Device device) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).device().getMacAddress() == device.getMac()) {
                return eventList.get(i);

            }
        }
        // Log.i(TAG, "seclectedDevice: "+eventList);
        return null;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, mac;
        public TextView rssi;
        public Button btnConnect;
        public View view1;


        public MyViewHolder(View view) {

            super(view);
            this.view1=view;
            name = (TextView) view.findViewById(R.id.name);
            mac = (TextView) view.findViewById(R.id.mac);
            //date = (TextView) view.findViewById(R.id.year);
            rssi=(TextView ) view.findViewById(R.id.rssi);
            btnConnect=(Button)view.findViewById(R.id.buttonConnect);
            btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //position=(int)view1.getTag();
                    Device device = devicesList.get((int)view1.getTag());
                    e = selectedDevice(MainActivity.getEventList(), device);
                    MainActivity.StopScan();

                    Intent mh2=new Intent(view.getContext(),ManHinhConnect.class);


                    view.getContext().startActivity(mh2);
                    //Toast.makeText(view.getContext(), (Integer)view.getTag() + "", Toast.LENGTH_SHORT).show();


                }
            });







        }
    }









    public DevicesAdapter(List<Device> devicesList) {
        this.devicesList = devicesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_row, parent, false);






        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Device device = devicesList.get(position);
        holder.name.setText(device.getName());
        holder.mac.setText(device.getMac());

        holder.rssi.setText(device.getRssi());
        if(device.isConnect()){
            holder.rssi.setTextColor(Color.BLACK);

        }else {
            holder.rssi.setTextColor(Color.GRAY);
        }

        holder.view1.setTag(position);





    }



    @Override
    public int getItemCount() {
        return devicesList.size();
    }


}
