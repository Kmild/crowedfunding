<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="commons/include-head.jsp" %>
<link rel="stylesheet" href="static/ztree/zTreeStyle.css">
<script type="text/javascript" src="static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="static/crowd/menu.js"></script>
<script type="text/javascript">
    $(function () {
        // 生成主菜单栏
        generateTree();

        // 为追加的按钮组分别添加点击事件
        // 1.添加
        // 1.1弹出模态框
        $("#treeDemo").on("click", ".addBtn", function () {

            // 弹出模态框
            $("#menuAddModal").modal("show");

            // 保存当前节点为新增节点的父节点
            window.pid = this.id;

            //取消超链接的默认行为
            return false;

        });

        // 1.2保存数据到后台
        $("#menuSaveBtn").click(function () {

            // 收集表单中用户输入的数据
            let name = $.trim($("#menuAddModal [name=name]").val());
            let url = $.trim($("#menuAddModal [name=url]").val());

            let icon = $("#menuAddModal [name=icon]:checked").val();  // 单选框要定位到“被选中”的那一个

            // 发送ajax请求
            $.ajax({
                url: 'menu/do/save.json',
                type: 'post',
                data: {
                    "name": name,
                    "url": url,
                    "icon": icon,
                    "pid": window.pid
                },
                dataType: 'json',
                success: function (res) {
                    if (res.statue == 'success') {
                        layer.msg("添加成功！");

                        // 关闭模态框
                        $("#menuAddModal").modal("hide");

                        // 清理数据
                        $("#menuResetBtn").click(); // 不传参数默认为触发点击

                        //重新加载主菜单页面 注意：不能放在外面，只有等操作成功后才执行页面刷新，放在外面这两个操作是异步的
                        generateTree();
                    } else {
                        layer.msg("添加失败1！" + res.message);
                    }
                },
                error: function (res) {
                    layer.msg("添加失败2！" + res.statusText);
                }
            });

        });

        // 2.保存
        // 2.1 弹出模态框
        $("#treeDemo").on("click", ".editBtn", function () {

            // 弹出模态框
            $("#menuEditModal").modal("show");

            // 保存当前节点的id值 用于发送ajax请求根据id更新数据
            window.id = this.id;

            // 利用ztree的函数 根据部分已知信息获取当前节点的所有数据
            let key = "id";  //需要获取的节点的已知信息
            let value = window.id;
            let curNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam(key, value);

            //回显数据
            $("#menuEditModal [name=name]").val(curNode.name);
            $("#menuEditModal [name=url]").val(curNode.url);

            // radio和checkbox回显时：需要给val()传一个数组，会根据数组中的值勾选对应的选项
            $("#menuEditModal [name=icon]").val([curNode.icon]);

            //取消超链接的默认行为
            return false;

        });

        // 2.2 执行更新
        $("#menuEditBtn").click(function () {

            // 收集表单中用户输入的数据
            let name = $.trim($("#menuEditModal [name=name]").val());
            let url = $.trim($("#menuEditModal [name=url]").val());

            let icon = $("#menuEditModal [name=icon]:checked").val();  // 单选框要定位到“被选中”的那一个

            // 发送ajax请求
            $.ajax({
                url: 'menu/do/update.json',
                type: 'post',
                data: {
                    "name": name,
                    "url": url,
                    "icon": icon,
                    "id": window.id
                },
                dataType: 'json',
                success: function (res) {
                    if (res.statue == 'success') {
                        layer.msg("修改成功！");

                        // 关闭模态框
                        $("#menuEditModal").modal("hide");

                        //重新加载主菜单页面 注意：不能放在外面，只有等操作成功后才执行页面刷新，放在外面这两个操作是异步的
                        generateTree();
                    } else {
                        layer.msg("修改失败1！" + res.message);
                    }
                },
                error: function (res) {
                    layer.msg("修改失败2！" + res.statusText);
                }
            });

        });

        // 3.删除
        // 3.1 弹出模态框
        $("#treeDemo").on("click", ".removeBtn", function () {

            // 弹出模态框
            $("#menuConfirmModal").modal("show");

            // 保存当前节点的id值 用于发送ajax请求根据id删除数据
            window.id = this.id;

            // 利用ztree的函数 根据部分已知信息获取当前节点的所有数据
            let key = "id";  //需要获取的节点的已知信息
            let value = window.id;
            let curNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam(key, value);

            // 填充提示信息文本值
            $("#removeNodeSpan").html("【<i class='" + curNode.icon + "'></i>" + curNode.name + "】");

            //取消超链接的默认行为
            return false;
        });

        // 3.2 执行删除
        $("#confirmBtn").click(function () {

            // 发送ajax请求
            $.ajax({
                url: 'menu/do/remove.json',
                type: 'post',
                data: {
                    "id": window.id
                },
                dataType: 'json',
                success: function (res) {
                    if (res.statue == 'success') {
                        layer.msg("删除成功！");

                        // 关闭模态框
                        $("#menuConfirmModal").modal("hide");

                        //重新加载主菜单页面 注意：不能放在外面，只有等操作成功后才执行页面刷新，放在外面这两个操作是异步的
                        generateTree();
                    } else {
                        layer.msg("删除失败1！" + res.message);
                    }
                },
                error: function (res) {
                    layer.msg("删除失败2！" + res.statusText);
                }
            });

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
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
<%@include file="models/menu-model.jsp" %>
</body>
</html>