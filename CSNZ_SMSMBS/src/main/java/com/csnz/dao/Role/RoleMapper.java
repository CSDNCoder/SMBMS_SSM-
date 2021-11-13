package com.csnz.dao.Role;/*
 *
 *@create 2021-11-07-15:02
 */

import com.csnz.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@Repository作用：把资源让Spring来管理，相当于在xml中配置了一个bean
value属性指定bean的id，如果不指定，默认id为当前类的类名(首字母小写) roleMapper
 */
@Repository
public interface RoleMapper {
    // 获取用户角色列表
    public abstract List<Role> getRoleList();
}
