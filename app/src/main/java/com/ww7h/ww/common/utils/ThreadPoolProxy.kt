package com.ww7h.ww.common.utils

import java.util.concurrent.*

class ThreadPoolProxy
/**
 * @param corePoolSize    核心池的大小
 * @param maximumPoolSize 最大线程数
 */
private constructor(private val mCorePoolSize: Int, private val mMaximumPoolSize: Int) {

    private var mExecutor: ThreadPoolExecutor? = null

    /**
     * 初始化ThreadPoolExecutor
     * 双重检查加锁,只有在第一次实例化的时候才启用同步机制,提高了性能
     */
    private fun initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor!!.isShutdown || mExecutor!!.isTerminated) {
            synchronized(ThreadPoolProxy::class.java) {
                if (mExecutor == null || mExecutor!!.isShutdown || mExecutor!!.isTerminated) {
                    val keepAliveTime: Long = 3000
                    val unit = TimeUnit.MILLISECONDS
                    val workQueue = LinkedBlockingDeque<Runnable>()
                    val threadFactory = Executors.defaultThreadFactory()
                    val handler = ThreadPoolExecutor.DiscardPolicy()

                    mExecutor = ThreadPoolExecutor(
                        mCorePoolSize, mMaximumPoolSize, keepAliveTime,
                        unit, workQueue, threadFactory, handler
                    )
                }
            }
        }
    }

    /*
     执行任务和提交任务的区别?
     1.有无返回值
     execute->没有返回值
     submit-->有返回值
     2.Future的具体作用?
     1.有方法可以接收一个任务执行完成之后的结果,其实就是get方法,get方法是一个阻塞方法
     2.get方法的签名抛出了异常===>可以处理任务执行过程中可能遇到的异常
     */

    /**
     * 执行任务
     */
    fun execute(task: Runnable) {
        initThreadPoolExecutor()
        mExecutor!!.execute(task)
    }

    /**
     * 提交任务
     */
    fun submit(task: Runnable): Future<*> {
        initThreadPoolExecutor()
        return mExecutor!!.submit(task)
    }

    /**
     * 移除任务
     */
    fun remove(task: Runnable) {
        initThreadPoolExecutor()
        mExecutor!!.remove(task)
    }

    companion object {
        private var mInstance: ThreadPoolProxy? = null

        val instance: ThreadPoolProxy
            get() {

                if (mInstance == null) {
                    synchronized(ThreadPoolProxy::class.java) {
                        if (mInstance == null) {
                            val cpuCount = Runtime.getRuntime().availableProcessors()
                            mInstance = ThreadPoolProxy(cpuCount, cpuCount * 10)
                        }
                    }
                }
                return this.mInstance!!
            }
    }
}
