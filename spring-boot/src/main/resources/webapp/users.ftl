<html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker 查询多个用户</title>
</head>
<body>
    <#list users as user>
        用户ID： ${user.id} &nbsp;,
        用户名： ${user.userName}
        <br>
    </#list>
</body>
</html>