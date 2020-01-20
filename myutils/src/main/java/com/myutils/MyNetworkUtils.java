/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.myutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/**
 * Created by amitshekhar on 07/07/17.
 */

public final class MyNetworkUtils {

    private MyNetworkUtils() {
        // This class is not publicly instantiable
    }

    public static String getIpAddress(){
        return Formatter.formatIpAddress(getWifiManager().getConnectionInfo().getIpAddress());
    }

    public static WifiManager getWifiManager(){
        return (WifiManager)MyApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static ConnectivityManager getConnectivityManager(){
        return (ConnectivityManager) MyApplication.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /*public static WifiManager getWifiManager(Context context){
        return (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }*/

    /*public static ConnectivityManager getConnectivityManager(Context context){
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }*/

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = getConnectivityManager();
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }


    public static NetworkInfo getNetworkInfoForWIFI(){
        return getConnectivityManager().getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }

    public static Boolean activeNetworkIsWIFI(){
        return isNetworkConnected() && getNetworkInfoForWIFI()!=null;
    }

    public static String getSSID() throws Exception {
        if( isNetworkConnected() && getNetworkInfoForWIFI()!=null ){
            WifiInfo info = getWifiManager().getConnectionInfo();
            return info.getSSID();
        }else{
            // TODO 需要自定义异常
            throw new Exception("请先连接无线网络");
        }
    }

    public static String getBSSID() throws Exception {
        if( isNetworkConnected() && getNetworkInfoForWIFI()!=null ){
            WifiInfo info = getWifiManager().getConnectionInfo();
            return info.getBSSID();
        }else{
            // TODO 需要自定义异常
            throw new Exception("请先连接无线网络");
        }
    }

    public static NetworkInfo.DetailedState getWifiDetailedState(){
        NetworkInfo networkInfo = getNetworkInfoForWIFI();
        if( networkInfo!=null ){
            return networkInfo.getDetailedState();
        }else{
            return null;
        }
    }

    /**
     * 判断wifi是否为2.4G
     * @param freq
     * @return
     */
    public static boolean is24GHz(int freq) {
        return freq > 2400 && freq < 2500;
    }

    /**
     * 判断wifi是否为5G
     * @param freq
     * @return
     */
    public static boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }


    public static String getWifiFrequency(ScanResult scanResult ){
        String result;
        if( is24GHz(scanResult.frequency) ){
            result = "2.4G";
        }else if( is5GHz(scanResult.frequency) ){
            result = "5G";
        }else{
            result = String.valueOf(scanResult.frequency);
        }
        return result;
    }


    /**
     * 根据频率获得信道
     *
     * @param scanResult
     * @return
     */
    public static String getChannelByFrequency(ScanResult scanResult) {
        int channel = -1;
        switch (scanResult.frequency) {
            case 2412:
                channel = 1;
                break;
            case 2417:
                channel = 2;
                break;
            case 2422:
                channel = 3;
                break;
            case 2427:
                channel = 4;
                break;
            case 2432:
                channel = 5;
                break;
            case 2437:
                channel = 6;
                break;
            case 2442:
                channel = 7;
                break;
            case 2447:
                channel = 8;
                break;
            case 2452:
                channel = 9;
                break;
            case 2457:
                channel = 10;
                break;
            case 2462:
                channel = 11;
                break;
            case 2467:
                channel = 12;
                break;
            case 2472:
                channel = 13;
                break;
            case 2484:
                channel = 14;
                break;
            case 5745:
                channel = 149;
                break;
            case 5765:
                channel = 153;
                break;
            case 5785:
                channel = 157;
                break;
            case 5805:
                channel = 161;
                break;
            case 5825:
                channel = 165;
                break;
        }
        return String.valueOf(channel);
    }
}
