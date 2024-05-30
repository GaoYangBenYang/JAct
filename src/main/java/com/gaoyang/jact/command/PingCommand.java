package com.gaoyang.jact.command;

import com.gaoyang.jact.asynchronous.VirtualThreadPool;
import picocli.CommandLine;

// 定义ping命令类
@CommandLine.Command(name = "ping", description = "Ping the application")
public class PingCommand implements Runnable {

    @Override
    public void run() {
        // 使用虚拟线程池执行命令
        VirtualThreadPool.submitTask(() -> {
            System.out.println("Pong");
        });
    }
}
