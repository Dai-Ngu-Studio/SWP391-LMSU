<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Login Successful</title>
    </head>
    <body>
        <h1>Welcome user</h1>
        Name: ${sessionScope.FULLNAME}</br>
        Email: ${sessionScope.USER_ID}</br>
    </body>
</html>