package com.chensino.core.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chensino.common.core.constant.SecurityConstants;
import com.chensino.core.api.dto.UserInfo;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUserName, username));
        UserInfo userInfo = sysUserService.findUserInfo(sysUser);
        return getUserDetails(userInfo);
    }

    /**
     * 根据sysUser构造UserDetails
     *
     * @param info
     * @return
     */
    private UserDetails getUserDetails(UserInfo info) {
        Objects.requireNonNull(info.getSysUser(), "用户不存在");
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(roleId -> dbAuthsSet.add(SecurityConstants.ROLE + roleId));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUser user = info.getSysUser();

        return new User(user.getUserName(), user.getPassword(), !user.getLockFlag(), true, true, !user.getLockFlag(), authorities);
    }
}
