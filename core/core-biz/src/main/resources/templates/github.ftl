<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>GitHub登录</title>
</head>
<body>
登陆中...
${loginUserVO}<br/>
${domain}
<script>
    window.onload = function () {
        //给指定domain发送消息
        window.opener.postMessage('${loginUserVO}', `${domain}`)
        window.close();
    }
</script>
</body>
</html>
