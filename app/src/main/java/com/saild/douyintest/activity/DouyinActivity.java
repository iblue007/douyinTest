package com.saild.douyintest.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.saild.douyintest.R;
import com.saild.douyintest.adapter.VideoAdapter;
import com.saild.douyintest.adapter.base.OnPullToRefreshListener;
import com.saild.douyintest.bean.VideoDetailsInfoBean;
import com.saild.douyintest.present.IVideoDetailView;
import com.saild.douyintest.util.DoubleClickDetector;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @Author 许少东
 * Created at 2018/6/25.
 */
public class DouyinActivity extends BaseActivity implements IVideoDetailView,DoubleClickDetector.OnPageSelectedListenner{
    private final static String TAG = DouyinActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ViewPagerLayoutManager mLayoutManager;
    private VideoAdapter adapter;
    private Handler handler = new Handler();
    DoubleClickDetector doubleClickDetector;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin);
        initView();
        setListener();
    }

    private void initView() {
        doubleClickDetector = new DoubleClickDetector(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_video_list);
        mLayoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new VideoAdapter(this,R.layout.item_video);
        mRecyclerView.setAdapter(adapter);
        adapter.refresh(null);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doubleClickDetector.handleEvent(event);
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    float absY = Math.abs(y1 - y2);
                    float absX = Math.abs(x1 - x2);
                    if(absY > absX) {
                        if(absY >= 50 && !directVertical){
                            Log.e("======","======top");
                            directVertical = true;
                            mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                        }
                    }else {
                        if(absX >= 50 && directVertical){
                            Log.e("======","======right");
                            directVertical = false;
                            mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                        }
                    }
                }
                return false;
            }
        });
        doubleClickDetector.setListener(this);
        adapter.setOnPullToRefreshListener(new OnPullToRefreshListener() {
            @Override
            public void onPullUpToRefresh() {
                adapter.addition(null);
            }
        });
    }

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    boolean directVertical = true;

    private void setListener() {
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playVideo();
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                playVideo();
            }
        });
    }

    private void releaseVideo(int pos) {
        try {
            View itemView = mRecyclerView.getChildAt(pos);
            final VideoView videoView = (VideoView) itemView.findViewById(R.id.video_item);
            final ImageView imgThumb = (ImageView) itemView.findViewById(R.id.img_thumb);
            final ImageView imgPlay = (ImageView) itemView.findViewById(R.id.img_play);
            videoView.stopPlayback();
            imgThumb.animate().alpha(1).start();
            imgPlay.animate().alpha(0f).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playVideo() {
        try {
            View itemview = mRecyclerView.getChildAt(0);
            VideoView videoView = (VideoView) itemview.findViewById(R.id.video_item);
            final ImageView imgPlay = (ImageView) itemview.findViewById(R.id.img_play);
            final ImageView imgThumb = (ImageView) itemview.findViewById(R.id.img_thumb);
            final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
            videoView.start();
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    mediaPlayer[0] = mp;
                    mp.setLooping(true);
                    imgThumb.animate().alpha(0).setDuration(200).start();
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoInfoLoadedSuccess(ArrayList<VideoDetailsInfoBean> videoDetails) {

    }

    @Override
    public void onVideoInfoLoadedFail() {

    }

    @Override
    public void onUpdate(int selectedPage) {
        Log.e("======","======onUpdate");

    }

    @Override
    public void onSwipeLeft() {
        Log.e("======","======onSwipeLeft");

    }

    @Override
    public void onSwipeRight() {
        Log.e("======","======onSwipeRight");

    }

    @Override
    public void onDoubleClick() {
        Log.e("======","======onDoubleClick");

    }

    @Override
    public void onSingleClick() {
        Log.e("======","======onSingleClick");
    }
}
