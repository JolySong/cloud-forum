//package com.forum.common.security;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 登录用户信息
// *
// * @author forum
// * @since 2024-03-15
// */
//@Data
//public class LoginUser implements UserDetails {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 用户ID
//     */
//    private Long id;
//
//    /**
//     * 用户名
//     */
//    private String username;
//
//    /**
//     * 密码
//     */
//    private String password;
//
//    /**
//     * 用户状态（0:禁用 1:正常）
//     */
//    private Integer status;
//
//    /**
//     * 角色列表
//     */
//    private List<String> roles;
//
//    @JsonIgnore
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//                .collect(Collectors.toList());
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isEnabled() {
//        return status == 1;
//    }
//}