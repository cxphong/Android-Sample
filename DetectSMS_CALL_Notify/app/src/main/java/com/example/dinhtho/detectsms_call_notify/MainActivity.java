package com.example.dinhtho.detectsms_call_notify;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Context mContext;
    String incoming_number;
    private int prev_state;
    private static final String TAG = "CustomPhoneStateListener";

    static IntentFilter phoneFilter;
    static IntentFilter smsFilter;
    public final static int REQUEST_CODE = 0x11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] permissions = {"android.permission.READ_SMS","android.permission.RECEIVE_SMS","android.permission.READ_PHONE_STATE"};
        ActivityCompat.requestPermissions(MainActivity.this,permissions, REQUEST_CODE);


        phoneFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(mPhoneCallReceiver, phoneFilter);

        smsFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        smsFilter.setPriority(2147483647);
        registerReceiver(mSmsReceiver, smsFilter);




        new Task().execute(MainActivity.this);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private final BroadcastReceiver mPhoneCallReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); //TelephonyManager object
            CustomPhoneStateListener customPhoneListener = new CustomPhoneStateListener();
            telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE); //Register our listener with TelephonyManager

            Bundle bundle = intent.getExtras();
//            String phoneNr = bundle.getString("incoming_number");
//            Log.i(TAG, "onReceive: "+phoneNr);
            mContext = context;


//
//            if (!intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
//                TelephonyManager manager = (TelephonyManager) context
//                        .getSystemService(context.TELEPHONY_SERVICE);
//                Log.i(TAG, "onReceive: "+"co dien thoai");
//            }
        }
    };

    public class CustomPhoneStateListener extends PhoneStateListener {

        private static final String TAG = "CustomPhoneStateListener";

        @Override
        public void onCallStateChanged(int state, String incomingNumber){

            if( incomingNumber != null && incomingNumber.length() > 0 )
                incoming_number = incomingNumber;


            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    //Log.d(TAG, "CALL_STATE_RINGING");
                    prev_state=state;
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(TAG, "da nhac may");
                    prev_state=state;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:

                    //Log.d(TAG, "CALL_STATE_IDLE==>"+incoming_number);

                    if((prev_state == TelephonyManager.CALL_STATE_OFFHOOK)){
                        prev_state=state;
                        //Answered Call which is ended
                    }
                    if((prev_state == TelephonyManager.CALL_STATE_RINGING)){
                        prev_state=state;
                        Log.i(TAG, "onCallStateChanged: "+"Rejected or Missed call");
                    }
                    break;

            }
        }
    }

    private final BroadcastReceiver mSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Receive sms");
            processReceive(context, intent);

//            if (isSMSEnable()) {
//                sendSMSColor();
//            }
        }
        public void processReceive(Context context, Intent intent)
        {
            Toast.makeText(context, "Hello .. có tin nhắn tới đó", Toast.LENGTH_LONG).show();
            TextView lbl=(TextView) findViewById(R.id.smsBody);
            //pdus để lấy gói tin nhắn
            String sms_extra="pdus";
            Bundle bundle=intent.getExtras();
            //bundle trả về tập các tin nhắn gửi về cùng lúc
            Object []objArr= (Object[]) bundle.get(sms_extra);
            String sms="";
            //duyệt vòng lặp để đọc từng tin nhắn
            for(int i=0;i<objArr.length;i++)
            {
                //lệnh chuyển đổi về tin nhắn createFromPdu
                SmsMessage smsMsg=SmsMessage.
                        createFromPdu((byte[]) objArr[i]);

                //lấy nội dung tin nhắn
                String body=smsMsg.getMessageBody();
                //lấy số điện thoại tin nhắn
                String address=smsMsg.getDisplayOriginatingAddress();
                sms+=address+":\n"+body+"\n";
                Log.i(TAG, "processReceive: "+sms);
                markMessageRead(MainActivity.this,address,body);
            }
            //hiển thị lên giao diện
            lbl.setText(sms);
        }


        private void markMessageRead(Context context, String number, String body) {

            Uri uri = Uri.parse("content://sms/inbox");
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try{

                while (cursor.moveToNext()) {


                    if ((cursor.getString(cursor.getColumnIndex("address")).equals(number)) && (cursor.getInt(cursor.getColumnIndex("read")) == 0)) {
                        if (cursor.getString(cursor.getColumnIndex("body")).startsWith(body)) {
                            String SmsMessageId = cursor.getString(cursor.getColumnIndex("_id"));
                            ContentValues values = new ContentValues();
                            values.put("read", true);
                            context.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "_id=" + SmsMessageId, null);
                            return;
                        }
                    }
                }
            }catch(Exception e)
            {
                Log.e("Mark Read", "Error in Read: "+e.toString());
            }
        }
    };


}
class Task extends AsyncTask<Context, Object, Void> {

    private static final String TAG = "CustomPhoneStateListener";
    private Exception exception;


    protected void onPostExecute(String r) {

    }

    private void checkSmsRead(Context context){

        int sumPre=sumFlagRead(context);


        while (true) {

              int sum=sumFlagRead(context);

            if(sum>sumPre){
                Log.i(TAG, "checkSmsRead: "+"tin nhan duoc doc");
                sumPre=sum;
            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private int sumFlagRead(Context context){
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        int sumFlag=0;
        while (cursor.moveToNext()) {
            sumFlag+=cursor.getInt(cursor.getColumnIndex("read"));


        }
        cursor.close();
        return sumFlag;
    }


    @Override
    protected Void doInBackground(Context... contexts) {
        checkSmsRead(contexts[0]);
        return null;
    }
}


