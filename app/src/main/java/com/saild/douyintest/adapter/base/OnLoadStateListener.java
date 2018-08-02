package com.saild.douyintest.adapter.base;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月09日 16:09.</br>
 * @update: </br>
 */

public interface OnLoadStateListener {

    void onLoadStart(boolean loadMore);

    void onLoadError(boolean pending, boolean isNothing, int errorCode, String errorMsg);

    void onLoadCompleted(boolean end, int code);
}
