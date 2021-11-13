<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
<div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>密码修改页面</span>
            </div>
            <div class="providerAdd">
                <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/User/pwdmodify">
                    <div class="">
                        <label for="oldpassword">旧密码：</label>
                        <input type="password" name="oldpassword" id="oldpassword" value="">
						<font color="red"></font>
                    </div>
                    <div>
                        <label for="newpassword">新密码：</label>
                        <input type="password" name="newpassword" id="newpassword" value="">
						<font color="red"></font>
                    </div>
                    <div>
                        <label for="rnewpassword">确认新密码：</label>
                        <input type="password" name="rnewpassword" id="rnewpassword" value="">
						<font color="red"></font>
                    </div>
                    <div class="info">${message}</div>
                    <div class="providerAddBtn">
                        <!--<a href="#">保存</a>-->
                        <input type="button" name="save" id="save" value="保存" class="input-button">
                    </div>
                </form>
            </div>
        </div>
    </section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="../js/pwdmodify.js"></script>