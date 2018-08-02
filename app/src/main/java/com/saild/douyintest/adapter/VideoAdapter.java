package com.saild.douyintest.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.saild.douyintest.R;
import com.saild.douyintest.adapter.base.BaseRecyclerViewHolder;
import com.saild.douyintest.adapter.base.EnhanceRecyclerAdapter;
import com.saild.douyintest.bean.VideoDetailsInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @Author 许少东
 * Created at 2018/6/25.
 */
public class VideoAdapter extends EnhanceRecyclerAdapter<VideoDetailsInfoBean> {

    private Context context;
    public VideoAdapter(Context context, int layoutResId) {
        super(context, layoutResId,true);
        this.context= context;

    }

    @Override
    protected void convert(BaseRecyclerViewHolder holder, int position) {
        super.convert(holder, position);
        VideoDetailsInfoBean item = getItem(position);
        holder.displayImageNew(R.id.img_thumb,Integer.parseInt(item.thumbnailUrl));
        holder.setVideoURI(R.id.video_item,Uri.parse("android.resource://"+context.getPackageName()+"/"+Integer.parseInt(item.previewUrl)));
        //   holder.img_thumb.setImageResource(imgs[position % 2]);
        //   holder.videoView.setVideoURI(Uri.parse("android.resource://"+context.getPackageName()+"/"+videos[position%2]));
//        holder.videoFlag.setText("======position："+position);
        holder.setText(R.id.video_flag, "======position："+position);
    }

    @Override
    protected List<VideoDetailsInfoBean> executeAsync(Bundle params) {
        List<VideoDetailsInfoBean> beanList = new ArrayList<>();
        for(int i=0;i<20;i++){
            VideoDetailsInfoBean  videoDetailsInfoBean = new VideoDetailsInfoBean();
            if(i % 2 ==0){
                videoDetailsInfoBean.thumbnailUrl = R.mipmap.img_video_1 +"";
                videoDetailsInfoBean.previewUrl = R.raw.video_1 +"";
            }else {
                videoDetailsInfoBean.thumbnailUrl = R.mipmap.img_video_2 +"";
                videoDetailsInfoBean.previewUrl = R.raw.video_2 +"";
            }
            beanList.add(videoDetailsInfoBean);
        }
        return beanList;
    }

    @Override
    protected int response(List<VideoDetailsInfoBean> result, boolean forMore) {
        int code;
        if (result != null && result.size() > 0) {
            code = 200;
            final List<VideoDetailsInfoBean> list = result;
            if (forMore) {
                responseForAddition(list);
            } else {
                responseForRefresh(list);
            }
            notifyDataSetChanged();
        } else {
            rollbackPage();
            code =  404;
        }
        return code;
    }

    private void responseForAddition(List<VideoDetailsInfoBean> data) {
        addData(data);
    }

    private void responseForRefresh(List<VideoDetailsInfoBean> data) {
//        mScanMapping.clear();
//        mExistIds.clear();
        refreshData(data);
    }

    @Override
    public void refresh(Bundle params) {
        super.refresh(params);
        load(params, false);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public VideoDetailsInfoBean getItem(int position) {
        return super.getItem(position);
    }
}
