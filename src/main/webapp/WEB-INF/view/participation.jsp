
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"      %>
<%@ page import="yuko_webapp.model.UserInfoDto"      %>
<%@ page import="yuko_webapp.model.ProjectDateDto"      %>
<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：participation.jsp■■■
概要：JSP
詳細：HTML文書（参加可否入力画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<%
//セッションからユーザーデータを取得
UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");
String userId  = userInfoOnSession.getUserId();
String userName  = userInfoOnSession.getUserName();
//リクエストからプロジェクト日程データを取得
List<ProjectDateDto> project_dato_list = (List<ProjectDateDto> )request.getAttribute("select_project_dates");
%>


<html>
<head>
  <title>参加可否入力画面</title>
</head>
<body>
 <h1>参加可否入力画面</h1>

  <form action="ParticipationServlet" method="get">

    <p>ユーザー名：<%= userId %>  <%= userName %>
    	<input type="hidden" name="USER_ID"  value="<%= userId %>">
      	<input type="hidden" name="USER_NAME"  value="<%= userName %>">
    </p>

	<%
    for (int i = 0; i < project_dato_list.size(); i++) {
	ProjectDateDto dto = project_dato_list.get(i);
	%>
		<%if(i==0){  %>
		    <p>プロジェクト名：<%= dto.getProjectId() %> <%= dto.getProjectName() %><br>
		    	<input type="hidden" name="PROJECT_ID"  value="<%= dto.getProjectId() %>">
		      	<input type="hidden" name="PROJECT_NAME" value="<%= dto.getProjectName() %>">
		    </p>
		<%}%>
	<%}%>


	<table class="surbey_list" border=1>
	    <tr bgcolor="#c0c0c0">
	      <th>日程</th>
	      <th>参加可否</th>
	    </tr>

		<%
		int date_cnt = 0;
		for (int i = 0; i < project_dato_list.size(); i++) {
		ProjectDateDto dto = project_dato_list.get(i);
		date_cnt = date_cnt + 1;
		%>
		<tr>
		  <td>
		  	<input type="hidden" name="PROJECT_DATE<%=i%>"  value="<%= dto.getProjectDate() %>"><%= dto.getProjectDate() %>
		  </td>
		  <td>
		      <input type="radio" name="SANKAKU<%=i%>" id =<%= dto.getProjectDate() %> value="1" checked>参加
		 	<input type="radio" name="SANKAKU<%=i%>" id =<%= dto.getProjectDate() %> value="2">不参加
		 	<input type="radio" name="SANKAKU<%=i%>" id =<%= dto.getProjectDate() %> value="3">未定
		  </td>

		</tr>
		<%}%>
    </table>
	<input type="hidden" name="DATE_CNT"  value="<%= date_cnt%>">

  	<br>
	<input type="submit" value="参加者追加" id="ID_SUBMIT">
  </form>

  <br>
  <a href="InputLoginMenu">ログインメニュー画面へ</a>

  <script type="text/javascript" src="js/login-check.js"></script>

</body>
</html>
