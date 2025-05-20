package com.zhongjh.retrofit2rxjava2download;

import java.text.DecimalFormat;

public class FileUtils {

    public static String formatFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"b", "kb", "M", "G", "T"};
        // 计算单位的，原理是利用lg,公式是 lg(1024^n) = nlg(1024)，最后 nlg(1024)/lg(1024) = n。
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        // 计算原理是，size/单位值。单位值指的是:比如说b = 1024,KB = 1024^2
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String formatTime(long time) {
        String temp;
        if (time >= 60 && time <= 3600) {
            temp = time / 60 + "分" + time % 60 + "秒";
        } else {
            if (time > 3600) {
                temp = time / 3600 + "小时" + (time % 3600) / 60 + "分" + time % 60 + "秒";
            } else {
                temp = time + "秒";
            }
        }
        return temp;
    }
}
