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
        List<Tag> tags = tagMapper.queryEnabledTagsById(id);
        if (tags.size() == 0) {
            return null;
        } else {
            return tags.get(0);
        }
    }
}
