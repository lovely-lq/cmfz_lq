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
        $("#ftable").jqGrid(
            {
                url: '${pageContext.request.contextPath}/album/queryByPage',
                datatype: "json",
                height: 500,
                colNames: ['Id', '标题', '评分', '描述', '封面', '作者', '播音', '集数', '发布日期'],
                colModel: [
                    {name: 'id'},
                    {name: 'title', editable: true},
                    {name: 'score'},
                    {name: 'description', editable: true},
                    {
                        name: 'headSrc',
                        editable: true,
                        edittype: "file",
                        editoptions: {enctype: "multipart/form-data"},
                        formatter: function (cellvalue, options, rowObject) {
                            return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                        }
                    },
                    {name: 'author', editable: true},
                    {name: 'broadcast'},
                    {name: 'acount', editable: true},
                    {name: 'createDate', editable: true}

                ],
                rowNum: 10,
                rowList: [8, 10, 20, 30],
                pager: '#fpage',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                mtype: "post",
                multiselect: false,
                autowidth: true,
                styleUI: "Bootstrap",
                editurl: "${pageContext.request.contextPath}/album/save",
                // 开启子表格支持
                subGrid: true,
                caption: "Grid as Subgrid",
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded: function (subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id, row_id);
                },
                // 删除表格的方法
                subGridRowColapsed: function (subgrid_id, row_id) {
                }
            });
        $("#ftable").jqGrid('navGrid', '#fpage', {
                add: true,
                edit: true,
                del: true
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/album/uploadalbum",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "headSrc",
                        success: function () {
                            //刷新表单
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }
            },

            {
                closeAfterAdd: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/album/uploadalbum",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "headSrc",
                        success: function () {
                            //刷新表单
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }
            }
        );
    });

    // subgrid_id 父行级id
    function addSubgrid(subgrid_id, row_id) {
        // 声明子表格Id
        var sid = subgrid_id + "table";
        // 声明子表格工具栏id
        var spage = subgrid_id + "page";
        $("#" + subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='" + spage + "' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定的json文件
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url: '${pageContext.request.contextPath}/chapter/queryById?id=' + row_id,
                datatype: "json",
                colNames: ['Id', '标题', '大小', '时长', '时间', '操作'],
                colModel: [
                    {name: "id", width: 80},
                    {name: "title", width: 130, editable: true},
                    {name: "csize", index: "item", width: 130},
                    {name: "time", index: "item", width: 130},
                    {name: "createTime", index: "item", width: 130, editable: true},
                    {
                        name: "url",
                        index: "item",
                        width: 130,
                        editable: true,
                        edittype: "file",
                        editoptions: {enctype: "multipart/form-data"}
                        ,
                        formatter: function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"dowloadChapter('" + cellvalue + "')\">下载</button>&nbsp;&nbsp;";
                            button += "<button type=\"button\" class=\"btn btn-danger\"  onclick=\"onPlay('" + cellvalue + "')\">在线播放</button>";
                            return button;
                        }
                    }
                ],
                rowNum: 20,
                pager: spage,
                sortname: 'num',
                sortorder: "asc",
                height: '100%',
                autowidth: true,
                styleUI: "Bootstrap",
                editurl: '${pageContext.request.contextPath}/chapter/save?row_id=' + row_id,
            });
        $("#" + sid).jqGrid('navGrid',
            "#" + spage, {
                edit: true,
                add: true,
                del: true
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/chapter/uploadChapter",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "url",
                        success: function () {
                            //刷新表单
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }
            },

            {
                closeAfterAdd: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/chapter/uploadChapter",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "url",
                        success: function () {
                            //刷新表单
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }
            }
        );
    };

    function onPlay(cellvalue) {
        $("#music").attr("src", cellvalue);
        $("#myModal").modal("show");

    }

    function dowloadChapter(cellvalue) {
        location.href = "${pageContext.request.contextPath}/chapter/dowloadChapter?url=" + cellvalue;
    }
</script>
<body>
<table id="ftable"></table>
<div id="fpage" style="height: 50px"></div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>
</body>