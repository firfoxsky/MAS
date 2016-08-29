package com.example.pengtaoh.mas.utils;

/**
 * Created by firefox on 2016/8/29.
 */
public class CommandUtil {
    /**
     * 请确认demo中的这个算法是否正确
     *
     * @param rssi_msb
     * @param rssi_lsb
     * @return
     */
    public static double rssi_calculate(char rssi_msb, char rssi_lsb) {
        int temp_rssi = (int) (((int) rssi_msb << 8) + (int) rssi_lsb);
        double sh_rssi = (double) (short) temp_rssi / 10;

        return sh_rssi;
    }
}
