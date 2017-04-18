<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD id=Head1><TITLE>详情</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
    <STYLE>
        body{
            background: #ebebeb;
            font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;
            color: #222;
            font-size: 12px;
        }
        *{padding: 0px;margin: 0px;}
        .top_div{
            background: #008ead;
            width: 100%;
            height: 400px;
        }
        a{
            text-decoration: none;
        }
        .tou{
            background: url("/images/tou.png") no-repeat;
            width: 97px;
            height: 92px;
            position: absolute;
            top: -87px;
            left: 140px;
        }
    </STYLE>
    <%
        String msg = ((msg=(String) request.getAttribute("msg"))==null?"":msg);
    %>

<BODY>
<form id=form action="login.action" method="post">
    <DIV class="top_div"></DIV>
    <DIV style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">

        <h1>
        <P style="padding: 80px 10px 10px; position: relative;"><font color="red"><%=msg%></font></P>
        </h1>
    </DIV>
</form>
</BODY>
</html>
