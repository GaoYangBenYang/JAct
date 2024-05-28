package com.gaoyang.jact;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class JactApplicationTests {

    @Test
    void contextLoads() {
        ExecutorService outputExecutor = Executors.newVirtualThreadPerTaskExecutor();

        // 启动一百万个虚拟线程
        int numThreads = 1000000;
        for (int i = 0; i < numThreads; i++) {
            int taskId = i + 1;
            outputExecutor.execute(() -> {
                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
            });
        }

        // 关闭线程池
        outputExecutor.shutdown();
    }

}
