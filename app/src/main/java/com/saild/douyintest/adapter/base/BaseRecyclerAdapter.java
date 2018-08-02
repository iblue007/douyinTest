package com.saild.douyintest.adapter.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description: </br>
 * Author: cxy
 * Date: 2017/2/28.
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected Context mContext;
    private IViewMapping mViewMapping;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private View.OnTouchListener mOnTouchListener;
    private View.OnHoverListener mOnHoverListener;

    private SparseArray<OnItemClickListener> mFavorClickListeners = new SparseArray<OnItemClickListener>();

    public BaseRecyclerAdapter(Context context, final int layoutResId) {
        this.mContext = context;
        this.mViewMapping = new IViewMapping() {
            @Override
            public int map(int position) {
                return 0;
            }

            @Override
            public int getViewResId(int viewType) {
                return layoutResId;
            }
        };
    }

    public BaseRecyclerAdapter(Context context, IViewMapping mapping) {
        this.mContext = context;
        this.mViewMapping = mapping;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final BaseRecyclerViewHolder holder = BaseRecyclerViewHolder.get(mContext, parent, addToRoot(), viewType, mViewMapping.getViewResId(viewType));
//        holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(parent, v, holder.getAdapterPosition(), v.getId());
//                }
//            }
//        });
//
//        holder.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnItemLongClickListener != null) {
//                    return mOnItemLongClickListener.onItemLongClick(parent, v, holder.getAdapterPosition(), v.getId());
//                }
//                return false;
//            }
//        });

//        holder.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mOnTouchListener != null) {
//                    return mOnTouchListener.onTouch(v, event);
//                }
//                return false;
//            }
//        });
//        holder.setOnHoverListener(new View.OnHoverListener() {
//            @Override
//            public boolean onHover(View v, MotionEvent event) {
//                if (mOnHoverListener != null) {
//                    return mOnHoverListener.onHover(v, event);
//                }
//                return false;
//            }
//        });

//        if (mFavorClickListeners != null && mFavorClickListeners.size() > 0) {
//            for (int i = 0, len = mFavorClickListeners.size(); i < len; i++) {
//                final OnItemClickListener listener = mFavorClickListeners.valueAt(i);
//                final int resId = mFavorClickListeners.keyAt(i);
//                if (listener == null) {
//                    continue;
//                }
//                holder.setOnClickListener(resId, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        listener.onItemClick(parent, v, holder.getAdapterPosition(), v.getId());
//                    }
//                });
//            }
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        convert(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mViewMapping != null) {
            return mViewMapping.map(position);
        }
        return super.getItemViewType(position);
    }

    public abstract void refresh(Bundle params);

    public abstract void clear();

    public abstract void addition(Bundle params);

    public boolean addToRoot() {
        return false;
    }


    /**
     * 视图转化 invoke in call back method {@link #onBindViewHolder(BaseRecyclerViewHolder, int)}
     *
     * @param holder
     * @param position
     */
    protected void convert(BaseRecyclerViewHolder holder, int position) {

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public void setOnHoverListener(View.OnHoverListener onHoverListener) {
        this.mOnHoverListener = onHoverListener;
    }

    public void setOnFavorClickListener(int favorId, OnItemClickListener listener) {
        this.mFavorClickListeners.put(favorId, listener);
    }

    public interface IViewMapping {
        int map(int position);

        int getViewResId(int viewType);
    }
}
