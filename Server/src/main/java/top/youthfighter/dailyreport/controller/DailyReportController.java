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
    public List<DailyReport> monthReport(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("author") String author) {
        //生成查询月日期
        long firstDay = DateUtil.getFirstDay(year, month);
        long lastDay = DateUtil.getLastDay(year, month);
        return dailyReportService.queryDataByAuthorAndMonth(firstDay, lastDay, author);
    }

    @RequestMapping("/dailyreport")
    public List<DailyReport> monthReport(@RequestParam("reportDate") Date reportDate, @RequestParam("author") String author) {
        return dailyReportService.queryDayData(reportDate, author);
    }

    @RequestMapping(value="/dailyreport", method = RequestMethod.POST)
    public RequestResult insert(@RequestBody DailyReport dailyReport) {
        RequestResult result = new RequestResult();
        //check
        if (dailyReport.getReportDate() == null) {
            result.setErrorCode("need reportDate");
            return result;
        }

        dailyReport.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        dailyReport.setCreateDateTime(System.currentTimeMillis());
        dailyReport.setAuthor("zhangsan");
        Date d = new Date(dailyReport.getReportDate());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dailyReport.getReportDate());
        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        dailyReport.setReportDate(c.getTimeInMillis()-(c.getTimeInMillis() + 60*60*8*1000)%oneDayTimestamps);
        dailyReportService.insert(dailyReport);
        result.setData("success");
        return result;
    }

    @RequestMapping(value="dailyreport", method=RequestMethod.PUT)
    public RequestResult update(@RequestBody DailyReport dailyReport){
        RequestResult result = new RequestResult();
        //check
        if (dailyReport.getId() == null) {
            result.setErrorCode("need dailyReport id");
            return result;
        }
        dailyReportService.update(dailyReport);
        result.setData("suceess");
        return result;
    }
}
