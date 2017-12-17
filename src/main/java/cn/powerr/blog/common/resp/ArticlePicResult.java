package cn.powerr.blog.common.resp;

import lombok.Data;

@Data
public class ArticlePicResult {

    /**
     * success : 0 | 1
     * message : 提示的信息
     * url : 图片地址
     */

    private Integer success = 1;
    private String message;
    private String url;
}
