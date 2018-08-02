package com.saild.douyintest.adapter.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月04日 20:02.</br>
 * @update: </br>
 */

public interface OnItemLongClickListener {

    boolean onItemLongClick(ViewGroup parent, View view, int position, long id);
}
