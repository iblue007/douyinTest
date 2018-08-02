package com.saild.douyintest.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;


/**
 * 双击监听
 * Created by linliangbin on 2017/6/27 18:53.
 */

public class DoubleClickDetector {

    GestureDetector gestureDetector;
    private Context context;
    private OnPageSelectedListenner mOnPageSelectedListenner;
    private boolean isDoubleClick = false;
    private boolean isSingleClick = false;

    public DoubleClickDetector(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                isDoubleClick = true;
                if(mOnPageSelectedListenner != null){
                    mOnPageSelectedListenner.onDoubleClick();
                }
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                isSingleClick = true;
                if(mOnPageSelectedListenner != null){
                    mOnPageSelectedListenner.onSingleClick();
                }
                return super.onSingleTapConfirmed(e);
            }

        });
    }

    public void handleEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
    }


    public boolean isDoubleClick() {
        return isDoubleClick;
    }
    public boolean isSingleClick() {
        return isSingleClick;
    }

    public void reset() {
        isDoubleClick = false;
    }
    public void resetSingle() {
        isSingleClick = false;
    }

    public void setListener(OnPageSelectedListenner mOnPageSelectedListenner1) {
        this.mOnPageSelectedListenner =mOnPageSelectedListenner1;
    }


    public interface OnPageSelectedListenner {
        /**
         * 上下滑动页面更新
         */
        void onUpdate(int selectedPage);

        /**
         * 向左滑动
         */
        void onSwipeLeft();

        /**
         * 向右滑动
         */
        void onSwipeRight();

        /**
         * 双击
         */
        void onDoubleClick();
        void onSingleClick();
    }
}
