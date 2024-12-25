package com.forum.controller;


import com.forum.common.R;
import com.forum.dto.UserLoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @PostMapping("/auth/login")
    public R login(@RequestBody UserLoginDTO dto) {
        // 生成token
        Map<String, Object> map = new HashMap<>();
        map.put("token", "misan");
        return R.ok(map);
    }

    @PostMapping("/auth/logout")
    public R logout() {
        return R.ok();
    }
}
