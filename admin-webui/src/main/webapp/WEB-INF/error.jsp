<%--
  Created by IntelliJ IDEA.
  User: zzj
  Date: 2020/7/6
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.getContextPath()}/"/>
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <style>

    </style>
</head>
<body>


<div class="container">

    <h1>error</h1>
    <h2>${requestScope.exception.message}</h2>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>