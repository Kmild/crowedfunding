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
            <button id="asyncBtn"></button>
        </div>

    </div>
</div>

</body>
</html>