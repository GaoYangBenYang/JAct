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
 * 日志记录类，负责将日志消息异步写入文件。
 */
@Component
public class LogInfo implements LogTaskHandler {

    private static final Logger LOGGER = LogManager.getLogger(LogInfo.class);
    /**
     * 阻塞队列，用于存储日志消息
     */
    private static final BlockingQueue<String> LOG_QUEUE = new LinkedBlockingQueue<>();

    /**
     * 日志单例实例
     */
    private static final LogInfo INSTANCE = new LogInfo();

    /**
     * 终止消息
     */
    private static final String POISON_PILL = "POISON_PILL";

    /**
     * 终止消息计数器，确保唯一性
     */
    private static final AtomicInteger POISON_PILL_COUNT = new AtomicInteger(0);

    /**
     * 私有构造方法，初始化日志记录任务
     */
    private LogInfo() {
        VirtualThreadPool.submitTask(() -> {
            while (true) {
                try {
                    String message = LOG_QUEUE.take();
                    if (message.equals(POISON_PILL)) {
                        if (POISON_PILL_COUNT.decrementAndGet() <= 0) {
                            break;
                        }
                    } else {
                        LOGGER.info(message);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    /**
     * 获取日志单例实例
     *
     * @return 日志实例
     */
    public static LogInfo getInstance() {
        return INSTANCE;
    }

    /**
     * 异步记录日志
     *
     * @param message 日志消息
     */
    @Override
    public void handleTask(String message) {
        try {
            LOG_QUEUE.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 关闭日志记录
     */
    @Override
    public void shutdown() {
        try {
            POISON_PILL_COUNT.incrementAndGet();
            LOG_QUEUE.put(POISON_PILL);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        VirtualThreadPool.shutdownExecutor(1, TimeUnit.SECONDS);
    }
}
