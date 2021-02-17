<%--
  Created by IntelliJ IDEA.
  User: zzj
  Date: 2020/7/20
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div id="assignModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">角色权限设置</h4>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                            <div class="panel-body">
                                <ul id="authTree" class="ztree"></ul>
                            </div>
                        </div>
            <div class="modal-footer">
                <button id="saveRoleAuthBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
