package com.csnz.Interceptor;/*
 *
 *@create 2021-11-07-17:55
 */

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //用户登陆成功 ： true
        if(request.getSession().getAttribute("userSession") != null) {
            return true;
        }
        // 用户请求登陆需求 ： true
        if(request.getRequestURI().contains("login") || request.getRequestURI().contains("logout")){
            return true;
        }
        // 其余请求都是 error
        System.out.println("拦截到非法请求...");
        request.setAttribute("msg","请先登陆!");
        request.getRequestDispatcher("/index.jsp").forward(request,response);
        return false;
    }
}
