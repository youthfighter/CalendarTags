package top.youthfighter.calendartag.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.youthfighter.calendartag.dto.DiaryDTO;
import top.youthfighter.calendartag.model.Diary;
import top.youthfighter.calendartag.model.RequestResult;
import top.youthfighter.calendartag.model.Tag;
import top.youthfighter.calendartag.service.DiaryService;
import top.youthfighter.calendartag.service.TagService;
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

    @Autowired
    public TagService tagService;

    @RequestMapping("/monthreport")
    public RequestResult monthReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        RequestResult result = new RequestResult();
        //check
        if (year < 1970) {
            result.setError("参数错误.");
            return result;
        }
        if (month < 1 || month > 12) {
            result.setError("参数错误.");
            return result;
        }
        String author = "youthfighter";

        //查询所有的标签信息
        List<Tag> tags = tagService.queryAll();
        //构造标签hashmap
        Map<String, Tag> tagMap = new HashMap<String, Tag>();
        for (Tag tag: tags) {
            tagMap.put(tag.getId(), tag);
        }
        //生成查询月日期
        long firstDay = DateUtil.getFirstDay(year, month-1);
        long lastDay = DateUtil.getLastDay(year, month-1);
        //查村某月的数据
        List<Diary> drs = diaryService.queryDataByAuthorAndMonth(firstDay, lastDay, author);
        //构造返回map
        Map<String, List<DiaryDTO>> map = new HashMap();
        for(Diary dr : drs) {
            Calendar c= Calendar.getInstance();
            c.setTimeInMillis(dr.getReportDate());
            String day = c.get(Calendar.DAY_OF_MONTH) + "";
            List<DiaryDTO> diaryList = map.get(day);
            if (diaryList ==null) {
                diaryList = new ArrayList<DiaryDTO>();
            }
            ModelMapper modelMapper = new ModelMapper();
            DiaryDTO diaryDTO = modelMapper.map(dr, DiaryDTO.class);
            diaryDTO.setTag(tagMap.get(dr.getTagId()));
            diaryList.add(diaryDTO);
            map.put(day, diaryList);
        }
        result.setData(map);
        return result;
    }

    @RequestMapping(value = "/diary/{id}", method = RequestMethod.DELETE)
    public RequestResult deleteDiary(@PathVariable("id") String id) {
        RequestResult result = new RequestResult();
        //to do 判断当前登录者是否有权限删除数据
        System.out.println(id);
        int num = diaryService.deleteById(id);
        System.out.println(num);
        result.setData("success");
        return result;
    }
    @RequestMapping(value="/diary", method = RequestMethod.POST)
    public RequestResult addDiary(@RequestBody Diary diary) {
        RequestResult result = new RequestResult();
        //check
        if (diary.getReportDate() == null) {
            result.setError("参数错误.");
            return result;
        }

        if (diary.getDescribition() == null || "".equals(diary.getDescribition())) {
            result.setError("请填写描述.");
            return result;
        }

        if (diary.getDescribition().length() > 200) {
            result.setError("描述需在200字以内.");
            return result;
        }

        if (diary.getTagId() == null || "".equals(diary.getTagId())) {
            result.setError("标签类型错误.");
            return result;
        }
        //验证tagId是否存在
        Tag tag = tagService.queryTagById(diary.getTagId());
        if (tag == null) {
            result.setError("该标签不存在.");
            return result;
        } else if(tag.getStatus() == 2) {
            result.setError("该标签已停用.");
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
        ModelMapper modelMapper = new ModelMapper();
        DiaryDTO diaryDTO = modelMapper.map(diary, DiaryDTO.class);
        diaryDTO.setTag(tag);
        result.setData(diaryDTO);
        return result;
    }

    @RequestMapping(value="/diary", method = RequestMethod.PUT)
    public RequestResult updateDiary(@RequestBody Diary diary) {
        RequestResult result = new RequestResult();
        //check
        if (diary.getDescribition() == null || "".equals(diary.getDescribition())) {
            result.setError("请填写描述.");
            return result;
        }

        if (diary.getDescribition().length() > 200) {
            result.setError("描述需在200字以内.");
            return result;
        }
        // TODO: 2019/4/22 0022  是否为当前登陆者
        Diary cdiary = diaryService.queryDataById(diary.getId());
        if (cdiary == null) {
            result.setError("该日记已被删除.");
            return result;
        }
        cdiary.setDescribition(diary.getDescribition());
        diaryService.update(diary);
        ModelMapper modelMapper = new ModelMapper();
        DiaryDTO diaryDTO = modelMapper.map(diary, DiaryDTO.class);
        diaryDTO.setTag(tagService.queryTagById(diary.getTagId()));
        result.setData(diaryDTO);
        return result;
    }
}
