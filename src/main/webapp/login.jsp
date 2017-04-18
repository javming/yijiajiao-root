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
        .ipt{
            border: 1px solid #d3d3d3;
            padding: 10px 10px;
            width: 290px;
            border-radius: 4px;
            padding-left: 35px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
            -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
            transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s
        }
        .ipt:focus{
            border-color: #66afe9;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
            box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
        }
        .u_logo{
            background: url("/images/username.png") no-repeat;
            padding: 10px 10px;
            position: absolute;
            top: 43px;
            left: 40px;

        }
        .p_logo{
            background: url("/images/password.png") no-repeat;
            padding: 10px 10px;
            position: absolute;
            top: 12px;
            left: 40px;
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
        .initial_left_hand{
            background: url("/images/hand.png") no-repeat;
            width: 30px;
            height: 20px;
            position: absolute;
            top: -12px;
            left: 100px;
        }
        .initial_right_hand{
            background: url("/images/hand.png") no-repeat;
            width: 30px;
            height: 20px;
            position: absolute;
            top: -12px;
            right: -112px;
        }

    </STYLE>
    <script src ="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js"></script>
    <%
        String msg = ((msg=(String) request.getAttribute("msg"))==null?"":msg);
    %>
    <script language="JavaScript">
        window.onload = function() {
            var msg = '<%=msg%>';
            if (msg!=''){
                alert(msg);
            }
        };
        $(document).ready(function () {
            var username = $("#username");
            var password = $("#password");
            $("#form").submit(function (e) {
                if (username.val().trim()== ''){
                    alert("请输入用户名");
                    e.preventDefault();
                    return false;
                }
                if (password.val().trim() == ''){
                    alert("请输入密码");
                    e.preventDefault();
                    return false;
                }
                username.val(username.val().trim());
                password.val(md5(password.val().trim()));

            })
        })
    </script>
    <BODY>
        <form id=form action="login.action" method="post">
        <DIV class="top_div"></DIV>
        <DIV style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
            <DIV style="width: 165px; height: 96px; position: absolute;">
                <DIV class="tou"></DIV>
                <DIV class="initial_left_hand" id="left_hand"></DIV>
                <DIV class="initial_right_hand" id="right_hand"></DIV>
            </DIV>
            <P style="padding: 30px 0px 10px; position: relative;">
                <SPAN class="u_logo"></SPAN>
                <INPUT class="ipt" id="username" name="username" type="text" placeholder="请输入用户名" value="">
            </P>
            <P style="position: relative;">
                <SPAN class="p_logo"></SPAN>
                <INPUT class="ipt" id="password" name="password" type="password" placeholder="请输入密码" value="">
            </P>
            <DIV style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
                <P style="margin: 0px 35px 20px 45px;">
                    <SPAN style="float: right;">
                      <input style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"
                         type="submit" value="登录"></input>
                   </SPAN>
                </P>
            </DIV>
        </DIV>
        </form>
    </BODY>
</html>
