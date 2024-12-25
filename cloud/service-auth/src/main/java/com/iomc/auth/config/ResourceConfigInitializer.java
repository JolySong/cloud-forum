package com.iomc.auth.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iomc.auth.entity.SysConfig;
import com.iomc.auth.entity.SysResource;
import com.iomc.auth.service.ISysConfigService;
import com.iomc.auth.service.ISysResourceService;
import com.iomc.common.core.utils.ListUtils;
import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.common.security.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资源配置
 */
@Configuration
@Slf4j
public class ResourceConfigInitializer implements CommandLineRunner {

    @Resource
    private ISysResourceService resourceService;

    @Resource
    private ISysConfigService configService;

    /**
     * 初始化公共资源
     *
     * todo 数据的更新
     */
    public void initPublicResources() {

        List<SysResource> list = resourceService.list(
                new LambdaQueryWrapper<SysResource>()
                        .eq(SysResource::getDeleted, 0)
                        .eq(SysResource::getIsPublic, 1));

        if (ListUtils.isNotEmpty(list)) {
            Set<String> collect = list.stream()
                    .map(SysResource::getApiUrl)
                    .collect(Collectors.toSet());

            RedisUtil.set(Const.PUBLIC_RESOURCES_KEY, collect);
        }
    }

    /**
     * 初始化系统配置
     *
     * todo 数据的更新
     */
    public void initSystemConfig() {
        List<SysConfig> list = configService.list(
                new LambdaQueryWrapper<SysConfig>()
                        .eq(SysConfig::getDeleted, 0));
        if (ListUtils.isNotEmpty(list)) {
            list.forEach(item -> RedisUtil.set(Const.SYSTEM_CONFIG_KEY + item.getConfigKey(), item.getConfigValue()));
        }
    }

    /**
     * 项目启动后，执行该方法
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // 在这里添加你的初始化代码
        log.info("初始化资源配置...");
        initPublicResources();

        log.info("初始化系统配置...");
        initSystemConfig();
    }
}
