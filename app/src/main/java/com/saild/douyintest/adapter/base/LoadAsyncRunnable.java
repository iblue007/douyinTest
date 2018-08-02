package com.saild.douyintest.adapter.base;

import android.os.AsyncTask;
import android.os.Bundle;

import com.saild.douyintest.util.Global;
import com.saild.douyintest.util.ThreadUtil;

import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月15日 20:04.</br>
 * @update: </br>
 */

public class LoadAsyncRunnable<T> {

    private Bundle mParams;
    private AsyncTask.Status mStatus = AsyncTask.Status.PENDING;
    private OnTaskProcessListener<T> mCallback;

    public LoadAsyncRunnable() {
        mStatus = AsyncTask.Status.PENDING;
    }

    public AsyncTask.Status getStatus() {
        return mStatus;
    }

    public void setOnTaskProcessListener(OnTaskProcessListener<T> callback) {
        mCallback = callback;
    }

    public void execute(Bundle params) {
        if (mStatus != AsyncTask.Status.PENDING) {
            throw new IllegalStateException("task has been run!");
        }
        mStatus = AsyncTask.Status.RUNNING;
        mParams = params;
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                runTask();
            }
        });
    }

    private void runTask() {
        mStatus = AsyncTask.Status.RUNNING;
        if (mCallback != null) {
            final List<T> res = mCallback.execute(mParams);

            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    mStatus = AsyncTask.Status.FINISHED;
                    if (mCallback != null) {
                        mCallback.onCompleted(res);
                    }
                }
            });
        }
    }
}
