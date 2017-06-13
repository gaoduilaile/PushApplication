package com.example.administrator.pushapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class+"";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniDate();
        initView();


    }

    private void iniDate() {

        String jsonContext="{ \"firstName\":\"John\" , \"lastName\":\"Doe\" }";

        JsonReader jsonReader = new JsonReader(new StringReader(jsonContext));//其中jsonContext为String类型的Json数据
        jsonReader.setLenient(true);
        Gson gson=new Gson();
        final Bean bean = gson.fromJson(jsonReader, Bean.class);
        Log.e(TAG,"----"+bean.getFirstName()+"===="+bean.getLastName());
        Log.e(TAG,"--getNewMac--"+getNewMac());
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.text_view);
    }

    /**
     * 通过网络接口取
     * @return
     */
    private static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
