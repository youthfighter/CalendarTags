package top.youthfighter.dailyreport;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {
    @Test
    public void aa() {
        int year = 2019;
        int month = 3;
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1,0,0,0);
        c.set(Calendar.MILLISECOND, 0);
        long firstDay = c.getTimeInMillis();
        System.out.println(firstDay);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        long lastDay = c.getTimeInMillis();
        System.out.println(lastDay);
    }
}
