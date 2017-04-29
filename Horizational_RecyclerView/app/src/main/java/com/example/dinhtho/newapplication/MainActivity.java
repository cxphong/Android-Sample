package com.example.dinhtho.newapplication;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.MyClickListener {
    private static final String TAG = "MainActivity";
    private ArrayList<Image>imageList=new ArrayList<>();
    private  RecyclerView mRecyclerView;
    private  RecyclerView.Adapter mAdapter;
    private MyRecyclerViewAdapter.MyClickListener myClickListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions_camera = {"android.permission.READ_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(MainActivity.this, permissions_camera, 0x11);

        ArrayList <String> list_image=getAllShownImagesPath(MainActivity.this);
        for (String path:list_image){
            Image image=new Image(path);
            imageList.add(image);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyRecyclerViewAdapter(MainActivity.this,this,imageList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index;
        StringTokenizer st1;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        // column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index);
            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x11) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
//
            }
        }
    }


    @Override
    public void onItemClick(int position) {
        Log.i(TAG, "onItemClick: "+position);
    }
}
