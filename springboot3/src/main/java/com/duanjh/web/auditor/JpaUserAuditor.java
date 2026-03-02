package com.duanjh.web.auditor;

import com.duanjh.shiro.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-27 周五 17:25
 * @Version: v1.0
 * @Description: JPA中@CreateBy、@LastModifiedBy自动注入
 */
@Slf4j
@Component(value = "jpaUserAuditor")
public class JpaUserAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //此处使用Shiro认证后获取到的用户
        Subject subject = SecurityUtils.getSubject();
        log.info("Current user: {}", subject.getPrincipal());
        if(subject.isAuthenticated()) {
            SysUser user = (SysUser) subject.getPrincipal();
            return Optional.ofNullable(user.getUsername());
        }
        return Optional.of("system");
    }
}
