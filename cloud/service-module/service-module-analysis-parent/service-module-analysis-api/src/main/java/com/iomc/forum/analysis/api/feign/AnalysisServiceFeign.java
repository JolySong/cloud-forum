package com.iomc.forum.analysis.api.feign;


import com.iomc.common.core.Res;
import com.iomc.common.security.interceptor.FeignInterceptor;
import com.iomc.forum.analysis.api.fallback.AnalysisServiceFeignFallbackFactory;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务接口
 */
@FeignClient(contextId = "analysisServiceFeign", value = "service-module-analysis",
        fallbackFactory = AnalysisServiceFeignFallbackFactory.class,
        configuration = FeignInterceptor.class)
public interface AnalysisServiceFeign {


    /**
     * 获取用户统计信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/inner/analysis/user")
    Res<UserStatVO> getUserStat(@RequestParam(value = "currentUserId", required = false) Long currentUserId,
                               @RequestParam(value = "userId") Long userId) ;
}
