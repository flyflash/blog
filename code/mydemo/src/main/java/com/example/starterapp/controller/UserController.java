package com.example.starterapp.controller;

import com.example.starterapp.POJO.User;
import com.example.starterapp.commons.ServerResponse;
import com.example.starterapp.enums.ResponseCode;
import com.example.starterapp.exception.CustomException;
import com.example.starterapp.service.UserService;
import com.example.starterapp.vo.LoginVo;
import io.swagger.annotations.*;
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
@Api(value = "测试API", tags = "用户API")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录",
                  notes = "用户登录返回用户名和token",
                  response = ServerResponse.class,
                  httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 201, message = "请求失败"),
            @ApiResponse(code = 2000, message = "请求参数为空", response = ServerResponse.class),
            @ApiResponse(code = 3000, message = "用户名不存在", response = ServerResponse.class),
            @ApiResponse(code = 3001, message = "用户名密码错误", response = ServerResponse.class),
            @ApiResponse(code = 3002, message = "token加密错误", response = ServerResponse.class)
    })
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
