package com.csnz.service.Impl;/*
 *
 *@create 2021-10-21-0:20
 */

import com.csnz.dao.User.UserMapper;
import com.csnz.pojo.User;
import com.csnz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    //业务层调用dao层
    @Autowired
    private UserMapper userMapper;

//  使用注解模式开发：对应类上加 @Service 和 @Autowired 此时就不用set方法注入了
//    public void setUserMapper(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }


    @Override
    public User login(String userCode, String password) {
        //先通过 userCode查找用户的信息 再判断用户密码是否相等 如果不相同就返回 null
        User user = userMapper.findUserByUserCode(userCode);
        boolean flag = user != null ? user.getUserPassword().equals(password) : false;
        return flag == true ? user : null;
    }

    @Override
    public boolean updatePassword(int id, String passWord) {
        return userMapper.updatePassword(id,passWord)==1;
    }

    //根据用户 userCode 查询是否拥有这个用户
    @Override
    public boolean ifExistUserCode(String userCode) {
        return userMapper.findUserByUserCode(userCode)!=null;
    }


    @Override
    public int getUserCounts(String username, int userRole) {
        return userMapper.getUserCounts(username,userRole);
    }

    @Override
    public List<User> getUserList(String QueryUserName, int QueryUserRole, int currentPageNo, int pageSize) {
        return userMapper.getUserList(QueryUserName,QueryUserRole,currentPageNo,pageSize);
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user)==1;
    }

    @Override
    public boolean deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    public User findById(int userId) {
        return userMapper.findById(userId);
    }

    @Override
    public boolean modify(int id, User user) {
        return userMapper.modify(id,user);
    }
}
