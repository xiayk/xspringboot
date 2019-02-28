layui.use(['layer','jquery','form'], function() {
    window.jQuery = window.$ = layui.jquery;
    window.layer = layui.layer;
    var form = layui.form;
    var href = window.location.href;
    if(href.indexOf('notLogin')>0){
        layer.msg("请登录后进行操作!");
    }

    var loginType = true;

    $(".loginType").on("click",function () {
        if (loginType){
            loginType = false;
            $("#password").attr("placeholder","请输入验证码");
            $("#username").attr("placeholder","请输入手机号码");
            $("#loginType").val("Num");
            $(this).text("点击账号密码登陆");
        } else{
            loginType = true;
            $("#password").attr("placeholder","请输入密码");
            $("#username").attr("placeholder","请输入用户名");
            $("#loginType").val("uname");
            $(this).text("点击验证码登陆");
        }
    })

    $('#form-login').submit(function (res) {
        $(".layui-btn-lg").addClass('layui-btn-disabled').val("正在登陆");
        $.post('/login', $("#form-login").serialize() , function (res) {
            layer.msg(res.msg);
            if (res.code==100){
                location.href = res.url;
            }else{
                $(".layui-btn").removeClass('layui-btn-disabled').val("登陆");
            }
        })
        return false;
    })
})