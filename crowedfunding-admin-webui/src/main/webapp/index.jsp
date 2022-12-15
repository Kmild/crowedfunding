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

    <script type="text/javascript" src="static/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="static/layer/layer.js"></script>

</head>
<body>
<P>HELLO</P>

<a href="test/ssm.html">测试ssm整合环境</a>

<button id="btn1">点我呀</button>
<button id="btn2">点我弹框</button>

<a href="admin/to/login.html" >管理员登录</a>


<script type="text/javascript">


    $(function () {
        $("#btn1").click(function () {
            var array=[2,12,53,25];
            //将JSON数组转换成JSON字符串 才能发请求 （JSON对象也需要转换）
            var requestBody=JSON.stringify(array);
            $.ajax({
                type:"POST",
                url: "send/array.html",
                data:requestBody,
                contentType:"application/json;charset=UTF-8", //设置请求体的内容类型
                // 如果请求的Content-Type设置为application/x-www-form-urlencoded，
                // 那么这个Post请求被认为是HTTP POST表单请求，参数出现在form data；
                // 其他情况如使用原生AJAX的POST请求如果不指定请求头Request Header，
                // 默认使用的Content-Type是text/plain;charset=UTF-8，或者application/json ，参数出现在Request payload块。
                dataType:'json',
                success: function (res) {
                    alert(res);
                },
                error: function (res) {
                    alert(res);
                }
            });
        });

        $("#btn2").click(function () {
            layer.msg("西内！");
            layer.alert("西内！！！")
        });


    });


</script>
</body>
</html>
