// 树形结构的菜单显示
function generateTree() {
    $.ajax({
        url:'menu/get/whole/tree.json',
        type:'post',
        dataType:'json',
        success:function (res) {
            if(res.statue == 'success'){
                let setting = {
                    "view":{
                        "addDiyDom":showMenuIco,
                        "addHoverDom":showHoverBtn,
                        "removeHoverDom":removeHoverBtn
                    },
                    "data":{
                        "key":{
                            "url":"maomi"
                        }
                    }
                };
                let root = res.data;
                $.fn.zTree.init($("#treeDemo"), setting, root);
            }else {
                layer.msg("失败1！"+res.message)
            }
        },
        error:function (res) {
            layer.msg("失败2！"+res.statusText);
        }
    });

}

// 生成菜单图片
function showMenuIco(treeId,treeNode) {

    console.log(treeId);
    console.log(treeNode);

    // 获取菜单图片span的id
    let spanId=treeNode.tId+'_ico';
    // 设置class属性生成菜单图片
    let select ="#"+spanId;
    console.log(select);
    $(select).removeClass()
                 .addClass(treeNode.icon);
}


// 在鼠标经过菜单栏时添加按钮组
function showHoverBtn(treeId,treeNode) {

    // 1.找到追加的位置
    // 找到附着按钮组的标签id
    let anchorId = treeNode.tId+"_a";

    // 为追加的按钮组的总span追加id使得方便删除
    let btnGrpId= treeNode.tId+"_Grp";


    // 2.追加按钮组

    // 为防止追加多个 鼠标移动时可能会出发多次而生成多个相同id的span，而每次删除只会删除一个第一个span
    if($("#"+btnGrpId).length > 0){
        return;
    }

    // 按钮组的标签结构：<span><a><i></i></a> <a><i></i></a></span>

    // 准备各个按钮的 HTML 标签
    let addBtn = "<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs addBtn' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    let removeBtn = "<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs removeBtn' style='margin-left:10px;padding-top:0px;' href='#' title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    let editBtn = "<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs editBtn' style='margin-left:10px;padding-top:0px;' href='#' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    // 判断级别
    let level = treeNode.level;

    // 声明按钮组
    let btnHtml = "";

    if(level == 0){  // 根节点级别 --> 只能添加
        btnHtml = addBtn;
    }else if(level == 1){ // 级别为 1:分支节点 -->添加子节点、修改 ,如果没有子节点可以修改
        btnHtml = addBtn +" "+editBtn ;

        if(treeNode.children.length == 0){  // 没有子节点可以修改
            btnHtml = btnHtml + " " + removeBtn;
    }

    }else {  // 为叶子节点 --> 修改 、删除
        btnHtml = removeBtn +" " +editBtn;
    }

    $("#"+anchorId).after("<span id='"+btnGrpId+"'>"+btnHtml+"</span>");

}

// 在鼠标离开时取消按钮组
function removeHoverBtn(treeId,treeNode) {

    // 找到追加的按钮组的总span的id
    let btnGrpId = treeNode.tId+"_Grp";

    // 删除此按钮组
    $("#"+btnGrpId).remove();

}


