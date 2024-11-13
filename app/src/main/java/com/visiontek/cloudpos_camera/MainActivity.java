package com.visiontek.cloudpos_camera;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "cloudpos VA 21";
    private static final int REQUEST_CAMERA = 10;
    private static final int REQUEST_STORAGE_READ_SDCARD_open = 80;

    private static final int R_CAM = 1;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Main : onCreate");
        context = this;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_READ_SDCARD_open);
        Log.i(TAG, "Main : onCreate : READ Req permissions ");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_camera:
                Log.d(TAG, "Main : onButtonClick : Camera Clicked ");
                //   Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                // startActivity(intent);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                Log.d(TAG, "Main : onButtonClick : Camera permissions Req");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Main : onStop");
        System.out.println("onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Main : onStart");
        System.out.println("onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    private void Camera() {
        // Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        try {
            PackageManager pm = MainActivity.this.getPackageManager();
            final ResolveInfo mInfo = pm.resolveActivity(i, 0);
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent2.setAction(Intent.ACTION_MAIN);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivityForResult(intent2, R_CAM);
        } catch (Exception e) {
            Log.d(TAG, "Main : Camera : Exception : " + e);
        }
    }

    public void dialogBox(final String st, String msg) {
        Log.d(TAG, "Main : dialogBox : " + st + " : " + " msg ");
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(st);
        alert.setCancelable(false);
        alert.setMessage(msg);
        alert.setPositiveButton("OK",
                (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Log.d(TAG, "Main : onRequestPermissionsResult : Camera Req permissions Granted ");
                        Camera();
                    }
                } else {
                    Log.d(TAG, "Main : onRequestPermissionsResult : Camera Req permissions Denied ");
                    dialogBox("Permissions", "Please allow these permissions in order to use application");
                }
                break;
            default:
                break;
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("Focus debug", "Focus changed !");

        if (!hasFocus) {
            Log.d("Focus debug", "Lost focus !");
        }
    }

}
