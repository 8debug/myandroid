package com.myutils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class MyImageUtils {

    private static Context getContext(){
        return MyApplication.getContext();
    }

    private static Resources getResources(){
        return getContext().getResources();
    }

    public static void compress(String pathSrc, String pathTarget ) throws IOException {
        compress(new File(pathSrc), new File(pathTarget) );
    }

    // TODO  Luban包中BitmapFactory.decodeStream(in, null, options);返回null，导致报错，需要搞清楚
    public static void compress(File source, File target ) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(source.getAbsolutePath());
        /*Luban.with(getContext())
                .load(source)
                .get(target.getAbsolutePath());*/
        new Compressor(getContext())
//                .setMaxWidth(bitmap.getWidth())
//                .setMaxHeight(bitmap.getHeight())
                .setDestinationDirectoryPath(target.getParent())
                .compressToFile(source, target.getName());
        bitmap.recycle();
    }

    public static void saveGallery(String path){
        saveGallery(new File(path));
    }

    public static void saveGallery(File file){
        try {
            Context context = getContext();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据屏幕坐标将位图截图
     * @param bitmap
     * @param left
     * @param top
     * @param right
     * @param buttom
     * @return
     */
    public static Bitmap getCropBitmap(Bitmap bitmap, int left, int top, int right, int buttom){

        int screenWidth, screenHeight;

        if( getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE ){
            screenWidth = getResources().getDisplayMetrics().widthPixels;
            screenHeight = getResources().getDisplayMetrics().heightPixels;
        }else{
            screenWidth = getResources().getDisplayMetrics().heightPixels;
            screenHeight = getResources().getDisplayMetrics().widthPixels;
        }


        float scaleWidth = (float)bitmap.getWidth()/(float)screenWidth;
        float scaleHeight = (float)bitmap.getHeight()/(float)screenHeight;
        int left2 = (int)(left * scaleWidth);
        int top2 = (int)(top * scaleHeight);
        int right2 = (int)(left2 + (right-left) * scaleWidth);
        int bottom2 = (int)(top2 + (buttom-top) * scaleHeight);

        return Bitmap.createBitmap(bitmap, left2, top2, right2-left2, bottom2-top2);
    }

    /**
     * 保存为bmp格式的图片
     * @param bitmap
     * @param path
     * @return
     */
    public static String saveBmp(Bitmap bitmap, String path) {

        if( !path.toLowerCase().endsWith(".bmp") ){
            String prefix = TextUtils.substring(path, 0, path.lastIndexOf("."));
            path = prefix + ".bmp";
        }

        // 位图大小
        int nBmpWidth = bitmap.getWidth();
        int nBmpHeight = bitmap.getHeight();
        // 图像数据大小
        int bufferSize = nBmpHeight * (nBmpWidth * 3 + nBmpWidth % 4);
        try {
            MyFileUtils.createFile(path);
            FileOutputStream fileos = new FileOutputStream(path);
            // bmp文件头
            int bfType = 0x4d42;
            long bfSize = 14 + 40 + bufferSize;
            int bfReserved1 = 0;
            int bfReserved2 = 0;
            long bfOffBits = 14 + 40;
            // 保存bmp文件头
            writeWord(fileos, bfType);
            writeDword(fileos, bfSize);
            writeWord(fileos, bfReserved1);
            writeWord(fileos, bfReserved2);
            writeDword(fileos, bfOffBits);
            // bmp信息头
            long biSize = 40L;
            long biWidth = nBmpWidth;
            long biHeight = nBmpHeight;
            int biPlanes = 1;
            int biBitCount = 24;
            long biCompression = 0L;
            long biSizeImage = 0L;
            long biXpelsPerMeter = 0L;
            long biYPelsPerMeter = 0L;
            long biClrUsed = 0L;
            long biClrImportant = 0L;
            // 保存bmp信息头
            writeDword(fileos, biSize);
            writeLong(fileos, biWidth);
            writeLong(fileos, biHeight);
            writeWord(fileos, biPlanes);
            writeWord(fileos, biBitCount);
            writeDword(fileos, biCompression);
            writeDword(fileos, biSizeImage);
            writeLong(fileos, biXpelsPerMeter);
            writeLong(fileos, biYPelsPerMeter);
            writeDword(fileos, biClrUsed);
            writeDword(fileos, biClrImportant);
            // 像素扫描
            byte bmpData[] = new byte[bufferSize];
            int wWidth = (nBmpWidth * 3 + nBmpWidth % 4);
            for (int nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol)
                for (int wRow = 0, wByteIdex = 0; wRow < nBmpWidth; wRow++, wByteIdex += 3) {
                    int clr = bitmap.getPixel(wRow, nCol);
                    bmpData[nRealCol * wWidth + wByteIdex] = (byte) Color.blue(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) Color.green(clr);
                    bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte) Color.red(clr);
                }

            fileos.write(bmpData);
            fileos.flush();
            fileos.close();

            if( !bitmap.isRecycled() ){
                bitmap.recycle();
                bitmap = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }


    private static void writeWord(FileOutputStream stream, int value) throws IOException {
        byte[] b = new byte[2];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        stream.write(b);
    }

    private static void writeDword(FileOutputStream stream, long value) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

    private static void writeLong(FileOutputStream stream, long value) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (value & 0xff);
        b[1] = (byte) (value >> 8 & 0xff);
        b[2] = (byte) (value >> 16 & 0xff);
        b[3] = (byte) (value >> 24 & 0xff);
        stream.write(b);
    }

}
