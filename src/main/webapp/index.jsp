<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quest Game</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Welcome!<br></h1>
<p>Начать игру?</p>
<form action="${pageContext.request.contextPath}/game" method="post">
    <button type="submit" class="button start"/>Start!</button>
</form>
</body>
</html>
