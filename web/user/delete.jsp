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

    <form method="post">
        <table>
            <tr>
                <th colspan ="2">Are you want to delete</th>
            </tr>
            <tr>
                <td><button type = "submit">YES</button></td>

<%--                <td><button><a href="user?action">NO</a></button></td>--%>
            </tr>
        </table>
        <button style="margin-top: 30px;width: auto">

            <a class = "return" href="user?action=show">Return to the list</a>
        </button>
    </form>
</div>
</body>
</html>
