package android.fiot.hawk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    class User {
        private String name;
        private int age;
        private String phone;

        public User(String name, int age, String phone) {
            this.name = name;
            this.age = age;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Install
        // (https://github.com/orhanobut/hawk)
        // compile 'com.orhanobut:hawk:2.0.1'

        // Init
        Hawk.init(this).build();

        // Save
        // put(String key, T value)
        Hawk.put("Age", 30);
        Hawk.put("Name", "ABC");
        Hawk.put("Exist", false);
        Hawk.put("Temperature", 12.345);
        Hawk.put("Message", new byte[]{(byte) 0xdd, (byte) 0xff});
        Hawk.put("User", new User("Phong", 27, "012345678"));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Phong1", 27, "012345678"));
        users.add(new User("Phong2", 27, "012345678"));
        users.add(new User("Phong13", 27, "012345678"));
        Hawk.put("Users", users);

        // .. Save every data type

        // Get, null if key not exist
        // get(String key, T defaultValue)
        // get(String key)
        Log.i(TAG, "onCreate: " + Hawk.get("Age", 0));
        Log.i(TAG, "onCreate: " + Hawk.get("Name", ""));
        Log.i(TAG, "onCreate: " + Hawk.get("Exist", true));
        Log.i(TAG, "onCreate: " + Hawk.get("Temperature"));
        Log.i(TAG, "onCreate: " + Hawk.get("Message"));
        Log.i(TAG, "onCreate: " + Hawk.get("User"));
        Log.i(TAG, "onCreate: " + Hawk.get("Users"));
        Log.i(TAG, "onCreate: " + Hawk.get("Usfsfsers"));

        // Check contain
        Log.i(TAG, "onCreate: " + Hawk.contains("Age"));

        // Size
        Log.i(TAG, "onCreate: " + Hawk.count());

        // Delete
        Log.i(TAG, "onCreate: " + Hawk.delete("Age"));
        Log.i(TAG, "onCreate: " + Hawk.deleteAll());
    }
}
