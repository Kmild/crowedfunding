<%--
  Created by IntelliJ IDEA.
  User: Yanshiwei
  Date: 2022/10/26
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
    <script src="static/jquery/jquery-2.1.1.min.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
    <script type="text/javascript" src="static/layer/layer.js"></script>

</head>
<body>


<div class="container" style="text-align: center;">
    <h2>系统信息页面</h2>
    <%--  requestScope 保存了request域中的所有数据信息--%>
    <h4>${requestScope.exception.message }</h4>
    <button style="width: 300px;margin: 0px auto 0px auto;" id="back_btn" class="btn btn-lg btn-success btn-block" >
        返回刚才页面
    </button>
</div>


<script type="text/javascript">
    $("#back_btn").click(function () {
        layer.msg("返回啦");

        // 调用 back()方法类似于点击浏览器的后退按钮
       setTimeout(function () {
           window.history.back();
       },1000);
    });
</script>

</body>
</html>
