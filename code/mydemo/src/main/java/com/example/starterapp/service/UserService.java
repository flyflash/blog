package com.example.starterapp.service;

import com.example.starterapp.POJO.User;
import com.example.starterapp.vo.LoginVo;

public interface UserService {

    /**
     * 校验用户是否存在
     * @param username
     * @return
     */
    User checkUserIsExistByName(String username);

    /**
     * 校验密码
     * @param user
     * @return
     */
    boolean checkPwdIsRight(User user);

    /**
     * 生成token
     * @param user
     * @return
     */
    LoginVo generateToken(User user) throws Exception;
}
