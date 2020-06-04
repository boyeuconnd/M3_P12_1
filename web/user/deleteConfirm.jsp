<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 04/06/2020
  Time: 10:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete User</title>
    <link rel="stylesheet" href="../css/style.css">
<body>
<div class ="body">
    <h1>Delete Confirm</h1>
    <p>
        <c:if test="${requestScope['messenger']!=null}">
            <span class="messenger">${requestScope['messenger']}</span>

        </c:if>
    </p>
    <a class = "return" href="/user">Return to the list</a>

</div>
</body>
</html>
