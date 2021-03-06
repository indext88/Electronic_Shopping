<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
时间：2015-12-30
描述：菜单栏
-->
<div class="container-fluid">
    <div class="col-md-4">
        <img src="img/logo2.png" />
    </div>
    <div class="col-md-5">
        <img src="img/header.png" />
    </div>
    <div class="col-md-3" style="padding-top:20px">
        <ol class="list-inline">
            <c:if test="${sessionScope.user != null}">
                欢迎:${sessionScope.user.username}
                <li><a href="userServlet?method=logout">注销</a></li>
                <li><a href="${pageContext.request.contextPath}/orderServlet?method=myOrderList&uid=${sessionScope.user.uid}">我的订单</a></li>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <li><a href="login.jsp">登录</a></li>
                <li><a href="register.jsp">注册</a></li>
            </c:if>

            <li><a href="cart.jsp">购物车</a></li>
        </ol>
    </div>
</div>
<!--
时间：2015-12-30
描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="categoryList">
                    <%--<li class="active"><a href="product_list.jsp">手机数码<span class="sr-only">(current)</span></a></li>--%>

                </ul>

                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url:"categoryServlet?method=findAll",
            type:"get",
            dataType:"json",
            success:function (json) {
                //循环遍历json
                for(var i = 0 ;i < json.length;i++)
                {
                    var category = json[i];
                    //拼装li节点
                    var liNode =  "<li><a href=\"productServlet?method=findPageByCid&cid=" +category.cid+"\">"+category.cname+"</a></li>";
                    //选中categoryList ，追加liNode
                    $("#categoryList").append(liNode);
                }
            },
            error:function (reason) {

            }
        })
    })

</script>