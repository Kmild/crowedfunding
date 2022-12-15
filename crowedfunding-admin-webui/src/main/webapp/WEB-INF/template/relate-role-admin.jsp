<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="commons/include-head.jsp" %>

<%--导入js条--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">

    $(function () {
        // 左→右
        $("#toRightBtn").click(function () {

            // select ：标签（元素）选择器
            // eq(0)  : 选择页面上的第一个该元素
            //   >    :  选择子元素
            // selected ： 表示被选中的option
            // appendTo : 将jquery对象追加到指定位置
            $("select:eq(0)>option:selected").appendTo($("select:eq(1)"));

        });

        // 右→左
        $("#toLeftBtn").click(function () {

            $("select:eq(1)>option:selected").appendTo($("select:eq(0)"));

        });

        // 提交数据时右边的select列表中的option应该全选中
        $("#submitBtn").click(function () {
            $("select:eq(1)>option").prop("selected","selected");

            // 此操作可以取消表单的默认提交
            // return false;
        });
    });

</script>

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
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                  <%--   form表单--%>
                    <form role="form" action="assign/do/assign/role/page.html" method="post" class="form-inline">
                         <%--        隐藏域数据      --%>
                         <input type="hidden" name="adminId" value="${param.adminId}">
                         <input type="hidden" name="pageNum" value="${param.pageNum}">
                         <input type="hidden" name="keyword" value="${param.keyword}">

                            <div class="form-group">
                                <label >未分配角色列表</label><br>
                                <select class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                    <c:forEach items="${requestScope.unAssignedRole}" var="role">
    <%-- 注意： <option value="将来在提交表单时一起发送给 handler 的值">在浏览器上让用户看到的数据</option> --%>
                                        <option value="${role.id}" >${role.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <ul>
                                    <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                    <br>
                                    <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                        style="margin-top:20px;"></li>
                                </ul>
                            </div>

                            <div class="form-group" style="margin-left:40px;">
                                <label >已分配角色列表</label><br>
                                <select name="assignedRoleIdList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                        <c:forEach items="${requestScope.assignedRole}" var="role">
                                            <option value="${role.id}" >${role.name}</option>
                                        </c:forEach>
                                </select>
                            </div>
<%--                            提交按钮--%>
                        <button id="submitBtn" type="submit" class="btn btn-success">
                            <i class="glyphicon glyphicon-transfer"></i> 分配
                        </button>
                    </form>

                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>