package com.iomc.forum.file.api.feign;


import com.iomc.common.core.Res;
import com.iomc.forum.file.api.dto.FileDTO;
import com.iomc.forum.file.api.fallback.FileServiceFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务接口
 */
@FeignClient(contextId = "fileServiceFeign", value = "service-module-file",
        fallbackFactory = FileServiceFeignFallbackFactory.class)
public interface FileServiceFeign {


    /**
     * 文件上传
     *
     * @param fileDTO
     * @return
     */
    @GetMapping("/file/upload")
    Res<String> upload(@RequestBody FileDTO fileDTO);

    /**
     * 文件下载
     *
     * @param fileName
     * @return
     */
    @GetMapping("/file/getUrl")
    Res<String> getFileUrl(@RequestParam("fileName") String fileName);

}
