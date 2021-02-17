//声明模态框 显示确认模态框
function showConfirmModal(roleArray) {

    $("#confirmModal").modal("show");

    $("#roleNameSpan").empty();

    window.roleIdArray = [];
    for (var i = 0; i < roleArray.length; i++){
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameSpan").append(roleName + "<br/>");
        window.roleIdArray.push(role.roleId);
    }
}


//分页
function generatePage() {
    getPageInfoRemote();

}

//从后端获取数据
function getPageInfoRemote() {
    $.ajax({
        "url": "role/get/info",
        "type": "post",
        "data":{
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "dataType": "json",
        "success": function (response) {
            var code = response.code;
            if(code != 200){
                layer.msg("服务器端服务调用异常: " + response.msg);
                return null;
            }
            var pageInfo = response.data;
            fillTableBody(pageInfo);
        },
        "error": function (response) {
            layer.msg("服务器端服务调用异常: " + response.status);
            return null;
        }
    });
}

//获取到的数据 填充页面
function fillTableBody(pageInfo) {

    $("#rolePageBody").empty();
    $("#Pagination").empty();

    if(pageInfo == null || pageInfo.list == null || pageInfo.list.length == 0){
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉没有查询到你要的数据</td></tr>")
        return;
    }

    for (var i = 0; i < pageInfo.list.length; i++){
        var role = pageInfo.list[i];

        var roleId = role.id;
        var roleName = role.name;

        var numberId = "<td>" + (i+1) + "</td>"

        /**
         *
         <td><input type="checkbox"></td>
         <td>CMO / CMS - 配置管理员</td>
         <td>
         <button type="button" class="btn btn-success btn-xs"><i
         class=" glyphicon glyphicon-check"></i></button>
         <button type="button" class="btn btn-primary btn-xs"><i
         class=" glyphicon glyphicon-pencil"></i></button>
         <button type="button" class="btn btn-danger btn-xs"><i
         class=" glyphicon glyphicon-remove"></i></button>
         </td>
         */
        var checkBoxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' id='" + roleId + "' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button type='button' id='" + roleId + "' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button type='button' id='" + roleId + "' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var btnTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>"

        var tr = "<tr>" + numberId + checkBoxTd + roleNameTd + btnTd + "</tr>"
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}


//生成分页的导航栏
function generateNavigator(pageInfo) {

    //获取总记录数
    var totalRecord = pageInfo.total;

    //pagination的json数据
    var properties = {
        num_edge_entries: 3,
        num_display_entries: 4,
        callback: paginationCallBack,
        items_per_page: pageInfo.pageSize,
        current_page: pageInfo.pageNum - 1,
        prev_text: "上一页",
        next_text: "下一页"
    };
    $("#Pagination").pagination(totalRecord, properties)
}

//分页的回调方法
function paginationCallBack(pageIndex, jQuery) {

    window.pageNum = pageIndex + 1;
    generatePage();
    return false;
}

function fillAuthTree(){
    //查询auth数据
    var authResponse = $.ajax({
        "url": "assign/get/all/auth",
        "type": "post",
        "dataType": "json",
        "async": false
    });

    if(authResponse.status != 200){
        layer.msg("服务器端服务调用异常: " + authResponse.status);
        return;
    }

    var setting = {
        data: {
            simpleData: {
                enable: true,
                pIdKey: "categoryId"
            },
            key: {
                name: "title"
            }
        },
        check: {
            enable: true
        }
    };

    var authList = authResponse.responseJSON.data;
    //生成树形结构
    $.fn.zTree.init($("#authTree"), setting, authList);

    var zTreeObj = $.fn.zTree.getZTreeObj("authTree");

    zTreeObj.expandAll(true);

    //查询已分配的auth的id
    var ajaxResponse = $.ajax({
        "url": "assign/get/auth/role/id",
        "type": "post",
        "dataType": "json",
        "data": {
            "roleId": window.roleId
        },
        "async": false
    });

    if(ajaxResponse.status != 200){
            layer.msg("服务器端服务调用异常: " + authResponse.status);
            return;
    }

    var authIds = ajaxResponse.responseJSON.data;
    for(var i = 0; i < authIds.length; i++){
        var authId = authIds[i];
        var treeNode = zTreeObj.getNodeByParam("id", authId);
        //第一个参数 节点 第二个参数 设置是否勾选  第三个参数 复选框是否联动
        zTreeObj.checkNode(treeNode, true, false);
    }
    //根据id将树形结构中的对应复选框勾选
}