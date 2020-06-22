<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
<link rel="stylesheet" type="text/css" href="css/goods.css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/cart.js"></script>
</head>
<body>
	<%@include file="common.jsp"%>
	<br>
	<font color="red">${error_message}</font>
	<br>
	<c:choose>
		<c:when test="${not empty(sessionScope.user)}">
			<hr>
			<c:choose>
				<%--用户登录，购车无商品 --%>
				<c:when test="${empty(cart) or fn:length(cart.items)==0 }">
					<font color="red">购物车中无商品<a
						href="javascript:location.href='goods.do';">返回</a></font>
				</c:when>
				<%-- 用户登录，购物车中有商品 --%>
				<c:otherwise>
					<hr>
					<div>
						总计：${cart.getTotalPrice()}&nbsp;&nbsp; <a href="orders.do" title="点击结账"><img
							alt="结账" src="images/goods_images/bank-terminal.png" class="fnImg"></a><br>
						&nbsp;&nbsp;<a href="goods.do">继续购物</a> &nbsp;&nbsp;<a
							href="javascript:doRemove('removeAll','carts.do?opt=removeAll');">清空购物车</a>
					</div>
					<hr>
					<table>
						<tr>
							<td>商品预览</td>
							<td>商品名</td>
							<td>单价</td>
							<td>购买数量</td>
							<td>小计</td>
							<td>操作</td>
						</tr>
						<%--遍历购物车 --%>
						<c:forEach var="item" items="${cart.items}">
							<tr>
								<td><img src="${item.goods_image}" alt="${item.goods_name}"></img></td>
								<td>${item.goods_name}</td>
								<td>${item.price}</td>
								<td>${item.buyCount}</td>
								<td>${item.buyCount*item.price}</td>
								<td><a
									href="javascript:doRemove('removeOne','carts.do?opt=removeOne&id=${item.id}');">
										<img src="images/goods_images/item_remove.png" alt="移除商品"
										class="removeImg"></img>
								</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</body>
</html>