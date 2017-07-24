<%@ page import="java.util.List" %>
<%@ page import="com.yijiajiao.root.manage.model.RouterModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD id=Head1><TITLE>模板</TITLE>
  <%
    String condition = (condition = (String )request.getAttribute("condition"))==null?"":condition;
    String keyWord = (keyWord = (String) request.getAttribute("keyWord"))==null?"":keyWord;
    String basePath = request.getContextPath();
  %>
  <META http-equiv=Content-Type content="text/html; charset=utf-8">
  <META content="MSHTML 6.00.2900.3492" name=GENERATOR></HEAD>
  <script src ="https://code.jquery.com/jquery-3.1.1.min.js"></script>

  <script>
    $(document).ready(function () {
        $("#add").click(function () {
            window.location.href="/detail.jsp"
        });
        $("#search").click(function () {
            var condition = $("#sClient").val();
            var keyWord = $("#keyWord").val();
            if (trimStr(condition) == ''){
                alert("请选择搜索条件！");
                return;
            }
            if(trimStr(keyWord) == ''){
                alert("请输入搜索关键字！");
                return;
            }
            window.location.href="/routers.action?condition="+condition+"&keyWord="+keyWord;
        });
    });
    function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}
  </script>
  <BODY>
    <FORM id=form1 name=form1 action="" method=post>
      <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
          <TR>
            <TD width=15></TD>
            <TD width="100%" height=20></TD>
            <TD width=15></TD>
          </TR>
        </TBODY>
      </TABLE>

      <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
          <TR>
            <TD width=15 ></TD>
            <TD vAlign=top width="100%" bgColor=#ffffff>
              <TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
                <TR>
                  <TD class=manageHead>当前位置：管理中心 &gt; 路径列表</TD></TR>
                <TR>
                  <TD height=2></TD>
                </TR>
              </TABLE>
              <TABLE borderColor=#cccccc cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
                <TBODY>
                  <TR>
                    <TD height=25>
                      <TABLE cellSpacing=0 cellPadding=2 border=0>
                        <TBODY>
                          <TR>
                            <TD>
                              <SELECT id=sClient name=condition>
                                <%
                                  if ("".equals(condition)){
                                      %>
                                <OPTION selected>搜索条件</OPTION>
                                <OPTION value=requestMethod>requestMethod</OPTION>
                                <%
                                  } else {
                                %>
                                <option value="<%=condition%>"><%=condition%></option>
                                <%
                                  }
                                %>
                                <OPTION value=requestURL>requestURL</OPTION>
                                <OPTION value=mappingURL>mappingURL</OPTION>
                                <OPTION value=requestStatus>requestStatus</OPTION>
                              </SELECT>
                            </TD>
                            <TD><INPUT class=textbox id=keyWord style="height: 20.5px; WIDTH:100px" maxLength=50 name=sChannel2 value=<%=keyWord%>></TD>
                            <TD><INPUT class=button id=search type=button value="搜索" name=sButton2></TD>
                            <TD><input class=button id=add type="button" value="添加"/></TD>
                          </TR>
                        </TBODY>
                      </TABLE>
                    </TD>
                  </TR>
                  <TR>
                    <TD>
                      <TABLE id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
                        <TBODY>
                        <TR style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
                          <td>序号</td>
                          <TD>requestURL</TD>
                          <TD>requestMethod</TD>
                          <TD>requestStatus</TD>
                          <TD>mappingURL</TD>
                          <TD>routerStatus</TD>
                          <TD>replaceRegex</TD>
                          <TD>decription</TD>
                          <TD>操作</TD></TR>
                        <%
                          List<RouterModel> list = (List<RouterModel>) request.getAttribute("routers");
                          int num = 1;
                          for (RouterModel ro : list){
                        %>
                        <TR style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                          <td><%=num%></td>
                          <TD><%=ro.getRequestUrl()%></TD>
                          <TD><%=ro.getRequestMethod()%></TD>
                          <TD><%=ro.getRequestStatus()%></TD>
                          <TD><%=ro.getMappingUrl()%></TD>
                          <TD><%=ro.getRouterStatus()%></TD>
                          <TD><%=ro.getReplaceRegex()%></TD>
                          <td><%=ro.getDescription()%></td>
                          <TD>
                            <A href="/detail.jsp?requestURL=<%=ro.getRequestUrl()%>&requestMethod=<%=ro.getRequestMethod()%>&mappingURL=<%=ro.getMappingUrl()%>&routerStatus=<%=ro.getRouterStatus()%>&requestStatus=<%=ro.getRequestStatus()%>&replaceRegex=<%=ro.getReplaceRegex()%>&requestId=<%=ro.getRequestId()%>&description=<%=ro.getDescription()%>">
                              编辑</A>
                            <A href="/saveOrUpdate.action?requestId=<%=ro.getRequestId()%>&type=0">删除</A>
                          </TD>
                        </TR>
                        <%
                            num++;
                          }

                        %>
                        </TBODY>
                      </TABLE>
                    </TD>
                  </TR>
                </TBODY>
              </TABLE>
            </TD>
            <TD width=15 ></TD>
          </TR>
        </TBODY>
      </TABLE>

      <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
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
