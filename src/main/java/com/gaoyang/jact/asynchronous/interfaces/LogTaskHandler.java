package com.gaoyang.jact.asynchronous.interfaces;

/**
 * 任务处理接口，定义任务处理方法
 */
public interface LogTaskHandler {
    /**
     * 处理任务
     *
     * @param message 任务消息
     */
    void handleTask(String message);

    /**
     * 关闭任务处理器
     */
    void shutdown();
}