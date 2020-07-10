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
<body style="padding-bottom: 200px">

<form class="layui-form" method="post" action="/promote/add" style="width: 80%;margin: 20px auto">

    <div class="layui-form-item">
        <label class="layui-form-label">教师</label>
        <div class="layui-input-block">
            <select name="teacherId" lay-filter="education">
                <#list teacherList as item>
                    <option value="${item.teacherId}">${item.name}</option>
                </#list>
            </select>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">考核名称</label>
        <div class="layui-input-block">
            <input type="text" name="examineName"
                   placeholder=""
                   autocomplete="off"
                   required
                   lay-verify="required"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">考核详情</label>
        <div class="layui-input-block">
            <textarea name="examineDetail" required
                      lay-verify="required"
                      placeholder="" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">开始日期</label>
        <div class="layui-inline">
            <input type="text" name="startTime" lay-verify="required" class="layui-input test-item" id="test1">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">结束日期</label>
        <div class="layui-inline">
            <input type="text" name="endTime" lay-verify="required" class="layui-input test-item" id="test2">
        </div>
    </div>


    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="*">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script>
    //JavaScript代码区域
    layui.use(['form', 'laydate', 'layer'], function () {

        var $ = layui.jquery;

        var layer = layui.layer;

        var laydate = layui.laydate;
        // 同时绑定多个
        lay('.test-item').each(function () {
            laydate.render({
                elem: this
                , format: 'yyyy-MM-dd'
                , type: 'datetime'
                , trigger: 'click'
            });
        });

        var form = layui.form;

        // 监听提交
        form.on('submit(*)', function (data) {  // *是提交按钮的lay-filter="*"
            // layer.msg(JSON.stringify(data.field));
            console.log(JSON.stringify(data.field))
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: data.field,
                url: "/examine/"+data.field.teacherId,
                success: function (data) {
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
                                // 新增数据之后，刷新父页面的表格数据
                                parent.layui.table.reload('test', {
                                    // url可以不指定，会使用table.render中指定的
                                    url: '${base}/examine/list',  // 因为设置了分页，所以会带上参数发请求 list-data2.json?page=1&limit=5&query= （query是下面where中定义的参数名）
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
            return false; // 返回false阻止form的默认提交事件
        })
        ;
        //自定义验证规则
        form.verify({
            name: [
                /^[\S]{2,12}$/
                , '必须2到12位，且不能出现空格'
            ],
        });

    });
</script>

</body>
</html>