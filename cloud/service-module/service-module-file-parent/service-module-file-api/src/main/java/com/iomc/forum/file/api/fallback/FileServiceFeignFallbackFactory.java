package com.iomc.forum.file.api.fallback;


import com.iomc.common.core.Res;
import com.iomc.forum.file.api.dto.FileDTO;
import com.iomc.forum.file.api.feign.FileServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务调用失败的降级处理
 */
@Component
public class FileServiceFeignFallbackFactory implements FallbackFactory<FileServiceFeign> {

    private static final Logger log =
            LoggerFactory.getLogger(FileServiceFeignFallbackFactory.class);

    @Override
    public FileServiceFeign create(Throwable throwable) {

        return new FileServiceFeign() {


            /**
             * 文件上传
             *
             * @param fileDTO
             * @return
             */
            @GetMapping("/upload")
            public Res<String> upload(@RequestBody FileDTO fileDTO) {
                log.error("文件上传失败 {}", throwable.getStackTrace());
                return null;
            }

            /**
             * 获取文件地址
             *
             * @param fileName
             * @return
             */
            @GetMapping("/file/getUrl")
            public Res<String> getFileUrl(@RequestParam("fileName") String fileName) {
                log.error("获取文件地址失败 {}", throwable.getStackTrace());
                return null;
            }
        };
    }
}
