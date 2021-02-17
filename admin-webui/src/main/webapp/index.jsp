<%--
  Created by IntelliJ IDEA.
  User: zzj
  Date: 2020/7/5
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.getContextPath()}/"/>
<html>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script>
    $(function () {
        $("#btn1").click(function () {
            $.ajax({
                "url": "test/send",           //请求目标的地址
                "type": "post",                //请求方式
                "data": {                      //请求参数
                    "array": [5,8,12]
                },
                "dataType": "text",            //服务端响应的数据
                "success": function (response) {       //请求成功的回调函数
                    alert(response);
                },
                "error": function (response) {
                    alert(response);
                }
            });
        });


        $("#btn2").click(function () {
            var array = [2,5,8];
            var requestBody = JSON.stringify(array);
            $.ajax({
                "url": "test/send2",           //请求目标的地址
                "type": "post",               //请求方式
                "contentType": "application/json",
                "data": requestBody,
                "dataType": "text",            //服务端响应的数据
                "success": function (response) {       //请求成功的回调函数
                    alert(response);
                },
                "error": function (response) {
                    alert(response);
                }
            });
        });

        $("#btn3").click(function () {
            layer.msg("你好")
        });

        $("#btn3").click(function () {
            layer.msg("你好")
        });
    });
</script>
<head>
    <title>hello</title>
</head>
<body>
    <a href="test/ssm">整合测试</a>

    <br/>

    <button id="btn1">send [5,8,12] one</button>

    <br/>

    <button id="btn2">send [5,8,12] one</button>
    <br/>
    <button id="btn3">layer</button>
    <br/>
    <a href="test/error"> error</a>
</body>
</html>
