package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.User;
import com.forum.vo.UserStatVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    
    @Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname}")
    int countByNickname(@Param("nickname") String nickname);

    /**
     * 获取用户统计信息
     */
    UserStatVO selectUserStats(@Param("userId") Long userId);


} 