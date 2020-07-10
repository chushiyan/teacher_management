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
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="toolbar">
    <div class="layui-table-tool-temp" style="float: left;padding-right: 20px;">
        <div class="layui-inline" lay-event="refresh"><i class="layui-icon layui-icon-refresh-3"></i></div>
    </div>
</script>


<script>
    layui.use(['layer', 'table'], function () {
        var table = layui.table;
        var layer = layui.layer;
        table.render({
            elem: '#test',    // 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
            url: '${base}/examine/mine', // 请求地址
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
                    field: 'examineId',
                    title: 'ID',
                    // width: 80,
                    fixed: 'left',
                    unresize: true,
                    sort: true
                }
                ,
                {
                    field: 'examineName',
                    title: '考核标题',
                    align: "center",   // 文字居中，默认居左
                    // width: 120,

                }
                ,
                {
                    field: 'examineDetail',
                    title: '考核详情',
                    align: "center",
                    // width: 150,

                }
                ,
                {
                    field: 'createTime',
                    title: '创建时间',
                    align: "center",
                    // width: 100,
                    sort: true
                }
                ,
                {
                    field: 'startTime',
                    title: '开始日期',
                    align: "center",
                    sort: true
                }
                ,
                {
                    field: 'endTime',
                    title: '结束日期',
                    align: "center",
                    sort: true
                }
                ,
                {
                    field: 'status',
                    title: '状态',
                    align: "center",
                    templet: function (d) {
                        var htm = ''
                        switch (d.status) {
                            case 0:
                                htm = "已删除"
                                break
                            case 1:
                                htm = "未开始"
                                break
                            case 2:
                                htm = "进行中"
                                break
                            case 3:
                                htm = "已完成"
                                break
                            default :
                                htm = "未知"
                        }
                        return htm;
                    }
                }
                ,
                // {
                //     field: '',
                //     title: '',
                //     // width: 200,
                //     align: "center",
                //     toolbar: '#barDemo'
                // }
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

        // 监听头工具栏事件（表头左边的 增加 刷新 搜索等按钮）
        table.on('toolbar(test)', function (obj) {
            switch (obj.event) {
                case "refresh":  // 如果是点击了刷新按钮
                    // 点击刷新后，让表格重载
                    // 第一个参数：test是表格的id（可以直接在table标签上写上id属性，也可以通过 table 模块参数id指定
                    table.reload('test', {
                        // url可以不指定，会使用table.render中指定的
                        url: '${base}/examine/mine',  // 因为设置了分页，所以会带上参数发请求 list-data2.json?page=1&limit=5&query= （query是下面where中定义的参数名）
                        // ,methods:"post"
                        where: {  //设定异步数据接口的额外参数

                        },
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