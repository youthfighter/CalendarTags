package top.youthfighter.calendartag.mapper;

import top.youthfighter.calendartag.model.Tag;

import java.util.List;

public interface TagMapper {

    public List<Tag> queryAll();

    public List<Tag> queryEnabledTagsById(String id);
}
