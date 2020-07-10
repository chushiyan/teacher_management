<#assign base=springMacroRequestContext.getContextUrl("")>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>教师首次登录修改密码</title>
    <link rel="stylesheet" href="${base}/lib/layui/css/layui.css">
    <script type="text/javascript" src="${base}/lib/jquery/jquery-3.3.1.js"></script>
    <script src="${base}/lib/layui/layui.js"></script>
</head>
<body>
<form class="layui-form" style="width: 80%;margin: 120px auto" action="/teacher/reset" method="post">
    <div class="layui-form-item">
    <#--<label class="layui-form-label">原密码</label>-->
        <div class="layui-input-block">
            <input type="password" name="password"
                   placeholder="请输入原密码"
                   required
                   lay-verify="password"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
    <#--<label class="layui-form-label">新密码</label>-->
        <div class="layui-input-block">
            <input type="password" name="password2"
                   required
                   lay-verify="password|differentPassword"
                   placeholder="请输入新密码"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="*">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            教师首次登录系统，需要先修改密码。
        </div>

    </div>


</form>

<script>
    //JavaScript代码区域
    layui.use(['form', 'layer'], function () {

        var layer = layui.layer;

        var $ = layui.jquery;

        var form = layui.form;

        // 监听提交
        form.on('submit(*)', function (data) {  // *是提交按钮的lay-filter="*"
            // layer.msg(JSON.stringify(data.field));
            // console.log(JSON.stringify(data.field))
            // $.ajax({
            //     type: 'POST',
            //     dataType: 'json',
            //     data: data.field,
            //     url: "/teacher/password",
            //     success: function (data) {
            //         layer.msg(data.message);
            //     },
            //     error: function (data) {
            //         layer.msg(data.message);
            //     }
            // });
            // return false; // 返回false阻止form的默认提交事件
            return true
        });

        // 自定义验证规则
        form.verify({
            password: [
                /^[\S]{6,12}$/
                , '密码必须6到12位，且不能出现空格'
            ],
            differentPassword: function (value) {
                if ($('input[name=password]').val() === value)
                    return '新旧密码不能相同！';
            }
        });
    })
    ;
</script>

</body>
</html>