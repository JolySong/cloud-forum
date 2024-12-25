package com.iomc.forum.analysis.api.fallback;


import com.iomc.common.core.Res;
import com.iomc.forum.analysis.api.feign.AnalysisServiceFeign;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 分析服务调用失败的降级处理
 */
@Component
public class AnalysisServiceFeignFallbackFactory implements FallbackFactory<AnalysisServiceFeign> {

    private static final Logger log =
            LoggerFactory.getLogger(AnalysisServiceFeignFallbackFactory.class);

    @Override
    public AnalysisServiceFeign create(Throwable throwable) {

        return new AnalysisServiceFeign() {

            /**
             * 获取用户统计信息
             *
             * @param currentUserId
             * @param userId
             * @return
             */
            @Override
            public Res<UserStatVO> getUserStat(Long currentUserId, Long userId) {
                log.error("分析服务->getUserStat()->调用失败:{}", throwable.getMessage());
                return null;
            }
        };
    }
}
