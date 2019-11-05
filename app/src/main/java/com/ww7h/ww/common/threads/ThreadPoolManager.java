package com.ww7h.ww.common.threads;

import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by: Android Studio.
 * Project Nam: Android-common
 * PackageName: com.ww7h.ww.common.threads
 * DateTime: 2019/3/27 18:55
 *
 * @author ww
 */
public class ThreadPoolManager {

    private ThreadPool threadPoolExecutor;
    private Handler mMainHandler;

    private static class ThreadPoolManagerInstance {
        private final static ThreadPoolManager INSTANCE = new ThreadPoolManager();
    }

    public static ThreadPoolManager getInstance() {
        return ThreadPoolManagerInstance.INSTANCE;
    }

    private ThreadPoolManager () {
        ThreadFactory tf = Executors.defaultThreadFactory();
        threadPoolExecutor = new ThreadPool(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 10, 3000,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(30),
                tf,  new ThreadPoolExecutor.DiscardPolicy());

    }

    private ThreadPool getThreadPoolExecutor() {
        if (threadPoolExecutor ==null || threadPoolExecutor.isShutdown() || threadPoolExecutor.isTerminated()) {
            synchronized (ThreadPoolManager.class) {
                if (threadPoolExecutor ==null || threadPoolExecutor.isShutdown() || threadPoolExecutor.isTerminated()) {
                    ThreadFactory tf = Executors.defaultThreadFactory();

                    threadPoolExecutor = new ThreadPool(Runtime.getRuntime().availableProcessors(),
                            Runtime.getRuntime().availableProcessors() * 10, 3000,
                            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(30),
                            tf,  new ThreadPoolExecutor.DiscardPolicy());
                }
            }
        }
        return threadPoolExecutor;
    }

    private void setThreadPoolCallBack(ThreadPoolCallBack threadPoolCallBack) {
        getThreadPoolExecutor().setThreadPoolCallBack(threadPoolCallBack);
    }

    public void execute(Runnable command) {
        getThreadPoolExecutor().execute(command);
    }

    private Handler getMainHandler() {
        if (mMainHandler == null) {
            synchronized (this) {
                if (mMainHandler == null) {
                    mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return mMainHandler;
    }

    public void executeOnUiThread(Runnable command) {
        getMainHandler().post(command);
    }

    public List<Runnable> shutdownNow() {
        return getThreadPoolExecutor().shutdownNow();
    }

    public void shutdown() {

        getThreadPoolExecutor().shutdown();
    }

    public Future<?> submit(Runnable task) {
        return getThreadPoolExecutor().submit(task);
    }

    public boolean remove(Runnable task) {
        return getThreadPoolExecutor().remove(task);
    }

    public interface ThreadPoolCallBack {
        /**
         * 任务执行结束后执行的方法
         * @param r 任务
         * @param t 异常
         */
        void afterExecute(Runnable r, Throwable t);

        /**
         * 任务执行前执行的方法
         * @param t 执行该任务的线程
         * @param r 任务
         */
        void beforeExecute(Thread t, Runnable r);

        /**
         * 关闭线程池时调用
         */
        void terminated();
    }


    private class ThreadPool extends ThreadPoolExecutor {

        private ThreadPoolCallBack threadPoolCallBack;

        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        void setThreadPoolCallBack(ThreadPoolCallBack threadPoolCallBack) {
            this.threadPoolCallBack = threadPoolCallBack;
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {

            if (this.threadPoolCallBack != null) {
                this.threadPoolCallBack.afterExecute(r, t);
            }
            super.afterExecute(r, t);

        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {

            if (this.threadPoolCallBack != null) {
                this.threadPoolCallBack.beforeExecute(t, r);
            }
            super.beforeExecute(t, r);
        }

        @Override
        protected void terminated() {
            super.terminated();
            if (this.threadPoolCallBack != null) {
                this.threadPoolCallBack.terminated();
            }
        }
    }

}
