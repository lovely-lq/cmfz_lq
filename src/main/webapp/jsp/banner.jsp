<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#bannerTable").jqGrid({
                url: "${pageContext.request.contextPath}/banner/queryByPage",
                datatype: "json",
                colNames: ['id', '标题', '图片', '描述', '状态', '上传时间'],
                colModel: [
                    {name: 'id', index: 'id', width: 55, align: "center"},
                    {name: 'title', index: 'invdate', width: 90, align: "center", editable: true},
                    {
                        name: 'url',
                        editable: true,
                        edittype: "file",
                        editoptions: {enctype: "multipart/form-data"},
                        formatter: function (cellvalue, options, rowObject) {
                            return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                        }
                    },
                    {name: 'description', index: 'invdate', width: 90, align: "center", editable: true},
                    {
                        name: 'status', index: 'amount', width: 80, align: "center",
                        formatter: function (cellvalue, options, rowObject) {
                            if (cellvalue == 1) {
                                return "<button type='button' class='btn btn-danger' onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                            } else {
                                return "<button type='button' class='btn btn-info' onclick='updateStatus(\"" + rowObject.id + "\",\"" + cellvalue + "\")'>激活</button>";
                            }
                        }
                    },
                    {
                        name: 'createDate',
                        index: 'note',
                        width: 150,
                        sortable: false,
                        align: "center",
                        editable: true,
                        edittype: "date"
                    }
                ],
                autowidth: true,
                pager: "#pager",
                rowNum: 2,
                rowList: [5, 10, 15, 20],
                viewrecords: true,
                caption: "轮播图",
                styleUI: "Bootstrap",
                editurl: "${pageContext.request.contextPath}/banner/save"
            });
            $("#bannerTable").jqGrid('navGrid', '#pager', {add: true, edit: true, del: true},
                {
                    closeAfterEdit: true,
                    afterSubmit: function (data) {
                        $.ajaxFileUpload({
                            // 指定上传路径
                            url: "${pageContext.request.contextPath}/banner/uploadBanner",
                            type: "post",
                            datatype: "json",
                            // 发送添加图片的id至controller
                            data: {id: data.responseText},
                            // 指定上传的input框id
                            fileElementId: "url",
                            success: function () {
                                //刷新表单
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                        // 防止页面报错
                        return "aaa";
                    }


                },//修改之后得操作
                {
                    closeAfterAdd: true,//添加之后得操作
                    //提交之后做得事情
                    afterSubmit: function (data) {
                        //文件上传
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/uploadBanner",
                            type: "post",
                            dataType: "json",
                            data: {id: data.responseText},
                            fileElementId: "url",
                            success: function () {
                                //刷新表单
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                        return "kjt";//必须要有返回值，可随便写
                    }
                },
                {closeAfterDel: true,} //删除之后得操作
            );
        })

        function updateStatus(id, status) {
            if (status == "1") {
                $.ajax({
                    url: "${pageContext.request.contextPath}/banner/save",
                    type: "POST",
                    datatype: "JSON",
                    data: {"id": id, "status": "2", "oper": "edit"},
                    success: function () {
                        $("#bannerTable").trigger("reloadGrid");
                    }
                });
            } else {
                $.ajax({
                    url: "${pageContext.request.contextPath}/banner/save",
                    type: "POST",
                    datatype: "JSON",
                    data: {"id": id, "status": "1", "oper": "edit"},
                    success: function () {
                        $("#bannerTable").trigger("reloadGrid");
                    }
                });
            }
        }

    </script>

</head>
<body>
<table id="bannerTable"></table>
<div id="pager" style="height: 50px"></div>
</body>
</html>
