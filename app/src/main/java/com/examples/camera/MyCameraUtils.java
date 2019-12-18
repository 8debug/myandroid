package com.examples.camera;

import android.hardware.Camera;

public class MyCameraUtils {

    /**
     * 访问相机
     * @return
     * @throws Exception
     */
    public static Camera getCameraInstance() throws Exception {
        Camera c;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e){
            // Camera is not available (in use or does not exist)
            throw new Exception("相机正在被使用或此设备无相机", e);
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * 检查相机功能
     * 获取有关相机功能的详细信息
     */
    public static void s(Camera camera){
        Camera.Parameters parameters = camera.getParameters();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(0, cameraInfo);

    }

}
