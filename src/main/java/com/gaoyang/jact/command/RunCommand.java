package com.gaoyang.jact.command;

import com.gaoyang.jact.asynchronous.VirtualThreadPool;
import picocli.CommandLine;

// 定义运行命令类
@CommandLine.Command(name = "run", description = "Run the application")
public class RunCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<params>", description = "The parameters to run with")
    private String[] params;

    @Override
    public void run() {
        // 使用虚拟线程池执行命令
        VirtualThreadPool.submitTask(() -> {
            System.out.println("Running with parameters: " + String.join(", ", params));
        });
    }
}
