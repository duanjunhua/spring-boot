<html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker Freemarker 多数据源二</title>
</head>
<body>
    <#list secondary as uumUser>
        用户ID： ${uumUser.userId} &nbsp;
        登录名： ${uumUser.userLoginName} &nbsp;
<#--        密码： ${uumUser.userPassword}-->
        <br>
    </#list>
</body>
</html>