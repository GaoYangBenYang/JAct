package com.gaoyang.jact.utils.asynchronous;

import com.gaoyang.jact.utils.GlobalConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 虚拟线程池
 */
public class VirtualThreadPool {
    /**
     * 虚拟线程池
     */
    protected static final ExecutorService outputExecutor = Executors.newVirtualThreadPerTaskExecutor();
    /**
     * 终止消息监听
     */
    protected static final String POISON_PILL = "POISON_PILL";
    /**
     * 计数器，用于跟踪剩余任务
     */
    protected static final AtomicInteger taskCount = new AtomicInteger(0);
    /**
     * log4j
     */
    private static final Logger logger = LogManager.getLogger(VirtualThreadPool.class);
    /**
     * 阻塞队列用于存储消息控制台打印消息
     */
    private static final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    /**
     * 阻塞队列用于存储日志消息
     */
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();

    /*
      启动虚拟线程处理不同的消息队列
     */
    static {
        //控制台打印输出
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
                logger.warn("Console output thread was interrupted", e);
            }
        });
        // 日志记录
        outputExecutor.execute(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(GlobalConstant.USER_HOME + GlobalConstant.JACT_LOG, true))) {
                while (true) {
                    String message = logQueue.take();
                    if (message.equals(POISON_PILL)) {
                        break; // 收到终止消息，退出循环
                    }
                    writer.write(message);
                    writer.newLine();
                    logger.info("Logged message: {}", message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Log writing thread was interrupted", e);
            } catch (IOException e) {
                logger.error("Failed to write log message", e);
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
            logger.debug("Message queued for console output: {}", message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Failed to queue message for console output", e);
        }
    }

    /**
     * 异步日志记录
     */
    public static void asyncLog(String message) {
        try {
            taskCount.incrementAndGet(); // 增加任务计数
            logQueue.put(message); // 将日志消息放入队列
            logger.debug("Message queued for logging: {}", message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Failed to queue message for logging", e);
        }
    }

    /**
     * 程序结束时关闭线程池
     */
    public static void shutdownExecutor() {
        try {
            if (taskCount.get() == 0) {
                messageQueue.put(POISON_PILL); // 如果没有任务，立即放入终止消息
                logQueue.put(POISON_PILL); // 同时放入日志终止消息
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Failed to put poison pill", e);
        }
        outputExecutor.shutdown();
        try {
            if (!outputExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                outputExecutor.shutdownNow(); // 强制关闭线程池
                logger.warn("Executor did not terminate in the specified time.");
            }
        } catch (InterruptedException e) {
            outputExecutor.shutdownNow(); // 强制关闭线程池
            Thread.currentThread().interrupt(); // 恢复中断状态
            logger.error("Shutdown interrupted", e);
        }
        logger.info("Executor service shut down");
    }

}
