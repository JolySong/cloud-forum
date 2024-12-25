package com.iomc.forum.file.controller;


import com.iomc.common.core.Res;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.forum.file.api.dto.FileDTO;
import com.iomc.forum.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param fileDTO
     *
     * @return path
     */
    @GetMapping("/file/upload")
    public Res<String> upload(@RequestBody FileDTO fileDTO) {
        if (fileDTO == null || fileDTO.getBytes() == null || StrUtils.isBlank(fileDTO.getFileName())) {
            return Res.fail("文件不能为空");
        }

        return Res.success(fileService.upload(fileDTO.getFileName(), fileDTO.getBytes()));
    }

    /**
     * 获取文件url
     *
     * @param fileName
     */
    @GetMapping("/file/getUrl")
    public Res<String> getFileUrl(@RequestParam("fileName") String fileName) {
        return Res.success(fileService.getFileUrl(fileName));
    }
}
