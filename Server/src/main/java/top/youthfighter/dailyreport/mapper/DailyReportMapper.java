package top.youthfighter.dailyreport.mapper;

import org.apache.ibatis.annotations.Param;
import top.youthfighter.dailyreport.model.DailyReport;

import java.util.Date;
import java.util.List;

public interface DailyReportMapper {

    public List<DailyReport> queryDataByAuthorAndMonth(@Param("startTime") long startTime, @Param("endTime") long endTime, @Param("author") String author);

    public List<DailyReport> selectDayData(@Param("reportDate") Date reportDate, @Param("author") String author);

    public List<DailyReport> selectDataById(String id);

    public void update(DailyReport dailyReport);

    public void insert(DailyReport dailyReport);

}
