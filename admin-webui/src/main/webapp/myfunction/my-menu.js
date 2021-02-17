function generateTree() {

    $.ajax({
        "url": "menu/whole/tree",
        "type": "post",
        "datatype":"json",
        "success":function (response) {
            var code = response.code;
            if(code == "200"){
                //对于树形的设置
                var setting = {
                    "view":{
                        "addDiyDom": myAddDiyDom,
                        "addHoverDom": myAddHoverDom,
                        "removeHoverDom": myRemoveHoverDom

                    },
                    "data":{
                        "key":{
                            "url":"maomi"
                        }
                    }
                };
                //菜单数据
                var zNodes = response.data;

                $.fn.zTree.init($("#treeDemo"),setting, zNodes);
            }else{
                layer.msg(response.msg);
            }
        }
    });
}

//修改默认图标
function myAddDiyDom(treeId, treeNode) {

    //根据id的生成规则拼接span的id
    var spanId = treeNode.tId + "_ico";
    $("#" + spanId).removeClass().addClass(treeNode.icon);

}

//鼠标移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {

    var btnGroupId = treeNode.tId + "_btnGroup";

    //防止出现多个提示
    if($("#" + btnGroupId).length > 0){
        return;
    }

    var editBtn = "<a id='"  +  treeNode.id + "' class=\"editBtn btn btn-info dropdown-toggle btn-xs\" style=\"margin-left:10px;padding-top:0px;\" title=\"修改节点\">&nbsp;&nbsp;<i class=\"fa fa-fw fa-edit rbg \"></i></a>";
    var addBtn = "<a id='"  +  treeNode.id + "' class=\"addBtn btn btn-info dropdown-toggle btn-xs\" style=\"margin-left:10px;padding-top:0px;\" title=\"新增节点\">&nbsp;&nbsp;<i class=\"fa fa-fw fa-plus rbg \"></i></a>";
    var removeBtn = "<a id='"  +  treeNode.id + "' class=\"removeBtn btn btn-info dropdown-toggle btn-xs\" style=\"margin-left:10px;padding-top:0px;\" title=\"删除节点\">&nbsp;&nbsp;<i class=\"fa fa-fw fa-times rbg \"></i></a>";

    var level = treeNode.level;
    var btnHtml = "";
    if(level == 0){
        btnHtml = addBtn;
    }

    if(level == 1){
        btnHtml = addBtn + " " + editBtn;
        var length = treeNode.children.length;
        if(length == 0){
            btnHtml = btnHtml + " " + removeBtn;
        }
    }
    if(level == 2){
        btnHtml = editBtn + " " + removeBtn;
    }

    var anchorId = treeNode.tId + "_a";

    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHtml + "</span>")
}

//鼠标离开节点范围时移除按钮组
function myRemoveHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGroup";
    $("#" + btnGroupId).remove();
}