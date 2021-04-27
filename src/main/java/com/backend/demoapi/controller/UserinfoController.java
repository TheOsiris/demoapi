package com.backend.demoapi.controller;


import com.backend.demoapi.common.lang.Result;
import com.backend.demoapi.entity.Userinfo;
import com.backend.demoapi.service.UserinfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.lang.Assert;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/userinfo")
public class UserinfoController {

    @Autowired
    UserinfoService userinfoService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        Userinfo userinfo = userinfoService.getById(1L);
        return Result.success(userinfo);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody Userinfo userinfo) {
        Userinfo user = userinfoService.getOne(new QueryWrapper<Userinfo>().eq("username", userinfo.getUsername()));
        Assert.isNull(user, "用户名已存在");
        userinfoService.save(userinfo);
        return Result.success(userinfo);
    }

}
