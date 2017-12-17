package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.entity.ArticleExample;
import cn.powerr.blog.blog.entity.ArticleWithBLOBs;
import cn.powerr.blog.blog.service.ArticleService;
import cn.powerr.blog.common.constants.Constants;
import cn.powerr.blog.common.utils.QiniuFileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    @Qualifier("articleMapper")
    private ArticleMapper articleMapper;
    /**
     * 文章图片上传回传图片路径
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public String uploadArticlePicture(MultipartFile file) throws IOException {
        String filename = QiniuFileUploadUtil.uploadArticleImg(file);
        String result = Constants.QINIU_PROTOCOL+ Constants.QINIU_ARTICLE_IMG_BUCKET_URL+"/"+filename;
        return result;
    }

    @Override
    @Transactional
    public ArticleWithBLOBs saveArticle(ArticleWithBLOBs article) {
        ArticleWithBLOBs articleRestlt = null;
        try {
            articleMapper.insertSelective(article);
            articleRestlt = articleMapper.selectByPrimaryKey(article.getId());
        } catch (Exception e) {
            log.error("发表博客失败："+e.getMessage());
        }
        return articleRestlt;
    }

    @Override
    public ArticleWithBLOBs refreshArticleMess(Integer articleId, Integer userId) {
        articleMapper.updateArticleByLookNum(articleId);
        ArticleWithBLOBs articleWithBLOBs = articleMapper.selectByPrimaryKey(articleId);

        return articleWithBLOBs;
    }

    @Override
    public void delArticle(Integer articleId) {
        try{
            ArticleExample example = new ArticleExample();
            ArticleExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(articleId);
            articleMapper.deleteByExample(example);
        }catch (Exception e){
            log.error("删除文章失败");
        }
    }

    @Override
    public Integer editArticle(ArticleWithBLOBs articleWithBLOBs) {
        int result = 0;
        try {
            result = articleMapper.updateByPrimaryKeySelective(articleWithBLOBs);
        } catch (Exception e) {
            log.error("编辑文章失败");
        }
        return result;
    }
}
