
package info.androidhive.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idevicesinc.sweetblue.BleDevice;
import com.idevicesinc.sweetblue.BleDeviceState;
import com.idevicesinc.sweetblue.BleManager;
import com.idevicesinc.sweetblue.utils.Uuids;

import java.util.Arrays;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static info.androidhive.recyclerview.ByteUtils.toHex;

public class ManHinhConnect extends AppCompatActivity {
    private TextView txvName,txvStatus;
    private TextView txvChromaMode,txvChromaBrightness,txvChromaBatteryLevel;
    private EditText edtSetMode,edtSetbr;
    private Button btnSetMode,btnSetBr;

    private static final String serviceuuid= "FFFFFFFF-FFFF-FFFF-FFFF-FF5544332200";
    private static final UUID MY_SERVICE_UUID = UUID.fromString(serviceuuid);

    private BleManager.DiscoveryListener.DiscoveryEvent event;
    private static final UUID MODE_UUID = UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FF5544332211");
    private static final UUID BR_UUID = UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FF5544332222");


    private static final UUID BATTERY_SERVICE_UUID = Uuids.fromString("180F");
    private static final UUID BATTERY_UUID = Uuids.fromString("2A19");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        txvName=(TextView)findViewById(R.id.textViewName);
        txvStatus=(TextView)findViewById(R.id.textViewStatus);
        txvChromaMode=(TextView)findViewById(R.id.textViewChromaMode);
        txvChromaBrightness=(TextView)findViewById(R.id.textViewChromaBrightness);
        txvChromaBatteryLevel=(TextView)findViewById(R.id.textViewBatteryLevel);


        event=MainActivity.DevicesAdapter.getE();



        txvStatus.append("CONNECTING....");

        txvName.append(event.device().getName_native());




        if (event.was(BleManager.DiscoveryListener.LifeCycle.REDISCOVERED)||event.was(BleManager.DiscoveryListener.LifeCycle.DISCOVERED)) {

            event.device().connect(new BleDevice.StateListener() {

                @Override
                public void onEvent( StateEvent event) {

                    if (event.didEnter(BleDeviceState.INITIALIZED)) {
                        //Log.i("SweetBlueExample", event.device().getName_debug() + " just initialized!");
                        txvStatus.setText("CONNECTED");

                        readDataMode();
                        readDataBr();
                        event.device().read(BATTERY_SERVICE_UUID,BATTERY_UUID, new BleDevice.ReadWriteListener()
                        {
                            @Override public void onEvent(ReadWriteEvent result)
                            {
                                if( result.wasSuccess() )
                                {
                                    Log.i("SweetBlueExample", "Battery level is " + result.data()[0] + "%");
                                    txvChromaBatteryLevel.setText("Battery Level="+result.data()[0] + "%");
                                }
                                else{
                                    Log.i(TAG, "onEvent: "+"loi");
                                }
                            }
                        });


                    }
                    else if(event.didEnter(BleDeviceState.DISCONNECTED)){
                        txvStatus.setText("DISCONNECT");

                    }
                }
            });

            edtSetMode=(EditText)findViewById(R.id.editTextSetMode);
            edtSetbr=(EditText)findViewById(R.id.editTextSetBr);
            btnSetMode=(Button)findViewById(R.id.buttonSetMode);
            btnSetBr=(Button)findViewById(R.id.buttonSetBr);

            btnSetMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mode=Integer.parseInt(edtSetMode.getText().toString());
                    byte[] dataM ={(byte)mode};

                    event.device().write(MY_SERVICE_UUID, MODE_UUID,dataM, new BleDevice.ReadWriteListener() {
                        @Override
                        public void onEvent(ReadWriteEvent event) {
                            if (event.wasSuccess()) {
                                Log.i("", "Write successful");
                            } else {
                                Log.i(TAG, "onEvent: "+event);
                                Log.e("log la", event.status().toString()); // Logs the reason why it failed.
                               // Log.i(TAG, "onEvent: "+MY_UUID.toString());
                            }
                        }
                    });

                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            readDataMode();

                        }
                    }, 100);



                }
            });


            btnSetBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int br=Integer.parseInt(edtSetbr.getText().toString());

                    byte[] dataM ={(byte)br};

                    event.device().write(MY_SERVICE_UUID, BR_UUID,dataM, new BleDevice.ReadWriteListener() {
                        @Override
                        public void onEvent(ReadWriteEvent event) {
                            if (event.wasSuccess()) {
                                Log.i("", "Write successful");
                            } else {
                                Log.i(TAG, "onEvent: "+event);
                                Log.e("log la", event.status().toString()); // Logs the reason why it failed.
                                // Log.i(TAG, "onEvent: "+MY_UUID.toString());
                            }
                        }
                    });
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           readDataBr();

                        }
                    }, 100);

                }
            });




        }


    }
    @Override
    public void onDestroy() {
        event.device().disconnect();
        super.onDestroy();
    }

    private void readDataMode(){
        event.device().read(MY_SERVICE_UUID, MODE_UUID, new BleDevice.ReadWriteListener() {
            @Override
            public void onEvent(ReadWriteEvent result) {
                if( result.wasSuccess() )
                {
                    Log.i(TAG, "onEvent: "+"scuccess");
                    txvChromaMode.setText("Chroma Mode="+Arrays.toString(toHex(result.data())));

                    ByteUtils.printArray(result.data());

                    Log.i(TAG, "onEvent: "+result);

                }else {
                    Log.i(TAG, "onEvent: "+result);
                    //Log.e("log la", event.status().toString());

                }
            }
        });


    }
    private void readDataBr(){
        event.device().read(MY_SERVICE_UUID, BR_UUID, new BleDevice.ReadWriteListener() {
            @Override
            public void onEvent(ReadWriteEvent result) {
                if( result.wasSuccess() )
                {


                    float b=result.data_byte();
                    Log.i(TAG, "onEvent: "+"giabla"+b);
                    txvChromaBrightness.setText("Chroma Brightness="+String.valueOf((int)Math.round(b/31*100)+"%"));


                }else {
                    Log.i(TAG, "onEvent: "+result);


                }
            }
        });
    }



}
