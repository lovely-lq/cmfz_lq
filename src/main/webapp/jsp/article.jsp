<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid({
            url: "${pageContext.request.contextPath}/article/queryAllArticle",
            datatype: "json",
            colNames: ['id', '标题', '图片', '内容', '创建时间', '上传时间', '状态', '所属上师', '操作'],
            colModel: [
                {name: 'id', width: 55, align: "center"},
                {name: 'title', width: 90, align: "center", editable: true},
                {
                    name: 'img',
                    editable: true,
                    edittype: "file",
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='" + cellvalue + "' style='width:100px;height:60px'>";
                    }
                },
                {name: 'content', width: 90, align: "center", editable: true},
                {
                    name: 'createDate',
                    width: 150,
                    sortable: false,
                    align: "center",
                    editable: true,
                    edittype: "date"
                },
                {
                    name: 'publishDate',
                    width: 150,
                    sortable: false,
                    align: "center",
                    editable: true,
                    edittype: "date"
                },

                {
                    name: 'status', index: 'amount', width: 80, align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == 1) {
                            return "冻结";
                        } else {
                            return "展示";
                        }
                    }
                },
                {
                    name: 'guruId',
                    align: "center",
                },
                {
                    name: 'xx',
                    formatter: function (cellvalue, options, rowObject) {
                        var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + rowObject.id + "')\">修改</button>&nbsp;&nbsp;";
                        button += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('" + rowObject.id + "')\">删除</button>";
                        return button;
                    }

                }
            ],
            rowNum: 2,
            rowList: [10, 20, 30],
            pager: '#articlePager',
            sortname: 'id',
            mtype: "post",
            styleUI: "Bootstrap",
            viewrecords: true,
            sortorder: "desc",
            editurl: "${pageContext.request.contextPath}/article/save"
        });
        $("#articleTable").jqGrid('navGrid', '#articlePager', {add: true, edit: true, del: true},
            {
                closeAfterEdit: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/article/articleUpload",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "img",
                        success: function () {
                            //刷新表单
                            $("#articleTable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }


            },//修改之后得操作
            {
                closeAfterAdd: true,
                afterSubmit: function (data) {
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url: "${pageContext.request.contextPath}/article/articleUpload",
                        type: "post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data: {id: data.responseText},
                        // 指定上传的input框id
                        fileElementId: "img",
                        success: function () {
                            //刷新表单
                            $("#articleTable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return "aaa";
                }
            },//添加之后得操作
            {closeAfterDel: true} //删除之后得操作
        );
    });

    //点击添加
    function showArticle() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id", "");
        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAll",
            dataType: "json",
            type: "post",
            success: function (data) {
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }

        });
        $("#myModal").modal("show");
    };


    //点击修改
    function update(id) {
        // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化的问题
        // $("#articleTable").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
        var data = $("#articleTable").jqGrid("getRowData", id);
        console.log(data)
        $("#id").val(data.id);
        $("#title").val(data.title);
        // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
        KindEditor.html("#editor_id", data.content)
        // 处理状态信息
        $("#status").val(data.status);
        var option = "";
        if (data.status == "展示") {
            option += "<option selected value=\"1\">展示</option>";
            option += "<option value=\"2\">冻结</option>";
        } else {
            option += "<option value=\"1\">展示</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);


        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAll",
            dataType: "json",
            type: "post",
            success: function (gurulist) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                gurulist.forEach(function (guru) {
                    if (guru.id == data.guruId) {
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option2);
            }

        });
        $("#myModal").modal("show");
    }


    //提交表单
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/saveArticle",
            type: "post",
            // ajaxFileUpload 不支持serialize() 格式化形式
            // 只支持{"id":1,XXX:XX}
            // 解决: 1. 手动封装  2. 更改ajaxFileUpload的源码

            // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "inputfile",
            success: function (data) {
                $("#closeId").click();
                $("#articleTable").trigger("reloadGrid");
            }
        })
    }

</script>

<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>文章信息</a></li>
    <li><a onclick="showArticle()">添加文章</a></li>
</ul>
<table id="articleTable"></table>
<div id="articlePager" style="height: 50px"></div>
