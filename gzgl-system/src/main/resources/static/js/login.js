function login(){
    const loginName = $("#loginName").val();
    const password = $("#password").val();

    if(loginName == ""){
        msg.info("请输入用户名!");
        return
    }

    if(password == ""){
        msg.info("请输入密码!");
        return;
    }
}