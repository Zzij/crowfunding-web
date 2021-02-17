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

<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">删除角色确认</h4>
            </div>
            <div class="modal-body">
                <h4>请确认是否需要删除以下角色</h4>
                <span id="roleNameSpan"></span>
            </div>
            <div class="modal-footer">
                <button id="removeRoleBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
