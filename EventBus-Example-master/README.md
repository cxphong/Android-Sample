## 1. Giới thiệu

EventBus là thư viện giao tiếp giữa các Activity, Fragment, Service, Thread, ...
thay thế cho BroadCastReceiver của Android.

Ưu điểm là có thể pass các object thay vì primitive data type của BroadcastReceiver.

## 2. Cài đặt

*https://github.com/greenrobot/EventBus*

```xml
compile 'org.greenrobot:eventbus:3.0.0'
```

## 3. Cách sử dụng

### Tạo 1 MessageEvent tuỳ ý

Object này dùng để pass đi 

```java
public class MessageEvent {
    public final String message;

    public MessageEvent(String message) {
        this.message = message;
    }
}
```

### Lắng nghe MessageEvent


```java

/* Tên hàm tuỳ ý, chỉ kiểm tra input parameter
 * Ví dụ: Hàm này chỉ lắng nghe MessageEvent
 */
@Subscribe (threadMode = ThreadMode.MAIN)
public void onMessageEvent(MessageEvent event){
    userStatus.setText("User Status : Logged in, message: " + event.message);
}

/* Lưu ý nới register & unegister */
@Override
public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this); // registering the bus
}

@Override
public void onStop() {
    EventBus.getDefault().unregister(this); // un-registering the bus
    super.onStop();
}
```

## 4. Cao cấp hơn: Thread

http://greenrobot.org/eventbus/documentation/delivery-threads-threadmode/

###  POSTING

*subcribe* sẽ chạy trên cùng thread với *post*

###  MAIN

*subcribe* sẽ chạy trên main thread

### BACKGROUND

*subcribe* sẽ ko chạy trên main thread. Nếu thread của *post* không là main thread thì
sẽ chạy luôn mà không cần tạo thread mới. Nêu *post* đang ở main thread thì tự động tạo
1 thread để chạy *subcribe*

### ASYNC

*subcribe* sẽ chạy trên 1 thread mới mà không phải là main thread hay là *post* thread
