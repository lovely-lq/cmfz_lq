<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        $("#Usertable").jqGrid({
            url: "${pageContext.request.contextPath}/user/queryByPage",
            datatype: "json",
            colNames: ['id', '电话', '密码', '名字', '昵称', '性别', '地区', '注册时间'],
            colModel: [
                {name: 'id'},
                {name: 'phone', editable: true},
                {
                    name: 'password', editable: true
                },

                {
                    name: 'name', editable: true
                },
                {
                    name: 'nick', editable: true

                }, {name: 'sex', editable: true}, {name: 'location', editable: true}, {
                    name: 'rigest_date',
                    editable: true
                }
            ],
            autowidth: true,
            pager: "#Userpage",
            rowNum: 2,
            rowList: [5, 10, 15, 20],
            viewrecords: true,
            caption: "用户",
            styleUI: "Bootstrap",
            editurl: "${pageContext.request.contextPath}/user/save"

        });
        $("#Usertable").jqGrid('navGrid', '#Userpage', {add: true, edit: true, del: true},
            {
                closeAfterEdit: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/user/UserUpload",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "photo",
                        success: function () {
                            //刷新表单
                            $("#Usertable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }


            },//修改之后得操作

            {
                closeAfterAdd: true,//添加之后得操作
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/user/UserUpload",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "photo",
                        success: function () {
                            //刷新表单
                            $("#Usertable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }

            },

            {closeAfterDel: true,} //删除之后得操作
        );
    })

    function poiExe() {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/imageUpload",
            type: "post",
            datatype: "json",
            success: function () {
                $("#Usertable").trigger("reloadGrid");
            }
        })
    }

    function lead() {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/leadExcel",
            type: "post",
            datatype: "json",
            success: function () {
                $("#Usertable").trigger("reloadGrid");
            }
        })
    }
</script>
<body>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
    <li><a onclick="poiExe()">导出</a></li>
    <li><a onclick="lead()">导入</a></li>
</ul>
<table id="Usertable"></table>


<div id="Userpage" style="height: 50px">

</div>
</body>