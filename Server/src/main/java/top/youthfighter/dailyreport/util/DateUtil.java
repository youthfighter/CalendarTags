package top.youthfighter.dailyreport.util;

import java.util.Calendar;

public class DateUtil {
    //获取某年某月第一天0点的时间戳
    public static long getFirstDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1,0,0,0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    //获取某年某月最后一天0点的时间戳
    public static long getLastDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, c.getActualMaximum(Calendar.DAY_OF_MONTH),0,0,0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
