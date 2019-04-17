package top.youthfighter.calendartag.mapper;

import top.youthfighter.calendartag.model.Tag;

import java.util.List;

public interface TagMapper {

    public List<Tag> queryAll();

    public  List<Tag> queryTagsByName();

    public List<Tag> queryTagsByParam(Tag tag);

    public void update(Tag tag);

    public void insert(Tag tag);
}
