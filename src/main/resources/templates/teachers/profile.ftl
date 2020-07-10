<#assign base=springMacroRequestContext.getContextUrl("")>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>个人资料</title>
    <link rel="stylesheet" href="${base}/lib/layui/css/layui.css">
    <script type="text/javascript" src="${base}/lib/jquery/jquery-3.3.1.js"></script>
    <script src="${base}/lib/layui/layui.js"></script>
</head>
<body>
<form class="layui-form" method="post" action="/teacher/update" style="width: 80%;margin: 20px auto">
    <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <input type="text" name="name"
                   placeholder=""
                   autocomplete="off"
                   required
                    <#if Session.teacher??>
                        value="${Session.teacher.name}"
                    </#if>
                   lay-verify="required|username"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block">
            <input type="text" name="email"
                   placeholder=""
                   autocomplete="off"
                   required
                   <#if Session.teacher??>
                        value="${Session.teacher.email}"
                   </#if>
                   lay-verify="required|email"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机</label>
        <div class="layui-input-block">
            <input type="text" name="phone"
                   placeholder=""
                   autocomplete="off"
                   required
                                      <#if Session.teacher??>
                        value="${Session.teacher.phone}"
                                      </#if>
                   lay-verify="required|phone"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">年龄</label>
        <div class="layui-input-block">
            <input type="number" name="age"
                   placeholder=""
                   autocomplete="off"
                   required
                <#if Session.teacher??>
                        value="${Session.teacher.age}"
                </#if>
                   lay-verify="age"
                   class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <select name="gender" lay-filter="">

                <option value="0"
                                                           <#if Session.teacher.gender == 0>
                        selected
                                                           </#if>
                >女
                </option>
                <option value="1"
                                                                                   <#if Session.teacher.gender == 1>
                        selected
                                                                                   </#if>
                >男
                </option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">籍贯</label>
        <div class="layui-input-block">
            <input type="text" name="birthplace"
                   placeholder=""
                   autocomplete="off"
                   required
                                   <#if Session.teacher??>
                        value="${Session.teacher.birthplace}"
                                   </#if>
                   lay-verify="required"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">身份证号</label>
        <div class="layui-input-block">
            <input type="text" name="idNumber"
                   placeholder=""
                   autocomplete="off"
                   required
                                   <#if Session.teacher?? >
                        value="${Session.teacher.idNumber}"
                                   </#if>
                   lay-verify="required|identity"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">毕业院校</label>
        <div class="layui-input-block">
            <input type="text" name="graduateSchool"
                   placeholder=""
                   autocomplete="off"
                   required
                                   <#if Session.teacher??>
                        value="${Session.teacher.graduateSchool}"
                                   </#if>
                   lay-verify="required"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">学历</label>
        <div class="layui-input-block">
            <select name="education" lay-filter="education">
                <option value="学士"
                         <#if Session.teacher.education == "学士">
                        selected
                         </#if>
                >学士</option>
                <option value="硕士"
                        <#if Session.teacher.education == "硕士">
                                                selected
                         </#if>
                >硕士</option>
                <option value="博士"
                        <#if Session.teacher.education == "博士">
                        selected
                        </#if>
                >博士</option>

                <option value="博士后"
                        <#if Session.teacher.education == "博士后">
                        selected
                        </#if>
                >博士后</option>


                <option value="其他"
                        <#if Session.teacher.education == "其他">
                        selected
                        </#if>
                >其他</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">所属部门</label>
        <div class="layui-input-block">
            <input type="text" name="department"
                   placeholder=""
                   autocomplete="off"
                   required
                    <#if Session.teacher??>
                        value="${Session.teacher.department}"
                     </#if>
                   lay-verify="required"
                   class="layui-input">
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
    layui.use('form', function () {

        var $ = layui.jquery;

        var form = layui.form;
        // 监听提交
        form.on('submit(*)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            return true;
        });
        //自定义验证规则
        form.verify({
            username: [
                /^[\S]{2,12}$/
                , '用户名必须2到12位，且不能出现空格'
            ],
            password: [
                /^[\S]{6,12}$/
                , '密码必须6到12位，且不能出现空格'
            ]
        });
    });
</script>
</body>
</html>