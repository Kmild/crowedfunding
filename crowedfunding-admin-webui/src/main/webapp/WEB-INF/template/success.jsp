<%--
  Created by IntelliJ IDEA.
  User: Yanshiwei
  Date: 2022/10/25
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
</head>
<body>
<p>SUCCESS</p>
众筹平台 - 控制面板
${requestScope.allAdmin }

</body>
</html>
