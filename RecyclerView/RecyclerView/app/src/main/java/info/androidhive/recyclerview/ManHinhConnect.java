
package info.androidhive.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.idevicesinc.sweetblue.BleDevice;
import com.idevicesinc.sweetblue.BleDeviceState;
import com.idevicesinc.sweetblue.BleManager;

import java.util.Arrays;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static info.androidhive.recyclerview.ByteUtils.toHex;

public class ManHinhConnect extends AppCompatActivity {
    private TextView txvName,txvStatus,txvRead;
    private BleManager.DiscoveryListener.DiscoveryEvent event;
    private static final String uuid= "0000fff2-0000-1000-8000-00805f9b34fb";
    private static final UUID MY_UUID = UUID.fromString(uuid);			// NOTE: Replace with your actual UUID.

    private static final String serviceuuid= "0000fff0-0000-1000-8000-00805f9b34fb";
    private static final UUID MY_SERVICE_UUID = UUID.fromString(serviceuuid);			// NOTE: Replace with your actual UUID.

    private BleManager m_bleManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        txvName=(TextView)findViewById(R.id.textViewName);
        txvStatus=(TextView)findViewById(R.id.textViewStatus);
        txvRead=(TextView)findViewById(R.id.textViewRead);

        event=DevicesAdapter.getE();






        txvStatus.setText("CONNECTING....");

        txvName.setText(event.device().getName_native());




        if (event.was(BleManager.DiscoveryListener.LifeCycle.REDISCOVERED)||event.was(BleManager.DiscoveryListener.LifeCycle.DISCOVERED)) {
            event.device().connect(new BleDevice.StateListener() {
                @Override
                public void onEvent( StateEvent event) {
                    if (event.didEnter(BleDeviceState.INITIALIZED)) {
                        Log.i("SweetBlueExample", event.device().getName_debug() + " just initialized!");
                        txvStatus.setText("CONNECTED");

                        event.device().read(MY_SERVICE_UUID, MY_UUID, new BleDevice.ReadWriteListener() {
                            @Override
                            public void onEvent(ReadWriteEvent result) {
                                if( result.wasSuccess() )
                                {
                                    Log.i(TAG, "onEvent: "+"scuccess");
                                    txvRead.setText(Arrays.toString(toHex(result.data())));

                                    ByteUtils.printArray(result.data());

                                    Log.i(TAG, "onEvent: "+result);

                                }else {
                                    Log.i(TAG, "onEvent: "+result);
                                    //Log.e("log la", event.status().toString());

                                }
                            }
                        });



                    }
                    else if(event.didEnter(BleDeviceState.DISCONNECTED)){
                        txvStatus.setText("DISCONNECT");

                    }
                }
            });
        }












    }
    @Override
    public void onBackPressed() {

        event.device().disconnect();
        Intent mainS = new Intent(ManHinhConnect.this,MainActivity.class);
        startActivity(mainS);
    }


}
