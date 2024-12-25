package com.iomc.auth.controller;

import com.alibaba.fastjson2.JSONObject;
import com.iomc.auth.service.PermissionService;
import com.iomc.common.core.Res;
import com.iomc.common.core.constants.MessageConst;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.mq.RocketMQProducerService;
import com.iomc.common.mq.constant.MqConst;
import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.common.security.constant.Const;
import com.iomc.common.security.dto.LoginUserDTO;
import com.iomc.common.security.utils.JWTUtils;
import com.iomc.common.security.vo.LoginUserVO;
import com.iomc.forum.message.api.dto.EmailSendDTO;
import com.iomc.forum.user.api.feign.UserServiceFeign;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceFeign userServiceFeign;
    private final PermissionService permissionService;
    private final RocketMQProducerService rocketMQProducerService;

    public AuthController(UserServiceFeign userServiceFeign,
                          PermissionService permissionService,
                          RocketMQProducerService rocketMQProducerService) {
        this.userServiceFeign = userServiceFeign;
        this.permissionService = permissionService;
        this.rocketMQProducerService = rocketMQProducerService;
    }


    /**
     * 登录
     *
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public Res login(@RequestBody LoginUserDTO dto) {

        if (null == dto || StrUtils.isBlank(dto.getUsername())) {
            return Res.fail("用户名不能为空");
        }

        Res<LoginUserVO> res = userServiceFeign.getLoginUserByUsername(dto.getUsername());
        if (null == res || !res.getStatus() || null == res.getData()) {
            return Res.fail("用户名或密码错误");
        }

        LoginUserVO loginUser = res.getData();
        // 获取系统配置
        Object o = RedisUtil.get(Const.SYSTEM_CONFIG_KEY + Const.SINGLE_LOGIN);
        if (o != null) {
            if (Boolean.parseBoolean(o.toString())) {
                // 移除其他登陆
                JWTUtils.kickUser(loginUser.getId() + "");
            }
        }
        // 记录设备
        loginUser.setDevice(dto.getDevice());
        // 执行登录
        Map<String, String> login = JWTUtils.login(loginUser);

        // 缓存资源权限
        permissionService.loadUserPermissions(loginUser.getId());

        return Res.success(login);
    }


    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public Res logout(HttpServletRequest request){
        JWTUtils.logout(request);
        return Res.success();
    }

    /**
     * 获取登陆配置
     */
    @GetMapping("/config")
    public Res config() {

        Map<String, Object> config = new HashMap<>();
        Object o = RedisUtil.get(Const.SYSTEM_CONFIG_KEY + Const.SINGLE_LOGIN);
        config.put("singleDeviceLogin", o);

        return Res.success(config);
    }

    /**
     * 检查用户登陆状态
     *
     * @param username
     */
    @GetMapping("/checkLoginStatus")
    public Res userInfo(@RequestParam(value = "username") String username) {

        Res<LoginUserVO> res = userServiceFeign.getLoginUserByUsername(username);
        if (null == res || !res.getStatus() || null == res.getData()) {
            return Res.fail("用户名或密码错误");
        }
        LoginUserVO loginUser = res.getData();
        Map<String, Object> config = new HashMap<>();
        if (RedisUtil.hasKey(JWTUtils.getOnlineUsersKey(loginUser.getId() + ""))) {
            config.put("isLoggedIn", true);
        }

        return Res.success(config);
    }

    /**
     * 检查用户名和邮箱是否一致
     *
     * @param userDTO
     */
    @PostMapping("/checkUsernameAndEmail")
    public Res checkUsernameAndEmail(@RequestBody LoginUserDTO userDTO) {

        if (null == userDTO || StrUtils.isBlank(userDTO.getEmail())
            || StrUtils.isBlank(userDTO.getUsername())) {
            return Res.fail("用户名或邮箱不能为空");
        }

        String username = userDTO.getUsername();
        String email = userDTO.getEmail();

        Res<LoginUserVO> res = userServiceFeign.getLoginUserByUsername(username);
        if (null == res || !res.getStatus() || null == res.getData()
                || ! res.getData().getEmail().equals(email)) {
            return Res.fail("用户名或邮箱不正确");
        }

        return Res.success();
   }

    /**
     * 发送验证码
     *
     * @param emailDto
     */
    @PostMapping("/sendCode")
    public Res sendCode(@RequestBody LoginUserDTO emailDto) {

        if (null == emailDto || StrUtils.isBlank(emailDto.getEmail())
                || StrUtils.isBlank(emailDto.getUsername())) {
            return Res.fail("用户名或邮箱不能为空");
        }
        String key = MessageConst.MESSAGE_TYPE_SECURITY_KEY + emailDto.getEmail();

        Object o = RedisUtil.get(key);
        if (null != o) {
            return Res.fail("验证码已发送，请检查邮箱");
        }


        String username = emailDto.getUsername();
        String email = emailDto.getEmail();
        Res<LoginUserVO> res = userServiceFeign.getLoginUserByUsername(username);
        if (null == res || !res.getStatus() || null == res.getData()
                || ! res.getData().getEmail().equals(email)) {
            return Res.fail("用户名或邮箱不正确");
        }

        EmailSendDTO dto = new EmailSendDTO();
        dto.setRecipientEmail(email);
        dto.setSubject("验证码");
        SecureRandom secureRandom = new SecureRandom();
        int code = 100000 + secureRandom.nextInt(900000);
        String content = "【验证码】您的验证码为：" + code + " 。 验证码五分钟内有效，逾期作废。\n\n\n" +
                "------------------------------\n\n\n";
        dto.setBody(content);
        dto.setEmailType(MessageConst.MESSAGE_TYPE_SECURITY);
        RedisUtil.set(key, code, 5 * 60);

        String jsonString = JSONObject.toJSONString(dto);

        rocketMQProducerService.send(MqConst.SERVICE_AUTH_MESSAGE_TOPIC, jsonString);
        return Res.success();
    }

    @PostMapping("/resetPassword")
    public Res resetPassword(@RequestBody LoginUserDTO dto) {

        if (null == dto || StrUtils.isBlank(dto.getEmail())
                || StrUtils.isBlank(dto.getCode()) || StrUtils.isBlank(dto.getPassword())) {
            return Res.fail("参数错误");
        }

        Object o = RedisUtil.get(MessageConst.MESSAGE_TYPE_SECURITY_KEY + dto.getEmail());
        if (null == o) {
            return Res.fail("验证码已过期");
        }
        if (!o.toString().equals(dto.getCode())) {
            return Res.fail("验证码不正确");
        }

        return userServiceFeign.resetPassword(dto);
    }
}
