



Example
#Step1 Xay dung interface
```
interface MyCallback {
        void callbackCall(String result);
    }
```
#Step2 tao ham co thread yeu cau tra ve khi ket thuc
```
 private void test(final int max, final MyCallback myCallback){
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i<=max){
                    i++;
                    Log.i(TAG, "run: "+i);
                }
                s="done";
                myCallback.callbackCall("done");

            }
        });
        thread.start();
    }
```
#Step3 Su dung
```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test(100, new MyCallback() {
            @Override
            public void callbackCall(String result) {
                Log.i(TAG, "callbackCall: "+result);
            }
        });

        Log.i(TAG, "onCreate: "+s);



    }
```
