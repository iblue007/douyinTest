package com.saild.douyintest.util;

import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理工具类
 */
public class ThreadUtil {
	/**
	 * 固定数量线程池
	 */
	private static ExecutorService executorService;
	/**
	 * 匣子专用线程池
	 */
	private static ExecutorService drawerExecutorService;

	public static ExecutorService getMoreExecutorService() {
		if (moreExecutorService == null)
			moreExecutorService = Executors.newCachedThreadPool();
		return moreExecutorService;
	}

	/**
	 * 非固定数量线程池
	 */
	private static ExecutorService moreExecutorService;
	
	/**
	 * 其它固定数量线程池
	 */
	private static ExecutorService otherExecutorService;
	
	/**
	 * 该方法为单线程执行仅适用于应用程序图标刷新
	 * @param command
	 */
	public static void execute(Runnable command) {
		if (executorService == null)
			executorService = Executors.newFixedThreadPool(1);
		
		executorService.execute(command);
	}
	
	/**
	 * 该方法仅为匣子应用程序图标刷新
	 * @param command
	 */
	public static void executeDrawer(Runnable command) {
		if (drawerExecutorService == null)
			drawerExecutorService = Executors.newFixedThreadPool(1);
		
		drawerExecutorService.execute(command);
	}

	/**
	 * 非固定数量线程池
	 * @param command
	 */
	public static void executeMore(Runnable command) {
		if (moreExecutorService == null)
			moreExecutorService = Executors.newCachedThreadPool();
		
		moreExecutorService.execute(command);
	}
	
	/**
	 * 其它固定数量线程池
	 * @param command
	 */
	public static void executeOther(Runnable command) {
		if (otherExecutorService == null)
			otherExecutorService = Executors.newFixedThreadPool(1);
		
		otherExecutorService.execute(command);
	}

	public static boolean isInMainThread(){
		return Looper.getMainLooper() == Looper.myLooper();
	}
}
