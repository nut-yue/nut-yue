$(function () {
    var userObj=$(".user input");
    var pwdObj=$(".pwd input");
    var btnObj=$("button");
    var user_isRight=$(".user_isRight");
    var pwd_isRight=$(".pwd_isRight");
    btnObj.click(function () {
        var userReg=/^[A-z]*$/;
        var pwdReg=/^[A-z0-9]*$/;
        if (userReg.test(userObj.val())&&userObj.val()!=""){
            console.log("用户输入正确");
            if (user_isRight.hasClass("layui-icon-close")) {
                user_isRight.removeClass("layui-icon-close");
            }
            user_isRight.addClass("layui-icon-ok");
        }else{
            console.log("用户输入错误");
            if (user_isRight.hasClass("layui-icon-ok")) {
                user_isRight.removeClass("layui-icon-ok");
            }
            user_isRight.addClass("layui-icon-close");
        }
        if (pwdReg.test(pwdObj.val())&&pwdObj.val()!="") {
            console.log("密码输入正确");
            if (pwd_isRight.hasClass("layui-icon-close")) {
                pwd_isRight.removeClass("layui-icon-close");
            }
            pwd_isRight.addClass("layui-icon-ok");
        }else{
            console.log("密码输入错误");
            if (pwd_isRight.hasClass("layui-icon-ok")) {
                pwd_isRight.removeClass("layui-icon-ok");
            }
            pwd_isRight.addClass("layui-icon-close");
        }
    });

})