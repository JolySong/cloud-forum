package com.forum.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 批量
 *
 * @author huyz
 * @since 2022/3/5
 */
public interface SpiceBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     * {@link com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn}
     *
     * @param list 要插入的数据
     * @return 成功插入的数据条数
     */
    int insertBatchSomeColumn(List<T> list);
}
