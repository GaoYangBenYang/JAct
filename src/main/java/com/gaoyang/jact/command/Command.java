package com.gaoyang.jact.command;

import com.gaoyang.jact.asynchronous.VirtualThreadPool;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.TimeUnit;

@Component
public class Command implements CommandLineRunner {

    @Override
    public void run(String... args) {
        new CommandLine(new Jact()).execute(args);
    }

    // 在应用程序关闭时清理虚拟线程池
    @PreDestroy
    public void onShutdown() {
        VirtualThreadPool.shutdownExecutor(1, TimeUnit.SECONDS);
    }

    @CommandLine.Command(name = "jact", version = "jact 1.0", description = "Jact CLI application", subcommands = {
            RunCommand.class,
            VersionCommand.class,
            PingCommand.class
    })
    private static class Jact implements Runnable {

        /**
         * Runs this operation.
         */
        @Override
        public void run() {
            CommandLine.usage(this, System.out);
        }
    }
}