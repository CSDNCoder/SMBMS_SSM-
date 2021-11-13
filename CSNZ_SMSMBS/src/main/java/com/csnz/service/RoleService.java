package com.csnz.service;/*
 *
 *@create 2021-11-07-15:07
 */

import com.csnz.pojo.Role;

import java.util.List;

public interface RoleService {
    // 获取用户角色列表
    public abstract List<Role> getRoleList();
}
