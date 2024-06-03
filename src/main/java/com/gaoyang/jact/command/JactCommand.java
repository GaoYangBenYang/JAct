package com.gaoyang.jact.command;

import picocli.CommandLine;

@CommandLine.Command(name = "jact", subcommands = {
        HelpCommand.class,
        RunCommand.class,
        VersionCommand.class,
        PingCommand.class
})
public class JactCommand implements Runnable{

    @Override
    public void run() {

    }
}
