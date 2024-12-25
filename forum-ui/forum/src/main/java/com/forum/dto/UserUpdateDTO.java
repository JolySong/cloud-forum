package com.forum.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDTO {

    @Size(min = 2, max = 20, message = "昵称长度必须在2-20个字符之间")
    private String nickname;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String avatar;

    @Size(max = 200, message = "个人简介不能超过200个字符")
    private String bio;

    private String password;

    private String newPassword;
} 