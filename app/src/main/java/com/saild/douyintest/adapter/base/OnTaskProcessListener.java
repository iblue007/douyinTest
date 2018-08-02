package com.saild.douyintest.adapter.base;

import android.os.Bundle;

import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月16日 9:34.</br>
 * @update: </br>
 */

public interface OnTaskProcessListener<T> {

    List<T> execute(Bundle params);

    void onCompleted(List<T> result);
}
