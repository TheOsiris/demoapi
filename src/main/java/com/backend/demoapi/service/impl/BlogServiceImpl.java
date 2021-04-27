package com.backend.demoapi.service.impl;

import com.backend.demoapi.entity.Blog;
import com.backend.demoapi.mapper.BlogMapper;
import com.backend.demoapi.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author null
 * @since 2021-04-25
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
