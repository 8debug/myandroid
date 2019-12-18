package com.examples.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.examples.R;

import java.util.ArrayList;
import java.util.List;

public class MyCameraActivity extends AppCompatActivity {

    private static final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> listPermission = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if( grantResults[i]== PackageManager.PERMISSION_DENIED ){
                listPermission.add(permissions[i]);
            }
        }
        requestAppPermissions(listPermission.toArray(new String[]{}));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        requestAppPermissions(permissions);
    }

    void requestAppPermissions(String[] permissions){
        List<String> listPermission = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if( checkSelfPermission(permission)== PackageManager.PERMISSION_DENIED){
                    listPermission.add(permission);
                }
            }
            if( listPermission.size()>0 ){
                requestPermissions(listPermission.toArray(new String[]{}), 1101);
            }
        }

        if( listPermission.size()==0 || !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) ){
            Toast.makeText(this, "请确保设备有相机功能，且拥有相机权限", Toast.LENGTH_SHORT).show();
        }
    }
}
