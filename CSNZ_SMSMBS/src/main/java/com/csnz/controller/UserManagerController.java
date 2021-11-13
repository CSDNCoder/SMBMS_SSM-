package com.csnz.controller;/*
 *
 *@create 2021-11-07-14:07
 */

import com.alibaba.fastjson.JSONArray;
import com.csnz.pojo.Role;
import com.csnz.pojo.User;
import com.csnz.service.RoleService;
import com.csnz.service.UserService;
import com.csnz.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/UserManager")
public class UserManagerController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PageSupport pageSupport;


    /*
     *************************      查询用户列表模块   *********************************
     */

    /**
     * @Des 去用户管理页面时 也需要查询用户信息
     * @Author zzj
     * @Date 2021/11/7/0007 14:09
     **/
    @GetMapping("/toUserManagerPage")
    public String toUserManagerPage(String queryName, String queryUserRole, String pageIndex, HttpServletRequest request) {
        //这里需要设置分页参数
        //1、下面是默认要返回的分页信息 还没根据前端传来的做转化
        int userRole = 0;       // 用户角色 默认 为 0
        int pageSize = 5;       // 一页显式的数据量 默认为 5
        int currentPageNo = 1;  // 当前页码
        int totalCount = 0;     // 根据条件查询出来的记录总数量 默认为0
        int totalPageCount = 0; // 全部数据量 算出 总共显示的页数

        //2、根据前端传来的信息做转化 替换默认分页信息
        if (queryUserRole != null && !queryUserRole.trim().equals("")) {
            try {
                userRole = Integer.parseInt(queryUserRole);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pageIndex != null && !pageIndex.trim().equals("")) {
            try {
                currentPageNo = Integer.parseInt(pageIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //3、根据条件(用户名、用户角色)查询出来的记录总数量
        totalCount = userService.getUserCounts(queryName, userRole);

        //4、设置pageSupport的参数
        pageSupport.setPageSize(pageSize);              // 设置 页面容量
        pageSupport.setTotalCount(totalCount);          // 设置 总数量(表)
        pageSupport.setCurrentPageNo(currentPageNo);    // 设置 当前页码 来自于用户输入
        //总共显示的页数
        totalPageCount = pageSupport.getTotalPageCount();

        //5、如果页数小于1，就显示第一页  页数大于 最后一页就 显示最后一页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        //6、根据条件 查询用户列表
        List<User> userList = userService.getUserList(queryName, userRole, (currentPageNo - 1) * pageSize, pageSize);
        //将信息返回至前端页面
        request.setAttribute("userList", userList);

        //潮汕奴仔_优化： 这里加一步 判断此 列表是否为空 是则 清零页码信息 并返回
        if(userList.size() == 0){
            System.out.println("\n潮汕奴仔_优化\n");
            request.setAttribute("queryUserName", queryName);
            request.setAttribute("queryUserRole", queryUserRole);
            request.setAttribute("totalPageCount",0);
            request.setAttribute("totalCount",0);
            request.setAttribute("currentPageNo",0);
            return "userlist";
        }
        //7、查询用户角色列表
        List<Role> roleList = roleService.getRoleList();
        //将信息返回至前端页面
        request.setAttribute("roleList", roleList);

        //8、将所有信息返回至前端展示
        request.setAttribute("queryUserName", queryName);
        request.setAttribute("queryUserRole", queryUserRole);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }

    /*
     *************************      查询用户详情模块   *********************************
     */
    @GetMapping("/toViewUser/{uid}")
    public String toViewUser(@PathVariable String uid, HttpServletRequest request) {
        int id = 0;
        try {
            id = Integer.parseInt(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //下面要根据用户id查询用户数据 展示出来
        User user = userService.findById(id);
        //将用户信息传至前端进行展示
        request.setAttribute("user", user);
        return "userview";
    }

    /*
     *************************      修改用户详情模块   *********************************
     */
    @GetMapping("/toModifyUserPage/{uid}")
    public String toModifyUserPage(@PathVariable String uid, HttpServletRequest request) {
        int id = 0;
        try {
            id = Integer.parseInt(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //下面要根据用户id查询用户数据 展示出来
        User user = userService.findById(id);
        //将用户信息传至前端进行展示
        request.setAttribute("user", user);
        System.out.println(user.toString());
        //再给用户进行修改
        return "usermodify";
    }


    // 修改用户
    @PostMapping("/modifyUser")
    public String modifyUser(String uid, String userName, String gender, String birthday, String phone, String address, String userRole, HttpServletRequest request) throws ParseException {
        int id = 0;
        try {
            id = Integer.parseInt(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUserName(userName);
        user.setGender(Integer.parseInt(gender));
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(userRole));
        //注意这两个参数不在表单的填写范围内
        user.setModifyBy(((User) request.getSession().getAttribute("userSession")).getId());
        user.setModifyDate(new Date());
        //要插入数据
        boolean flag = userService.modify(id, user);
        //如果执行成功了 到 用户管理页面(即 查询全部用户列表)
        if (flag) {
            return "redirect:/UserManager/toUserManagerPage";
        }
        return "redirect:/UserManager/toModifyUserPage/" + id;
    }
    /*
     *************************      删除用户模块   *********************************
     */
    @GetMapping(value = "/deleteUser/{uid}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteUser(@PathVariable("uid") String id){
        int userId = Integer.parseInt(id);
        System.out.println("id ："+userId);
        HashMap<String, String> map = new HashMap<>();
        boolean flag = userService.deleteUser(userId);
        if (userId <= 0) {
            map.put("delResult","notexist");
        }else if(flag){
            map.put("delResult","true");
        }else{
            map.put("delResult","false");
        }
        return JSONArray.toJSONString(map);
    }

    /*
     *************************      添加用户模块   *********************************
     */
    //去往 添加用户信息的页面
    @GetMapping("/toAddUserPage")
    public String toAddUserPage() {
        return "useradd";
    }

    @PostMapping("/addUser")
    public String addUser(String userCode, String userName, String userPassword, String gender, String birthday, String phone, String address, String userRole,  HttpServletRequest request) throws ParseException {
        //转换格式后 调用service层 添加用户
        int resGender = 0;
        int resUserRole = 0;
        try {
            resGender = Integer.parseInt(gender);
            resUserRole = Integer.parseInt(userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setGender(resGender);
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(resUserRole);
        //注意这两个参数不在表单的填写范围内
        user.setCreatedBy(((User) request.getSession().getAttribute("userSession")).getId());
        user.setCreateDate(new Date());

        boolean flag = userService.addUser(user);
        if (flag) {
            //说明执行成功 到 用户管理页面(即 查询全部用户列表)
            return "redirect:/UserManager/toUserManagerPage";
        } else {
            //说明 添加失败 回到 添加页面
            return "useradd";
        }
    }


    /*
     *************************      用户通用模块   *********************************
     */

    //验证是否存在 此userCode
    @GetMapping(value = "/ifExistUserCode/{uCode}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String ifExistUserCode(@PathVariable("uCode") String userCode){
        System.out.println("进入ifExistUserCode");
        //根据此 userCode 查询是否有这个用户
        boolean flag = userService.ifExistUserCode(userCode);
        HashMap<String, String> map = new HashMap<>();

        if(userCode==null || userCode.trim().equals("")){
            map.put("userCode","NoWrite");
        }else if(flag){
            map.put("userCode","exist");
        }
        return JSONArray.toJSONString(map);
    }


    //用户管理模块中 子模块(表单中的用户角色下拉框)
    @GetMapping(value = "/getRoleList", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getRoleList() {
        List<Role> roleList = roleService.getRoleList();
        //JSONArray 阿里巴巴的JSON工具类 用途就是：转换格式
        return JSONArray.toJSONString(roleList);
    }
}
