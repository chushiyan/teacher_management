<#assign base=springMacroRequestContext.getContextUrl("")>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${base}/lib/layui/css/layui.css">
    <script type="text/javascript" src="${base}/lib/jquery/jquery-3.3.1.js"></script>
    <script src="${base}/lib/layui/layui.js"></script>

</head>
<body>

<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="barDemo">
    <#--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>-->
    <#--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>-->
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="toolbar">
    <div class="layui-table-tool-temp" style="float: left;padding-right: 20px;">
        <div class="layui-inline" lay-event="add"><i class="layui-icon layui-icon-add-1"></i></div>
        <div class="layui-inline" lay-event="refresh"><i class="layui-icon layui-icon-refresh-3"></i></div>
</script>

<script>
    layui.use(['layer', 'table'], function () {
        var table = layui.table;
        var layer = layui.layer;
        table.render({
            elem: '#test',    // 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
            url: '${base}/application/mine', // 请求地址
            toolbar: "#toolbar",
            title: '表格',   // 定义 table 的大标题（在文件导出等地方会用到）layui 2.4.0 新增
            totalRow: true,  //是否开启合计行区域。layui 2.4.0 新增
            limit: 5,    // 每页显示的条数（默认：10）。值务必对应 limits 参数的选项。注意：优先级低于 page 参数中的 limit 参数
            limits: [5, 10, 20, 30, 40, 50, 60],  // 每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。注意：优先级低于 page 参数中的 limits 参数

            defaultToolbar: ['filter', 'print', 'exports'], //  头部工具栏右侧图标。
            // 该参数可自由配置头部工具栏右侧的图标按钮，值为一个数组，支持以下可选值：
            // filter: 显示筛选图标
            // exports: 显示导出图标
            // print: 显示打印图标

            text: {none: '暂无相关数据'},//默认：无数据。注：该属性为 layui 2.2.5 开始新增


            cols: [[    // 	设置表头。值是一个二维数组。方法渲染方式必填
                {
                    field: 'applicationId',
                    title: 'ID',
                    // width: 80,
                    fixed: 'left',
                    unresize: true,
                    sort: true
                }
                ,
                {
                    field: 'teacher.name',
                    title: '教师',
                    align: "center",   // 文字居中，默认居左
                    // width: 120,
                    edit: 'text',
                    templet: "<div>{{d.teacher.name}}</div>"
                },
                {
                    field: 'teacher.jobGrade',
                    title: '目前职称',
                    align: "center",   // 文字居中，默认居左
                    // width: 120,
                    edit: 'text',
                    templet: "<div>{{d.teacher.jobGrade}}</div>"
                }
                ,
                {
                    field: 'promote.name',
                    title: '申请职称',
                    align: "center",
                    // width: 150,
                    edit: 'text',
                    templet: "<div>{{d.promote.name}}</div>"
                }
                , {
                    field: 'createTime',
                    title: '申请时间',
                    align: "center",
                    // width: 100,
                    sort: true
                }
                , {
                    field: 'status',
                    title: '状态',
                    align: "center",
                    templet: function (d) {
                        var htm = ''

                        switch (d.status) {
                            case -1:
                                htm = "待审核"
                                break
                            case 0:
                                htm = "已删除"
                                break
                            case 1:
                                htm = "已通过"
                                break
                            case 2:
                                htm = "未通过"
                                break
                            default :
                                htm = "未知"
                        }
                        return htm;
                    }
                }
                ,
                {
                    field: '',
                    title: '',
                    // width: 200,
                    align: "center",
                    toolbar: '#barDemo'
                }
            ]],
            page: true,
            response: {
                statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
            },
            parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.status, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.rows //解析数据列表
                };
            }
        });


        // 监听工具条(每一行最右边的查看修改删除等按钮)
        table.on('tool(test)', function (obj) {
            var data = obj.data;

            if (obj.event === 'detail') {  // 点击了查看按钮
                layer.msg('ID：' + data.id + ' 的查看操作');
            }
            // 点击了删除按钮
            else if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    // obj.del();
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        type: 'DELETE',
                        dataType: 'json',
                        // data: data.field,
                        url: "/application/" + data.applicationId,
                        success: function (data) {
                            layer.msg(
                                    data.message,
                                    {
                                        icon: 1,
                                        time: 2000  //2秒关闭（如果不配置，默认是3秒）
                                    },
                                    function () {
                                        // 新增数据之后，刷新表格数据
                                        table.reload('test', {
                                            // url可以不指定，会使用table.render中指定的
                                            url: '${base}/application/mine',  // 因为设置了分页，所以会带上参数发请求 list-data2.json?page=1&limit=5&query= （query是下面where中定义的参数名）
                                            // ,methods:"post"
                                            where: {  //设定异步数据接口的额外参数
                                                // query: inputVal
                                            },
                                            page: {
                                                curr: 1  // 重新从第 1 页开始
                                            }
                                        });
                                    }
                            )
                        },
                        error: function (data) {
                            layer.msg(
                                    data.message,
                                    {
                                        icon: 1,
                                        time: 2000  //2秒关闭（如果不配置，默认是3秒）
                                    },
                                    function () {
                                        // 下面就是提交成功后关闭自己
                                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                        parent.layer.close(index); // 再执行关闭
                                    }
                            )
                        }
                    });


                });
                // 点击了编辑按钮
            } else if (obj.event === 'edit') {


            }
        });


        // 监听单元格编辑。单元格被编辑，且值发生改变时触发，回调函数返回一个object参数，携带的成员如下：
        table.on('edit(test)', function (obj) { //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
            console.log(obj.value); //得到修改后的值
            console.log(obj.field); //当前编辑的字段名
            console.log(obj.data); //所在行的所有相关数据

        });


        // 监听头工具栏事件（表头左边的 增加 刷新 搜索等按钮）
        table.on('toolbar(test)', function (obj) {
            switch (obj.event) {
                case 'add':  // 如果是点击了增加按钮
                    layer.open({
                        type: 2,
                        title: "添加",
                        area: ['70%', '90%'],
                        fix: false,
                        maxmin: true,
                        shadeClose: true,
                        scrollbar: true,
                        shade: 0.4,
                        skin: 'layui-layer-rim',
                        content: ["/teachers/application/add", "no"],
                        yes: function (index, layero) {
                            //do something
                            layer.close(index); //如果设定了yes回调，需进行手工关闭
                            console.log("确认")
                        }
                    });
                    break;

                case "refresh":  // 如果是点击了刷新按钮
                    // 点击刷新后，让表格重载
                    // 第一个参数：test是表格的id（可以直接在table标签上写上id属性，也可以通过 table 模块参数id指定
                    table.reload('test', {
                        // url可以不指定，会使用table.render中指定的
                        url: '${base}/application/mine',  // 因为设置了分页，所以会带上参数发请求 list-data2.json?page=1&limit=5&query= （query是下面where中定义的参数名）
                        // ,methods:"post"
                        page: {
                            curr: 1  // 重新从第 1 页开始
                        }
                    });
                    break;
            }
        });


    });
</script>

</body>
</html>