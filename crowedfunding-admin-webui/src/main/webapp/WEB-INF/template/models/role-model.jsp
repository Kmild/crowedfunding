<%--
  Created by IntelliJ IDEA.
  User: Yanshiwei
  Date: 2022/11/3
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--新增的模态框--%>
<div id="roleAddModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <form  class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                <input type="text" id="saveRolInput" class="form-control" name="username" id="inputSuccess4" placeholder="请输入角色名称" autofocus>
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveRolBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<%--更新的模态框--%>
<div id="roleEditModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <form  class="form-signin" role="form">
                    <div class="form-group has-success has-feedback">
                        <input type="text" id="editRoleInput" class="form-control" name="username"  placeholder="请输入角色名称" autofocus>
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="editRolBtn" type="button" class="btn btn-primary">修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--删除的模态框--%>
<div id="roleRemoveModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                <form  class="form-signin" role="form">
                    <p>确认删除以下角色？</p>
                    <div  id="removeDiv" class="form-group has-success has-feedback">

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="removeRolBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--分配权限--%>
<div id="assignAuthModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">尚筹网系统弹窗</h4>
            </div>
            <div class="modal-body">
                    <div class="panel panel-default">
                        <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限分配列表<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                        <div class="panel-body">
                            <ul id="authTreeDemo" class="ztree">
                            </ul>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button id="assignAuthBtn" type="button" class="btn btn-primary">确认分配</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->