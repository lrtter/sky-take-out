package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UseServiceImpl implements UserService {

    @Autowired
    private WeChatProperties properties;
    @Autowired
    private UserMapper mapper;

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {

        String json = getString(userLoginDTO);

        JSONObject jsonObject = JSON.parseObject(json);
        String  openid = jsonObject.getString("openid");

        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user=mapper.getByOpenId(openid);

        if(user==null){
            user=User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            mapper.insert(user);
        }

        return user;
    }

    private String getString(UserLoginDTO userLoginDTO) {
        Map<String, String> map=new HashMap<>();
        map.put("appid",properties.getAppid());
        map.put("secret",properties.getSecret());
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type","authorization_code");

        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        return json;
    }
}
