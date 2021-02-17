<%--
  Created by IntelliJ IDEA.
  User: zzj
  Date: 2020/7/7
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cn">
<%@include file="/WEB-INF/include_head.jsp" %>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" >
    $(function () {
        initPagination();
    });
    
    function initPagination() {

        //获取总记录数
        var totalRecord = ${requestScope.pageInfo.total};

        //pagination的json数据
        var properties = {
            num_edge_entries: 3,
            num_display_entries: 4,
            callback: pageSelectCallback,
            items_per_page: ${requestScope.pageInfo.pageSize},
            current_page: ${requestScope.pageInfo.pageNum - 1},
            prev_text: "上一页",
            next_text: "下一页"
        };
        $("#Pagination").pagination(totalRecord, properties)
    }

    function pageSelectCallback(page_index, jQuery) {
        var pageNum = page_index + 1;
        window.location.href = "admin/get/page?pageNum=" + pageNum + "&keyword=${param.keyword}";
        return false;
    }
</script>
<body>
<%@include file="/WEB-INF/include_nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include_sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;" method="post" action="admin/get/page">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" name="keyword" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <a href="admin/do/add/page" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉没有查询到你要的数据</td>
                                </tr>
                            </c:if>

                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${ requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/to/assign/role/page?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                            <a href="admin/do/edit/page?id=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
                                            <a href="admin/do/removeadmin/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
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
</body>
</html>
