package top.youthfighter.dailyreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.youthfighter.dailyreport.mapper.DailyReportMapper;
import top.youthfighter.dailyreport.model.DailyReport;

import java.util.Date;
import java.util.List;

@Service
public class DailyReportService {

    @Autowired
    public DailyReportMapper dailyReportMapper;

    public List<DailyReport> queryDataByAuthorAndMonth(long startTime, long endTime, String author) {
        return dailyReportMapper.queryDataByAuthorAndMonth(startTime, endTime, author);
    }

    public void update(DailyReport dailyReport) {
        dailyReportMapper.update(dailyReport);
    }

    public void insert(DailyReport dailyReport) {
        dailyReportMapper.insert(dailyReport);
    }

    public List<DailyReport> queryDayData(Date reportDate, String author) {
        return dailyReportMapper.selectDayData(reportDate, author);
    }

    public List<DailyReport> queryDataById(String id) {
        return dailyReportMapper.selectDataById(id);
    }
}
