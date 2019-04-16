package top.youthfighter.calendartag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.youthfighter.calendartag.model.RequestResult;
import top.youthfighter.calendartag.model.Tag;
import top.youthfighter.calendartag.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public RequestResult getAllTags() {
        RequestResult result = new RequestResult();
        result.setData(this.tagService.queryAll());
        return result;
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public RequestResult addTag(@RequestBody Tag tag) {
        RequestResult result = new RequestResult();
        if (tag.getName() == null) {
            result.setError("请输入标签名称.");
            return result;
        }
        if (tag.getColor() == null) {
            result.setError("请输入标签对应的颜色值.");
            return result;
        }
        tag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tag.setDelete(false);
        tagService.insert(tag);
        result.setData(tag);
        return result;
    }

    @RequestMapping(value="/tag", method = RequestMethod.PATCH)
    public RequestResult updateStatus(@RequestBody Tag tag) {
        RequestResult result = new RequestResult();
        if (tag.getName() == null) {
            result.setError("请输入标签名称.");
            return result;
        }
        if (tag.getColor() == null) {
            result.setError("请输入标签对应的颜色值.");
            return result;
        }
        tag.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        tag.setDelete(false);
        tagService.update(tag);
        result.setData(tag);
        return result;
    }
}
