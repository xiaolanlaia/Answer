package com.example.answer.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by W on 2019/3/7.
 */

public class ExecutorServiceDemo {
    public static final int size = 2;
    public static void main(String[] args) {
        // 创建一个线程池对象，控制要创建几个线程对象。
        // public static ExecutorService newFixedThreadPool(int nThreads)

        ExecutorService pool = new ThreadPoolExecutor(size,size,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());

        // 可以执行Runnable对象或者Callable对象代表的线程
        pool.submit(new MyRunnable());
        //结束线程池
        pool.shutdown();
    }
}
