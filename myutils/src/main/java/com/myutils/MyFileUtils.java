package com.myutils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * Created by yhr on 2017-03-06.
 *
 */

public class MyFileUtils {

    public static String getName(String path){
        return new File(path).getName();
    }

    public static String getStringForBase64(String path) throws IOException {
        File file = new File(path);
        if( file.exists() ){
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        }
        return null;
    }

    public static boolean exists(String path){
        return !TextUtils.isEmpty(path) && new File(path).exists();
    }

    public static void delete( String uri ){
        if( !TextUtils.isEmpty(uri) ){
            File file = new File(uri);
            if( file.exists() ){
                file.delete();
            }
        }
    }

    public static void delete( File file ){
        if( file.exists() ){
            file.delete();
        }
    }

    public static void copy(File src, File target ) throws IOException {
        copy(src.getAbsolutePath(), target.getAbsolutePath());
    }

    public static void copy(String src, String target ) throws IOException {
        File in = new File(src);
        File out = new File(target);
        FileInputStream inputStream = new FileInputStream(in);
        FileOutputStream outputStream = new FileOutputStream(out);
        copy(inputStream, outputStream);
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            byte[] buffer = new byte[4096];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
        }  catch (IOException e) {
            throw e;
        }finally {
            inputStream.close();
            outputStream.close();
        }
    }

    public static boolean createFile( File file ) throws IOException {
        return createFile(file.getAbsolutePath());
    }

    public static boolean createFile( String path ) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        return file.createNewFile();
    }

    /**
     * 将文件以时间戳为前缀重命名
     * @param filename
     * @return
     * @throws IOException
     */
    public static String createRenameFile(String filename ) throws IOException {
        File file = new File(filename);
        File dir = file.getParentFile();
        String suffix = getSuffix(filename);
        dir.mkdirs();
        File rename = new File(dir, System.currentTimeMillis() + suffix);
        rename.createNewFile();
        return rename.getAbsolutePath();
    }

    /*private static File loopCreate(String...options ) throws IOException {
        String path;
        if( Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ){
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        }else{
            path = MyApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        }

        File file = new File(path);
        for (String option : options) {
            file = new File(file, option);
        }

        file.getParentFile().mkdirs();
        file.createNewFile();

        return file;

    }*/

    /**
     * 文件名前缀
     * @param filename
     * @return
     */
    public static String getPrefix(String filename ){
        return TextUtils.substring(filename, 0, filename.lastIndexOf("."));
    }

    /**
     * 文件名后缀
     * @param filename
     * @return
     */
    public static String getSuffix(String filename ){
        return TextUtils.substring(filename, filename.lastIndexOf("."), filename.length());
    }

    /**
     * 文件长度转为文件大小
     * @param length
     * @return
     */
    public static String convertByteWithUnit(long length) {
        if (length > 1024 * 1024 * 1024) {
            return String.format(Locale.ENGLISH, "%.2fGB", (float) length
                    / (1024 * 1024 * 1024));
        } else if (length > 1024 * 1024) {
            return String.format(Locale.ENGLISH, "%.2fMB", (float) length / (1024 * 1024));
        } else if (length > 1024) {
            return String.format(Locale.ENGLISH, "%.2fkB", (float) length / 1024);
        } else {
            return length + "byte";
        }
    }

}
