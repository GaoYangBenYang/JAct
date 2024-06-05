package com.gaoyang.jact.config;

import com.gaoyang.jact.asynchronous.logger.ConsoleLog;
import com.gaoyang.jact.asynchronous.logger.LogInfo;
import com.gaoyang.jact.utils.constant.EmojiConstant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    /**
     * 操作系统
     */
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    /**
     * 系统环境变量分隔符
     */
    private static final String PATH_SEPARATOR = File.pathSeparator;
    /**
     * 系统路径分隔符
     */
    private static final String SEPARATOR = File.separator;
    /**
     * 用户目录
     */
    private static final String USER_HOME = System.getProperty("user.home");
    /**
     * 配置文件
     */
    private static final String JACT_JSON = USER_HOME + SEPARATOR + ".jact" + SEPARATOR + "jact.json";
    /**
     * 日志文件目录
     */
    private static final String JACT_LOG_DIR = USER_HOME + SEPARATOR + ".jact" + SEPARATOR + "logs";
    /**
     * all级别日志文件目录
     */
    private static final String JACT_LOG_ALL_DIR = JACT_LOG_DIR + SEPARATOR + "all";
    /**
     * debug级别日志文件目录
     */
    private static final String JACT_LOG_DEBUG_DIR = JACT_LOG_DIR + SEPARATOR + "debug";
    /**
     * info级别日志文件目录
     */
    private static final String JACT_LOG_INFO_DIR = JACT_LOG_DIR + SEPARATOR + "info";
    /**
     * warn级别日志文件目录
     */
    private static final String JACT_LOG_WARN_DIR = JACT_LOG_DIR + SEPARATOR + "warn";
    /**
     * error级别日志文件目录
     */
    private static final String JACT_LOG_ERROR_DIR = JACT_LOG_DIR + SEPARATOR + "error";
    /**
     * 环境变量备份文件
     */
    private static final String ENV_BACKUP = USER_HOME + SEPARATOR + ".jact" + SEPARATOR + "path_backup.txt";
    /**
     * JAVA程序类路径
     */
    private static final String JAVA_CLASS_PATH = System.getProperty("java.class.path").split(PATH_SEPARATOR)[0];
    /**
     * jar包类路径
     */
    private static final String JAR_PATH = new File(JAVA_CLASS_PATH).getAbsolutePath();
    /**
     * Windows脚本内容
     */
    private static final String WIN_SCRIPT_CONTENT = """
            @echo off
            setlocal
            REM 获取当前批处理文件所在的目录
            set SCRIPT_DIR=%~dp0
            REM 执行jact.exe
            "%SCRIPT_DIR%jact.exe" %*
            endlocal
            """;
    /**
     * Windows脚本路径
     */
    private static final String WIN_SCRIPT_PATH = USER_HOME + SEPARATOR + ".jact" + SEPARATOR + "jact.bat";
    /**
     * Unix脚本内容
     */
    private static final String UNIX_SCRIPT_CONTENT = """
            #!/bin/bash
            # 获取当前脚本所在的目录
            SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
            # 执行jact
            "$SCRIPT_DIR/jact.exe" "$@"
            """;
    /**
     * Unix脚本路径
     */
    private static final String UNIX_SCRIPT_PATH = USER_HOME + SEPARATOR + ".jact" + SEPARATOR + "jact";

    // 日志实例
    private final ConsoleLog consoleLog = ConsoleLog.getInstance();
    private final LogInfo logInfo = LogInfo.getInstance();

    /**
     * 根据操作系统进行对应的脚本创建以及执行
     *
     * @return CommandLineRunner实例
     */
    @Bean
    public CommandLineRunner createScript() {
        return args -> {
            if (OS_NAME.contains("win")) {
                createWindowsScript();
            } else {
                createUnixScript();
            }
            createJSONFile();
            createLogDirectory();
        };
    }

    /**
     * 创建Windows批处理文件以及环境变量配置
     */
    private void createWindowsScript() {
        try {
            // 创建脚本文件并写入内容
            createScriptFile(WIN_SCRIPT_PATH, WIN_SCRIPT_CONTENT);
            // 备份并设置环境变量
            backupAndSetEnvironmentVariable(WIN_SCRIPT_PATH);
        } catch (IOException e) {
            logError("Failed to create Windows script", e);
        } catch (InterruptedException e) {
            logError("Failed to create Windows script", e);
        } finally {
            consoleLog.shutdown();
        }
    }

    /**
     * 创建Unix二进制文件启动脚本以及环境变量配置
     */
    private void createUnixScript() {
        try {
            // 创建脚本文件并写入内容
            createScriptFile(UNIX_SCRIPT_PATH, UNIX_SCRIPT_CONTENT);
            // 设置脚本的执行权限
            setScriptPermissions();
            // 备份并设置环境变量
            backupAndSetEnvironmentVariable(UNIX_SCRIPT_PATH);
        } catch (IOException e) {
            logError("Failed to create Unix script", e);
        } catch (InterruptedException e) {
            logError("Failed to create Unix script", e);
        } finally {
            consoleLog.shutdown();
        }
    }

    /**
     * 创建脚本文件并写入内容
     *
     * @param scriptPath 脚本文件路径
     * @param content    脚本内容
     * @throws IOException 如果写入文件时发生错误
     */
    private void createScriptFile(String scriptPath, String content) throws IOException {
        // 创建脚本文件的父目录（如果不存在）
        File scriptFile = new File(scriptPath);
        boolean scriptFileDir = scriptFile.getParentFile().mkdirs();
        if (!scriptFileDir) {
            logSuccess("Script path already exists.");
        }

        // 写入脚本内容
        try (FileWriter writer = new FileWriter(scriptPath)) {
            writer.write(content);
        }
        logSuccess("Script writing success. Script file path: " + scriptPath);
    }

    /**
     * 备份并设置环境变量
     *
     * @param scriptPath 脚本文件路径
     * @throws IOException          如果写入文件时发生错误
     * @throws InterruptedException 如果设置环境变量时发生错误
     */
    private void backupAndSetEnvironmentVariable(String scriptPath) throws IOException, InterruptedException {
        // 获取当前的PATH环境变量
        String currentPath = System.getenv("PATH");

        // 将 PATH 分割成数组并去除重复路径
        String[] paths = currentPath.split(PATH_SEPARATOR);
        Set<String> uniquePaths = new LinkedHashSet<>(Arrays.asList(paths));
        String newUniquePathPath = String.join(PATH_SEPARATOR, uniquePaths);

        // 备份当前的PATH
        try (FileWriter writer = new FileWriter(ENV_BACKUP)) {
            writer.write(newUniquePathPath);
        }
        logSuccess("Environment variable backup succeeded. Backup file path: " + ENV_BACKUP);

        // 将脚本路径追加到现有PATH中
        String newPath = OS_NAME.contains("win") ? scriptPath : new File(scriptPath).getParent();
        boolean contains = uniquePaths.contains(newPath);
        String finalPath = contains ? newUniquePathPath : newUniquePathPath + PATH_SEPARATOR + newPath;

        // 使用ProcessBuilder更新环境变量
        if (OS_NAME.contains("win")) {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "setx", "PATH", "\"" + finalPath + "\"");
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logSuccess("Environment variable write successfully.");
                logSuccess("jact command installed. You may need to restart your terminal.");
            } else {
                logError("Failed to update environment variable. Exit code: " + exitCode, null);
            }
        } else {
            String exportCommand = "export PATH=\"$PATH:" + newPath + "\"";
            Files.write(Paths.get(USER_HOME + "/.jact/jact.sh"), exportCommand.getBytes());
            logSuccess("jact command installed. You may need to restart your terminal.");
        }
    }

    /**
     * 设置Unix脚本的执行权限
     *
     * @throws IOException 如果设置权限时发生错误
     */
    private void setScriptPermissions() throws IOException {
        Path path = Paths.get(UNIX_SCRIPT_PATH);
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        Files.setPosixFilePermissions(path, perms);
        logSuccess("Script execution succeeds.");
    }

    /**
     * 创建Jact CLI JSON配置文件
     */
    private void createJSONFile() {
        File flagFile = new File(JACT_JSON);
        try {
            // 创建父目录
            File parentDir = flagFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (parentDir.mkdirs()) {
                    logSuccess("Parent directories created.");
                } else {
                    logError("Failed to create parent directories.", null);
                    return;
                }
            }

            // 创建新文件并写入默认配置
            if (flagFile.createNewFile()) {
                logSuccess("JSON configuration file created.");
                writeDefaultConfig(flagFile);
            } else {
                logError("JSON configuration file already exists or failed to create.", null);
            }
        } catch (IOException e) {
            logError("Failed to create the JSON configuration file.", e);
        }
    }

    /**
     * 写入默认配置
     *
     * @param file JSON配置文件
     */
    private void writeDefaultConfig(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            JsonObject config = new JsonObject();
            config.addProperty("version", "1.0");
            // 可以根据需要添加更多默认配置
            Gson gson = new Gson();
            gson.toJson(config, writer);
            logSuccess("Default configuration written to JSON file.");
        } catch (IOException e) {
            logError("Failed to write default configuration to JSON file.", e);
        }
    }

    /**
     * 创建日志目录
     */
    private void createLogDirectory() {
        // 创建基础日志目录
        createDirectory(JACT_LOG_DIR);
        // 创建各级别日志目录
        createDirectory(JACT_LOG_ALL_DIR);
        createDirectory(JACT_LOG_DEBUG_DIR);
        createDirectory(JACT_LOG_INFO_DIR);
        createDirectory(JACT_LOG_WARN_DIR);
        createDirectory(JACT_LOG_ERROR_DIR);
    }

    /**
     * 创建单个目录
     *
     * @param dirPath 目录路径
     */
    private void createDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                logSuccess("Log directory created: " + dirPath);
            } else {
                logError("Failed to create log directory: " + dirPath, null);
            }
        } else {
            logSuccess("Log directory already exists: " + dirPath);
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
