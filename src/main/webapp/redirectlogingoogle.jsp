<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Redirecting</title>
</head>
<body>
<c:set var="roleID" value="${sessionScope.LOGIN_USER.roleID}"/>
<c:if test="${roleID eq 4}">
    <c:redirect url="IndexServlet"></c:redirect>
</c:if>
<c:if test="${roleID eq 3 or roleID eq 2 or roleID eq 1}">
    <c:redirect url="dashboard.jsp"></c:redirect>
</c:if>
<c:if test="${empty roleID}">
    <c:redirect url="login.jsp"></c:redirect>
</c:if>
</body>
</html>
