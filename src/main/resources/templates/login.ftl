<#assign base=springMacroRequestContext.getContextUrl("")>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>管理系统</title>
    <link rel="stylesheet" href="${base}/lib/layui/css/layui.css">
</head>
<body class="layui-layout-body">
<script src="${base}/lib/jquery/jquery-3.3.1.js"></script>
<script src="${base}/lib/layui/layui.js"></script>

<script type="text/javascript">
    if (window != top)
        top.location.href = location.href;
</script>



<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;text-align: center">
    <legend>教师考核晋升系统登录</legend>
</fieldset>

<div class="layui-tab" style="margin-left: 100px;">
    <ul class="layui-tab-title">
        <li class="layui-this">教师登录</li>
        <li>管理登录</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <div>
                <fieldset class="layui-elem-field layui-field-title" style=>
                    <legend>教师登录</legend>
                </fieldset>
                <form class="layui-form layui-form-pane" method="post" action="/teacher/login">
                    <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
                    <div class="layui-form-item">
                        <label class="layui-form-label">邮箱</label>
                        <div class="layui-input-inline">
                            <input type="email" name="email"
                                   required
                                   lay-verify="required|email"
                                   placeholder="请输入邮箱"
                                   autocomplete="on"
                                   class="layui-input">
                        </div>
                        <i class="layui-icon layui-icon-email" style="font-size: 30px; color: #1E9FFF;"></i>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="password"
                                   required lay-verify="required|password"
                                   placeholder="请输入密码"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <i class="layui-icon layui-icon-password" style="font-size: 30px; color: #1E9FFF;"></i>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="formDemo">登录</button>
                            <button type="reset" class="layui-btn">重置</button>
                        </div>
                    </div>

                </form>
            </div>

        </div>

        <div class="layui-tab-item">
            <div>
                <fieldset class="layui-elem-field layui-field-title">
                    <legend>管理登录</legend>
                </fieldset>
                <form class="layui-form layui-form-pane" method="post" action="/admin/login">
                    <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
                    <div class="layui-form-item">
                        <label class="layui-form-label">邮箱</label>
                        <div class="layui-input-inline">
                            <input type="email" name="email"
                                   required
                                   lay-verify="required|email"
                                   placeholder="请输入邮箱"
                                   autocomplete="on"
                                   class="layui-input">
                        </div>
                        <i class="layui-icon layui-icon-email" style="font-size: 30px; color: #1E9FFF;"></i>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="password"
                                   required lay-verify="required|password"
                                   placeholder="请输入密码"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                        <i class="layui-icon layui-icon-password" style="font-size: 30px; color: #1E9FFF;"></i>
                    </div>


                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="formDemo">登录</button>
                            <button type="reset" class="layui-btn">重置</button>
                        </div>

                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    layui.use(['form', 'element', 'layer'], function () {
        var layer = layui.layer;

        <#if Session.login_msg??>
               layer.msg('${Session.login_msg}');
        </#if>

        var $ = layui.jquery
                , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

        var $ = layui.jquery;

        var form = layui.form;
        // 监听提交
        form.on('submit(formDemo)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            return true;
        });
        // 自定义验证规则
        form.verify({
            password: [
                /^[\S]{6,12}$/
                , '密码必须6到12位，且不能出现空格'
            ]
        });

    });
</script>


</body>
</html>