package com.gaoyang.jact.config;

import com.gaoyang.jact.asynchronous.logger.ConsoleLog;
import com.gaoyang.jact.asynchronous.logger.LogInfo;
import com.gaoyang.jact.utils.constant.EmojiConstant;
import com.gaoyang.jact.utils.constant.FileConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安装脚本
 * <p>
 * 当运行程序时，自动将 jact 启动命令注册到系统环境变量
 * <p>
 * 验证版本信息，根据版本信息判断是否需要进行更新
 */
@Configuration
public class InstallScript {

    private final ConsoleLog consoleLog;

    private final LogInfo logInfo;

    public InstallScript(ConsoleLog consoleLog, LogInfo logInfo) {
        this.consoleLog = consoleLog;
        this.logInfo = logInfo;
    }

    /**
     * 根据操作系统进行对应的脚本创建以及执行
     *
     * @throws IOException
     */
    @Bean
    public CommandLineRunner createScript() {
        return args -> {
            if (FileConstant.OS_NAME.contains("win")) {
                createWindowsScript();
            } else {
                createUnixScript();
            }
            createXMLFile();
        };
    }

    /**
     * 创建Windows批量处理文件以及环境变量配置
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private void createWindowsScript() {
        try {
            // 脚本内容
            String scriptContent = """
                    @echo off
                    setlocal
                    REM 获取当前批处理文件所在的目录
                    set SCRIPT_DIR=%~dp0
                    REM 执行jact.exe
                    "%SCRIPT_DIR%jact.exe" %*
                    endlocal""";
            logSuccess("Initializes the script content.");
            // 获取用户的主目录路径，将脚本配置在用户主目录下的.jact目录下
            String scriptPath = FileConstant.USER_HOME + "\\.jact\\jact.bat";

            // 在Windows脚本路径设置时，创建父目录（如果不存在）
            File scriptFile = new File(scriptPath);
            boolean scriptFileDir = scriptFile.getParentFile().mkdirs();
            if (!scriptFileDir) {
                logSuccess("Script path already exists.");
            }
            // 写入脚本内容
            try (FileWriter writer = new FileWriter(scriptPath)) {
                writer.write(scriptContent);
            }
            logSuccess("Script writing success. Script file path: " + scriptPath);

            //环境变量
            String currentPath = System.getenv("PATH");
            // 将 PATH 分割成数组
            String[] paths = currentPath.split(FileConstant.PATH_SEPARATOR);
            // 去除重复路径
            Set<String> uniquePaths = new LinkedHashSet<>(Arrays.asList(paths));
            // 转换为以路径分隔符分隔的字符串
            String newUniquePathPath = String.join(FileConstant.PATH_SEPARATOR, uniquePaths);
            // 备份当前的PATH
            String backupPath = FileConstant.USER_HOME + "\\.jact\\path_backup.txt";
            try (FileWriter writer = new FileWriter(backupPath)) {
                writer.write(newUniquePathPath);
            }
            logSuccess("Environment variable backup succeeded. Backup file path: " + backupPath);

            // 将脚本路径追加到现有PATH中
            String scriptFilePath = scriptFile.getParent();
            String newPath = newUniquePathPath + FileConstant.PATH_SEPARATOR + scriptFilePath;
            // 使用ProcessBuilder更新环境变量
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "setx", "PATH", "\"" + newPath + "\"");
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logSuccess("Environment variable write successfully.");
                logSuccess("jact command installed. You may need to restart your terminal.");
            } else {
                logError("Failed to update environment variable. Exit code: " + exitCode, null);
            }
        } catch (IOException e) {
            logInfo.handleTask("Failed to update environment variable. Exit code: " + e);
        } catch (InterruptedException e) {
            logInfo.handleTask("Failed to update environment variable. Exit code: " + e);
        } finally {
            consoleLog.shutdown();
        }
    }

    /**
     * 创建Unix二进制文件启动脚本以及环境变量配置
     *
     * @throws IOException
     */
    private void createUnixScript() {
        try {
            //创建脚本目录
            Files.createDirectories(Paths.get(FileConstant.USER_HOME + "/.jact"));
            logSuccess("Initializes the unix script content.");

            //脚本内容
            String scriptContent = """
                    #!/bin/bash
                    # 获取当前脚本所在的目录
                    SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
                    # 执行jact
                    "$SCRIPT_DIR/jact.exe" "$@\"""";
            // 获取用户的主目录路径，将脚本配置在用户主目录下的.jact目录下
            Path scriptPath = Paths.get(FileConstant.USER_HOME + "/.jact/jact");
            //写入脚本
            Files.write(scriptPath, scriptContent.getBytes());
            logSuccess("Script writing success. Script file path: " + scriptPath);

            //执行脚本
            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(scriptPath, perms);
            logSuccess("Script execution succeeds.");

            // 环境变量
            String currentPath = System.getenv("PATH");
            // 将 PATH 分割成数组
            String[] paths = currentPath.split(FileConstant.PATH_SEPARATOR);
            // 去除重复路径
            Set<String> uniquePaths = new LinkedHashSet<>(Arrays.asList(paths));
            // 转换为以路径分隔符分隔的字符串
            String newUniquePathPath = String.join(FileConstant.PATH_SEPARATOR, uniquePaths);
            // 备份文件路径
            String backupPath = FileConstant.USER_HOME + "/.jact/path_backup.txt";
            // 备份当前的PATH
            Files.write(Paths.get(backupPath), newUniquePathPath.getBytes());
            logSuccess("Environment variable backup succeeded. Backup file path: " + backupPath);

            // 将新路径追加到现有PATH中
            String newPath = new File(String.valueOf(scriptPath)).getParent();
            String exportCommand = "export PATH=\"$PATH:" + newPath + "\"";
            Files.write(Paths.get(FileConstant.USER_HOME + "/.jact/jact.sh"), exportCommand.getBytes());
            logSuccess("jact command installed. You may need to restart your terminal.");
        } catch (IOException e) {
            logError("Failed to create Unix script", e);
        }
    }

    /**
     * 创建Jact CLI XML配置文件
     *
     * @throws IOException
     */
    private void createXMLFile() {
        File flagFile = new File(FileConstant.JACT_XML);
        try {
            if (flagFile.getParentFile().mkdirs() || flagFile.createNewFile()) {
                logSuccess("XML configuration file created.");
            }
        } catch (IOException e) {
            logError("Description Failed to create the XML configuration file.", e);
        }
    }

    /**
     * 同时处理日志和控制台错误信息
     *
     * @param message 提示信息
     * @param e       异常信息
     */
    private void logError(String message, Exception e) {
        if (e != null) {
            consoleLog.handleTask(EmojiConstant.ERROR + " " + message + ": " + e.getMessage());
            logInfo.handleTask(EmojiConstant.ERROR + " " + message + ": " + e.getMessage());
        } else {
            consoleLog.handleTask(EmojiConstant.ERROR + " " + message);
            logInfo.handleTask(EmojiConstant.ERROR + " " + message);
        }
    }

    /**
     * 同时处理日志和控制台成功信息
     *
     * @param message 提示信息
     */
    private void logSuccess(String message) {
        consoleLog.handleTask(EmojiConstant.SUCCESSFUL + " " + message);
        logInfo.handleTask(EmojiConstant.SUCCESSFUL + " " + message);
    }
}
