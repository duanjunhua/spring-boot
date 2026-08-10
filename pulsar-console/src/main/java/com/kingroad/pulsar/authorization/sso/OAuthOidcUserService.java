package com.kingroad.pulsar.authorization.sso;

import com.kingroad.pulsar.common.CommonConst;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.domain.entity.SysUserRole;
import com.kingroad.pulsar.repository.SysUserRoleRepository;
import com.kingroad.pulsar.service.SysRoleService;
import com.kingroad.pulsar.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-31 周五 09:41
 * @Version: v1.0
 * @Description: OAuth2从id_token获取用户
 */
@Slf4j
@Service
public class OAuthOidcUserService extends OidcUserService {

    @Resource
    SysUserService service;

    @Resource
    SysUserRoleRepository urRepository;

    @Resource
    SysRoleService roleService;

    /**
     * OIDC登录必经入口，一定会执行
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("==================== OIDC登录进入自定义用户解析服务 ====================");
        OidcUser oidcUser = super.loadUser(userRequest);

        // 通过id_token获取用户信息
        OidcIdToken idToken = oidcUser.getIdToken();
        Map<String, Object> claims = idToken.getClaims();

        // OIDC全局唯一标识
        String ssoId = (String)claims.get(SsoConst.ATTR_SUB);

        String email = claims.containsKey("email") ? (String) claims.get("email") : "";
        String nickname = claims.containsKey("name") ? (String) claims.get("name") : SsoConst.ATTR_SUB;

        log.info("OIDC用户信息 ssoId:{},email:{},nick:{}", ssoId, email, nickname);

        // 2.根据sub查询本地系统用户
        SysUser sysUser = service.findEntityBySsoId(ssoId);

        // 自动新建用户
        if(ObjectUtils.isEmpty(sysUser)) {
            log.info("首次SSO登录，自动创建本地账号：{}", ssoId);
            SysUser u = SysUser.buildSsoUser(ssoId, email, nickname);
            sysUser = service.saveOrUpdate(u);

            /**
             * 用户普通角色授予通过用户申请
             */
            /*
            SysRole role = roleService.findEntityByRoleCode("ROLE_USER");
            if(ObjectUtils.isNotEmpty(role)){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(role.getId());
                sysUserRole.setUserId(sysUser.getId());
                urRepository.save(sysUserRole);
            }
            sysUser.setRoleList(Arrays.asList(role));
            */
            // 封装自定义OidcUser，可存入用户ID、角色等后续鉴权使用
            return new SysOidcUser(CollectionUtils.emptyCollection(), oidcUser.getIdToken(), oidcUser.getUserInfo(), SsoConst.ATTR_SUB, sysUser);
        }

        log.info("已绑定本地账号，直接登录：{}", sysUser.getUsername());

        List<SimpleGrantedAuthority> authorities = sysUser.getRoleList().stream().map(SysRole::getRoleCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 封装自定义OidcUser，可存入用户ID、角色等后续鉴权使用
        return new SysOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), SsoConst.ATTR_SUB, sysUser);
    }
}
