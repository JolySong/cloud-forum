package com.iomc.forum.file.service;

public interface FileService {

    /**
     * 上传文件
     *
     * @param fileName
     * @param bytes
     * @return
     */
    String upload(String fileName, byte[] bytes);

    /**
     * 下载文件
     *
     * @param fileName
     * @return
     */
    byte[] download(String fileName);

    /**
     * 删除文件
     *
     * @param fileName
     */
    void delete(String fileName);

    /**
     * 获取文件访问地址
     *
     * @param fileName
     * @return
     */
    String getFileUrl(String fileName);
}
