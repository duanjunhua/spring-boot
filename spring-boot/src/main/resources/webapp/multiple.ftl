<html>
<head>
    <meta charset="UTF-8">
    <title>Freemarker 多数据源</title>
</head>
<body>
    <h2>第一数据源</h2>
    <#list primary?keys as key>
        ${key}: ${primary[key]}<br>
    </#list>

    <hr>
    <h2>第二数据源</h2>
    <#list secondary?keys as key>
        ${key}---${secondary[key]}<br>
    </#list>
</body>
</html>