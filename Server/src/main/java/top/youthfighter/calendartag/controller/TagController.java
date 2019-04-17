package top.youthfighter.calendartag.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.youthfighter.calendartag.dto.TagDTO;
import top.youthfighter.calendartag.model.Diary;
import top.youthfighter.calendartag.model.RequestResult;
import top.youthfighter.calendartag.model.Tag;
import top.youthfighter.calendartag.service.DiaryService;
import top.youthfighter.calendartag.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/calendartag/v1")
public class TagController {

    @Autowired
    public HttpServletRequest httpServletRequest;

    @Autowired
    public HttpServletResponse httpServletResponse;

    @Autowired
    public TagService tagService;

    @Autowired
    public DiaryService diaryService;

    //添加标签信息
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public RequestResult getAllTags() {
        RequestResult result = new RequestResult();
        List<Tag> tags = tagService.queryAll();
        ModelMapper mp = new ModelMapper();
        List<TagDTO> tagsDto = new ArrayList<TagDTO>();
        for (Tag tag : tags) {
            TagDTO tagDto = mp.map(tag, TagDTO.class);
            tagDto.setUsageCount(diaryService.tagCount(tag.getId()));
            tagsDto.add(tagDto);
        }
        result.setData(tagsDto);
        return result;
    }

    //添加标签信息
    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public RequestResult addTag(@RequestBody Tag tag) {
        RequestResult result = new RequestResult();
        if (tag.getName() == null || "".equals(tag.getName())) {
            result.setError("请输入标签名称.");
            return result;
        }
        if (tag.getColor() == null || "".equals(tag.getColor())) {
            result.setError("请输入标签对应的颜色值.");
            return result;
        }
        if (tagService.queryTagsByName(tag.getName()) == null) {
            result.setError("该标签名称已存在.");
            return result;
        }
        tag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tag.setDisable(false);
        ModelMapper mp = new ModelMapper();
        TagDTO tagDto = mp.map(tagService.insert(tag), TagDTO.class);
        tagDto.setUsageCount(0);
        result.setData(tagDto);
        return result;
    }

    //修改标签信息
    @RequestMapping(value = "/tag", method = RequestMethod.PUT)
    public RequestResult updateTag(@RequestBody Tag tag) {
        RequestResult result = new RequestResult();
        if (tag.getId() == null || "".equals(tag.getId())) {
            result.setError("该标签已被删除.");
            return result;
        }
        if (tag.getName() == null || "".equals(tag.getName())) {
            result.setError("请输入标签名称.");
            return result;
        }
        if (tag.getColor() == null || "".equals(tag.getColor())) {
            result.setError("请输入标签对应的颜色值.");
            return result;
        }
        ModelMapper mp = new ModelMapper();
        TagDTO tagDto = mp.map(tagService.update(tag), TagDTO.class);
        tagDto.setUsageCount(diaryService.tagCount(tag.getId()));
        result.setData(tagDto);
        return result;
    }

    //启用 停用
    @RequestMapping(value="/tag/{tagId}", method = RequestMethod.PATCH)
    public RequestResult updateStatus(@PathVariable("tagId") String tagId,@RequestParam("disable") boolean disable ) {
        RequestResult result = new RequestResult();
        Tag tag = tagService.queryTagsById(tagId);
        if ( tag == null) {
            result.setError("请标签已被删除.");
            return result;
        }
        tag.setDisable(disable);
        ModelMapper mp = new ModelMapper();
        TagDTO tagDto = mp.map(tagService.update(tag), TagDTO.class);
        tagDto.setUsageCount(diaryService.tagCount(tag.getId()));
        result.setData(tagDto);
        return result;
    }
}
