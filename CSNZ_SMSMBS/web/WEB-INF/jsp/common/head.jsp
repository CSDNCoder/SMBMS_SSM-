<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>	
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>潮汕奴仔超市管理系统</title>
    <link type="text/css" rel="stylesheet" href="../../../css/style.css" />
    <link type="text/css" rel="stylesheet" href="../../../css/public.css" />
</head>
<body>


<!--头部-->
    <header class="publicHeader">
        <h1>潮汕奴仔超市管理系统</h1>
        <div class="publicHeaderR">
            <p><span id="hello">潮汕奴仔你好~</span><span style="color: #fff21b"> </span></p>
            <a href="${pageContext.request.contextPath}/User/logout">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time" ></span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（推荐Google浏览器）</a>
    </section>
 <!--主体内容-->
 <section class="publicMian ">
     <div class="left">
         <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
         <nav>
             <ul class="list">
              <li ><a href="${pageContext.request.contextPath}/Bill/toBillManagerPage">订单管理</a></li>
              <li><a href="${pageContext.request.contextPath}/Provider/toProviderPage">供应商管理</a></li>
              <li><a href="${pageContext.request.contextPath}/UserManager/toUserManagerPage">用户管理</a></li>
              <li><a href="${pageContext.request.contextPath}/User/toUpdatePassword">密码修改</a></li>
              <li><a href="${pageContext.request.contextPath}/User/logout">退出系统</a></li>
             </ul>
         </nav>
     </div>
<%-- </section>这是主体的一部分 另外的section结束标签在 其他主体的结束位置--%>
<input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
     <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>
</body>
</html>