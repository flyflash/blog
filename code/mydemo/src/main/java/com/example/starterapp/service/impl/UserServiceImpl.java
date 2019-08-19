package com.example.starterapp.service.impl;

import com.example.starterapp.constant.Constant;
import com.example.starterapp.POJO.User;
import com.example.starterapp.mapper.UserMapper;
import com.example.starterapp.service.UserService;
import com.example.starterapp.utils.DESUtil;
import com.example.starterapp.utils.TokenCacheUtil;
import com.example.starterapp.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUserIsExistByName(String username) {
        Example userExample = new Example(User.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);

        User user = userMapper.selectOneByExample(userExample);

        return user == null ? null : user;
    }

    @Override
    public boolean checkPwdIsRight(User user) {
        Example userExample = new Example(User.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", user.getUsername());
        criteria.andEqualTo("password", user.getPassword());

        User u = userMapper.selectOneByExample(userExample);
        return u == null ? false : true;
    }

    @Override
    public LoginVo generateToken(User user) throws Exception{
        ConcurrentMap<String, User> map = TokenCacheUtil.localCache.asMap();
        Iterator<Map.Entry<String, User>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, User> next = iterator.next();
            if (next.getValue().getId() == user.getId()){
                TokenCacheUtil.delToken(Constant.CACHE_TOKEN_PREFIX + next.getKey());
            }
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String uniqueToken = user.getId() + "@" + uuid;
        uniqueToken = DESUtil.encrypt(uniqueToken);
        TokenCacheUtil.setToken(Constant.CACHE_TOKEN_PREFIX + uniqueToken, user);
        LoginVo vo = new LoginVo();
        vo.setUsername(user.getUsername());
        vo.setToken(uniqueToken);
        return  vo;
    }
}
