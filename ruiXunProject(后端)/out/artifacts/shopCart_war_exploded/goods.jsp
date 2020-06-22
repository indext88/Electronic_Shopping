<%@page import="com.ruixun.shopcart.pojo.GoodsView"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品浏览</title>
<link rel="stylesheet" type="text/css" href="css/goods.css">
<script type="text/javascript" src="js/goods.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
	<%@include file="common.jsp"%>
	<font color="red">${error_message}</font>

	<c:choose>
		<c:when
			test="${empty(sessionScope.allGoods) or fn:length(sessionScope.allGoods)==0}">
			<font color="red">没有任何商品信息！！</font>
		</c:when>
		<c:otherwise>
			<form action="carts.do" method="post">
				<c:if test="${not empty(sessionScope.user)}">
					<a href="javascript:doSubmit();" title="添加到购物车"> <img
						class="fnImg" alt="添加到购物车"
						src="images/goods_images/shopping-cart.png">
					</a>
		&nbsp;&nbsp;
		<a href="cart.jsp" title="查看购物车"> <img class="fnImg" alt="查看购物车"
						src="images/goods_images/watch_cart.png">
					</a>
				</c:if>
			

			<table>
				<tr>
					<td>请选择</td>
					<td>商品预览</td>
					<td>商品名</td>
					<td>类型</td>
					<td>单价</td>
					<td>库存量</td>
				</tr>
				<!-- 循环输出商品信息 -->
				<c:forEach var="goods" items="${allGoods}">
					<tr>
						<td><input type="checkbox" class="checkBoxStyple"
							id="chooseIds${goods.id}" name="chooseIds" value="${goods.id}"></td>
						<td><img src="${goods.goods_image}" alt="${goods.goods_name}"></img></td>
						<td>${goods.goods_name}</td>
						<td>${goods.type_name}</td>
						<td>${goods.price}</td>
						<td>
							<!-- 放置一个隐藏字段，用于比对库存，进而显示库存不足，数量仅剩等信息--> <input type="hidden"
							id="goodsCount${goods.id}" name="count" value="${goods.count}" />
							<c:choose>
								<c:when test="${goods.count==0}">
									<font color="red">缺货</font>
								</c:when>
								<c:when test="${goods.count>0 and goods.count<10}">
									<font color="red">仅剩${goods.count}</font>
								</c:when>
								<c:otherwise>${goods.count}</c:otherwise>
							</c:choose>
						</td>
						<td width="100px"><input type="text"
							style="visibility: ${goods.count==0?'hidden':'visiable'}"
							name="input${goods.id}" placeholder="请输入购买数量"
							onblur="javascript:checkBuyCount(${goods.id},this.value);" /></td>
						<td><div id="div${goods.id}"></div></td>
					</tr>
				</c:forEach>
			</table>
			</form>
		</c:otherwise>
	</c:choose>


</body>
</html>