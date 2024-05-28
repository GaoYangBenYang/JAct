package com.gaoyang.jact.utils.asynchronous;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 虚拟线程池，负责管理虚拟线程的创建和执行。
 */
public class VirtualThreadPool {
    /**
     * 虚拟线程池，用于执行任务
     */
    private static final ExecutorService outputExecutor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * 任务计数器，用于跟踪提交的任务数量
     */
    private static final AtomicInteger taskCount = new AtomicInteger(0);

    /**
     * 运行中的任务计数器，用于跟踪当前运行的任务数量
     */
    private static final AtomicInteger runningTasks = new AtomicInteger(0);

    /**
     * 提交任务到虚拟线程池
     *
     * @param task 要执行的任务
     */
    public static void submitTask(Runnable task) {
        taskCount.incrementAndGet();
        runningTasks.incrementAndGet();
        outputExecutor.execute(() -> {
            try {
                task.run();
            } finally {
                runningTasks.decrementAndGet();
                taskCount.decrementAndGet();
            }
        });
    }

    /**
     * 获取当前任务计数
     *
     * @return 当前任务计数
     */
    public static int getTaskCount() {
        return taskCount.get();
    }

    /**
     * 获取当前运行中的任务计数
     *
     * @return 当前运行中的任务计数
     */
    public static int getRunningTasks() {
        return runningTasks.get();
    }

    /**
     * 关闭虚拟线程池
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public static void shutdownExecutor(long timeout, TimeUnit unit) {
        outputExecutor.shutdown();
        try {
            if (!outputExecutor.awaitTermination(timeout, unit)) {
                outputExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            outputExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
