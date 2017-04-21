

## Installation
compile 'io.realm:realm-android:0.84.1'

Example
`Realm realm = Realm.getInstance(this);`
##Save object
```
		DeviceInfoDB deviceInfoDB=new DeviceInfoDB();
                deviceInfoDB.setMac(connectingManager.getDevice().getAddress());
                deviceInfoDB.setMake(et_make.getText().toString());
                deviceInfoDB.setModel(et_model.getText().toString());
                deviceInfoDB.setYear(et_year.getText().toString());
                deviceInfoDB.setPic(path_image_file);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(deviceInfoDB);
                realm.commitTransaction();
```

##Get object
```
RealmResults<DeviceInfoDB> realmResults = realm.where(DeviceInfoDB.class).equalTo("mac",connectingManager.getDevice().getAddress()).findAll();
                Log.i(TAG, "run: "+realmResults.size());

                if(realmResults.size()>0){
                    et_make.setText(realmResults.get(0).getMake());
                    et_model.setText(realmResults.get(0).getModel());
                    et_year.setText(realmResults.get(0).getYear());
                    path_image_file=realmResults.get(0).getPic();
                    String filename = realmResults.get(0).getPic().substring(realmResults.get(0).getPic().lastIndexOf("/") + 1);
                    tv_name_picture.setText(filename);
                }else{
                    et_make.setText("");
                    et_model.setText("");
                    et_year.setText("");
                    tv_name_picture.setText("");
                }
```



