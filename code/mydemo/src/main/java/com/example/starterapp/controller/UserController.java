package com.example.starterapp.controller;

import com.example.starterapp.POJO.User;
import com.example.starterapp.commons.ServerResponse;
import com.example.starterapp.enums.ResponseCode;
import com.example.starterapp.exception.CustomException;
import com.example.starterapp.service.UserService;
import com.example.starterapp.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ServerResponse<LoginVo> login(@RequestBody User user){

        log.info("【用户登录】请求参数:[{}]", user);

        if (StringUtils.isAnyBlank(user.getUsername(), user.getPassword())){
            log.error("【用户登录】请求参数为空");
            return ServerResponse.createByErrorCodeMessage(ResponseCode.PARAM_NULL_ERROR.getCode(),
                                                            ResponseCode.PARAM_NULL_ERROR.getMsg());
        }

        User u = userService.checkUserIsExistByName(user.getUsername());
        if (u == null){
            log.error("【用户登录】用户名:[{}]不存在", user.getUsername());
            return ServerResponse.createByErrorCodeMessage(ResponseCode.USER_NOT_EXIST.getCode(),
                                                            ResponseCode.USER_NOT_EXIST.getMsg());
        }
        boolean pwdIsRight = userService.checkPwdIsRight(user);
        if(!pwdIsRight){
            log.error("【用户登录】用户:[{}]密码错误", user.getUsername());
            return ServerResponse.createByErrorCodeMessage(ResponseCode.LOGIN_PWD_ERROR.getCode(),
                                                            ResponseCode.LOGIN_PWD_ERROR.getMsg());
        }

        u.setPassword(null);
        LoginVo loginVo = null;
        try{
            loginVo = userService.generateToken(u);
        }catch (Exception e){
            throw new CustomException(ResponseCode.TOKEN_ENCRYPT_ERROR);
        }
        return ServerResponse.createBySuccess(loginVo);
    }
}
