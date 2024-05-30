package com.gaoyang.jact.asynchronous.logger;

import com.gaoyang.jact.asynchronous.VirtualThreadPool;
import com.gaoyang.jact.asynchronous.interfaces.LogTaskHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 控制台日志输出类，负责将日志消息异步输出到控制台。
 */
@Component
public class ConsoleLog implements LogTaskHandler {

    private static final Logger logger = LogManager.getLogger(ConsoleLog.class);
    /**
     * 阻塞队列，用于存储控制台日志消息
     */
    private static final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    /**
     * 控制台日志单例实例
     */
    private static final ConsoleLog instance = new ConsoleLog();
    /**
     * 终止消息
     */
    private static final String POISON_PILL = "POISON_PILL";
    /**
     * 终止消息计数器，确保唯一性
     */
    private static final AtomicInteger poisonPillCount = new AtomicInteger(0);

    /**
     * 私有构造方法，初始化控制台日志输出任务
     */
    private ConsoleLog() {
        VirtualThreadPool.submitTask(() -> {
            try {
                while (true) {
                    String message = messageQueue.take();
                    if (message.equals(POISON_PILL)) {
                        if (poisonPillCount.decrementAndGet() <= 0) {
                            break;
                        }
                    } else {
                        System.out.println(message);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Description Console log output failed", e);
            }
        });
    }

    /**
     * 获取控制台日志单例实例
     *
     * @return 控制台日志实例
     */
    public static ConsoleLog getInstance() {
        return instance;
    }

    /**
     * 异步输出控制台日志
     *
     * @param message 日志消息
     */
    @Override
    public void handleTask(String message) {
        try {
            messageQueue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to output console log", e);
        }
    }

    /**
     * 关闭控制台日志输出
     */
    @Override
    public void shutdown() {
        try {
            poisonPillCount.incrementAndGet();
            messageQueue.put(POISON_PILL);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Failed to turn off console log output", e);
        }
        VirtualThreadPool.shutdownExecutor(60, TimeUnit.SECONDS);
    }
}
