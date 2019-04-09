package top.youthfighter.calendartag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.youthfighter.calendartag.mapper.DiaryMapper;
import top.youthfighter.calendartag.model.Diary;

import java.util.Date;
import java.util.List;

@Service
public class DiaryService {

    @Autowired
    public DiaryMapper diaryMapper;

    public List<Diary> queryDataByAuthorAndMonth(long startTime, long endTime, String author) {
        return diaryMapper.queryDataByAuthorAndMonth(startTime, endTime, author);
    }

    public void update(Diary diary) {
        diaryMapper.update(diary);
    }

    public void insert(Diary diary) {
        diaryMapper.insert(diary);
    }

    public List<Diary> queryDayData(Date reportDate, String author) {
        return diaryMapper.selectDayData(reportDate, author);
    }

    public List<Diary> queryDataById(String id) {
        return diaryMapper.selectDataById(id);
    }
}
