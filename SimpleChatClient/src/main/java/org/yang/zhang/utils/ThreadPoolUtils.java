package org.yang.zhang.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolUtils {

    private  static ExecutorService threadPoolExecutor=Executors.newFixedThreadPool(100);

    public static void run(Runnable runnable){
        threadPoolExecutor.submit(runnable);
    }
}
