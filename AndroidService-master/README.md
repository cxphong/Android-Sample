Step1: Tạo class Service
Step2: Viết lại các hàm
```
@Override
   public void onCreate(){
       super.onCreate();
       // Tạo đối tượng MediaPlayer, chơi file nhạc của bạn.
       mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mysong);
   }
 
   @Override
   public int onStartCommand(Intent intent, int flags, int startId){
       // Chơi nhạc.
       mediaPlayer.start();
 
       return START_STICKY;
   }
 
   // Hủy bỏ dịch vụ.
   @Override
   public void onDestroy() {
       // Giải phóng nguồn dữ nguồn phát nhạc.
       mediaPlayer.release();
       super.onDestroy();
   }
```
Step3:Sử dụng
```
Intent myIntent = new Intent(context, ClockService.class);
context.startService(myIntent);
```
```
Intent myIntent = new Intent(context, ClockService.class);
context.stopService(myIntent);
```
