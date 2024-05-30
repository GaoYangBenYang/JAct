package com.gaoyang.jact.asynchronous.logger;


import com.gaoyang.jact.asynchronous.VirtualThreadPool;
import com.gaoyang.jact.asynchronous.interfaces.LogTaskHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 日志记录类，负责将日志消息异步写入文件。
 */
@Component
public class LogInfo implements LogTaskHandler {

    private static final Logger logger = LogManager.getLogger(LogInfo.class);
    /**
     * 阻塞队列，用于存储日志消息
     */
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();

    /**
     * 日志单例实例
     */
    private static final LogInfo instance = new LogInfo();

    /**
     * 终止消息
     */
    private static final String POISON_PILL = "POISON_PILL";

    /**
     * 终止消息计数器，确保唯一性
     */
    private static final AtomicInteger poisonPillCount = new AtomicInteger(0);

    /**
     * 日志文件路径
     */
    private static final String logFilePath = System.getProperty("user.home") + "/jact.log";

    /**
     * 私有构造方法，初始化日志记录任务
     */
    private LogInfo() {
        VirtualThreadPool.submitTask(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
                while (true) {
                    String message = logQueue.take();
                    if (message.equals(POISON_PILL)) {
                        if (poisonPillCount.decrementAndGet() <= 0) {
                            break;
                        }
                    } else {
                        writer.write(message);
                        writer.newLine();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                logger.error("Failed to write log", e);
                logToBackupFile("Failed to write log: " + e.getMessage());
            }
        });
    }

    /**
     * 获取日志单例实例
     *
     * @return 日志实例
     */
    public static LogInfo getInstance() {
        return instance;
    }

    /**
     * 异步记录日志
     *
     * @param message 日志消息
     */
    @Override
    public void handleTask(String message) {
        try {
            logQueue.put(message);
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
            poisonPillCount.incrementAndGet();
            logQueue.put(POISON_PILL);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        VirtualThreadPool.shutdownExecutor(60, TimeUnit.SECONDS);
    }

    /**
     * 当写入日志文件失败时，写入备用日志文件
     *
     * @param message 错误消息
     */
    private void logToBackupFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/jact_backup.log", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            logger.error("Failed to write to backup log", e);
        }
    }
}
