package com.iomc.forum.file.service.impl;

import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.exception.ExceptionEnum;
import com.iomc.common.core.utils.DateUtil;
import com.iomc.common.core.utils.FileUtil;
import com.iomc.forum.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.upload}")
    private String uploadPath;

    /**
     * 上传文件
     *
     * @param fileName
     * @param bytes
     * @return
     */
    @Override
    public String upload(String fileName, byte[] bytes) {

        if (bytes == null) {
            throw new BizException("文件不能为空");
        }

        // 增加日期做文件夹
        String path = uploadPath + DateUtil.getCurrentDate();
        boolean flag = FileUtil.checkAndCreateDirectory(path);
        if (!flag) {
            log.error("创建目录失败");
            throw new BizException(ExceptionEnum.FILE_ERROR);
        }

        String directoryPath = uploadPath + FileUtil.getSystemPathSeparator() + fileName;

        // 写入文件
        flag = FileUtil.writeFile(directoryPath, bytes);
        if (!flag) {
            log.error("写入文件失败");
            throw new BizException(ExceptionEnum.FILE_ERROR);
        }

        return directoryPath;
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @return
     */
    @Override
    public byte[] download(String fileName) {
        if (fileName == null) {
            throw new BizException("文件名称不能为空");
        }

        return FileUtil.readFile(fileName);
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    @Override
    public void delete(String fileName) {

    }

    /**
     * 获取文件访问地址
     *
     * @param fileName
     * @return
     */
    @Override
    public String getFileUrl(String fileName) {
        return "/files/" + fileName;
    }


}
