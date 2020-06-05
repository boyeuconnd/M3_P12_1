<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 04/06/2020
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Success</title>
    <link rel="stylesheet" href="../css/editStyle.css">
</head>
<body>
<h2 style="text-align: center">Edit User informations</h2>
<div>
    <p>
       <c:if test="${requestScope['messenger']!=null}">
           <span class="messenger">${requestScope['messenger']}</span>

       </c:if>
    </p>
    <button>
        <a id = "return" href="user?action=show">BACK TO LIST</a>
    </button>
<%--    <form method="post">--%>
<%--        <table>--%>
<%--            <tr>--%>
<%--                <td>Name</td>--%>
<%--                <td>--%>
<%--                    <input type="text" name="name" value="${requestScope['editUser'].getName()}">--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Email</td>--%>
<%--                <td><input type="email" name ="email" value ="${requestScope['editUser'].getEmail()}">--%>

<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Country</td>--%>
<%--                <td>--%>
<%--                    <input type="text" name="country" value="${requestScope['editUser'].getCountry()}">--%>

<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td colspan ="2">--%>
<%--                    <button type="submit">YES</button>--%>
<%--                    --%>
<%--                </td>--%>

<%--            </tr>--%>

<%--        </table>--%>
<%--    </form>--%>
</div>
</body>
</html>
