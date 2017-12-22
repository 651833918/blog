package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.dao.TagMapper;
import cn.powerr.blog.blog.entity.Tag;
import cn.powerr.blog.blog.entity.TagExample;
import cn.powerr.blog.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Tag> searchTags(Integer userId) {
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Tag> tags = null;
        try {
            tags = tagMapper.selectByExample(example);
        } catch (Exception e) {
            log.error("查询标签失败");
        }
        return tags;
    }

    /**
     * 新增标签并验证是否同名
     *
     * @param tag
     * @return
     */
    @Override
    @Transactional
    public int addTag(Tag tag) {
        int result = 0;
        boolean flag = true;
        try {
            List<Tag> tags = tagMapper.selectTags();
            for (Tag tagExamle : tags) {
                if (tag.getTagName().equals(tagExamle.getTagName())&&tag.getUserId().equals(tagExamle.getUserId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result = tagMapper.insertSelective(tag);
            }

        } catch (Exception e) {
            log.error("插入标签失败");
        }
        return result;
    }

    /**
     * 删除标签，并删除标签相关联的文章的标签id
     *
     * @param tagId
     */
    @Transactional
    @Override
    public int delTag(Integer tagId) {
        try {
            int updateResp = articleMapper.updateArticleTagToNull(tagId);
        } catch (Exception e) {
            log.error("更新ArticleId为NULL失败");
        }
        int reslut = 0;
        try {
            reslut = tagMapper.deleteByPrimaryKey(tagId);
        } catch (Exception e) {
            log.error("删除tag失败");
        }
        return reslut;
    }

    @Override
    public Tag refreshTagName(Integer tagId) {
        Tag tag = null;
        try {
            tag = tagMapper.selectByPrimaryKey(tagId);
        } catch (Exception e) {
            log.error("查找tag失败");
        }
        return tag;
    }

    @Override
    public int editTagName(Tag tag) {
        int result = 0;
        try {
            result = tagMapper.updateByPrimaryKeySelective(tag);
        } catch (Exception e) {
            log.error("更新TagName失败");
        }
        return result;
    }
}
