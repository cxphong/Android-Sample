package com.viralandroid.androidtabsatbottom;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.bluetooth.le.FioTBluetoothCharacteristic;
import com.bluetooth.le.FioTBluetoothService;
import com.bluetooth.le.FioTManager;
import com.bluetooth.le.FioTScanManager;
import com.bluetooth.le.FiotBluetoothInit;
import com.bluetooth.le.FiotBluetoothUtils;
import com.bluetooth.le.exception.NotFromActivity;
import com.bluetooth.le.exception.NotSupportBleException;
import com.bluetooth.le.utils.ByteUtils;
import com.bumptech.glide.Glide;
import com.dd.processbutton.iml.ActionProcessButton;
import com.viralandroid.androidtabsatbottom.utils.DateTimeUtils;
import com.viralandroid.androidtabsatbottom.utils.DeviceInfoDB;
import com.viralandroid.androidtabsatbottom.utils.DialogUtils;
import com.viralandroid.androidtabsatbottom.utils.Prefences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.viralandroid.androidtabsatbottom.R.id.battery_state_of_charge;
import static com.viralandroid.androidtabsatbottom.R.id.tv_charging_volatage;
import static com.viralandroid.androidtabsatbottom.R.id.tv_toolbar_back;


public class MainActivity extends AppCompatActivity implements MyCallback, ScrollListener {
    private ArrayAdapter<String> adapter;
    private ActionProcessButton btnProcess;
    private Realm realm;

    public static final int GALLERY_REQUEST = 0;
    private final String title_toolbar = "Back";
    private CountDownTimer activeBackupPowerTimer;
    private long totalTimeRemainRunning;
    private boolean reset = false;
    private ScrollListener scrollListener = this;
    private Spinner spinner_CCA;
    private MyCallback myCallBack = this;
    private static final String TAG = "MainActivity";
    private Button bt_on_off, bt_sync_up_now, bt_on, bt_off, bt_metric, bt_imperial, bt_start_stop, bt_upload_picture;

    private String BUTTON_ON_OFF_STATUS = "OFF";
    private ProgressBar progressBar;
    private TextView tv_circle, tv_status_heater, tv_check_battery, tv_no_devices_found, tv_setttings, tv_test_battey;
    private TextView tv_datalog, tv_add_device, tv_find_device, tv_find_device_tilte, tv_ok_find_device, tv_save;
    private TextView tv_name_picture;
    private EditText et_make, et_model, et_year, et_dataLog_interval;
    private String path_image_file = "";

    private static RecyclerView.Adapter mAdapter;
    private  RecyclerView.Adapter adapter_devices;
    private static RecyclerView mRecyclerView;
    private RecyclerView recycler_view_list_device;
    static Activity activity;
    private LinearLayout layout_on, layout_off;
    private LinearLayout layout_home, layout_check_battery, layout_settings, layout_test_battery;
    private LinearLayout layout_home_reports, layout_home_more, layout_add_device, layout_find_device, layout_find_device_info;
    private LinearLayout layout_list_find_device, layout_more, layout_device, layout_list_device;
    private LinearLayout layout_datalog;
    private TabHost tabHost;
    private Toolbar toolbar;
    private TextView tv_temperature;
    private TextView tv_current_battery_voltage;
    private TextView tv_battery_state_of_health;
    private TextView tv_battery_state_of_health_time;
    private TextView tv_battery_state_of_charge;
    private ProgressDialog progressDialog;
    private TextView tv_cancel;
    private ScrollView layout_viewlog;
    private ActionProcessButton btnRefresh;

    private TextView tv_battery_state_of_health_01, tv_battery_state_of_health_01_time;
    private TextView tv_battery_load_voltage, tv_battery_load_voltage_time;
    private TextView tv_charging_system_health, tv_charging_system_health_time;
    private TextView tv_charging_voltage, tv_charging_voltage_time;
    private TextView tv_sync_time_now;
    private TextView tv_view_log;

    private FioTScanManager scanManager;
    private ArrayList<Device> scanDeviceList = new ArrayList<>();
    private ArrayList<DeviceInfo> connectedDeviceList = new ArrayList<>();
    private int deviceIndex;
    private FioTManager connectingManager;
    private DevicesAdapter dAdapter;
    private Timer datalogTimer;
    private StringBuilder dataLogStr;

    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    private static final String SERVICE_01_UUID = "00001800-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_01_SERVICE_01_UUID = "00002a00-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_02_SERVICE_01_UUID = "00002a01-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_03_SERVICE_01_UUID = "00002a02-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_04_SERVICE_01_UUID = "00002a03-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_05_SERVICE_01_UUID = "00002a04-0000-1000-8000-00805f9b34fb";

    private static final String SERVICE_02_UUID = "00001801-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_01_SERVICE_02_UUID = "00002a05-0000-1000-8000-00805f9b34fb";

    private static final String SERVICE_03_UUID = "0000180a-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_01_SERVICE_03_UUID = "00002a23-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_02_SERVICE_03_UUID = "00002a26-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_03_SERVICE_03_UUID = "00002a27-0000-1000-8000-00805f9b34fb";

    private static final String SERVICE_04_UUID = "0000ff00-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_01_SERVICE_04_UUID = "0000ff01-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_02_SERVICE_04_UUID = "0000ff02-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_03_SERVICE_04_UUID = "0000ff03-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_04_SERVICE_04_UUID = "0000ff04-0000-1000-8000-00805f9b34fb";
    private static final String CHARAC_05_SERVICE_04_UUID = "0000ff05-0000-1000-8000-00805f9b34fb";

    private final static int REQUEST_CODE = 0x11;

    private enum WORKING_MODE {
        NOTHING("NOTHING"),
        ACTIVE_BACKUP_POWER("ACTIVE_BACKUP_POWER"),
        TEST_BATTERY_AND_CHARGING_SYSTEM("TEST_BATTERY_AND_CHARGING_SYSTEM"),
        CHECK_BATTERY_STATUS("CHECK_BATTERY_STATUS"),
        SETTING("SETTING"),
        DATA_LOG("DATA_LOG");

        private String value;

        public String getName(WORKING_MODE mode) {
            return mode.name();
        }

        WORKING_MODE(String value) {
            this.value = value;
        }
    }

    private WORKING_MODE workingMode = WORKING_MODE.NOTHING;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getInstance(this);
        String[] permissions_camera = {"android.permission.READ_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(MainActivity.this, permissions_camera, REQUEST_CODE);


        initializeToolbar();
        initializeTab();

//        btnProcess = (ActionProcessButton) findViewById(R.id.btnRefresh);
//        btnProcess.setMode(ActionProcessButton.Mode.ENDLESS);
//        btnProcess.setProgress(0);
//        btnProcess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActionProcessButton btn = (ActionProcessButton) view;
//
//                btn.setProgress(50);
//                // we add 25 in the button progress each click
////                if (btn.getProgress() < 100) {
////                    btn.setProgress(btn.getProgress() + 25);
////                }
//            }
//        });
        btnRefresh = (ActionProcessButton) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCheckBatteryStatus();
            }
        });


        final List<String> list_CCA = new ArrayList<String>();
        for (int i = 100; i <= 1200; i += 5) {
            list_CCA.add(i + "");
        }

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list_CCA);
        spinner_CCA = (Spinner) findViewById(R.id.spinner_CCA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_CCA.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition("500");
        spinner_CCA.setSelection(spinnerPosition);

        spinner_CCA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.i(TAG, "onItemSelected: " + spinner_CCA.getSelectedItem().toString());
                Prefences.saveBatteryCCA(MainActivity.this, Integer.parseInt(spinner_CCA.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        activity = MainActivity.this;

        layout_home_reports = (LinearLayout) findViewById(R.id.layout_home_reports);
        layout_datalog = (LinearLayout) findViewById(R.id.layout_datalog);
        layout_home_more = (LinearLayout) findViewById(R.id.layout_home_more);
        layout_more = (LinearLayout) findViewById(R.id.layout_more);
        layout_device = (LinearLayout) findViewById(R.id.layout_device);
        layout_list_device = (LinearLayout) findViewById(R.id.layout_list_device);
        layout_add_device = (LinearLayout) findViewById(R.id.layout_add_device);
        layout_find_device = (LinearLayout) findViewById(R.id.layout_find_device);
        layout_find_device_info = (LinearLayout) findViewById(R.id.layout_find_device_info);
        layout_list_find_device = (LinearLayout) findViewById(R.id.layout_list_find_device);
        layout_viewlog = (ScrollView) findViewById(R.id.layout_view_log);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_circle = (TextView) findViewById(R.id.tv_circle);
        tv_status_heater = (TextView) findViewById(R.id.tv_status_heater);
        tv_check_battery = (TextView) findViewById(R.id.tv_check_battery);
        tv_setttings = (TextView) findViewById(R.id.tv_setttings);
        tv_test_battey = (TextView) findViewById(R.id.tv_test_battey);
        tv_datalog = (TextView) findViewById(R.id.tv_datalog);
        tv_find_device = (TextView) findViewById(R.id.tv_find_device);
        tv_find_device_tilte = (TextView) findViewById(R.id.tv_find_device_tilte);
        tv_ok_find_device = (TextView) findViewById(R.id.tv_ok_find_device);
        tv_no_devices_found = (TextView) findViewById(R.id.tv_no_devices_found);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_name_picture = (TextView) findViewById(R.id.tv_name_picture);
        tv_add_device = (TextView) findViewById(R.id.tv_add_device);
        layout_home = (LinearLayout) findViewById(R.id.layout_home);
        layout_check_battery = (LinearLayout) findViewById(R.id.layout_check_battery);
        layout_settings = (LinearLayout) findViewById(R.id.layout_setttings);
        layout_test_battery = (LinearLayout) findViewById(R.id.layout_test_battery);
        et_make = (EditText) findViewById(R.id.et_make);
        et_model = (EditText) findViewById(R.id.et_model);
        et_year = (EditText) findViewById(R.id.et_year);
        et_dataLog_interval = (EditText) findViewById(R.id.et_dataLog_interval);
        tv_sync_time_now = (TextView) findViewById(R.id.tv_sync_time_now);
        tv_view_log = (TextView) findViewById(R.id.tv_view_log);

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = connectedDeviceList.size();
                DeviceInfo deviceInfo = new DeviceInfo("GH" + size, et_model.getText().toString(), et_year.getText().toString(), path_image_file, connectingManager);
                connectedDeviceList.add(deviceInfo);

                DeviceInfoDB deviceInfoDB = new DeviceInfoDB();
                deviceInfoDB.setMac(connectingManager.getDevice().getAddress());
                deviceInfoDB.setMake(et_make.getText().toString());
                deviceInfoDB.setModel(et_model.getText().toString());
                deviceInfoDB.setYear(et_year.getText().toString());
                deviceInfoDB.setPic(path_image_file);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(deviceInfoDB);
                realm.commitTransaction();
                path_image_file = "";


                Log.i(TAG, "onClick: a");
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_device_info);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                adapter_devices = new DevicesInfoAdapter(connectedDeviceList);
                recyclerView.setAdapter(adapter_devices);
                adapter_devices.notifyDataSetChanged();


//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_device_info);
//                RecyclerView.Adapter adapter = new DevicesInfoAdapter(connectedDeviceList);
//                recyclerView.setHasFixedSize(true);
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                recyclerView.setLayoutManager(mLayoutManager);
//                recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

                stopFindDevice();
            }
        });

        tv_ok_find_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanDeviceList.clear();
                layout_list_find_device.setVisibility(View.VISIBLE);
                layout_more.setVisibility(View.GONE);
                tv_find_device_tilte.setText("Finding devices ...");
                ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                toolbar.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                recycler_view_list_device = (RecyclerView) findViewById(R.id.recycler_view_list_device);
                dAdapter = new DevicesAdapter(scanDeviceList, myCallBack);

                recycler_view_list_device.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recycler_view_list_device.setLayoutManager(mLayoutManager);
                recycler_view_list_device.setItemAnimator(new DefaultItemAnimator());
                recycler_view_list_device.setAdapter(dAdapter);

                scanManager = new FioTScanManager(MainActivity.this);
                recycler_view_list_device.setVisibility(View.GONE);
                tv_no_devices_found.setVisibility(View.VISIBLE);
                startScan();
            }
        });
        ((TextView) findViewById(tv_toolbar_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressBack();
            }
        });

        tv_find_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                ((TextView) findViewById(tv_toolbar_back)).setVisibility(View.VISIBLE);
                layout_list_device.setVisibility(View.GONE);
                layout_find_device.setVisibility(View.INVISIBLE);
                layout_find_device_info.setVisibility(View.VISIBLE);
                tv_find_device_tilte.setText("Find Device");

                try {
                    FiotBluetoothInit.enable(MainActivity.this, new FiotBluetoothInit.FiotBluetoothInitListener() {
                        @Override
                        public void completed() {

                        }
                    });
                } catch (NotSupportBleException e) {
                    e.printStackTrace();
                } catch (NotFromActivity notFromActivity) {
                    notFromActivity.printStackTrace();
                }
            }
        });

        tv_add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAddDevice();
            }
        });

        tv_datalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                layout_home_reports.setVisibility(View.GONE);
                layout_datalog.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        tv_check_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showMessage(MainActivity.this, "Please Turn Off All Accessories Then Proceed",
                        true,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startCheckBatteryStatus();
                            }
                        });
            }
        });

        tv_setttings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workingMode = WORKING_MODE.SETTING;
                layout_home.setVisibility(View.GONE);
                layout_settings.setVisibility(View.VISIBLE);
//                tv_toolbar_title.setText("Settings");
                ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                toolbar.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            }
        });
        tv_test_battey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTestBatteryAndChargingSystem();
            }
        });


        bt_on = (Button) findViewById(R.id.bt_on);
        bt_off = (Button) findViewById(R.id.bt_off);
        bt_metric = (Button) findViewById(R.id.bt_metric);
        bt_imperial = (Button) findViewById(R.id.bt_imperial);
        bt_sync_up_now = (Button) findViewById(R.id.bt_sync_up_now);
        bt_sync_up_now.setOnClickListener(clickSyncTime);

        bt_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefences.saveNotify(MainActivity.this, false);
                bt_off.setBackgroundResource(R.drawable.button_background);
                bt_on.setBackgroundResource(R.drawable.border);

            }
        });

        bt_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefences.saveNotify(MainActivity.this, true);
                bt_off.setBackgroundResource(R.drawable.border);
                bt_on.setBackgroundResource(R.drawable.button_background);
            }
        });

        bt_imperial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prefences.saveImperial(MainActivity.this,true);
                bt_metric.setBackgroundResource(R.drawable.border);
                bt_imperial.setBackgroundResource(R.drawable.button_background);
                clickImperial();
            }
        });

        bt_metric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  prefences.saveImperial(MainActivity.this,false);
                bt_metric.setBackgroundResource(R.drawable.button_background);
                bt_imperial.setBackgroundResource(R.drawable.border);
                clickMetric();
            }
        });

        bt_start_stop = (Button) findViewById(R.id.bt_start_stop);
        bt_on_off = (Button) findViewById(R.id.bt_on_off);
        layout_on = (LinearLayout) findViewById(R.id.layout_on);
        layout_off = (LinearLayout) findViewById(R.id.layout_off);
        bt_upload_picture = (Button) findViewById(R.id.bt_upload_picture);

        bt_upload_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });

        bt_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BUTTON_ON_OFF_STATUS.equalsIgnoreCase("OFF")) {
                    startActiveBackupPower(31000);
                } else {
                    Log.i(TAG, "onClick: off");
                    stopActiveBackupPower();

                }
            }
        });

        bt_start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bt_start_stop.getText().equals("Start")) {
                    bt_start_stop.setBackgroundResource(R.drawable.bg_button_off);
                    bt_start_stop.setText("Stop");

                    startDataLog();
                } else {
                    bt_start_stop.setBackgroundResource(R.drawable.bg_button_on);
                    bt_start_stop.setText("Start");

                    stopDataLog();
                }
            }
        });


        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_current_battery_voltage = (TextView) findViewById(R.id.tv_current_battery_voltage);
        tv_battery_state_of_health = (TextView) findViewById(R.id.tv_battery_state_of_health);
        tv_battery_state_of_health_time = (TextView) findViewById(R.id.tv_battery_state_of_health_time);
        tv_battery_state_of_charge = (TextView) findViewById(battery_state_of_charge);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectingManager.end();
                connectingManager = null;
                stopFindDevice();
            }
        });

        tv_battery_state_of_health_01 = (TextView) findViewById(R.id.tv_battery_state_of_health_01);
        tv_battery_state_of_health_01_time = (TextView) findViewById(R.id.tv_battery_state_of_health_01_time);
        tv_battery_load_voltage = (TextView) findViewById(R.id.tv_battery_load_voltage);
        tv_battery_load_voltage_time = (TextView) findViewById(R.id.tv_battery_load_voltage_time);
        tv_charging_system_health = (TextView) findViewById(R.id.tv_charging_system_health);
        tv_charging_system_health_time = (TextView) findViewById(R.id.tv_charging_system_health_time);
        tv_charging_voltage = (TextView) findViewById(tv_charging_volatage);
        tv_charging_voltage_time = (TextView) findViewById(R.id.tv_charging_volatage_time);

        requestAddNewDevice();
        Set<BluetoothDevice> set = FiotBluetoothUtils.getBondedDevices(this);
        for (BluetoothDevice bluetoothDevice : set) {
            Log.i(TAG, "onCreate: " + bluetoothDevice.getName());
        }

        getSettings();
    }


    private void backTabHome() {
        // go to tab home
        tabHost.setCurrentTab(0);
        toolbar.setVisibility(View.INVISIBLE);
        layout_home.setVisibility(View.VISIBLE);
        layout_off.setVisibility(View.VISIBLE);
        layout_check_battery.setVisibility(View.GONE);
        layout_test_battery.setVisibility(View.GONE);
        layout_settings.setVisibility(View.GONE);
        layout_on.setVisibility(View.GONE);

    }

    private void backTabReports() {
        tabHost.setCurrentTab(1);
        toolbar.setVisibility(View.INVISIBLE);
        layout_home_reports.setVisibility(View.VISIBLE);
        layout_viewlog.setVisibility(View.GONE);
        layout_datalog.setVisibility(View.GONE);
    }

    private void endProcessRefreshButton() {
        btnProcess.setProgress(100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
//
            }
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            path_image_file = getRealPathFromURI(imageUri);
            String filename = getRealPathFromURI(imageUri).substring(getRealPathFromURI(imageUri).lastIndexOf("/") + 1);
            tv_name_picture.setText(filename);
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                image_view.setImageBitmap(selectedImage);

        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(MainActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void stopFindDevice() {
//        backTabHome();
        if (connectedDeviceList.size() > 0) {
            mRecyclerView.smoothScrollToPosition(connectedDeviceList.size() - 1);
            deviceIndex = connectedDeviceList.size() - 1;
        }

        layout_device.setVisibility(View.GONE);
        layout_add_device.setVisibility(View.VISIBLE);
        layout_find_device_info.setVisibility(View.GONE);
        layout_list_device.setVisibility(View.VISIBLE);
        layout_more.setVisibility(View.VISIBLE);
        layout_find_device.setVisibility(View.VISIBLE);
        tv_find_device_tilte.setVisibility(View.VISIBLE);
        tv_find_device_tilte.setText("Devices");
        toolbar.setVisibility(View.VISIBLE);
        ((TextView) findViewById(tv_toolbar_back)).setTextSize(20);
    }

    private void requestAddNewDevice() {
        if (connectedDeviceList.size() == 0) {
            DialogUtils.showMessage(MainActivity.this,
                    "Device Not Paired Please Follow  More>Add Device And Retry",
                    false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tabHost.setCurrentTab(2);
                            setTabColor();
                            clickAddDevice();
                        }
                    });
        }
    }


    private void clickAddDevice() {
        tv_find_device_tilte.setVisibility(View.VISIBLE);
        layout_home_more.setVisibility(View.GONE);
        layout_add_device.setVisibility(View.VISIBLE);
        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void clickRefreshCheckBatteryStatus(View view) {
        startCheckBatteryStatus();
    }


    private void clickMetric() {
        Prefences.saveImperial(MainActivity.this, false);
    }

    private void clickImperial() {
        Prefences.saveImperial(MainActivity.this, true);
    }

    View.OnClickListener clickSyncTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            long sendTime = System.currentTimeMillis() - 1475280000000L;
            byte[] second = ByteUtils.integerToByteArray((int) (sendTime / 1000));
            byte[] millSecond = ByteUtils.integerToByteArray((int) (sendTime % 1000));

            Log.i(TAG, "clickSyncTime: " + System.currentTimeMillis() + ", " + sendTime);
            byte[] data = new byte[7];
            data[0] = 2;
            ByteUtils.append(data, second, 1);
            ByteUtils.append(data, ByteUtils.subByteArray(millSecond, 2, 2), 5);
            Log.i(TAG, "onClick: " + deviceIndex);
            if (connectedDeviceList.size() > 0) {
                connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_03_SERVICE_04_UUID, data);
            }
            Log.i(TAG, "clickSyncTime: " + sendTime);
            Log.i(TAG, "clickSyncTime: " + Arrays.toString(ByteUtils.toHex(second)));
            Log.i(TAG, "clickSyncTime: " + Arrays.toString(ByteUtils.toHex(millSecond)));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (connectedDeviceList.size() > 0) {
                        connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_02_SERVICE_04_UUID, new byte[]{0x02});
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (connectedDeviceList.size() > 0) {
                                connectedDeviceList.get(deviceIndex).getManager().read(CHARAC_02_SERVICE_04_UUID);
                            }
                        }
                    }, 1000);
                }
            }, 500);
        }
    };

    private void startTestBatteryAndChargingSystem() {
        workingMode = WORKING_MODE.TEST_BATTERY_AND_CHARGING_SYSTEM;
        layout_home.setVisibility(View.GONE);
        resetLayoutTestBattery();
        layout_test_battery.setVisibility(View.VISIBLE);
        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (connectedDeviceList.size() > 0) {
            connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_01_SERVICE_04_UUID, new byte[]{0x04});
        }
        Log.i(TAG, "startTestBatteryAndChargingSystem: " + deviceIndex);

        DialogUtils.showMessage(this,
                "Start and Run Heater for At Least 2 Minutes Then Tap OK",
                false,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog_get_results);
                        TextView tv = (TextView) dialog.findViewById(R.id.tv_get_test_results);
                        dialog.setCancelable(false);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "onClick: click get test results");
                                dialog.dismiss();
                                if (connectedDeviceList.size() > 0) {
                                    connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_01_SERVICE_04_UUID, new byte[]{0x05});
                                }
                                Log.i(TAG, "onClick: " + deviceIndex);
                            }
                        });
                        dialog.show();
                    }
                });

    }

    private void resetLayoutTestBattery() {
        tv_battery_state_of_health_01.setText("");
        tv_battery_state_of_health_01_time.setText("");
        tv_battery_load_voltage.setText("");
        tv_battery_load_voltage_time.setText("");
        tv_charging_system_health.setText("");
        tv_charging_system_health_time.setText("");
        tv_charging_voltage.setText("");
        tv_charging_voltage_time.setText("");

    }

    private void resetLayoutCheckBatteryStatus() {
        tv_battery_state_of_charge.setText("");
        tv_battery_state_of_health.setText("");
        tv_battery_state_of_health_time.setText("");
        tv_current_battery_voltage.setText("");
        tv_temperature.setText("");
    }

    private void startCheckBatteryStatus() {
        workingMode = WORKING_MODE.CHECK_BATTERY_STATUS;
        layout_home.setVisibility(View.GONE);
        resetLayoutCheckBatteryStatus();
        layout_check_battery.setVisibility(View.VISIBLE);
        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (connectedDeviceList.size() > 0) {
            connectedDeviceList.get(deviceIndex).getManager().
                    write(CHARAC_01_SERVICE_04_UUID, new byte[]{0x03});
        }
    }

    private void pauseActiveBackupPower() {
        activeBackupPowerTimer.cancel();
    }

    private void resumeActiveBackupPower() {
        Log.i(TAG, "resumeActiveBackupPower: " + totalTimeRemainRunning);
        startActiveBackupPower(totalTimeRemainRunning);
    }

    private void startActiveBackupPower(long startTime) {
        workingMode = WORKING_MODE.ACTIVE_BACKUP_POWER;
        tv_status_heater.setText("Battery Boost In Progress \nPlease Wait To Start Heater");
        bt_on_off.setBackgroundResource(R.drawable.bg_button_on);
        bt_on_off.setText("On");

        totalTimeRemainRunning = startTime;
        activeBackupPowerTimer = new CountDownTimer(startTime, 1000) {

            public void onTick(long millisUntilFinished) {
                totalTimeRemainRunning = millisUntilFinished;
                int s = (int) millisUntilFinished / 1000;
                int count_down = 30 - s;
                tv_circle.setText(String.valueOf(s));
                progressBar.setProgress(count_down);
            }

            public void onFinish() {
                tv_status_heater.setText("Please Start Your Heater Now");
                tv_circle.setText(String.valueOf(0));
                progressBar.setProgress(30);
            }
        }.start();

        layout_off.setVisibility(View.GONE);
        layout_on.setVisibility(View.VISIBLE);

        BUTTON_ON_OFF_STATUS = "ON";

        Log.i(TAG, "onClick: on");
        if (connectedDeviceList.size() > 0) {
            connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_01_SERVICE_04_UUID, new byte[]{0x01});
        }
    }

    private void stopActiveBackupPower() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                totalTimeRemainRunning = 31000;
                activeBackupPowerTimer.cancel();
                reset = true;

                bt_on_off.setBackgroundResource(R.drawable.bg_button_off);
                bt_on_off.setText("Off");
                layout_on.setVisibility(View.GONE);
                layout_off.setVisibility(View.VISIBLE);
                BUTTON_ON_OFF_STATUS = "OFF";
                if (connectedDeviceList.size() > 0) {
                    connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_01_SERVICE_04_UUID, new byte[]{0x02});
                }
            }
        });
    }

    private void getSettings() {
        if (Prefences.getNotify(MainActivity.this)) {
            bt_off.setBackgroundResource(R.drawable.border);
            bt_on.setBackgroundResource(R.drawable.button_background);
        } else {
            bt_off.setBackgroundResource(R.drawable.button_background);
            bt_on.setBackgroundResource(R.drawable.border);
        }
        if (Prefences.getImperial(MainActivity.this)) {
            bt_metric.setBackgroundResource(R.drawable.border);
            bt_imperial.setBackgroundResource(R.drawable.button_background);
        } else {
            bt_metric.setBackgroundResource(R.drawable.button_background);
            bt_imperial.setBackgroundResource(R.drawable.border);
        }
        int spinnerPosition = adapter.getPosition(String.valueOf(Prefences.getBatteryCCA(MainActivity.this)));
        spinner_CCA.setSelection(spinnerPosition);
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setVisibility(View.INVISIBLE);


    }

    private void initializeTab() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Dashboard");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Dashboard");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Reports");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Reports");
        tabHost.addTab(spec);

        //Tab 3
        spec = tabHost.newTabSpec("More");
        spec.setContent(R.id.tab3);
        spec.setIndicator("More");
        tabHost.addTab(spec);

        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.border);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.border);
        tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.border);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                setTabColor();

//                if (s.equalsIgnoreCase("Dashboard") || s.equalsIgnoreCase("Reports")) {
//                    requestAddNewDevice();
//                }

                if (tabHost.getCurrentTab() == 0) {
                    if (layout_home.isShown()) {
                        toolbar.setVisibility(View.INVISIBLE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    } else {
                        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                        toolbar.setVisibility(View.VISIBLE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    setRecyclerView(R.id.my_recycler_view);
                } else if (tabHost.getCurrentTab() == 1) {
                    backTabReports();
//                    if (layout_home_reports.isShown()) {
//                        toolbar.setVisibility(View.INVISIBLE);
//                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//
//                    } else {
//                        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
//                        toolbar.setVisibility(View.VISIBLE);
//                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    }

                    setRecyclerView(R.id.my_recycler_view_reports);
                } else if (tabHost.getCurrentTab() == 2) {
                    if (layout_home_more.isShown()) {
                        toolbar.setVisibility(View.INVISIBLE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    } else {
                        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                        toolbar.setVisibility(View.VISIBLE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
//                    toolbar.setVisibility(View.INVISIBLE);
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                }
            }
        });

        setTabColor();
        setRecyclerView(R.id.my_recycler_view);
    }

    private void setTabColor() {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#14718b"));
    }

    private void setRecyclerView(int id) {
        mRecyclerView = (RecyclerView) findViewById(id);
        mRecyclerView.addOnScrollListener(new CustomScrollListener(scrollListener));
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyRecyclerViewAdapter(connectedDeviceList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void enterDeviceInfo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                RealmResults<DeviceInfoDB> realmResults = realm.where(DeviceInfoDB.class).equalTo("mac", connectingManager.getDevice().getAddress()).findAll();
                RealmResults<DeviceInfoDB> results = realm.where(DeviceInfoDB.class).findAll();
                if (realmResults.size() > 0) {
                    Log.i(TAG, "runmac: " + realmResults.get(0).getMac());
                    et_make.setText(realmResults.get(0).getMake());
                    et_model.setText(realmResults.get(0).getModel());
                    et_year.setText(realmResults.get(0).getYear());
                    path_image_file = realmResults.get(0).getPic();
                    String filename = realmResults.get(0).getPic().substring(realmResults.get(0).getPic().lastIndexOf("/") + 1);
                    tv_name_picture.setText(filename);
                } else {
                    et_make.setText("");
                    et_model.setText("");
                    et_year.setText("");
                    tv_name_picture.setText("");
                }

                layout_list_find_device.setVisibility(View.GONE);
                layout_device.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
                ((TextView) findViewById(tv_toolbar_back)).setTextSize(15);
                tv_find_device_tilte.setText("");
            }
        });
    }

    @Override
    public void callbackCall(int position) {

        Log.i(TAG, "callbackCall: " + position);
        final ArrayList<FioTBluetoothCharacteristic> charac01 = new ArrayList<>();
        charac01.add(new FioTBluetoothCharacteristic(CHARAC_01_SERVICE_01_UUID, false));
        charac01.add(new FioTBluetoothCharacteristic(CHARAC_02_SERVICE_01_UUID, false));
        charac01.add(new FioTBluetoothCharacteristic(CHARAC_03_SERVICE_01_UUID, false));
        charac01.add(new FioTBluetoothCharacteristic(CHARAC_04_SERVICE_01_UUID, false));
        charac01.add(new FioTBluetoothCharacteristic(CHARAC_05_SERVICE_01_UUID, false));

        ArrayList<FioTBluetoothCharacteristic> charac02 = new ArrayList<>();
        charac02.add(new FioTBluetoothCharacteristic(CHARAC_01_SERVICE_02_UUID, false));

        ArrayList<FioTBluetoothCharacteristic> charac03 = new ArrayList<>();
        charac03.add(new FioTBluetoothCharacteristic(CHARAC_01_SERVICE_03_UUID, false));
        charac03.add(new FioTBluetoothCharacteristic(CHARAC_02_SERVICE_03_UUID, false));
        charac03.add(new FioTBluetoothCharacteristic(CHARAC_03_SERVICE_03_UUID, false));

        ArrayList<FioTBluetoothCharacteristic> charac04 = new ArrayList<>();
        charac04.add(new FioTBluetoothCharacteristic(CHARAC_01_SERVICE_04_UUID, false));
        charac04.add(new FioTBluetoothCharacteristic(CHARAC_02_SERVICE_04_UUID, false));
        charac04.add(new FioTBluetoothCharacteristic(CHARAC_03_SERVICE_04_UUID, false));
        charac04.add(new FioTBluetoothCharacteristic(CHARAC_04_SERVICE_04_UUID, true));
        charac04.add(new FioTBluetoothCharacteristic(CHARAC_05_SERVICE_04_UUID, true));

        ArrayList<FioTBluetoothService> services = new ArrayList<>();
        services.add(new FioTBluetoothService(SERVICE_01_UUID, charac01));
        services.add(new FioTBluetoothService(SERVICE_02_UUID, charac02));
        services.add(new FioTBluetoothService(SERVICE_03_UUID, charac03));
        services.add(new FioTBluetoothService(SERVICE_04_UUID, charac04));

        adapter_devices = new DevicesInfoAdapter(connectedDeviceList);
        scanManager.stop();
        final FioTManager manager = new FioTManager(this, scanDeviceList.get(position).getDevice(), services);
        manager.connect(10000);
        connectingManager = manager;
        progressDialog = ProgressDialog.show(this, null, "Connecting ...", true, false);
        progressDialog.show();
        manager.setFioTConnectManagerListener(new FioTManager.FioTConnectManagerListener() {
            @Override
            public void onConnectFail(int error) {
                Log.i(TAG, "onConnectFail: ");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        DialogUtils.showMessage(MainActivity.this, "Could not connect to device",
                                true,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                    }
                });
            }

            @Override
            public void onConnected() {
                Log.i(TAG, "onConnected: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        enterDeviceInfo();
                    }
                });
            }

            @Override
            public void onDisconnected(FioTManager manager) {
                Log.i(TAG, "onDisconnected: ");
                removeDevice(manager);
                handleBleDisconnect(manager);
                manager.end();
                deviceIndex = 0;
            }

            @Override
            public void onNotify(FioTBluetoothCharacteristic characteristic) {
                Log.i(TAG, "onNotify: " + characteristic);
                ByteUtils.printArray(characteristic.getUuid(), characteristic.getCharacteristic().getValue());

                parseData(characteristic);
            }

            @Override
            public void onRead(FioTBluetoothCharacteristic characteristic) {
                Log.i(TAG, "onRead: " + characteristic.getUuid());
                ByteUtils.printArray(characteristic.getUuid(), characteristic.getCharacteristic().getValue());

                final byte[] data = characteristic.getCharacteristic().getValue();
                if (characteristic.getUuid().equalsIgnoreCase(CHARAC_02_SERVICE_04_UUID)) {
                    if (data[0] == 0x02) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_sync_time_now.setText(parseTimeFromDevice(data));
                            }
                        });
                    }
                }
            }

        });
    }

    private void removeDevice(FioTManager manager) {
        for (Iterator<DeviceInfo> iterator = connectedDeviceList.iterator(); iterator.hasNext(); ) {
            DeviceInfo deviceInfo = iterator.next();
            if (deviceInfo.getManager() == manager) {
                iterator.remove();
            }

        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                adapter_devices.notifyDataSetChanged();
            }
        });
    }

    private void handleActiveBackupPower(byte[] data) {
        if (new String(data).equalsIgnoreCase("W01")) {
            stopActiveBackupPower();
            DialogUtils.showMessage(this, "WARNING", "OK", "Device Too Hot Please Retry After 5 Minutes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        } else if (new String(data).equalsIgnoreCase("W00")) {
            pauseActiveBackupPower();
            DialogUtils.showMessage(this, "WARNING", "YES", "NO", "Freezing Conditions Detected Unsafe To Proceed\n" +
                    "Do You Still Want To Continue?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (connectedDeviceList.size() > 0) {
                        connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_03_SERVICE_04_UUID, new byte[]{0x0e, 0x01});
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resumeActiveBackupPower();
                        }
                    }, 500);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    stopActiveBackupPower();
                }
            });
        } else if (new String(data).equalsIgnoreCase("W02")) {
            pauseActiveBackupPower();
            DialogUtils.showMessage(this, "CAUTION", "YES", "NO", "Battery Not Present Or Below Charging Level Do You Still Want To Proceed?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (connectedDeviceList.size() > 0) {
                        connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_03_SERVICE_04_UUID, new byte[]{0x0f, 0x01});
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resumeActiveBackupPower();
                        }
                    }, 500);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    stopActiveBackupPower();
                }
            });
        } else if (new String(data).equalsIgnoreCase("W03")) {
            stopActiveBackupPower();
            DialogUtils.showMessage(this, "WARNING", "YES", "Booster Disconnected Device Too Hot Please Retry After 5 Minutes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        } else if (new String(data).equalsIgnoreCase("W04")) {
            stopActiveBackupPower();
            DialogUtils.showMessage(this, null, "YES", "Charging Voltage Detected" +
                    "Booster Connection Aborted To Avoid Booster Damage", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        } else if (new String(data).equalsIgnoreCase("W05")) {
            stopActiveBackupPower();
            DialogUtils.showMessage(this, null, "YES", "Main Battery is charging", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
    }

    private void parseData(FioTBluetoothCharacteristic characteristic) {
        final byte[] data = characteristic.getCharacteristic().getValue();

        if (characteristic.getUuid().equalsIgnoreCase(CHARAC_05_SERVICE_04_UUID)) {
            handleActiveBackupPower(data);
        } else if (characteristic.getUuid().equalsIgnoreCase(CHARAC_04_SERVICE_04_UUID)) {
            Log.i(TAG, "parseData: ");
            if (data[0] == 0x02) {
                //
                handleTimeStamp(data);
            } else if (data[0] == 0x03) {
                handleReceiveTemperature(data);
            } else if (data[0] == 0x04) {
                handlePackTemp(data);
            } else if (data[0] == 0x05) {
                handleCurrentBatteryVoltage(data);
            } else if (data[0] == 0x06) {
                handlePackVolt(data);
            } else if (data[0] == 0x07) {
                handleLoadTime(data);
            } else if (data[0] == 0x08) {
                handleLoadVolt(data);
            } else if (data[0] == 0x09) {
                handleChargeTime(data);
            } else if (data[0] == 0x0A) {
                handleChargeVolt(data);
            } else if (data[0] == 0x0b) {
                handlePackMainPin(data);
            } else if (data[0] == 0x0c) {
                handlePackChargingPin(data);
            } else if (data[0] == 0x0D) {
                handleBatteryStateOfCharge(data);
            }
        }
    }

    private void handlePackChargingPin(byte[] data) {
        if (workingMode == WORKING_MODE.DATA_LOG) {
            dataLogStr.append("\nPack chargin pin: " + String.format("%.2f", ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4)) / 1000.0f));
        }
    }

    private void handlePackMainPin(byte[] data) {
        if (workingMode == WORKING_MODE.DATA_LOG) {
            dataLogStr.append("\nPack main Pin: " + String.format("%.2f", ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4)) / 1000.0f));
        }
    }

    private void handlePackVolt(byte[] data) {
        if (workingMode == WORKING_MODE.DATA_LOG) {
            dataLogStr.append("\nPack volt: " + String.format("%.2f Volts", ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4)) / 1000.0f));
        }
    }

    private void handlePackTemp(byte[] data) {
        if (workingMode == WORKING_MODE.DATA_LOG) {
            dataLogStr.append("\nPack temperature: " + String.format("%.2f C", ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4))));
        }
    }

    private void handleTimeStamp(byte[] data) {
        if (workingMode == WORKING_MODE.DATA_LOG) {
            if (dataLogStr.length() > 0) {
                dataLogStr.append("\n\n");
            }

            dataLogStr.append("Timestamp: " + parseTimeFromDevice(data));
        }
    }

    private void handleChargeVolt(byte[] data) {
        final float val = ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (val > 15000) {
                    tv_charging_system_health.setText("Overcharging".toUpperCase());
                } else if (val >= 13800 && val <= 15000) {
                    tv_charging_system_health.setText("Normal".toUpperCase());
                } else if (val < 13800) {
                    tv_charging_system_health.setText("Undercharging".toUpperCase());
                }

                tv_charging_voltage.setText(String.format("%.2f VOLTS", val / 1000.0f));
            }
        });
    }

    private void handleChargeTime(final byte[] data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_charging_voltage_time.setText(parseTimeFromDevice(data));
                tv_charging_system_health_time.setText(parseTimeFromDevice(data));
            }
        });
    }

    private void handleLoadVolt(byte[] data) {
        final float val = ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (val >= 10400) {
                    tv_battery_state_of_health_01.setText("GOOD");
                } else if (val < 10400 && val > 900) {
                    tv_battery_state_of_health_01.setText("WEEK");
                } else if (val <= 900) {
                    tv_battery_state_of_health_01.setText("BAD");
                }

                tv_battery_load_voltage.setText(String.format("%.2f VOLTS", val / 1000.0f));
                Prefences.saveBatteryStateOfHealth(MainActivity.this, String.format("%.2f VOLTS", val / 1000.0f));
            }
        });
    }

    private void handleLoadTime(final byte[] data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_battery_load_voltage_time.setText(parseTimeFromDevice(data));
                tv_battery_state_of_health_01_time.setText(parseTimeFromDevice(data));
                Prefences.saveBatteryStateOfHealthTime(MainActivity.this, parseTimeFromDevice(data));
            }
        });
    }

    private void handleReceiveTemperature(byte[] data) {
        float temperature = ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4));
        Log.i(TAG, "parseData: temperature = " + temperature);


        final String val;
        if (Prefences.getImperial(MainActivity.this)) {
            val = String.format("%.2fC", temperature);
        } else {
            val = String.format("%.2fF", 32 + (temperature * 9 / 5));
        }

        if (workingMode == WORKING_MODE.DATA_LOG) {
            dataLogStr.append("\nMCU temperature: " + String.format("%.2f C", ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4))));
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_temperature.setText(val);
                }
            });
        }

    }

    private void handleBatteryStateOfCharge(final byte[] data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data[4] == 2) {
                    tv_battery_state_of_charge.setText("HIGH");
                } else if (data[4] == 1) {
                    tv_battery_state_of_charge.setText("MEDIUM");
                } else if (data[4] == 0) {
                    tv_battery_state_of_charge.setText("LOW");
                }
            }
        });
    }

    private void handleCurrentBatteryVoltage(byte[] data) {
        final float val = ByteUtils.toFloat(ByteUtils.subByteArray(data, 1, 4));
        Log.i(TAG, "parseData: main volt = " + val);

        final String str = String.format("%.2f VOLTS", val / 1000.0f);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (workingMode == WORKING_MODE.DATA_LOG) {
                    dataLogStr.append("\nMain Voltage: " + String.format("%.2f volts", val / 1000.0f));
                } else {
                    tv_current_battery_voltage.setText(str);
                    tv_battery_state_of_health.setText(Prefences.getBatteryStateOfHealth(MainActivity.this));
                    tv_battery_state_of_health_time.setText(Prefences.getBatteryStateOfHealthTime(MainActivity.this));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        endConnections();
    }

    @Override
    public void scrollendlistenter() {
        if (connectedDeviceList.size() <= 0) {
            return;
        }
        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
//        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        int firstItemPos = layoutManager.findFirstVisibleItemPosition();
        View firstItemView = layoutManager.findViewByPosition(firstItemPos);
        int lastItemPos = layoutManager.findLastVisibleItemPosition();
        View lastItemView = layoutManager.findViewByPosition(lastItemPos);

        float p2 = Math.abs(lastItemView.getX()) / lastItemView.getWidth();
        float p1 = Math.abs(firstItemView.getX()) / firstItemView.getWidth();

        if (p1 <= p2) {
            mRecyclerView.smoothScrollToPosition(firstItemPos);
            deviceIndex = firstItemPos;
            //mRecyclerView.scrollToPosition(firstItemPos);
        } else {
            mRecyclerView.smoothScrollToPosition(lastItemPos);
            deviceIndex = lastItemPos;
//            mRecyclerView.scrollToPosition(lastItemPos);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                pressBack();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void endConnections() {
        for (DeviceInfo fioTManager : connectedDeviceList) {
            fioTManager.getManager().end();
        }
    }

    private void pressBack() {
        if (layout_check_battery.isShown()) {
            // tv_toolbar_title.setText("");
            toolbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            layout_home.setVisibility(View.VISIBLE);
            layout_check_battery.setVisibility(View.GONE);
        }

        if (layout_settings.isShown()) {
            // tv_toolbar_title.setText("");
            toolbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            layout_home.setVisibility(View.VISIBLE);
            layout_settings.setVisibility(View.GONE);
        }

        if (layout_test_battery.isShown()) {
            //tv_toolbar_title.setText("");
            toolbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            layout_home.setVisibility(View.VISIBLE);
            layout_test_battery.setVisibility(View.GONE);
        }

        if (layout_datalog.isShown()) {
            toolbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            layout_datalog.setVisibility(View.GONE);
            layout_home_reports.setVisibility(View.VISIBLE);
        }

        if (layout_find_device.isShown()) {
            toolbar.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            layout_add_device.setVisibility(View.GONE);
            layout_home_more.setVisibility(View.VISIBLE);
            tv_find_device_tilte.setVisibility(View.GONE);
        }

        if (layout_find_device_info.isShown()) {
            toolbar.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            layout_find_device.setVisibility(View.VISIBLE);
            layout_list_device.setVisibility(View.VISIBLE);
            layout_find_device_info.setVisibility(View.GONE);
            ((TextView) findViewById(tv_toolbar_back)).setText("Back");
            tv_find_device_tilte.setText("Devices");
        }

        if (layout_list_find_device.isShown()) {
            toolbar.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            layout_more.setVisibility(View.VISIBLE);
            layout_list_find_device.setVisibility(View.GONE);
            layout_find_device_info.setVisibility(View.VISIBLE);
            ((TextView) findViewById(tv_toolbar_back)).setText("Back");
            tv_find_device_tilte.setText("Find Device");

            stopScan();
        }

        if (layout_device.isShown()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setVisibility(View.VISIBLE);
            layout_list_find_device.setVisibility(View.VISIBLE);
            layout_device.setVisibility(View.GONE);
            ((TextView) findViewById(tv_toolbar_back)).setText("Back");
            tv_find_device_tilte.setText("Finding devices ...");
            recycler_view_list_device.setVisibility(View.GONE);
            tv_no_devices_found.setVisibility(View.VISIBLE);

            stopAddDevice();
            startScan();
        }

        if (layout_viewlog.isShown()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setVisibility(View.VISIBLE);
            layout_datalog.setVisibility(View.VISIBLE);
            layout_viewlog.setVisibility(View.GONE);
            ((TextView) findViewById(tv_toolbar_back)).setText("Back");
        }
    }

    private void stopAddDevice() {
        if (connectingManager != null) {
            connectingManager.end();
            connectingManager = null;
        }

        scanDeviceList.clear();
        dAdapter.notifyDataSetChanged();
    }

    private void stopScan() {
        if (scanManager != null) {
            scanManager.stop();
        }
    }

    private void startScan() {
        scanManager.start("SJ_Battery_Boost", true, new FioTScanManager.ScanManagerListener() {
            @Override
            public void onFoundDevice(BluetoothDevice device, int rssi) {
                if(scanDeviceList.size()==0){
                    scanDeviceList.add(new Device(device));
                    Log.i(TAG, "onFoundDevice0: "+device.getAddress());
                }
                else {
                    for (int i = 0; i < scanDeviceList.size(); i++) {
                        if (!scanDeviceList.get(i).getDevice().getAddress().equalsIgnoreCase(device.getAddress()) && i == scanDeviceList.size() - 1) {
                            scanDeviceList.add(new Device(device));
                            Log.i(TAG, "onFoundDevice1: "+device.getAddress());
                        }
                    }
                }
                dAdapter.notifyDataSetChanged();
                recycler_view_list_device.setVisibility(View.VISIBLE);
                tv_no_devices_found.setVisibility(View.GONE);
            }
        });
    }

    private void startDataLog() {
        if (dataLogStr == null) {
            dataLogStr = new StringBuilder();
        }

        if (datalogTimer != null) {
            datalogTimer.cancel();
        }

        datalogTimer = new Timer();
        datalogTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                workingMode = WORKING_MODE.DATA_LOG;
                if (connectedDeviceList.size() > 0) {
                    connectedDeviceList.get(deviceIndex).getManager().write(CHARAC_01_SERVICE_04_UUID, new byte[]{0x6});
                }
            }
        }, 0, Integer.parseInt(et_dataLog_interval.getText().toString()));
    }

    private void stopDataLog() {
        if (datalogTimer != null) {
            datalogTimer.cancel();
        }
    }

    public void clickRetrieveDatalog(View view) {
        tv_view_log.setText(dataLogStr);
        layout_datalog.setVisibility(View.GONE);
        layout_viewlog.setVisibility(View.VISIBLE);
        ((TextView) findViewById(tv_toolbar_back)).setText(title_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void clickEraseAppData(View view) {
        if (dataLogStr != null) {
            dataLogStr.setLength(0);
        }
    }
    public void clickNewDevice(View view){
        tabHost.setCurrentTab(2);
        setTabColor();
        clickAddDevice();
    }

    private String parseTimeFromDevice(byte[] bytes) {
        long current_timestamp = (ByteUtils.toLongFirstMostSignificant(
                ByteUtils.subByteArray(bytes, 1, 4)) + (1475280000L)) * 1000 +
                ByteUtils.toLongFirstLeastSignificant(ByteUtils.subByteArray(bytes, 5, 2));
        return DateTimeUtils.epochToStringNoTimeZone(current_timestamp, "MM/dd/yyyy HH:mm:ss.SSS");
    }

    private void showDeviceDisconnected(FioTManager manager) {
        Log.i(TAG, "showDeviceDisconnected: " + manager.getDevice().getAddress());
        resetLayoutCheckBatteryStatus();
        resetLayoutTestBattery();
        DialogUtils.showMessage(MainActivity.this,
                "Device " + manager.getDevice().getName() + " disconnected",
                false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestAddNewDevice();
                    }
                });
    }

    private void handleBleDisconnect(final FioTManager manager) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "working mode: " + workingMode.name());

                switch (workingMode) {
                    case ACTIVE_BACKUP_POWER:
                        stopActiveBackupPower();

                        break;

                    case DATA_LOG:
                        stopDataLog();
                        showDeviceDisconnected(manager);
                        break;

                    case CHECK_BATTERY_STATUS:
                    case TEST_BATTERY_AND_CHARGING_SYSTEM:
                    case SETTING:
                    default:
                        showDeviceDisconnected(manager);
                        break;
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (layout_home.isShown() || layout_home_reports.isShown() || layout_home_more.isShown()) {
            DialogUtils.showMessage(this, "Are you sure you want to exit?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.super.onBackPressed();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        } else {
            pressBack();
        }
    }

    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.Holder> {

        private String LOG_TAG = "MyRecyclerViewAdapter";
        private ArrayList<DeviceInfo> mDataset;
        private MyClickListener myClickListener;

        public MyRecyclerViewAdapter(ArrayList<DeviceInfo> myDataset) {
            mDataset = myDataset;
        }


        public void setOnItemClickListener(MyClickListener myClickListener) {
            this.myClickListener = myClickListener;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_product, parent, false);

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

}
