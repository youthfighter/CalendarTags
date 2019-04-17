package top.youthfighter.calendartag.mapper;

import top.youthfighter.calendartag.model.Tag;

import java.util.List;

public interface TagMapper {

    public List<Tag> queryAll();

    public List<Tag> queryTagsById(String id);

    public  List<Tag> queryTagsByName();
}
