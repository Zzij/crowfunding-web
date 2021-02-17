<%--
  Created by IntelliJ IDEA.
  User: zzj
  Date: 2020/7/7
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<%@include file="/WEB-INF/include_head.jsp" %>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script src="ztree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="myfunction/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        //1. 分页参数初始化
        window.pageNum=1;
        window.pageSize=10;
        window.keyword="";
        generatePage();

        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();
            generatePage();
        });

        //点击新增打开模态框
        $("#showModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        //保存角色按钮
        $("#saveRoleBtn").click(function () {
            var roleName = $.trim($("#addModal [name=roleName]").val());
            $.ajax({
                "url": "role/save",
                "type": "post",
                "data":{
                    "roleName": roleName
                },
                "datatype":"json",
                "success":function (response) {
                    var code = response.code;
                    if(code == 200){
                        layer.msg("操作成功")
                    }else{
                        layer.msg("操作失败，" + response.msg)
                    }
                    //重新加载页面数据
                    generatePage();
                },
                "error": function (response) {
                    layer.msg("请求失败," + response.statusText)
                }
            });
            //隐藏模态框
            $("#addModal").modal("hide");
            //清除内容
            $("#addModal [name=roleName]").val("");
        });

        //铅笔按钮 打开模态框
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            $("#editModal").modal("show");

            //获取当前行的rolename,当前td的父标签td的前一个td中的文本内容
            var roleName = $(this).parent().prev().text();
            var roleId = this.id;

            window.roleId = roleId;
            $("#editModal [name=roleName]").val(roleName);

        });

        //更新模态框按钮
        $("#updateRoleBtn").click(function () {

            var roleName = $("#editModal [name=roleName]").val();

            $.ajax({
                "url": "role/update",
                "type":"post",
                "data":{
                    "id": window.roleId,
                    "name":roleName
                },
                "dataType":"json",
                "success":function (response) {
                    var code = response.code;
                    if(code == 200){
                        layer.msg("操作成功")
                    }else{
                        layer.msg("操作失败，" + response.msg)
                    }
                    //重新加载页面数据
                    generatePage();
                },
                "error":function (response) {
                    layer.msg("请求失败," + response.statusText)
                }
            });
            $("#editModal").modal("hide");
        });

        //删除单条
        $("#rolePageBody").on("click", ".removeBtn", function () {

            var roleName = $(this).parent().prev().text();

            var roleArray = [{
                roleId: this.id,
                roleName: roleName
            }];

            showConfirmModal(roleArray);

        });

        //全选框
        $("#totalBox").click(function () {
            var checked = this.checked;
            $(".itemBox").prop("checked",checked);
        });

        // 点击确认删除模态框的按钮 执行删除
        $("#removeRoleBtn").click(function () {

            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                "url": "role/remove",
                "type": "post",
                "data": requestBody,
                "contentType":"application/json;charset=utf-8",
                "dataType": "json",
                "success":function (response) {
                    var code = response.code;
                    if(code == 200){
                        layer.msg("操作成功")
                    }else{
                        layer.msg("操作失败，" + response.msg)
                    }
                    //重新加载页面数据
                    generatePage();
                },
                "error":function (response) {
                    layer.msg("请求失败," + response.statusText)
                }
            });
            $("#confirmModal").modal("hide");
            $("#totalBox").prop("checked", false);
        });

        //取消部分全选
        $("#rolePageBody").on("click", ".itemBox", function () {
            var checkedBoxCount = $(".itemBox:checked").length;
            var totalBoxCount = $(".itemBox").length;

            $("#totalBox").prop("checked", checkedBoxCount == totalBoxCount);

        });

        //批量删除
        $("#batchRemove").click(function () {

            var roleArray = [];
            //遍历选择的复选框
            $(".itemBox:checked").each(function () {
                var roleId = this.id;
                var roleName = $(this).parent().next().text();
                roleArray.push({
                    "roleId": roleId,
                    "roleName": roleName
                });
            });

            if(roleArray.length == 0){
                layer.msg("请至少选择一个删除");
                return;
            }

            showConfirmModal(roleArray);
        });

        //分配权限
        $("#rolePageBody").on("click", ".checkBtn", function () {

            window.roleId = this.id;

            $("#assignModal").modal("show");
            //装配树形结构
            fillAuthTree();
        });

        $("#saveRoleAuthBtn").click(function(){

            var authIdArray = [];

            var zTreeObj = $.fn.zTree.getZTreeObj("authTree");

            var checkedNodes = zTreeObj.getCheckedNodes(true);

            for(var i = 0; i < checkedNodes.length; i++){
                var authId = checkedNodes[i].id;

                authIdArray.push(authId);
            }

            var requestBody = {
                "authIdArray": authIdArray,
                "roleId": [window.roleId]
            };

            requestBody = JSON.stringify(requestBody);

            $.ajax({
                "url": "assign/do/role/assign",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=utf-8",
                "dataType": "json",
                "success": function(response){
                    if(response.code == 200){
                        layer.msg("操作成功");
                    }else{
                        layer.msg("操作失败，" + response.msg);
                    }
                },
                "error": function(response){
                    layer.msg("请求失败," + response.statusText);
                }
            });

            $("#assignModal").modal("hide");
        });

    });
</script>
<body>
<%@include file="/WEB-INF/include_nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include_sidebar.jsp" %>
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
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemove" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" id="showModalBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="totalBox" type="checkbox"></th>
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
        </div>
    </div>
</div>

<%@include file="modal-role-add.jsp"%>
<%@include file="modal-role-edit.jsp"%>
<%@include file="modal-role-confirm.jsp"%>
<%@include file="modal-role-assign-auth.jsp"%>
</body>
</html>
