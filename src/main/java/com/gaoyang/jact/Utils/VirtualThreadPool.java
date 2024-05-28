package com.gaoyang.jact.Utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 虚拟线程池
 */
public class VirtualThreadPool {
    /**
     * 虚拟线程池
     */
    private static final ExecutorService outputExecutor = Executors.newVirtualThreadPerTaskExecutor();
    /**
     * 阻塞队列用于存储消息
     */
    private static final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    /**
     * 终止消息监听
     */
    private static final String POISON_PILL = "POISON_PILL";
    /**
     * 计数器，用于跟踪剩余任务
     */
    private static final AtomicInteger taskCount = new AtomicInteger(0);

    /*
      启动一个虚拟线程不停的从队列中取消息
     */
    static {
        outputExecutor.execute(() -> {
            try {
                while (true) {
                    String message = messageQueue.take();
                    if (message.equals(POISON_PILL)) {
                        break; // 收到终止消息，退出循环
                    }
                    System.out.println(message); // 输出消息
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    /**
     * 异步消息输出
     */
    public static void asyncOutput(String message) {
        try {
            taskCount.incrementAndGet(); // 增加任务计数
            messageQueue.put(message); // 将消息放入队列
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 确保程序结束时关闭线程池
     */
    public static void shutdownExecutor() {
        try {
            if (taskCount.get() == 0) {
                messageQueue.put(POISON_PILL); // 如果没有任务，立即放入终止消息
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        outputExecutor.shutdown();
    }

}
