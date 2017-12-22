package cn.powerr.blog.blog.service;

import cn.powerr.blog.blog.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> searchTags(Integer userId);

    int addTag(Tag tag);

    int delTag(Integer tagId);

    Tag refreshTagName(Integer tagId);

    int editTagName(Tag tag);
}
