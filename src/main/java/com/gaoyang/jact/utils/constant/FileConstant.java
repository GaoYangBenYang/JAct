package com.gaoyang.jact.utils.constant;

import java.io.File;

/**
 * 文件路径常量
 */
public final class FileConstant {

    /**
     * 安装环境对应的操作系统
     */
    public static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    /**
     * 系统分隔符
     */
    public static final String PATH_SEPARATOR = File.pathSeparator;
    /**
     * 用户目录
     */
    public static final String USER_HOME = System.getProperty("user.home");
    /**
     * 配置文件
     */
    public static final String JACT_XML = "/.jact/jact.xml";
    /**
     * 日志文件
     */
    public static final String JACT_LOG = "/.jact/log";
    /**
     * JAVA程序类路径
     */
    public static final String JAVA_CLASS_PATH = System.getProperty("java.class.path").split(PATH_SEPARATOR)[0];
    /**
     * jar包类路径
     */
    public static final String JAR_PATH = new File(JAVA_CLASS_PATH).getAbsolutePath();

    private FileConstant() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
