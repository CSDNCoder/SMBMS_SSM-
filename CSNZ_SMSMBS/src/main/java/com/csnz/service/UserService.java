package com.csnz.service;/*
 *
 *@create 2021-04-29-10:21
 */
import com.csnz.pojo.User;
import java.util.List;


public interface UserService {
    //登陆
    public abstract User login(String userCode,String password);

    //根据用户ID修改密码
    public abstract boolean updatePassword(int id, String passWord);

    //根据用户 userCode 查询是否拥有这个用户
    public abstract boolean ifExistUserCode(String userCode);




    //用户管理——查询记录数
    public abstract int getUserCounts(String username, int userRole);
    //根据条件 查询用户列表
    public abstract List<User> getUserList(String QueryUserName, int QueryUserRole, int currentPageNo, int pageSize);



    //用户管理模块中的 子模块—— 添加用户
    public abstract boolean addUser(User user);
    //用户管理模块中的子模块 —— 删除用户
    public abstract boolean deleteUser(int userId);
    //根据id查询用户信息
    public abstract User findById(int userId);
    //用户管理模块中的子模块 —— 更改用户信息
    public abstract boolean modify(int id, User user);
}
