package com.saild.douyintest.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.saild.douyintest.R;

/**
 * Description: </br>
 * Author: cxy
 * Date: 2017/3/1.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews = new SparseArray<View>();

    private Object mAssociateObj;

    private volatile int mPos;

    private int mViewType;

    protected BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        RelativeLayout relativeLayout = (RelativeLayout) itemView.findViewById(R.id.root_view);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public static BaseRecyclerViewHolder get(View converView) {
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(converView);
        converView.setTag(holder);
        return holder;
    }

    public static BaseRecyclerViewHolder get(Context context, int layoutResId) {
        View convertView = LayoutInflater.from(context).inflate(layoutResId, null);
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(convertView);
        convertView.setTag(holder);
        return holder;
    }

    public static BaseRecyclerViewHolder get(Context context, int viewType, int layoutResId) {
        View convertView = LayoutInflater.from(context).inflate(layoutResId, null);
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(convertView);
        convertView.setTag(holder);
        holder.mViewType = viewType;
        return holder;
    }

    public static BaseRecyclerViewHolder get(Context context, ViewGroup parent, boolean addToRoot, int viewType, int layoutResId) {
        if (!addToRoot) {
            return get(context, viewType, layoutResId);
        }
        View convertView = LayoutInflater.from(context).inflate(layoutResId, parent, false);
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(convertView);
        convertView.setTag(holder);
        holder.mViewType = viewType;
        return holder;
    }

    public <T extends View> T getView(int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }

        return (T) view;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }

    public void setOnClickListener(int resId, View.OnClickListener onClickListener) {
        View view = getView(resId);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void setOnLongClickListener(int resId, View.OnLongClickListener listener) {
        View view = getView(resId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        itemView.setOnTouchListener(onTouchListener);
    }

    public void setOnHoverListener(View.OnHoverListener onHoverListener) {
        itemView.setOnHoverListener(onHoverListener);
    }

    public void setVisibility(int resId, int visibility) {
        View view = getView(resId);
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public void setText(int resId, CharSequence text) {
        View view = getView(resId);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(text);
        }
    }

    public void setTextSize(int resId, int textSize) {
        View view = getView(resId);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextSize(textSize);
        }
    }

    public void setBackgroundDrawable(int resId, int imgResId) {
        View view = getView(resId);
        if (view != null) {
            view.setBackgroundResource(imgResId);
        }
    }

    public void setImageResource(int resId, int imgResId) {
        View view = getView(resId);
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(imgResId);
        }
    }

    public void setImageBitmap(int resId, Bitmap bitmap) {
        View view = getView(resId);
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageBitmap(bitmap);
        }
    }

    public void setImageDrawable(int resId, Drawable drawable) {
        View view = getView(resId);
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageDrawable(drawable);
        }
    }

    public void setVideoURI(int resId, Uri imageUri){
        VideoView videoView = getView(resId);
        videoView.setVideoURI(imageUri);
    }

    public void displayImageNew(int resId, int imageUri) {
        ImageView iv = getView(resId);
        iv.setImageResource(imageUri);
    }

    public void displayImage(int resId, String imageUri) {
        ImageView iv = getView(resId);
       // ImageLoader.getInstance().displayImage(imageUri, iv);
    }

//    public void displayImage(int resId, String imageUri, DisplayImageOptions options) {
//        ImageView iv = getView(resId);
//        //ImageLoader.getInstance().displayImage(imageUri, iv, options);
//    }

    public void setSelected(int resId, boolean selected) {
        View view = getView(resId);
        if (view != null) {
            view.setSelected(selected);
        }
    }

    public void setTag(int resId, Object obj) {
        View view = getView(resId);
        if (view != null) {
            view.setTag(obj);
        }
    }

    public void setAssociateObj(Object obj) {
        mAssociateObj = obj;
    }

    public <T> T getAssociateObj() {
        return (T) mAssociateObj;
    }
}
