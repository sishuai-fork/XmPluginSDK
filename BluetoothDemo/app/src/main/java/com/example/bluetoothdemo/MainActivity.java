package com.example.bluetoothdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaomi.smarthome.bluetooth.BluetoothManager;
import com.xiaomi.smarthome.bluetooth.XmBluetoothDevice;
import com.xiaomi.smarthome.bluetooth.search.BluetoothSearchHelper;
import com.xiaomi.smarthome.bluetooth.search.BluetoothSearchRequest;
import com.xiaomi.smarthome.bluetooth.search.BluetoothSearchResponse;
import com.xiaomi.smarthome.bluetooth.security.BleSecurityRegister;
import com.xiaomi.smarthome.bluetooth.utils.BluetoothLog;

public class MainActivity extends AppCompatActivity {

    private Button mBtnScan;
    private Button mBtnCancel;

    private BluetoothSearchRequest mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnScan = (Button) findViewById(R.id.scan);
        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        mBtnCancel = (Button) findViewById(R.id.cancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mRequest != null) {
                    BluetoothSearchHelper.getInstance().cancelSearch(mRequest);
                }
            }
        });
    }

    private void scan() {
        mRequest = new BluetoothSearchRequest.Builder().searchBluetoothLeDevice(10000).build();
        BluetoothSearchHelper.getInstance().startSearch(mRequest, new BluetoothSearchResponse() {
            @Override
            public void onSearchStarted() {
                BluetoothLog.i("onSearchStarted");
            }

            @Override
            public void onDeviceFounded(XmBluetoothDevice device) {
                BluetoothLog.i("onDeviceFounded" + device);
            }

            @Override
            public void onSearchStopped() {
                BluetoothLog.i("onSearchStopped");
                mRequest = null;
            }

            @Override
            public void onSearchCanceled() {
                BluetoothLog.i("onSearchCanceled");
                mRequest = null;
            }
        });
    }

    private void register() {
        BleSecurityRegister register = new BleSecurityRegister("08:7C:BE:0D:CD:1B", 0x9c);
        register.register(new BleSecurityRegister.BleRegisterResponse() {
            @Override
            public void onResponse(int i, byte[] bytes) {
                if (i == BluetoothManager.Code.REQUEST_SUCCESS) {
                    BluetoothLog.i("REQUEST_SUCCESS");
                } else {
                    BluetoothLog.i("REQUEST_FAILED");
                }
            }
        });
    }
}
