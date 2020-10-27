<%@ page import="com.model.User" %><%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 17-5-11
  Time: 下午4:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提示信息页面</title>
</head>
<body>
<%
    //获取提示信息
    String info = (String) request.getAttribute("info");
    if (info != null) {
        out.print(info);
    }

    /*获取用户的登录信息*/
    User user = (User) session.getAttribute("user");
    if (user != null) {
%>
<table align="center" width="600" border="1" height="550" bordercolor="#E8F4CC">
    <tr>
        <td align="center" colspan="2">
            <span style="font-weight: bold;font-size: 18px;"><%=user.getUsername()%></span>登录成功！
        </td>
    </tr>
    <tr>
        <td align="center" colspan="2">性别：</td>
        <td align="center" colspan="2"><%=user.getSex()%></td>
    </tr>
    <tr>
        <td align="center" colspan="2">邮箱</td>
        <td align="center" colspan="2"><%=user.getEmail()%></td>
    </tr>
</table>
<%
    }else {
        out.print("<br>对不起您没有登录！");
    }
%>
</body>
</html>
