package com.iomc.common.core.utils;

import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {


    /**
     * 检查并创建目录
     *
     * @param directoryPath
     */
    public static boolean checkAndCreateDirectory(String directoryPath) {

        Path path = Paths.get(directoryPath);
        if (Files.exists(path)) {
            return true;
        }

        try {
            // 创建目录
            Files.createDirectories(path);
            return true;
        } catch (Exception e) {
            throw new BizException(ExceptionEnum.FILE_ERROR);
        }
    }


    /**
     * 获取系统路径符号
     */
    public static String getSystemPathSeparator() {

        String separator = "/";

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            return "\\";
        }

        return separator;
    }

    /**
     * 写入文件
     */
    public static boolean writeFile(String filePath, byte[] bytes) {

        Path path = Paths.get(filePath);

        try {
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new BizException(ExceptionEnum.FILE_ERROR);
        }

        return true;
    }

    /**
     * 读取文件
     * @param filePath
     */
    public static byte[] readFile(String filePath) {

        Path path = Paths.get(filePath);
        try {
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new BizException(ExceptionEnum.FILE_ERROR);
        }
    }
}
