package com.saild.douyintest.adapter.base;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: </br>
 * Author: cxy
 * Date: 2017/3/1.
 */

public abstract class EnhanceRecyclerAdapter<T> extends BaseRecyclerAdapter {

    public List<T> getmData() {
        return mData;
    }

    private List<T> mData = new ArrayList<T>();
    private LoadAsyncRunnable<T> mAsyncTask;

    protected int mPageIndex = 0;
    protected int mPageSize = 20;
    protected int mDataTotal = 0;
    protected int mConcreteCount = 0;

    protected boolean isNoMoreData = false;
    private boolean mNeedPullUpToLoadMore = false;
    //待加载，是否未加载过数据
    private boolean isPendingLoad = true;

    private Bundle mReqParams;

    private OnPullToRefreshListener mPullToRefreshListener;
    private OnLoadStateListener mOnLoadStateListener;
//    private ItemFilter<T> mItemFilter;

    private boolean isDetachedFromParent = false;

    public EnhanceRecyclerAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public EnhanceRecyclerAdapter(Context context, IViewMapping mapping) {
        super(context, mapping);
    }

    public EnhanceRecyclerAdapter(Context context, int layoutResId, boolean pushToLoadMore) {
        super(context, layoutResId);
        mNeedPullUpToLoadMore = pushToLoadMore;
    }

    @Override
    public void clear() {
        mData.clear();
        resetPage();
        mDataTotal = 0;
        mConcreteCount = 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        if (position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public void refresh(Bundle params) {
        resetPage();
        nextPage();
        load(params, false);
    }

    @Override
    public void addition(Bundle params) {
        nextPage();
        load(params, true);
    }

    protected synchronized void load(final Bundle params, final boolean forMore) {
        //增加脱离检查
        if (isDetachedFromParent) {
            rollbackPage();
            return;
        }
        mReqParams = params;
//        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
//            rollbackPage();
//            return;
//        }
//
        if (mAsyncTask == null || mAsyncTask.getStatus() != AsyncTask.Status.PENDING) {
            mAsyncTask = new LoadAsyncRunnable<T>();
        }
        if (mOnLoadStateListener != null && !isDetachedFromParent) {
            mOnLoadStateListener.onLoadStart(forMore);
        }

        //模拟加载数据
        mAsyncTask.setOnTaskProcessListener(new OnTaskProcessListener<T>() {
            @Override
            public List<T> execute(Bundle params) {
                try {
                    return executeAsync(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onCompleted(List<T> result) {
                if (result != null  ) {
                    mDataTotal = 10000;//TODO:数据乱写
                }
                int code = response(result, forMore);
//                if(result != null && result.size() > 0){
//                    mOnLoadStateListener.onLoadCompleted(isNoMoreData, 200);
//                }else {
//                    if (mOnLoadStateListener != null && !isDetachedFromParent) {
//                        mOnLoadStateListener.onLoadError(isPendingLoad, isNothing(), 404, "加载出错");
//                    }
//                }
//                if (result != null && result.getCsResult().isRequestOK() && result.getPageInfo() != null) {
//                    mDataTotal = result.getPageInfo().totalRecordNums;
//                }
//                int code = response(result, forMore);
//                if (code == ResultCodeMap.SERVER_RESPONSE_CODE_SUCCESS) {
//                    if (mOnLoadStateListener != null && !isDetachedFromParent) {
//                        mOnLoadStateListener.onLoadCompleted(isNoMoreData, code);
//                    }
//                } else {
//                    if (mOnLoadStateListener != null && !isDetachedFromParent) {
//                        mOnLoadStateListener.onLoadError(isPendingLoad, isNothing(), code, result == null ? "unknown" : result.getCsResult().getResultMessage());
//                    }
//                }
                isPendingLoad = false;
            }
        });

        mAsyncTask.execute(params);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mNeedPullUpToLoadMore) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy < 0) {//滑到上面，不做拉到底部更新处理
                        return;
                    }
                    int visibleItemCount = 0;
                    int totalItemCount = 0;
                    int firstVisibleItem = 0;
                    int visibleThreshold = 1;
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)[staggeredGridLayoutManager.getSpanCount() - 1];
                        visibleThreshold = staggeredGridLayoutManager.getSpanCount()/* * staggeredGridLayoutManager.getSpanCount()*/;
                    }

                    if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                        visibleThreshold = gridLayoutManager.getSpanCount()/* * gridLayoutManager.getSpanCount()*/;
                    }

                    if (!isLoading() && !end() && ((totalItemCount - visibleItemCount) <= (firstVisibleItem))) {
                        if (mPullToRefreshListener != null) {
//                            Log.d("cxydebug", "load more");
                            Log.e("========","========totalItemCount:"+totalItemCount+"--visibleItemCount:"+visibleItemCount+"--firstVisibleItem:"+firstVisibleItem+"--mdata:"+mData.size());
                            mPullToRefreshListener.onPullUpToRefresh();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        isDetachedFromParent = true;
    }

    protected void nextPage() {
        mPageIndex++;
    }

    protected void rollbackPage() {
        mPageIndex = mPageIndex <= 0 ? 0 : --mPageIndex;
    }

    protected void resetPage() {
        mPageIndex = 0;
        isNoMoreData = false;
    }

    protected void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            return;
        }
        this.mPageSize = pageSize;
    }

    protected boolean end() {
        return isNoMoreData;
    }

    private void evalNoMore(List<T> tmpData) {
        //如果有总数，优先判断总数，否则判断页大小
        if (mDataTotal > 0) {
            isNoMoreData = mConcreteCount >= mDataTotal;
            if (!isNoMoreData) {
                isNoMoreData = (mPageIndex * mPageSize) >= mDataTotal;
            }
        } else if (tmpData == null || tmpData.size() < mPageSize) {
            isNoMoreData = true;
        }
    }

    private List<T> filter(List<T> source) {
//        if (mItemFilter == null) {
//            return source;
//        }
//        List<T> list = new ArrayList<T>();
//        if (source != null) {
//            for (T e : source) {
//                if (mItemFilter.accept(mData, e)) {
//                    list.add(e);
//                }
//            }
//        }
        return source;
    }

    protected void addData(List<T> data) {
        evalNoMore(data);
        if (data != null) {
            mConcreteCount += data.size();
            mData.addAll(filter(data));
        }
    }

    /**
     * 往前插入数据
     *
     * @param data
     */
    public void addForward(List<T> data) {
        evalNoMore(data);
        if (data != null) {
            mData.addAll(0, filter(data));
            mConcreteCount += data.size();
        }
    }

    protected void refreshData(List<T> data) {
        mData.clear();
        mConcreteCount = 0;
        addData(data);
    }

    protected List<T> executeAsync(Bundle params) {
        return null;
    }

    protected int response(List<T> result, boolean forMore) {
        int code;
        if (result != null && result.size()>0) {
            code = 200;
            final List<T> list = result;
            if (list != null) {
                if (!list.isEmpty()) {
                    if (forMore) {
                        addData(list);
                    } else {
                        refreshData(list);
                    }
                } else {
                    if (!forMore) {
                        clear();
                    } else {
                        addData(list);
                    }
                 //   code = ResultCodeMap.RESULT_CODE_NOTHING;
                }
            } else {
                if (!forMore) {
                    clear();
                } else {
                    addData(list);
                }
              //  code = ResultCodeMap.RESULT_CODE_NOTHING;
            }
            notifyDataSetChanged();
        } else {
            rollbackPage();
            code = 404;//result == null ? ResultCodeMap.RESULT_CODE_EXCEPTION : result.getCsResult().getResultCode();
        }
        return code;
    }

    public boolean isLoading() {
        if (mAsyncTask == null || mAsyncTask.getStatus() == AsyncTask.Status.PENDING || mAsyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            return false;
        }
        return true;
    }

    public boolean isPendingLoad() {
        return isPendingLoad;
    }

    public boolean isNothing() {
        return mData.isEmpty();
    }

    public Bundle getParams() {
        return mReqParams;
    }

    public int getKnownCount() {
        return mDataTotal;
    }

    public void setOnPullToRefreshListener(OnPullToRefreshListener listener) {
        this.mPullToRefreshListener = listener;
    }

    public void setOnLoadStateListener(OnLoadStateListener listener) {
        this.mOnLoadStateListener = listener;
    }

//    public void setItemFilter(ItemFilter<T> filter) {
//        this.mItemFilter = filter;
//    }
}
