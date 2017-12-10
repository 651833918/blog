package cn.powerr.blog.common.resp;

import lombok.Data;

public class ArticlePicResult {

    /**
     * success : 0 | 1
     * message : 提示的信息
     * url : 图片地址
     */

    private Integer success = 1;
    private String message;
    private String url;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
