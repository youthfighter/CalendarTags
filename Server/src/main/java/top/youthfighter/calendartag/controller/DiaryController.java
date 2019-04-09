package top.youthfighter.calendartag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.youthfighter.calendartag.model.Diary;
import top.youthfighter.calendartag.model.RequestResult;
import top.youthfighter.calendartag.service.DiaryService;
import top.youthfighter.calendartag.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/calendartag/v1")
public class DiaryController {

    @Autowired
    public HttpServletResponse httpServletResponse;

    @Autowired
    public HttpServletRequest httpServletRequest;

    @Autowired
    public DiaryService diaryService;

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
        List<Diary> drs = diaryService.queryDataByAuthorAndMonth(firstDay, lastDay, author);
        Map<String, List<Diary>> map = new HashMap();
        for(Diary dr : drs) {
            Calendar c= Calendar.getInstance();
            c.setTimeInMillis(dr.getReportDate());
            String day = c.get(Calendar.DAY_OF_MONTH) + "";
            List<Diary> diaryList = map.get(day);
            if (diaryList ==null) {
                diaryList = new ArrayList<Diary>();
            }
            diaryList.add(dr);
            map.put(day, diaryList);
        }
        result.setData(map);
        return result;
    }

    @RequestMapping(value="/calendartag", method = RequestMethod.POST)
    public RequestResult insert(@RequestBody Diary diary) {
        RequestResult result = new RequestResult();
        //check
        if (diary.getReportDate() == null) {
            result.setErrorCode("need reportDate");
            return result;
        }

        if (diary.getDescribition() == null || "".equals(diary.getDescribition())) {
            result.setErrorCode("need describition");
            return result;
        }

        if (diary.getDescribition().length() > 200) {
            result.setErrorCode("describition too long");
            return result;
        }

        if (diary.getTagId() == null || "".equals(diary.getTagId())) {
            result.setErrorCode("need tagId");
            return result;
        }

        diary.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        diary.setCreateDateTime(System.currentTimeMillis());
        diary.setAuthor("youthfighter");
        Date d = new Date(diary.getReportDate());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(diary.getReportDate());
        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        diary.setReportDate(c.getTimeInMillis()-(c.getTimeInMillis() + 60*60*8*1000)%oneDayTimestamps);
        diaryService.insert(diary);
        result.setData(diary);
        return result;
    }
}
