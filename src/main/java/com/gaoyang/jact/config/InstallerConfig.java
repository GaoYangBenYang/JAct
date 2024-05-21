package com.gaoyang.jact.config;

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

@Configuration
public class InstallerConfig {

    @Bean
    public CommandLineRunner installer() {
        return args -> {
            if (args.length > 0 && args[0].equals("install")) {
                try {
                    String jarPath = getJarPath();
                    String osName = System.getProperty("os.name").toLowerCase();
                    createScript(osName, jarPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 获取JAR文件路径
     *
     * @return
     */
    private String getJarPath() {
        return new File(System.getProperty("java.class.path")).getAbsolutePath();
    }

    private void createScript(String osName, String jarPath) throws IOException {
        if (osName.contains("win")) {
            createWindowsScript(jarPath);
        } else {
            createUnixScript(jarPath);
        }
    }

    private void createWindowsScript(String jarPath) throws IOException {
        //脚本类容
        String scriptContent = "@echo off\n"
                + "java -jar \"" + jarPath + "\" %*\n";
        //获取用户的主目录路径，将脚本路径设置为用户主目录下的.jact目录。
        String userHome = System.getProperty("user.home");
        String scriptPath = userHome + "\\.jact\\jact.bat";
        //在Windows脚本路径设置时，创建父目录（如果不存在）。
        File scriptFile = new File(scriptPath);
        scriptFile.getParentFile().mkdirs();
        //写入脚本类容
        try (FileWriter writer = new FileWriter(scriptPath)) {
            writer.write(scriptContent);
        }
        // 备份当前的PATH
        String currentPath = System.getenv("PATH");
        String backupPath = userHome + "\\.jact\\path_backup.txt";
        try (FileWriter writer = new FileWriter(backupPath)) {
            writer.write(currentPath);
        }
        // 将新路径追加到现有PATH中
        String newPath = scriptFile.getParent();
        String command = "cmd.exe /c setx PATH \"" + currentPath + ";" + newPath + "\"";
        Runtime.getRuntime().exec(command);
        System.out.println("jact command installed. You may need to restart your terminal.");
    }

    private void createUnixScript(String jarPath) throws IOException {
        String userHome = System.getProperty("user.home");
        String scriptPath = userHome + "/.jact/jact";
        Files.createDirectories(Paths.get(userHome + "/.jact"));
        String scriptContent = "#!/bin/bash\n"
                + "java -jar \"" + jarPath + "\" \"$@\"\n";
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
        String backupPath = userHome + "/.jact/path_backup.txt";
        Files.write(Paths.get(backupPath), currentPath.getBytes());
        // 将新路径追加到现有PATH中
        String newPath = new File(scriptPath).getParent();
        String exportCommand = "export PATH=\"$PATH:" + newPath + "\"";
        Files.write(Paths.get(userHome + "/.jact/setpath.sh"), exportCommand.getBytes());
        System.out.println("jact command installed. You may need to restart your terminal.");
    }
}
