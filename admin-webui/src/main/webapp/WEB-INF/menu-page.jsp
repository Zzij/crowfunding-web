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
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/doc.min.css">
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="myfunction/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        generateTree();

        //菜单点击增加按钮
        $("#treeDemo").on("click", ".addBtn", function () {

            //当前节点的pid保存为全局变量
            window.pid=this.id;
            //打开模态框
            $("#menuAddModal").modal("show");
            return false;
        });

        //保存节点按钮
        $("#menuSaveBtn").click(function(){
            //手机表单数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            //单选按钮要定位到选中的那个
            var icon = $("#menuAddModal [name=icon]:checked").val();

            $.ajax({
                "url": "menu/save",
                "type": "post",
                "data":{
                    "pid": window.pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function(response){
                    var result = response.code;
                    if(result == 200){
                        layer.msg("操作成功");
                        generateTree();
                    }else{
                        layer.msg("操作失败 " + response.msg);
                    }
                },
                "error": function(response){
                    layer.msg(response.status + " " + response.statusText);
                }

            });
            $("#menuAddModal").modal("hide");

            $("#menuResetBtn").click();
        });

        //菜单点击修改按钮
        $("#treeDemo").on("click", ".editBtn", function () {

            //当前节点的pid保存为全局变量
            window.id=this.id;
            //打开模态框
            $("#menuEditModal").modal("show");

            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            var key = "id";
            var value=window.id;

            var currentNode = zTreeObj.getNodeByParam(key, value);

            //回显表单
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            //根据id查询
            return false;
        });

        $("#menuEditBtn").click(function(){
                    //手机表单数据
                    var name = $.trim($("#menuEditModal [name=name]").val());
                    var url = $.trim($("#menuEditModal [name=url]").val());
                    //单选按钮要定位到选中的那个
                    var icon = $("#menuEditModal [name=icon]:checked").val();

                    $.ajax({
                        "url": "menu/update",
                        "type": "post",
                        "data":{
                            "id": window.id,
                            "name": name,
                            "url": url,
                            "icon": icon
                        },
                        "dataType": "json",
                        "success": function(response){
                            var result = response.code;
                            if(result == 200){
                                layer.msg("操作成功");
                                generateTree();
                            }else{
                                layer.msg("操作失败 " + response.msg);
                            }
                        },
                        "error": function(response){
                            layer.msg(response.status + " " + response.statusText);
                        }

                    });
                    $("#menuEditModal").modal("hide");
                });

        //删除菜单
        $("#treeDemo").on("click", ".removeBtn", function () {

                    //当前节点的pid保存为全局变量
                    window.id=this.id;
                    //打开模态框
                    $("#menuConfirmModal").modal("show");

                    var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

                    var key = "id";
                    var value=window.id;

                    var currentNode = zTreeObj.getNodeByParam(key, value);

                    $("#removeNodeSpan").text(currentNode.name);

                    //根据id查询
                    return false;
                });

        $("#confirmBtn").click(function(){
            $.ajax({

                "url": "menu/remove",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "dataType": "json",
                "success": function(response){
                    var result = response.code;
                    if(result == 200){
                        layer.msg("操作成功");
                        generateTree();
                    }else{
                        layer.msg("操作失败 " + response.msg);
                    }
                },
                "error": function(response){
                    layer.msg(response.status + " " + response.statusText);
                }

            });

            $("#menuConfirmModal").modal("hide");

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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="modal-menu-add.jsp" %>
<%@include file="modal-menu-confirm.jsp" %>
<%@include file="modal-menu-edit.jsp" %>
</body>
</html>
