package edu.weber.scotthadzik.cobrabcmapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.ParcelFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    // TAG is used to debug in Android logcat console
    private static final String TAG = "ArduinoAccessory";

    private static final String ACTION_USB_PERMISSION = "com.google.android.DemoKit.action.USB_PERMISSION";

    private UsbManager mUsbManager;
    private PendingIntent mPermissionIntent;
    private boolean mPermissionRequestPending;
    private ToggleButton button1;
    private ToggleButton button2;
    private ToggleButton button3;
    private ToggleButton button4;
    private ToggleButton button5;
    private ToggleButton button6;
    private ToggleButton button7;
    private ToggleButton button8;

    UsbAccessory mAccessory;
    ParcelFileDescriptor mFileDescriptor;
    FileInputStream mInputStream;
    FileOutputStream mOutputStream;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        openAccessory(accessory);
                    } else {
                        Log.d(TAG, "permission denied for accessory "
                                + accessory);
                    }
                    mPermissionRequestPending = false;
                }
            } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
                UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                if (accessory != null && accessory.equals(mAccessory)) {
                    closeAccessory();
                }
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        registerReceiver(mUsbReceiver, filter);

        setContentView(R.layout.activity_main);
        button1 = (ToggleButton) findViewById(R.id.toggleButton1);
        button2 = (ToggleButton) findViewById(R.id.toggleButton2);
        button3 = (ToggleButton) findViewById(R.id.toggleButton3);
        button4 = (ToggleButton) findViewById(R.id.toggleButton4);
        button5 = (ToggleButton) findViewById(R.id.toggleButton5);
        button6 = (ToggleButton) findViewById(R.id.toggleButton6);
        button7 = (ToggleButton) findViewById(R.id.toggleButton7);
        button8 = (ToggleButton) findViewById(R.id.toggleButton8);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mInputStream != null && mOutputStream != null) {
            return;
        }

        UsbAccessory[] accessories = mUsbManager.getAccessoryList();
        UsbAccessory accessory = (accessories == null ? null : accessories[0]);
        if (accessory != null) {
            if (mUsbManager.hasPermission(accessory)) {
                openAccessory(accessory);
            } else {
                synchronized (mUsbReceiver) {
                    if (!mPermissionRequestPending) {
                        mUsbManager.requestPermission(accessory,mPermissionIntent);
                        mPermissionRequestPending = true;
                    }
                }
            }
        } else {
            Log.d(TAG, "mAccessory is null");
        }
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        closeAccessory();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    private void openAccessory(UsbAccessory accessory) {
        mFileDescriptor = mUsbManager.openAccessory(accessory);
        if (mFileDescriptor != null) {
            mAccessory = accessory;
            FileDescriptor fd = mFileDescriptor.getFileDescriptor();
            mInputStream = new FileInputStream(fd);
            mOutputStream = new FileOutputStream(fd);
            Log.d(TAG, "accessory opened");
        } else {
            Log.d(TAG, "accessory open fail");
        }
    }


    private void closeAccessory() {
        try {
            if (mFileDescriptor != null) {
                mFileDescriptor.close();
            }
        } catch (IOException e) {
        } finally {
            mFileDescriptor = null;
            mAccessory = null;
        }
    }

    public void toggleRelay1(View v){

        byte[] buffer = new byte[1];

        if(button1.isChecked())
            buffer[0]=(byte)10; // button says on, light is off
        else
            buffer[0]=(byte)11; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }
    public void toggleRelay2(View v){

        byte[] buffer = new byte[1];

        if(button2.isChecked())
            buffer[0]=(byte)20; // button says on, light is off
        else
            buffer[0]=(byte)21; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer[0]));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }
    public void toggleRelay3(View v){

        byte[] buffer = new byte[1];

        if(button3.isChecked())
            buffer[0]=(byte)30; // button says on, light is off
        else
            buffer[0]=(byte)31; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer[0]));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }

    public void toggleRelay4(View v){

        byte[] buffer = new byte[1];

        if(button4.isChecked())
            buffer[0]=(byte)40; // button says on, light is off
        else
            buffer[0]=(byte)41; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }
    public void toggleRelay5(View v){

        byte[] buffer = new byte[1];

        if(button5.isChecked())
            buffer[0]=(byte)50; // button says on, light is off
        else
            buffer[0]=(byte)51; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer[0]));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }
    public void toggleRelay6(View v){

        byte[] buffer = new byte[1];

        if(button6.isChecked())
            buffer[0]=(byte)60; // button says on, light is off
        else
            buffer[0]=(byte)61; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer[0]));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }

    public void toggleRelay7(View v){

        byte[] buffer = new byte[1];

        if(button7.isChecked())
            buffer[0]=(byte)70; // button says on, light is off
        else
            buffer[0]=(byte)71; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }
    public void toggleRelay8(View v){

        byte[] buffer = new byte[1];

        if(button8.isChecked())
            buffer[0]=(byte)80; // button says on, light is off
        else
            buffer[0]=(byte)81; // button says off, light is on
        Log.e(TAG, String.valueOf(buffer[0]));
        if (mOutputStream != null) {
            try {
                mOutputStream.write(buffer);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }
}
