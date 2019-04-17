package top.youthfighter.calendartag.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.youthfighter.calendartag.dto.TagDTO;
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
    public RequestResult getAllTags(@RequestParam(name="status", required = false) Integer status) {
        RequestResult result = new RequestResult();
        List<Tag> tags = new ArrayList<>();
        Tag param = new Tag();
        if (status != null) param.setStatus(status);
        tags = tagService.queryTagsByParam(param);
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
        if (tag.getName().length() > 3) {
            result.setError("标签名称过长.");
            return result;
        }
        if (tagService.queryTagsByName(tag.getName()) != null) {
            result.setError("该标签名称已存在.");
            return result;
        }
        tag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tag.setStatus(1);
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
        if (tagService.queryTagById(tag.getId()) == null ) {
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
    public RequestResult updateStatus(@PathVariable("tagId") String tagId,@RequestParam("status") int status ) {
        RequestResult result = new RequestResult();
        Tag tag = tagService.queryTagById(tagId);
        if ( tag == null) {
            result.setError("请标签已被删除.");
            return result;
        }
        if (status !=1 && status != 2) {
            result.setError("不存在该状态.");
            return result;
        }
        tag.setStatus(status);
        ModelMapper mp = new ModelMapper();
        TagDTO tagDto = mp.map(tagService.update(tag), TagDTO.class);
        tagDto.setUsageCount(diaryService.tagCount(tag.getId()));
        result.setData(tagDto);
        return result;
    }
}
