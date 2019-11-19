package com.boot.demo.core;

import java.util.Calendar;
import java.util.Date;

public class MyDate {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println("1111111111111===" + cal.getTimeInMillis());
        System.out.println("2222222222222==" + System.currentTimeMillis());
        System.out
                .println("3333333333333=="
                        + ((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000 / 60 /
                        60));

        /*******       获得当前日期到凌晨24点的秒数  ,  用于存储redis作为当天的时效     **********/
//        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        long time1 = cal1.getTimeInMillis();
        cal1.setTime(cal.getTime());
        long time2 = cal1.getTimeInMillis();

        System.out.println((time2 - time1) / 1000);
    }
}
