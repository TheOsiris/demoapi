package com.backend.demoapi.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.backend.demoapi.entity.Userinfo;
import com.backend.demoapi.service.UserinfoService;
import com.backend.demoapi.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserinfoService userinfoService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;
        String userid = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        Userinfo userinfo = userinfoService.getById(Integer.valueOf(userid));

        if (userinfo == null) {
            throw new UnknownAccountException("账户不存在");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(userinfo, profile);

        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
