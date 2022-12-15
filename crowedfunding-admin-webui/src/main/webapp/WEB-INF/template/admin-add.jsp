<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="commons/include-head.jsp" %>

<body>

<%--导航栏--%>
<%@include file="commons/include-nav.jsp" %>


<div class="container-fluid">
    <div class="row">
        <%--        控制面板siderbar--%>
        <%@include file="commons/include-siderbar.jsp" %>

        <%--        主体内容--%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/admin-main.html">首页</a></li>
                <li><a href="admin/do/page.html">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>

                <div class="panel-body">
                    <%--                    错误信息提示--%>
                 <button type="button" class="close left-button" aria-label="Close">
                     <span aria-hidden="true">${requestScope.exception.message}</span>
                 </button>
<%--                    form表单--%>
                    <form role="form" action="admin/do/add.html" method="post">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆账号</label>
                            <input type="text" name="loginAcct" class="form-control" value="${param.loginAcct}" id="exampleInputPassword1" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆密码</label>
                            <input type="password" name="userPsw" class="form-control"  value="${param.userPsw}" id="exampleInputPassword2" placeholder="请输入登陆密码">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input type="text" name="userName" class="form-control"  value="${param.userName}"  id="exampleInputPassword3" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email"  name="email" class="form-control"  value="${param.email}" id="exampleInputEmail1" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
<%--                                新增--%>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增
                        </button>
<%--                                 重置--%>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置
                        </button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>