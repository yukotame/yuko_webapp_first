<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"      %>
<%@ page import="yuko_webapp.model.UserInfoDto"      %>
<%@ page import="yuko_webapp.model.ProjectInfoDto"      %>

<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：project_result.jsp■■■
概要：JSP
詳細：HTML文書（検索結果一覧画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<%
//セッションからユーザーデータを取得
UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");
String UserName  = userInfoOnSession.getUserName();
String UserId  = userInfoOnSession.getUserId();
//リクエストからプロジェクトデータを取得
String menu_id = (String)request.getAttribute("MENU");
String search_id = (String)request.getAttribute("SEARCH");
System.out.println("project_result.jsp menu_id:" + menu_id);
System.out.println("project_result.jsp search_id:" + search_id);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索結果</title>
</head>
<body>
<h1>検索結果一覧画面</h1>

  <p><%= UserName %>さん　ようこそ
      <input type="hidden" name="UserName"  value="<%= UserName %>">
  </p>


  <% String LINK_URL = "";%>
  <% switch (menu_id) {
	case "1":
		break;
	case "999":
		//SLINK_URL = "InputAddParticipation";
		break;
	case "2":
		LINK_URL = "InputParticipation";
		break;
	case "3":
		LINK_URL = "InputParticipationStatus";
		break;
	} %>



	<p>
	<input type="hidden" name="UserId"  value="<%=UserId %>">
	</p>

	<!-- priject_idで案件検索した場合の検索結果出力 -->
	<%if(search_id.equals("1")){
		ProjectInfoDto p_dto1 = (ProjectInfoDto)request.getAttribute("PROJECT_LIST");
		String msg = "";
		if(p_dto1.getProjectId()==0){
			msg = "案件が検索できませんでした。";
		}else{%>
			<a href = <%=LINK_URL%>?PRO_ID=<%=p_dto1.getProjectId()%>&PRO_NAME=<%=p_dto1.getProjectName()%>&ORI_ID=<%=p_dto1.getOriginatorId()%>><%=p_dto1.getProjectId()%> <%=p_dto1.getProjectName() %></a>
		<%}%>
		<p><%=msg%></p>
	<%}%>

	<!-- 全件検索で案件検索した場合の検索結果出力 -->
	<%if(search_id.equals("2")){
		List<ProjectInfoDto> list = (List<ProjectInfoDto>)request.getAttribute("PROJECT_LIST");
		String msg = "";
		if(list.size()==0){
			msg = "案件が検索できませんでした。";
		}else{

			for(int i = 0; i < list.size(); i++) {
				ProjectInfoDto p_dto2 = list.get(i);
			%>
			 <a href = <%=LINK_URL%>?PRO_ID=<%=p_dto2.getProjectId()%>&PRO_NAME=<%=p_dto2.getProjectName()%>&ORI_ID=<%=p_dto2.getOriginatorId()%>><%=p_dto2.getProjectId()%> <%=p_dto2.getProjectName() %></a>
			 <br>
			<%}	%>
		<%}	%>
		<p><%=msg%></p>
	<%} %>



  <br>
  <a href="InputSelectProject?MENU=<%=menu_id%>">プロジェクト検索画面へ</a>
  <br>
  <a href="InputLoginMenu">ログインメニュー画面へ</a>

</body>
</html>