<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单页面</title>
<link rel="stylesheet" type="text/css" href="css/goods.css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/goods.js"></script>
</head>
<body>
	<%@include file="common.jsp"%>
	<c:choose>
		<c:when test="${not empty(user)}">
			<hr>
			<%--判断订单是否存在 --%>
			<c:choose>
				<c:when test="${empty(orders)} or fn.length(orders)==0">
					<font>该用户没有任何订单！！<a href="goods.do">去购买</a></font>
				</c:when>
				<c:otherwise>
该用户的订单(按时间先后排序)：<br>
					<c:forEach var="showorders" items="${orders}">
						<a
							href="showOrders.do?opt=showOrderDetails&order_id=${showorders.id}"
							title="查看订单"><fmt:formatDate
								value="${showorders.order_date}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></a><br>
					</c:forEach>
					<hr>
					<font color="red">${error_message}</font>
					<%--订单详细商品信息 --%>
					<c:if test="${not empty(ordersViews) and fn:length(ordersViews)>0}">
						<%--定义变量，用来计算用户订单总额 --%>
						<c:set var="orderViews_total" value="${0}"></c:set>
					订单号：${order_id}$nbsp;$nbsp;
					下单时间：<fmt:formatDate value="${order_date}"
							pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
						<br>
					订单商品明细如下：<br>
						<table>
							<tr>
								<th>商品图</th>
								<th>商品名</th>
								<th>单价</th>
								<th>购买数量</th>
								<th>小计</th>
							</tr>
							<c:forEach var="orderdetail" items="${ordersViews}">
								<tr>
									<td><img alt="${orderdetail.goods_name}"
										src="${orderdetail.goods_image}"></td>
									<td>${orderdetail.goods_name}</td>
									<td>${orderdetail.price}</td>
									<td>${orderdetail.buyCount}</td>
									<td>${orderdetail.price*orderdetail.buyCount}</td>
								</tr>
								<%--每遍历一个商品明细，就需要 累加订单总额 --%>
								<c:set var="orderViews_total"
									value="${orderViews_total+orderdetail.price*orderdetail.buyCount}"></c:set>
							</c:forEach>
						</table>
						<hr class="detailAllprice" align="left">
					总计(￥):${orderViews_total}			
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</body>
</html>