/*
 *    Copyright (c) 2018-2025, ccs All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: ccs
 */

package com.chensino.core.security.utils;

import cn.hutool.core.util.ArrayUtil;
import com.chensino.common.core.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccs
 * @Date 2018/6/22 接口权限判断工具
 */
@Slf4j
@Component("pms")
public class PermissionService {

	/**
	 * 判断接口是否有任意xxx，xxx权限
	 * @param permissions 权限
	 * @return {boolean}
	 */
	public boolean hasPermission(String... permissions) {
		if (ArrayUtil.isEmpty(permissions)) {
			return false;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtils::hasText)
				.anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
	}

	/**
	 * 角色和权限都在用户的authorities字段中，不同之处是用户上下文中存储的角色会自动加上ROLE_前缀，所以判断时需要对应上，手动加上ROLE_后，
	 * 变成ROLE_ADMIN，实际和普通权限一样，因此可以调用 {@link PermissionService#hasPermission(String...)}进行判断
	 * @param roles
	 * @return
	 */
	public boolean hasRole(String... roles){
		if (ArrayUtil.isEmpty(roles)) {
			return false;
		}
		List<String> list = Arrays.stream(roles).map(role -> SecurityConstants.ROLE + role).collect(Collectors.toList());
		return this.hasPermission(ArrayUtil.toArray(list,String.class));
	}

}
