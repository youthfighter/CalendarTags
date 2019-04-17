package top.youthfighter.calendartag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.youthfighter.calendartag.mapper.TagMapper;
import top.youthfighter.calendartag.model.Tag;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagMapper tagMapper;

    public List<Tag> queryAll() {
        return tagMapper.queryAll();
    }

    public Tag queryEnabledTagsById(String id) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setStatus(1);
        List<Tag> tags = queryTagsByParam(tag);
        if (tags.size() == 0) {
            return null;
        } else {
            return tags.get(0);
        }
    }

    public List<Tag> queryTagsByParam(Tag tag) {
        return tagMapper.queryTagsByParam(tag);
    }

    public Tag queryTagById(String id) {
        Tag tag = new Tag();
        tag.setId(id);
        List<Tag> tags = tagMapper.queryTagsByParam(tag);
        if (tags.size() == 0) {
            return null;
        } else {
            return tags.get(0);
        }
    }

    public Tag insert(Tag tag){
        tagMapper.insert(tag);
        return queryTagById(tag.getId());
    }

    public Tag update(Tag tag) {
        tagMapper.update(tag);
        return queryTagById(tag.getId());
    }

    public Tag queryTagsByName(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        List<Tag> tags = tagMapper.queryTagsByParam(tag);
        if (tags.size() == 0) {
            return null;
        } else {
            return tags.get(0);
        }
    }
}
