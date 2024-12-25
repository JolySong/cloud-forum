package com.forum.controller;


import com.forum.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BannerController {


    @GetMapping("/banner/list")
    public R getBannerList() {
        return R.ok();
    }
}
