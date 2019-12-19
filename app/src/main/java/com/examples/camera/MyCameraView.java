package com.examples.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * SurfaceView 可以显示来自相机的实时图像数据，以便用户可以构图并捕捉招聘或视频。
 * SurfaceHolder.Callback 捕获用于创建和销毁视图的回调事件，这些是分配相机预览输入的必需事件。
 */
public class MyCameraView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MyCameraView.class.getName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Context mContext;
    private Camera.Size mFitPreviewSize;
    private Camera.Size mFitPictureSize;
    private int width;
    private int height;

    public Camera getCamera(){
        return mCamera;
    }

    /*public MyCameraView(Context context, Camera camera) {
        super(context);
        mContext = context;
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mFitPreviewSize = getBestPreviewSize(
                mCamera.getParameters().getSupportedPreviewSizes(),
                getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels);
        mFitPictureSize = getBestPreviewSize(
                mCamera.getParameters().getSupportedPictureSizes(),
                getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels);
    }*/

    public MyCameraView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mCamera = MyCameraUtils.getCameraInstance();
        mFitPreviewSize = getBestPreviewSize( mCamera.getParameters().getSupportedPreviewSizes(), width, height );
        mFitPictureSize = getBestPreviewSize( mCamera.getParameters().getSupportedPictureSizes(), width, height );
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        log("surfaceCreated======");
        try {
            if( mCamera==null ){
                mCamera = MyCameraUtils.getCameraInstance();
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
                log("info.orientation==="+info.orientation);
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException e) {
            releaseMyCamera();
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    void releaseMyCamera(){
        if(mCamera!=null){
            mCamera.setPreviewCallback(null); //必须在前！！！
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {

            mFitPreviewSize = getBestPreviewSize( mCamera.getParameters().getSupportedPreviewSizes(), this.width, this.height );
            mFitPictureSize = getBestPreviewSize( mCamera.getParameters().getSupportedPictureSizes(), this.width, this.height );

            mCamera.setPreviewDisplay(mHolder);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(mFitPreviewSize.width, mFitPreviewSize.height);
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setFocusMode(Camera.Parameters.FLASH_MODE_AUTO);
            parameters.setPictureSize(mFitPictureSize.width, mFitPictureSize.height);
            mCamera.setParameters(parameters);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

     static void log(Object obj){
        Log.d(TAG, String.valueOf(obj));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Take care of releasing the Camera preview in your activity.
        releaseMyCamera();
    }

    public Camera.Size getFitPreviewSize(){
        return mFitPreviewSize;
    }

    private Camera.Size getBestPreviewSize(List<Camera.Size> sizes, int width, int height) {
        Camera.Size result = null;
        //特别注意此处需要规定rate的比是大的比小的，不然有可能出现rate = height/width，但是后面遍历的时候，current_rate = width/height,所以我们限定都为大的比小的。
        float rate = (float) Math.max(width, height)/ (float)Math.min(width, height);
        float tmp_diff;
        float min_diff = -1f;
        for (Camera.Size size : sizes) {
            float current_rate = (float) Math.max(size.width, size.height)/ (float)Math.min(size.width, size.height);
            tmp_diff = Math.abs(current_rate-rate);
            if( min_diff < 0){
                min_diff = tmp_diff ;
                result = size;
            }
            if( tmp_diff < min_diff ){
                min_diff = tmp_diff ;
                result = size;
            }
        }
        return result;
    }
}
