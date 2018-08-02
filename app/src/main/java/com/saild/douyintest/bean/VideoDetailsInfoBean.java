package com.saild.douyintest.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 视频详情bean
 * 作者：xiaomao on 2017/6/7.
 */

public class VideoDetailsInfoBean implements Serializable{
    public String thumbnailUrl;//缩略图Url
    public String smallPreviewUrl;//小预览图Url
    public String previewUrl;//大预览图Url
    public long videoId;//视频Id
    public String downloadUrl;//视频下载地址
    public String previewDownloadUrl;//视频预览地址
    public int pageView;//浏览量
    public int laudCount;//点赞数
    public int commentCount;//评论数
    public String videoSign;//视频签名
    public String resName;//资源标题
    public String updateTime;//发布时间
    public boolean isPraise;//是否点赞
    public String identifier;//Identifier
    public String videoMd5;//视频MD5
    public long videoLength;//视频时长,单位毫秒

    public long videoUid;
    public String userIconUrl;//用户头像Url
    public String userNickName;//用户昵称
    public int userSex;//用户性别
    public static final int STATUS_PUBLIC = 1;
    public static final int STATUS_PRIVATE = 6;
    public int resStatus;//视频状态1-公开，2-私有
    public long resSize;//视频大小
    public long videoTimeLength;//视频时长
    public int isMusic;//是否有声音
    public String HotNumber ;//热度
    public int isOriginal ;//是否原创，1=是，0=不是

    /**
     * 数字转换成字符串
     * @param count
     * @return
     */
    public static String countChangeString(int count){
        if (count < 10000){
            return count+"";
        }
        double tmp = count/10000.0;
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(tmp);
    }

    public boolean isPublic(){
        return resStatus == STATUS_PUBLIC;
    }
}
