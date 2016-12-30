package info.androidhive.recyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idevicesinc.sweetblue.BleManager;
import com.idevicesinc.sweetblue.utils.BluetoothEnabler;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Device> devicesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView txtNod;
    private static TextView txvS;
    private DevicesAdapter dAdapter;
    private static BleManager m_bleManager;

    public static List<BleManager.DiscoveryListener.DiscoveryEvent> getEventList() {
        return eventList;
    }

    private static List<BleManager.DiscoveryListener.DiscoveryEvent> eventList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNod = (TextView) findViewById(R.id.textViewNoD);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        dAdapter = new DevicesAdapter(devicesList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dAdapter);


        BluetoothEnabler.start(MainActivity.this);

        m_bleManager = BleManager.get(MainActivity.this);

        eventList.clear();
        m_bleManager.startScan(new BleManager.DiscoveryListener() {
            @Override
            public void onEvent(DiscoveryEvent event) {
                addListEvent(event);
                addDeviceData(event);

                showDevices(devicesList);
            }

        });

        txvS= (TextView) findViewById(R.id.textViewS);
        txvS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (txvS.getText() == "START SCAN") {
                    eventList.clear();
                    devicesList.clear();

                    txvS.setText("STOP SCAN");
                    m_bleManager.startScan(new BleManager.DiscoveryListener() {
                        @Override
                        public void onEvent(DiscoveryEvent event) {

                            addListEvent(event);
                            addDeviceData(event);
                            showDevices(devicesList);
                        }
                    });
                } else {

                    stopScan();
                }

            }
        });

    }

    private void addListEvent(BleManager.DiscoveryListener.DiscoveryEvent event){
        for (int i = 0; i < eventList.size(); i++){
            if(eventList.get(i).device().getMacAddress().equals(event.device().getMacAddress())){

                eventList.set(i,event);
                return;
            }
        }
        eventList.add(event);
    }


    private void addDeviceData(BleManager.DiscoveryListener.DiscoveryEvent event) {


        String nameDevice = event.device().getName_native();
        String macDevice = event.device().getMacAddress();
        String rssi = String.valueOf(event.device().getRssi())+" dBm";

        if (!isMacExist(macDevice, rssi)) {

            Device device = new Device(nameDevice, macDevice, rssi);
            device.setLastTimeUpdateRssi(System.currentTimeMillis());
            devicesList.add(device);

            dAdapter.notifyDataSetChanged();
        }
        updateListDevice();

    }

    private boolean isMacExist(String mac,String rssi){

        for(int i=0;i<devicesList.size();i++){
            if(devicesList.get(i).getMac().equals(mac)){

                long currentTime=System.currentTimeMillis();
                if((currentTime - devicesList.get(i).getLastTimeUpdateRssi()>1000)) {

                    devicesList.get(i).setRssi(rssi);
                    dAdapter.notifyDataSetChanged();
                    devicesList.get(i).setLastTimeUpdateRssi(currentTime);
                }

                return true;
            }
        }

        return false;
    }
    
    
    private void  updateListDevice(){
        for(int i=0;i<eventList.size();i++){
            double firstTime=eventList.get(i).device().getLastDiscoveryTime().toMilliseconds();
            double currentTime=System.currentTimeMillis();

            if(currentTime-firstTime>10000) {

                devicesList.get(i).setConnect(false);

            }
            else
            {
                devicesList.get(i).setConnect(true);
            }

        }

    }
    
    
    private void showDevices(List<Device> devicesList){

        if(devicesList.isEmpty()){

            txtNod.setVisibility(View.VISIBLE);

            recyclerView.setVisibility(View.INVISIBLE);
        }
        else{
            txtNod.setVisibility(View.INVISIBLE);

            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    private static void stopScan(){
        m_bleManager.stopScan();
        txvS.setText("START SCAN");

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        stopScan();
    }

    public static class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {


        private List<Device> devicesList;

        private static BleManager.DiscoveryListener.DiscoveryEvent e;
        public static BleManager.DiscoveryListener.DiscoveryEvent getE() {
            return e;
        }

        public void setE(BleManager.DiscoveryListener.DiscoveryEvent e) {
            this.e = e;
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
                rssi=(TextView ) view.findViewById(R.id.rssi);
                btnConnect=(Button)view.findViewById(R.id.buttonConnect);
                btnConnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        e=eventList.get((int)view1.getTag());
                        MainActivity.stopScan();
                        Intent mh2=new Intent(view.getContext(),ManHinhConnect.class);

                        view.getContext().startActivity(mh2);

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


}
