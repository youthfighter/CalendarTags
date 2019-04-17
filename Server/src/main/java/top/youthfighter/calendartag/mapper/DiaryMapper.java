package top.youthfighter.calendartag.mapper;

import org.apache.ibatis.annotations.Param;
import top.youthfighter.calendartag.model.Diary;

import java.util.Date;
import java.util.List;

public interface DiaryMapper {

    public List<Diary> queryDataByAuthorAndMonth(@Param("startTime") long startTime, @Param("endTime") long endTime, @Param("author") String author);

    public List<Diary> selectDayData(@Param("reportDate") Date reportDate, @Param("author") String author);

    public List<Diary> selectDataById(String id);

    public void update(Diary diary);

    public void insert(Diary diary);

    public int tagCount(String tagId);

}
