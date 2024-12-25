package com.iomc.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.auth.dao.SysConfigDao;
import com.iomc.auth.entity.SysConfig;
import com.iomc.auth.service.ISysConfigService;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements ISysConfigService {
}
