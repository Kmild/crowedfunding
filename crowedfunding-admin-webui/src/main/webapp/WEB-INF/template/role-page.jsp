<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="commons/include-head.jsp" %>
<link rel="stylesheet" href="static/ztree/zTreeStyle.css">
<script type="text/javascript" src="static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="static/crowd/role.js" charset="UTF-8"></script>
<link rel="stylesheet" href="static/pagination-lib/pagination.css"/>
                    <%--此文件重复引入--%>
<%--<script type="text/javascript" src="static/pagination-lib/jquery.min.js"></script>--%>
<script type="text/javascript" src="static/pagination-lib/jquery.pagination.js"></script>
<script type="text/javascript">

    $(function () {
        // 1. 为分页准备初始化参数
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 2.执行分页
        generatePage();
        // getPageInfoFromRemote();

        // 3. 给查询按钮绑定点击响应事件
        $("#searchBtn").click(function () {

            // 3.1 获取 keyword 并将其赋值给全局变量
            window.keyword = $("#searchInput").val();

            // 3.2 调用分页函数
            generatePage();

        });

        // 4.弹出添加的模态框
        $("#showAddModelBtn").click(function () {

            // $("#addModel").modal("show");
        });

        // 5.执行添加的保存
        $("#saveRolBtn").click(function () {

            // 获取用户输入的内容
           let roleName = $.trim($("#saveRolInput").val());

           // 发送ajax请求保存时数据
            $.ajax({
                url : 'role/do/add.json',
                type : 'post',
                data : {
                    "name" : roleName
                },
                dataType : 'json',
                success :function (res) {
                    var result = res.statue;

                    if(result == 'success'){
                        layer.msg('操作成功！');

                        // 重新加载分页数据
                        window.pageNum = 999999;
                        generatePage();
                    }else {
                        layer.msg('操作失败！'+res.message);
                    }

                },
                error : function (res) {
                    layer.msg("操作失败："+res.statusText);
                }
            });

            // 关闭模态框
            $("#roleAddModel").modal('hide');

            // 清理模态框的内容
            $("#saveRolInput").val("");
        });

        // 6.执行更新的页面显示
        $("#rolePageBody").on("click",".pencilBtn",function () {  // 为pencilBtn绑定click事件

            // 打开模态框
            $("#roleEditModel").modal("show");

            // 获取表格当前的数据并回显
            let roleName = $(this).parent().prev().text();
            $("#editRoleInput").val(roleName);

            //获取当前角色的id
            window.roleId = this.id;

        });

        // 7.执行更新的数据保存
        $("#editRolBtn").click(function () {
            //发送ajax请求保存数据
            let roleName=$("#editRoleInput").val(); //发送请求的请求参数需要到外面获取

            $.ajax({
                url:'role/do/eidt.json',
                type:'post',
                data: {
                    "id" :window.roleId,
                    "name" :roleName
                },
                dataType: 'json',
                success:function (res) {
                    if(res.statue == 'success'){
                        layer.msg("更改成功！");
                        generatePage();
                        // 关闭模态框
                        $("#roleEditModel").modal("hide");
                    }else {
                        layer.msg(res.statue+",保存失败："+res.message);
                    }
                },
                error:function (res) {
                    layer.msg(res.statue+",保存失败："+res.statusText);
                }
            });

        });


        // 8.单行删除
        $("#rolePageBody").on("click",".removeBtn",function () {
            let role = [{roleId:this.id,roleName:$(this).parent().prev().text()}];
            showConfirmModal(role);
        });

        // 9.多行删除
            // 9.1给总的checkbox绑定单击响应事件 全选
        $("#mainCheckbox").click(function () {
            // 获取当前状态
            let curStatus = this.checked;

            // 用当前多选框的状态设置其他多选框
            $(".itemBox").prop("checked",curStatus);

        });
            // 9.2 全选、全不选的反向操作
            $("#rolePageBody").on("click",".itemBox",function () {
                // 获取当前已经选中的 .itemBox 的数量
                let checkBoxsCount = $(".itemBox:checked").length;

                // 获取全部 .itemBox 的数量
                let allCheckBoxsCount = $(".itemBox").length;

                //设置总选框的状态
                $("#mainCheckbox").prop("checked",checkBoxsCount == allCheckBoxsCount);

            });

            // 9.3 给总删除按钮绑定点击事件
            $("#showRemoveModelBtn").click(function () {
                let roles = [];
                // 遍历已经选中的多选框
                $(".itemBox:checked").each(function () {
                    let roleId = this.id;
                    let roleName = $(this).parent().next().text();

                    roles.push({
                        "roleId":roleId,
                        "roleName":roleName
                    });
                });

                if(roles.length == 0){
                    layer.msg("可恶！乱点。");
                    return;
                }

                showConfirmModal(roles);
            });


        // 10.执行删除
        $("#removeRolBtn").click(function () {

            // 把id信息打包成json格式发送
            let roleIds =JSON.stringify(window.roleList);

            // 发送ajax请求执行删除
            $.ajax({
                url:'role/do/remove.json',
                type:'post',
                data:roleIds,
                contentType:'application/json;charset=UTF-8',
                dataType:'json',
                success:function (res) {
                    if(res.statue == 'success'){
                        layer.msg("删除成功！");
                        generatePage();

                    }else {
                        layer.msg(res.statue+",删除失败1："+res.message);
                    }
                },
                error:function (res) {
                    layer.msg(res.statue+",删除失败2："+res.statusText);
                }

            });
            // 关闭模态框
            $("#roleRemoveModel").modal("hide");

            // 取消全选删除后全选总框的选中状态
            $("#mainCheckbox").prop("checked",false);

        });

        // 11.分配角色
         // 11.1弹出模态框
        $("#rolePageBody").on("click",".checkBtn",function () {

            // 弹出模态框
           $("#assignAuthModel").modal("show");

           // 获取当前role的id并通过全局变量传入到请求参数中获取role的auth信息
            window.roleId = this.id;

           // 在模态框中装配auth的树形结构
            fillAuthTree();

        });

        // 11.2执行分配
        $("#assignAuthBtn").click(function () {

            // [1]获取所有分配的auth的id
                // 得到zTreeObj对象
                let zTreeObj= $.fn.zTree.getZTreeObj("authTreeDemo");
                // 得到所有被选中的节点
                let authNodes=zTreeObj.getCheckedNodes(true);
                // 保存所有节点的id到authIds中
                let authIds=[];
                for (let i = 0; i < authNodes.length; i++) {
                    authIds.push(authNodes[i].id);
                }
                // 获取roleId 字符串
                let roleIdAtr =$.trim(window.roleId);
                // 转换成数字
                let roleIdNum = parseInt(roleIdAtr);

            // [2]发送请求保存数据
              // 将数据打包为json对象
              let requestBody ={
                    "roleId":[roleIdNum],
                    "authIds":authIds
              };
              requestBody=JSON.stringify(requestBody);
              // 发送请求
              $.ajax({
                  url:'assign/do/role/auth.json',
                  type:'post',
                  data:requestBody,
                  contentType: 'application/json;charset=UTF-8',
                  dataType:'json',
                  success: function (res) {
                      if (res.statue = 'success') {
                          layer.msg("分配成功！");
                      } else {
                          layer.msg("分配失败1!:" + res.message);
                      }
                  },
                  error: function (res) {
                      layer.msg("分配失败2！" + res.statue);
                  }
              });

            // 关闭模态框
            $("#assignAuthModel").modal("hide");

        });



    })
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
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="searchInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="showRemoveModelBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" data-toggle="modal" data-target="#roleAddModel" class="btn btn-primary" style="float:right;"
                            id="showAddModelBtn"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="mainCheckbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>l

    </div>
</div>
<%@include file="models/role-model.jsp"%>
</body>
</html>