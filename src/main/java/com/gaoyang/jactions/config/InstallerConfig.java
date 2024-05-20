package com.gaoyang.jactions.config;

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
            String osName = System.getProperty("os.name").toLowerCase();
            String jarPath = InstallerConfig.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

            if (args.length > 0 && args[0].equals("install")) {
                createScript(osName, jarPath);
            }
        };
    }

    private void createScript(String osName, String jarPath) throws IOException {
        if (osName.contains("win")) {
            createWindowsScript(jarPath);
        } else {
            createUnixScript(jarPath);
        }
    }

    private void createWindowsScript(String jarPath) throws IOException {
        String scriptContent = "@echo off\n"
                + "java -jar \"" + jarPath + "\" %*\n";
        String scriptPath = "C:\\path\\to\\your\\script\\jact.bat"; // Change this path accordingly

        try (FileWriter writer = new FileWriter(scriptPath)) {
            writer.write(scriptContent);
        }

        // Add directory containing script to PATH (this requires administrator privileges)
        String command = "setx PATH \"%PATH%;" + new File(scriptPath).getParent() + "\"";
        Runtime.getRuntime().exec(command);

        System.out.println("jact command installed. You may need to restart your terminal.");
    }

    private void createUnixScript(String jarPath) throws IOException {
        String scriptContent = "#!/bin/bash\n"
                + "java -jar \"" + jarPath + "\" \"$@\"\n";
        String scriptPath = "/usr/local/bin/jact"; // Change this path accordingly

        Files.write(Paths.get(scriptPath), scriptContent.getBytes());

        // Make the script executable
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        Files.setPosixFilePermissions(Paths.get(scriptPath), perms);

        System.out.println("jact command installed. You may need to restart your terminal.");
    }
}
