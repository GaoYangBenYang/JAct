package com.gaoyang.jact.command;

import com.gaoyang.jact.utils.asynchronous.VirtualThreadPool;
import picocli.CommandLine;

// 定义版本命令类
@CommandLine.Command(name = "version", description = "Show the application version")
public class VersionCommand implements Runnable {

    @Override
    public void run() {
        // 使用虚拟线程池执行命令
        VirtualThreadPool.submitTask(() -> {
            System.out.println("Jact version 1.0");
        });
    }
}
