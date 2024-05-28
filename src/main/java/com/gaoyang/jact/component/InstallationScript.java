package com.gaoyang.jact.component;

import com.gaoyang.jact.Utils.Emoji;
import com.gaoyang.jact.Utils.GlobalConstant;
import com.gaoyang.jact.Utils.VirtualThreadPool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

/**
 * 安装脚本
 * <p>
 * 当运行程序时，自动将 jact 启动命令注册到系统环境变量
 * <p>
 * 验证版本信息，根据版本信息判断是否需要进行更新
 */
@Configuration
public class InstallationScript {

    @Bean
    public CommandLineRunner installer() {
        return args -> {
            try {
                createScript();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 根据操作系统进行对应的脚本创建以及执行
     *
     * @throws IOException
     */
    private void createScript() throws IOException, InterruptedException {
        if (GlobalConstant.OS_NAME.contains("win")) {
            createWindowsScript();
        } else {
            createUnixScript();
        }
        createXMLFile();
    }

    /**
     * 创建Jact CLI XML配置文件
     *
     * @throws IOException
     */
    private void createXMLFile() throws IOException {
        File flagFile = new File(GlobalConstant.JACT_XML);
        flagFile.getParentFile().mkdirs();
        flagFile.createNewFile();
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
            VirtualThreadPool.asyncOutput(Emoji.SUCCESSFUL + " Initializes the script content.");

            // 获取用户的主目录路径，将脚本配置在用户主目录下的.jact目录下
            String scriptPath = GlobalConstant.USER_HOME + "\\.jact\\jact.bat";

            // 在Windows脚本路径设置时，创建父目录（如果不存在）
            File scriptFile = new File(scriptPath);
            boolean scriptFileDir = scriptFile.getParentFile().mkdirs();
            if (!scriptFileDir) {
                VirtualThreadPool.asyncOutput(Emoji.WARN + " Script path already exists.");
            }
            // 写入脚本内容
            try (FileWriter writer = new FileWriter(scriptPath)) {
                writer.write(scriptContent);
            }
            VirtualThreadPool.asyncOutput(Emoji.SUCCESSFUL + " Script writing success. Script file path: " + scriptPath);

            // 备份当前的PATH
            String currentPath = System.getenv("PATH");
            String backupPath = GlobalConstant.USER_HOME + "\\.jact\\path_backup.txt";
            try (FileWriter writer = new FileWriter(backupPath)) {
                writer.write(currentPath);
            }
            VirtualThreadPool.asyncOutput(Emoji.SUCCESSFUL + " Environment variable backup succeeded. Backup file path: " + backupPath);

            // 将脚本路径追加到现有PATH中
            String scriptFilePath = scriptFile.getParent();
            String newPath = currentPath + GlobalConstant.PATH_SEPARATOR + scriptFilePath;

            // 使用ProcessBuilder更新环境变量
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "setx", "PATH", "\"" + newPath + "\"");
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                VirtualThreadPool.asyncOutput(Emoji.SUCCESSFUL + " Environment variable write successfully.");
                VirtualThreadPool.asyncOutput(Emoji.SUCCESSFUL + " jact command installed. You may need to restart your terminal.");
            } else {
                VirtualThreadPool.asyncOutput(Emoji.ERROR + " Failed to update environment variable. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            VirtualThreadPool.shutdownExecutor();
        }
    }

    private void createUnixScript() throws IOException {
        String scriptPath = GlobalConstant.USER_HOME + "/.jact/jact";
        Files.createDirectories(Paths.get(GlobalConstant.USER_HOME + "/.jact"));
        String scriptContent = """
                #!/bin/bash
                # 获取当前脚本所在的目录
                SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
                # 执行jact
                "$SCRIPT_DIR/jact.exe" "$@\"""";
        Files.write(Paths.get(scriptPath), scriptContent.getBytes());
        //执行脚本
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        Files.setPosixFilePermissions(Paths.get(scriptPath), perms);
        // 备份当前的PATH
        String currentPath = System.getenv("PATH");
        String backupPath = GlobalConstant.USER_HOME + "/.jact/path_backup.txt";
        Files.write(Paths.get(backupPath), currentPath.getBytes());
        // 将新路径追加到现有PATH中
        String newPath = new File(scriptPath).getParent();
        String exportCommand = "export PATH=\"$PATH:" + newPath + "\"";
        Files.write(Paths.get(GlobalConstant.USER_HOME + "/.jact/setpath.sh"), exportCommand.getBytes());
        System.out.println("jact command installed. You may need to restart your terminal.");
    }
}
