<%@page import="com.ruixun.shopcart.pojo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公共页面</title>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${not empty(sessionScope.user)}">
          欢迎你，${user.getUname()}
     <a href="goods.do">去购物</a>&nbsp;|&nbsp;
	<a href="showOrders.do?opt=showOrders&uid=${user.id}">查看订单</a>&nbsp;|&nbsp;
	<a href="javascript:doLogout();">安全退出</a>
		</c:when>
		<c:otherwise>
        用户未<a href="index.html">登录</a>，之后才能操作
        </c:otherwise>
	</c:choose>
</body>
</html>