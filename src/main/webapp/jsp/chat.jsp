<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${path}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/boot/css/back.css">
    <link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${path}/jqgrid/css/jquery-ui.css">
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${path}/boot/js/bootstrap.min.js"></script>
    <script src="${path}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${path}/boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-47e47f95448848df966df1209ab41af7", //替换为您的应用appkey
        });

        goEasy.subscribe({
            channel: "cmfz", //替换为您自己的channel
            onMessage: function (message) {
                var data = message.content;
                var src = "";
                $("#in").val("");
                src += "<div class=\"alert alert-success\" role=\"alert\">" + data + "</div >",
                    $("#ltTable").prepend(src);
            },

        });

        function go() {
            goEasy.publish({
                channel: "cmfz",
                message: "倩倩小可爱：" + $("#in").val(),
            });
        }

        $(function () {
            // 初始化jgGrid插件
            $("#ltTable").jqGrid({
                url: "",
                colNames: ["界面好看不"],
                colModel: [
                    {name: "in", align: "center"},
                ],
                height: "45%",
                autowidth: true,
                styleUI: "Bootstrap",
            });
        })
    </script>
</head>
<body>
<div class="page-header">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="javascript:void(0)" aria-controls="home" role="tab"
                                                  data-toggle="tab">欢迎来到聊天室</a></li>
    </ul>
    <table class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                6组匿名群聊现场
            </h3>
        </div>
        <table id="ltTable">

        </table>
        <div class="panel-footer" style="position: fixed;bottom: 160px; ">
            <div class="input-group">
                <input type="text" class="form-control" id="in">
                <span class="input-group-btn">
                        <input type="submit" class="btn btn-primary" onclick="go()">发送！！！</input>
                     </span>
            </div>
        </div>

</div>
</div>
</body>


