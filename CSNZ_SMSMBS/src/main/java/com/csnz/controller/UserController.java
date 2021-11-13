package com.csnz.controller;/*
 *
 *@create 2021-10-21-20:10
 */

import com.alibaba.fastjson.JSONArray;
import com.csnz.pojo.User;
import com.csnz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    /*
     *****************************  用户登陆 与 注销模块   *****************************
     */
    @RequestMapping("/login")
    public String findUser(String userCode, String userPassword,HttpServletRequest request){
        User user = userService.login(userCode,userPassword);
        if (user != null) { //如果用户不为空 将用户信息存入session
                request.getSession().setAttribute("userSession",user);
                return "frame";
            }
        request.getSession().setAttribute("msg","当前账号或密码错误");
        return "redirect:/index.jsp";
    }

    //退出系统
    @GetMapping("/logout")
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //移除用户的session
        req.getSession().removeAttribute("userSession");
        //返回登录页面
        req.setAttribute("msg","");
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }


    /*
     *************************      用户修改密码模块   *********************************
     */

    //去往更新密码页面
    @GetMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "pwdmodify";
    }

    //验证旧密码是否正确
    @GetMapping("/verifyPwd")
    @ResponseBody
    public String verifyPwd(String oldpassword,HttpServletRequest req) {
        //依旧从session中取ID
        User user = (User) req.getSession().getAttribute("userSession");
        //System.out.println("前端传来的旧密码："+oldpassword);
        //将结果存放在map集合中 让Ajax使用
        Map<String, String> resultMap = new HashMap<>();
        //下面开始判断 键都是用result 此处匹配js中的Ajax代码
        if(user == null){
            //说明session被移除了 或未登录|已注销
            resultMap.put("result","sessionerror");
        }else if(oldpassword == null){
            //前端输入的密码为空
            resultMap.put("result","error");
        }else {
            //如果旧密码与前端传来的密码相同
            if((user).getUserPassword().equals(oldpassword)){
                resultMap.put("result","true");
            }else{
                //前端输入的密码和真实密码不相同
                resultMap.put("result","false");
            }
        }
        //JSONArray 阿里巴巴的JSON工具类 用途就是：转换格式
        return JSONArray.toJSONString(resultMap);
    }
    // 修改密码
    @RequestMapping("/pwdmodify")
    public String pwdmodify(String newpassword,HttpServletRequest req, ModelAndView model)  {
        //从Session中获取ID
        User user = (User) req.getSession().getAttribute("userSession");
        //先判断不为空 再比较密码是否相等
        if(user != null && newpassword != null){
            //修改密码并返回结果
            boolean flag = userService.updatePassword(user.getId(), newpassword);
            //如果密码修改成功 移除当前session
            if(flag){
                model.addObject("message","修改密码成功，请使用新密码登录!");
                req.getSession().removeAttribute("userSession");
            }else{
                model.addObject("message","密码修改失败 新密码不符合规范");
            }
        }else{
            model.addObject("message","新密码不能为空!");
        }
        //修改完了 重定向到此修改页面
        return "pwdmodify";
    }



}
