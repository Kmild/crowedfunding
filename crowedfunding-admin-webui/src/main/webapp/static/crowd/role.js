// 执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {

    // 1.获取分页数据
    let pageInfo = getPageInfoFromRemote();

    // 2.填充数据
    fillTableBody(pageInfo);

}

// 发送请求，远程获取服务器端数据
function getPageInfoFromRemote() {

    // 1. 发送ajax请求获取数据
    let resAjax = null;
    $.ajax({
        url: "role/get/page/info.json",
        type: "post",
        data: {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        async: false,  // 同步请求 -->发送ajax请求，在这个期间等待服务器处理请求，等待期间客户端不能做任何处理，当ajax请求执行完毕才会继续执行其他代码
        dataType: "json",
        success: function (res) {
            resAjax = res;
        }
    });

    console.log(resAjax);

    // // 2. 处理ajax不成功的请求
    // var ajaxCode = resAjax.status;
    // if (ajaxCode != 200) {
    //     layer.msg(ajaxCode + "原因：" + resAjax.statusText);
    //     return null;
    // }
    //
    // 3.处理ajax成功的请求

    // 3.0 获取服务器响应的json对象


    // 3.1 处理响应不成功的请求
    let resStatue = resAjax.statue;
    if (resStatue != "success") {
        layer.msg(resAjax.message);
        return null;
    }

    // 3.1 否则成功,返回响应对象中的pageInfo数据
    return resAjax.data;

}

// 填充表格数据
function fillTableBody(pageInfo) {

    // 填充表格之前应该先清空原先添加了的数据
    $("#rolePageBody").empty();
    // 没有搜索结果时不显示导航条
    $("#Pagination").empty();

    // 判断数据pageInfo是否有效
    if (!pageInfo || pageInfo.list == null || pageInfo.list.length <= 0) {  // 数据无效！
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }

    // 填充表格数据

    for (let i = 0; i < pageInfo.list.length; i++) {
        // 1.获取pageInfo中的数据
        let roleName = pageInfo.list[i].name;
        let roleId = pageInfo.list[i].id;

        // 2.生成td单元格
        let numberTd = "<td>" + (i + 1) + "</td>";
        let checkboxTd = "<td><input type='checkbox' class='itemBox' id='"+ roleId +" '></td>";
        let roleNameTd = "<td>" + roleName + "</td>";
        let checkBtn = "<button type='button' id='" + roleId + "' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
        // 为此动态生成的按钮绑定单击响应事件   为此按钮设置id属性以便后续直接通过 this.id 获取实际的（数据库）id值
        let pencilBtn = "<button type='button' id='"+ roleId + "' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        let removeBtn = "<button type='button' id='"+ roleId + "' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        let buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        let tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);

        // 3.生成导航栏
        generateNav(pageInfo);
    }

}

// 生成分页页码导航条
function generateNav(pageInfo) {

    // 获取总记录数
    let totalRecord = pageInfo.total;
    // 声明相关属性
    let properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }
    // 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);

}

// 翻页页码的回调函数
function paginationCallBack(pageIndex, jQuery) {

    // 1. 修改页码
    window.pageNum = pageIndex + 1;

    // 2.调用分页函数
    generatePage();  /**paginationCallBack在generateNav中被调用，generateNav又是通过generatePage调用的，
     即generatePage会调用paginationCallBack
     但是paginationCallBack在generateNav中被调用  与 generatePage调用paginationCallBack并不在一个线程，
     它们是异步的。**/

    // 3.取消页码的超链接默认行为
    return false;

}

// 确认批量删除
function showConfirmModal(roleList) {
    // 弹出提示框
    $("#roleRemoveModel").modal("show");

    // 清除之前的数据
    $("#removeDiv").empty();

    // 设置全局变量保存id信息
    window.roleList = [];
    // 显示待删数据
    for (let i = 0; i < roleList.length; i++) {
        window.roleList.push(roleList[i].roleId);
        let roleName = roleList[i].roleName;
        $("#removeDiv").append($("<div></div>").append(roleName));
    }

}

// 装配role的auth的树形结构
function fillAuthTree() {

    // 1.发送ajax请求获取全部auth数据
    let authData = null;
    $.ajax({
        url: 'assign/get/all/auth.json',
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (res) {
            if (res.statue = 'success') {
                authData = res.data;
            } else {
                layer.msg("错误1!:" + res.message);
            }
        },
        error: function (res) {
            layer.msg("错误2！" + res.statue);
        }
    });

    if (authData == null) {
        return;
    } else {
    // 2.装配数据到zTree --> 可以将数据直接交给ztree进行组装（setting中设置）
          // 2.1配置 zTree的 setting
        let setting = {
            "data":
                {
                    "simpleData": {
                        // 开启简单 JSON 功能
                        "enable": true,
                        // 使用 categoryId 属性关联父节点，不用 默认的 pId 了
                        "pIdKey": "categoryId"
                    },
                    "key": {
                        // 使用 title 属性显示节点名称，不用 默认的 name 作为属性名了
                        "name": "title"
                    }
                },
            "check": {
                "enable": true //显示checkbox/radio (默认设置此属性显示checkbox)
            }
        };
        // 2.2 生成树形结构   <ul id="authTreeDemo" class="ztree"></ul>
        $.fn.zTree.init($("#authTreeDemo"), setting, authData);

        // 获取zTreeObj对象
        let zTreeObj= $.fn.zTree.getZTreeObj("authTreeDemo");

        // 2.3 默认展开树形结构
        zTreeObj.expandAll(true);

     // 3. 使role的authTree默认勾选已有的角色
        // 3.1 发送请求 获取role所有已有角色的authIdList
        let authIdList = null;
        $.ajax({
            url :'assign/get/auth/by/roleId.json',
            type:'post',
            data:{
                roleId:window.roleId
            },
            dataType:'json',
            async:false,
            success: function (res) {
                if (res.statue = 'success') {
                    authIdList = res.data;
                } else {
                    layer.msg("错误3!:" + res.message);
                }
            },
            error: function (res) {
                layer.msg("错误4！" + res.statue);
            }
        });

        if(authIdList == null || authIdList.length < 1){
            return;
        }else {
        // 3.2  把树形结构中authIdList中对应的节点进行勾选 （节点是auth的json对象）
            for (let i = 0; i < authIdList.length; i++) {
                // 拿到id为authIdList[i]的节点
                let node=  zTreeObj.getNodeByParam("id",authIdList[i]);

                // 设置该节点被选中
                let checked=true; //表示节点勾选
                let checkTypeFlag = false; //表示子父节点不联动，避免不该的勾选

                zTreeObj.checkNode(node,checked,checkTypeFlag);
            }

        }

    }

}