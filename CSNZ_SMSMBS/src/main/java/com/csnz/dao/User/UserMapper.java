package com.csnz.dao.User;/*
 *
 *@create 2021-04-29-9:44
 */

import com.csnz.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

//登录 判断 的接口
/*
@Repository作用：把资源让Spring来管理，相当于在xml中配置了一个bean
value属性指定bean的id，如果不指定，默认id为当前类的类名(首字母小写) userMapper
 */
@Repository
public interface UserMapper {
    //根据userCode 查找用户信息
    public abstract User findUserByUserCode(String userCode);


    //修改密码
    public abstract int updatePassword(@Param("userId") int userId ,@Param("newPassword") String newPassword);

    //根据用户名 或 角色 查询用户总数
    public abstract int getUserCounts(@Param("username") String username,@Param("userRole") int userRole);

    //根据条件 查询 获取用户列表 userlist
    public abstract List<User> getUserList(@Param("username") String username, @Param("userRole") int userRole,@Param("currentPageNo") int currentPageNo,@Param("pageSize") int pageSize);



    //用户管理模块中的 子模块—— 添加用户
    public abstract int addUser(User user);

    //用户管理模块中的子模块 —— 删除用户
    public abstract boolean deleteUser(@Param("userId") int userId);

    //根据用户id 查询用户信息
    public abstract User findById(@Param("id") int userId);

    //用户管理模块中的子模块 —— 更改用户信息
    public abstract boolean modify(@Param("id") int id,@Param("user") User user);
}
