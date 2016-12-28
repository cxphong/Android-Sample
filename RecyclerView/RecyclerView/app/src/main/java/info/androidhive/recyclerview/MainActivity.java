package info.androidhive.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
    private double changeUItime;


    public static List<BleManager.DiscoveryListener.DiscoveryEvent> getEventList() {
        return eventList;
    }

    //private Button btnS;
    private static List<BleManager.DiscoveryListener.DiscoveryEvent> eventList = new ArrayList<>();



    private Device device;


//    public static BleManager.DiscoveryListener.DiscoveryEvent getE() {
//        return e;
//    }
//
//    public void setE(BleManager.DiscoveryListener.DiscoveryEvent e) {
//        this.e = e;
//    }

//    private static final String uuid= "0000fff4-0000-1000-8000-00805f9b34fb";
//    private static final UUID MY_UUID = UUID.fromString(uuid);			// NOTE: Replace with your actual UUID.
//
//    private static final String serviceuuid= "0000fff0-0000-1000-8000-00805f9b34fb";
//    private static final UUID MY_SERVICE_UUID = UUID.fromString(serviceuuid);			// NOTE: Replace with your actual UUID.
//
//
//    private static final byte[] MY_DATA = {(byte) 0x7};		//  NOTE: Replace with your actual data, not 0xC0FFEE




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

                    StopScan();
                }

            }
        });






    }

    private void addListEvent(BleManager.DiscoveryListener.DiscoveryEvent event){
        for (int i = 0; i < eventList.size(); i++){
            if(eventList.get(i).device().getMacAddress().equals(event.device().getMacAddress())){
                //devicesList.get(i).setLastTimeUpdateRssi(eventList.get(i).device().getLastDiscoveryTime().toMilliseconds());

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


        boolean checkMac = CheckMac(devicesList, macDevice, rssi);

        if (checkMac) {
            //final double firstDiscoveryTime=0;// device duoc phat hien lan dau se khong co LastTimeUpdateRssi

            Device device = new Device(nameDevice, macDevice, rssi);
            device.setLastTimeUpdateRssi(System.currentTimeMillis());
            devicesList.add(device);

            dAdapter.notifyDataSetChanged();
            changeUItime=System.currentTimeMillis();

        }
        updateListDevice();



    }
    private boolean CheckMac(List<Device> devicesList,String mac,String rssi){

        for(int i=0;i<devicesList.size();i++){
            if(devicesList.get(i).getMac().equals(mac)){







                double currentTime=System.currentTimeMillis();
                Log.i("thoigian", ""+(currentTime - devicesList.get(i).getLastTimeUpdateRssi()));


                if((currentTime - devicesList.get(i).getLastTimeUpdateRssi()>1000)) {

                    //Log.i(TAG, "CheckMac: "+"davaoday");
                    devicesList.get(i).setRssi(rssi);


                    dAdapter.notifyDataSetChanged();
                    devicesList.get(i).setLastTimeUpdateRssi(currentTime);
                    changeUItime=System.currentTimeMillis();


                }



                return false;

            }
        }

        return true;
    }
    private void  updateListDevice(){
        for(int i=0;i<eventList.size();i++){
            double firstTime=eventList.get(i).device().getLastDiscoveryTime().toMilliseconds();
            double currentTime=System.currentTimeMillis();
           // Log.i("abc", "updateListDevice: "+(currentTime-firstTime));


            if(currentTime-firstTime>10000) {

//                eventList.remove(i);
//                devicesList.remove(i);

//                dAdapter.notifyDataSetChanged();
//                changeUItime=System.currentTimeMillis();
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
    public static void StopScan(){
        m_bleManager.stopScan();
        txvS.setText("START SCAN");

    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        StopScan();

    }










}
