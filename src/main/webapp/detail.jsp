<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD id=Head1><TITLE>详情</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=utf-8">
    <script src ="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <%
        String requestURL = ((requestURL=request.getParameter("requestURL"))==null)?"":requestURL;
        String requestMethod = ((requestMethod=request.getParameter("requestMethod"))==null)?"":requestMethod;
        String mappingURL = ((mappingURL=request.getParameter("mappingURL"))==null)?"":mappingURL;
        String requestStatus = ((requestStatus=request.getParameter("requestStatus"))==null)?"":requestStatus;
        String routerStatus = ((routerStatus=request.getParameter("routerStatus"))==null)?"":routerStatus;
        String replaceRegex = ((replaceRegex=request.getParameter("replaceRegex"))==null)?"":replaceRegex;
        String description = ((description=request.getParameter("description"))==null)?"":description;
        String requestId = ((requestId=request.getParameter("requestId"))==null)?"":requestId;
    %>
    <script language="JavaScript">
        window.onload = function() {
            var url = '<%=requestURL%>';
            var subType = document.getElementById("subType");

            if(url ==''){
                subType.value="1";
            }else {
                subType.value="2";
                $("input[name=requestURL]").attr("readonly","readonly");
            }
        };
        $(document).ready(function () {
            var requestURL = $("[name='requestURL']");
            var requestMethod = $("[name='requestMethod']");
            var mappingURL = $("[name='mappingURL']");
            var routerStatus = $("[name='routerStatus']");
            var requestStatus = $("[name='requestStatus']");
            $("#form1").submit(function (e) {
                if (requestURL.val().trim()==''){
                    e.preventDefault();
                    alert("requestURL不能为空");
                    return;
                }else if (requestMethod.val().trim()==''){
                    e.preventDefault();
                    alert("requestMothed不能为空");
                    return;
                }else if (mappingURL.val().trim()==''){
                    e.preventDefault();
                    alert("mappingURL不能为空");
                    return;
                } else if (routerStatus.val().trim()==''){
                    e.preventDefault();
                    alert("routerStatus不能为空");
                    return;
                } else if (requestStatus.val().trim()==''){
                    e.preventDefault();
                    alert("requestStatus不能为空");
                    return;
                }
                requestURL.attribute("value",requestURL.val().trim());
                requestMethod.attribute("value",requestMethod.val().trim());
                mappingURL.attribute("value",mappingURL.val().trim());
                routerStatus.attribute("value",routerStatus.val().trim());
                requestStatus.attribute("value",requestStatus.val().trim());
            })
        })
    </script>
<BODY>
<FORM id=form1 name=form1 action="/saveOrUpdate.action" method=post>
    <TABLE cellSpacing=0 cellPadding=0 width="60%" border=0>
        <TBODY>
        <TR>
            <TD width=15></TD>
            <TD width="100%" height=20></TD>
            <TD width=15></TD>
        </TR>
        </TBODY>
    </TABLE>

    <TABLE cellSpacing=0 cellPadding=0 width="60%" align="center">

        <TBODY>
        <tr height="20px"></tr>
        <tr>
            <TD  width="100%"  bgColor="#f0f8ff">
                <TABLE cellSpacing=0 cellPadding=0 width="100%" align=middle>
                    <tr>
                        <td>requestURL:</td>
                        <td><input name="requestURL" type="text" value="<%=requestURL%>"></td>
                    </tr>
                    <tr>
                        <td>requestMothed:</td>
                        <td><input name="requestMethod" type="text" value="<%=requestMethod%>"></td>
                    </tr>
                    <tr>
                        <td>mappingURL:</td>
                        <td><input name="mappingURL" type="text" value="<%=mappingURL%>"></td>
                    </tr>
                    <tr>
                        <td>routerStatus:</td>
                        <td><input name="routerStatus" type="text" value="<%=routerStatus%>"></td>
                    </tr>
                    <tr>
                        <td>requestStatus:</td>
                        <td><input name="requestStatus" type="text" value="<%=requestStatus%>"></td>
                    </tr>
                    <tr>
                        <td>replaceRegex:</td>
                        <td><input name="replaceRegex" type="text" value="<%=replaceRegex%>"></td>
                    </tr>
                    <tr>
                        <td>description:</td>
                        <td><input name="description" type="text" value="<%=description%>"></td>
                    </tr>
                </TABLE>
                <input id="subType" type="hidden" name="type">
                <input id="requestId" type="hidden" name="requestId" value="<%=requestId%>">
                <input id="submit" type="submit" value="提交" align=middle>
            </TD>
        </tr>
        <tr height="20px"></tr>
        </TBODY>
    </TABLE>

    <TABLE cellSpacing=0 cellPadding=0 width="60%" border=0>
        <TBODY>
        <TR>
            <TD width=15></TD>
            <TD align=middle width="100%" height=15></TD>
            <TD width=15></TD>
        </TR>
        </TBODY>
    </TABLE>

</FORM>
</BODY>
</html>
