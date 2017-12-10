package cn.powerr.blog.blog.service.impl;

import cn.powerr.blog.blog.dao.ArticleMapper;
import cn.powerr.blog.blog.entity.Article;
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
    public Article saveArticle(Article article) {
        Article articleRestlt = null;
        try {
            int articleId = articleMapper.insertSelective(article);
            articleRestlt = articleMapper.selectByPrimaryKey(articleId);
        } catch (Exception e) {
            log.error("发表博客失败："+e.getMessage());
        }
        return articleRestlt;
    }
}
