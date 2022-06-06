<html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker 多数据源一</title>
</head>
<body>
    <#list primary as xtUser>
        用户ID： ${xtUser.id} &nbsp;
        用户名： ${xtUser.name} &nbsp;
        登录名： ${xtUser.loginName}
        <br>
    </#list>
</body>
</html>