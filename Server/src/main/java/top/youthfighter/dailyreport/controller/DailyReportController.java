package top.youthfighter.dailyreport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.youthfighter.dailyreport.model.DailyReport;
import top.youthfighter.dailyreport.model.RequestResult;
import top.youthfighter.dailyreport.service.DailyReportService;
import top.youthfighter.dailyreport.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/dailyreport/v1")
public class DailyReportController {

    @Autowired
    public HttpServletResponse httpServletResponse;

    @Autowired
    public HttpServletRequest httpServletRequest;

    @Autowired
    public DailyReportService dailyReportService;

    @RequestMapping("/monthreport")
    public RequestResult monthReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        RequestResult result = new RequestResult();
        //check
        if (year < 1970) {
            result.setErrorCode("params error");
            return result;
        }
        if (month < 1 || month > 12) {
            result.setErrorCode("params error");
            return result;
        }
        String author = "youthfighter";
        //生成查询月日期
        long firstDay = DateUtil.getFirstDay(year, month-1);
        long lastDay = DateUtil.getLastDay(year, month-1);
        List<DailyReport> drs = dailyReportService.queryDataByAuthorAndMonth(firstDay, lastDay, author);
        Map<String, List<DailyReport>> map = new HashMap();
        for(DailyReport dr : drs) {
            Calendar c= Calendar.getInstance();
            c.setTimeInMillis(dr.getReportDate());
            String day = c.get(Calendar.DAY_OF_MONTH) + "";
            List<DailyReport> dailyReportList = map.get(day);
            if (dailyReportList==null) {
                dailyReportList = new ArrayList<DailyReport>();
            }
            dailyReportList.add(dr);
            map.put(day, dailyReportList);
        }
        result.setData(map);
        return result;
    }

    @RequestMapping(value="/dailyreport", method = RequestMethod.POST)
    public RequestResult insert(@RequestBody DailyReport dailyReport) {
        RequestResult result = new RequestResult();
        //check
        if (dailyReport.getReportDate() == null) {
            result.setErrorCode("need reportDate");
            return result;
        }

        if (dailyReport.getDescribition() == null || "".equals(dailyReport.getDescribition())) {
            result.setErrorCode("need describition");
            return result;
        }

        if (dailyReport.getDescribition().length() > 200) {
            result.setErrorCode("describition too long");
            return result;
        }

        if (dailyReport.getTagId() == null || "".equals(dailyReport.getTagId())) {
            result.setErrorCode("need tagId");
            return result;
        }

        dailyReport.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        dailyReport.setCreateDateTime(System.currentTimeMillis());
        dailyReport.setAuthor("youthfighter");
        Date d = new Date(dailyReport.getReportDate());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dailyReport.getReportDate());
        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        dailyReport.setReportDate(c.getTimeInMillis()-(c.getTimeInMillis() + 60*60*8*1000)%oneDayTimestamps);
        dailyReportService.insert(dailyReport);
        result.setData(dailyReport);
        return result;
    }
}
