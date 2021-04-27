package com.backend.demoapi.controller;


import cn.hutool.core.bean.BeanUtil;
import com.backend.demoapi.common.lang.Result;
import com.backend.demoapi.entity.Blog;
import com.backend.demoapi.service.BlogService;
import com.backend.demoapi.util.ShiroUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author null
 * @since 2021-04-25
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Integer id) {

        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.success(blog);
    }

    @RequiresAuthentication
    @GetMapping("/blog/delete/{id}")
    public Result deleteblog(@PathVariable(name = "id") Integer id) {

        blogService.removeById(id);

        return Result.success(null);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result list(@Validated @RequestBody Blog blog) {

        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //设置编辑权限
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有编辑权限");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }
}
