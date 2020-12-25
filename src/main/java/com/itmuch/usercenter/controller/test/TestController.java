package com.itmuch.usercenter.controller.test;

import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

// @RequiredArgsConstructor(onConstructor = @__(@Autowired)) 相当于 @Autowired(requird=false)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    //@Autowired
    private final UserMapper userMapper;

    @GetMapping("/test")
    public String testInsert() {
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);
        return "success";
    }


    @GetMapping("/q")
    public User query(User user) {
        return user;
    }

    @PostMapping("/post")
    public User post(@RequestBody User user) {
        return user;
    }

}
