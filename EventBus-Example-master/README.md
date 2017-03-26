<<<<<<< HEAD
# EventBus


=======
# Project Name

TODO: Write a project description
>>>>>>> d82b684831088abbf9750b90dc75ae8a0e9a9069

## Installation


<<<<<<< HEAD
`compile 'org.greenrobot:eventbus:3.0.0'`


Example
1. phia gui
`private EventBus bus = EventBus.getDefault();`
`bus.postSticky(new LoginEvent(userName.getText().toString()));`
2. phia nhan
`private EventBus bus = EventBus.getDefault();`
`@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)`
 `   public void onLoginEvent(LoginEvent event){`
   `     userStatus.setText("User Status : Logged in, userName: " + event.userName);`
`    }`

`    @Override`
 `   public void onStart() {`
  `      super.onStart();`
        bus.register(this); // registering the bus
  `  }`

   ` @Override`
  `  public void onStop() {`
        bus.unregister(this); // un-registering the bus
        super.onStop();
  `  }`

=======



    `compile fileTree(dir: 'libs', include: ['*.jar'])`
    `compile 'com.android.support:appcompat-v7:24.0.0-beta1'`
    `compile 'com.android.support:design:24.0.0-beta1'`
    `compile 'com.android.support:support-v4:24.0.0-beta1'`
 
    // Glide image library
    `compile 'com.github.bumptech.glide:glide:3.7.0'`
>>>>>>> d82b684831088abbf9750b90dc75ae8a0e9a9069
