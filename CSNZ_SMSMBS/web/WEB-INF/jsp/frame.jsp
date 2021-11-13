<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
    <img class="wColck" src="../images/photo.jpg" alt=""/>
    <div class="wFont">
        <h2>${ userSession.userName }</h2>
        <h1 style="color: #24bbff;">欢迎来到潮汕奴仔超市管理系统!</h1>
        <h3 style="color: #24bbff;">你可以继续选择 操作订单管理、供应商管理、用户管理、密码修改或退出系统</h3>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
