package com.saild.douyintest.present;

import com.saild.douyintest.bean.VideoDetailsInfoBean;
import java.util.ArrayList;

/**
 * Created by linliangbin on 2017/6/17 10:31.
 */

public interface IVideoDetailView {

    /**
     * @desc 视频详情信息加载成功
     * @author linliangbin
     * @time 2017/6/17 10:31
     */
    public void onVideoInfoLoadedSuccess(ArrayList<VideoDetailsInfoBean> videoDetails);


    /**
     * @desc 视频详情加载失败
     * @author linliangbin
     * @time 2017/6/17 10:33
     */
    public void onVideoInfoLoadedFail();

}
