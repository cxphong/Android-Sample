
# EventBus


=======
# Project Name



## Installation



`compile 'org.greenrobot:eventbus:3.0.0'`


Example
#1. phia gui
```
private EventBus bus = EventBus.getDefault();`
bus.postSticky(new LoginEvent(userName.getText().toString()));
```
#2. phia nhan
`private EventBus bus = EventBus.getDefault();`

``` 
@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event){
        userStatus.setText("User Status : Logged in, userName: " + event.userName);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this); // registering the bus
    }

    @Override
    public void onStop() {
        bus.unregister(this); // un-registering the bus
        super.onStop();
    }
```


