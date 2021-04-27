package com.backend.demoapi.service.impl;

import com.backend.demoapi.entity.Userinfo;
import com.backend.demoapi.mapper.UserinfoMapper;
import com.backend.demoapi.service.UserinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements UserinfoService {

}
