package com.examples.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.examples.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MyCameraActivity extends AppCompatActivity {

    private static final String TAG = MyCameraView.class.getName();
    private Camera mCamera;
    private MyCameraView mPreview;

    private static final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                File pictureFile = MyCameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (pictureFile == null){
                    Log.d(TAG, "Error creating media file, check storage permissions");
                    return;
                }
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        // Create an instance of Camera
        mCamera = MyCameraUtils.getCameraInstance();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new MyCameraView(this, mCamera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Button captureButton = findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
            }
        });

    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
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
