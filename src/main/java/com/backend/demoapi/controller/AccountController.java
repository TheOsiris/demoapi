package com.backend.demoapi.controller;

import cn.hutool.core.map.MapUtil;
import com.backend.demoapi.common.dto.LoginDto;
import com.backend.demoapi.common.lang.Result;
import com.backend.demoapi.entity.Userinfo;
import com.backend.demoapi.service.UserinfoService;
import com.backend.demoapi.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        Userinfo userinfo = userinfoService.getOne(new QueryWrapper<Userinfo>().eq("username", loginDto.getUsername()));
        Assert.notNull(userinfo, "用户不存在");

        if (!userinfo.getPassword().equals(loginDto.getPassword())) {
            return Result.failed("密码错误");
        }
        String jwt = jwtUtils.generateToken(userinfo.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.success(MapUtil.builder()
                .put("id",userinfo.getId())
                .put("username",userinfo.getUsername())
                .put("gender",userinfo.getGender())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
