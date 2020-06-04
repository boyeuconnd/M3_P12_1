<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 04/06/2020
  Time: 10:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Users</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="user?action=users">List All Users</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New User</h2>
            </caption>
            <tr>
                <th>User Name:</th>
                <td>
                    <input type="text" name="name" id="name" size="45"/>
                </td>
            </tr>
            <tr>
                <th>User Email:</th>
                <td>
                    <input type="text" name="email" id="email" size="45"/>
                </td>
            </tr>
            <tr>
                <th>Country:</th>
                <td>
                    <input type="text" name="country" id="country" size="15"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input style="padding: 5px 10px" type="submit" value="Save"/>
                </td>
            </tr>
        </table>
        <p>
            <c:if test="${requestScope['messenger']!=null}">
                <span style="color: green;font-size: 18px" class="messenger">${requestScope['messenger']}</span>

            </c:if>
        </p>
    </form>
</div>
</body>
</html>
