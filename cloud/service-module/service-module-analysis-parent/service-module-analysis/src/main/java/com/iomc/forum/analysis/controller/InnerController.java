package com.iomc.forum.analysis.controller;


import com.iomc.common.core.Res;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import com.iomc.forum.analysis.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InnerController {

    @Autowired
    private AnalysisService analysisService;

    /**
     * 获取用户统计信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/inner/analysis/user")
    public Res<UserStatVO> getUserStat(@RequestParam(value = "currentUserId", required = false) Long currentUserId,
                                       @RequestParam(value = "userId") Long userId) {
        return Res.success(analysisService.getUserStat(currentUserId, userId));
    }
}
