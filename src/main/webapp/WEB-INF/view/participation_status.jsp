<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"      %>
<%@ page import="yuko_webapp.model.UserInfoDto"      %>
<%@ page import="yuko_webapp.model.UserParticipationDto"      %>
<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：participation_status.jsp■■■
概要：JSP
詳細：HTML文書（参加状況確認画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<%
//セッションからユーザーデータを取得
UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");
String userId  = userInfoOnSession.getUserId();
String userName  = userInfoOnSession.getUserName();

List<UserParticipationDto> user_participation_list = (List<UserParticipationDto>)request.getAttribute("PARTICIPATION_LIST");

%>



<html>
<head>
  <title>参加可否一覧画面</title>
</head>
<body>
 <h1>参加可否一覧画面</h1>
  <form action="ParticipationServlet" method="post">


	<!-- ユーザー名とプロジェクト名を出力 -->
    <p>ユーザー名：<%= userId %>  <%= userName %> </p>
	<%
    for (int i = 0; i < user_participation_list.size(); i++) {
    	UserParticipationDto dto = user_participation_list.get(i);
	%>
	<%if(i==0){  %>
	    <p>プロジェクト名：<%= dto.getProjectId() %> <%= dto.getProjectName() %><br>
		</p>
	<%}%>

	<%}%>


	<!--参加可否一覧テーブルの出力 -->
	<table class="surbey_list" border=1>
	    <tr bgcolor="#c0c0c0">
	        <th>参加候補者</th>
	        <%-- ヘッダーの日程をp_date_listsに格納 --%>
			<%
			ArrayList<String> p_date_lists = new ArrayList<String>();
			for (int y = 0 ; y < user_participation_list.size() ; y++) {
				UserParticipationDto  dto = user_participation_list.get(y);
				String pro_date = dto.getProjectDate();
				if(y == 0){

					p_date_lists.add(pro_date);
				}

				boolean add_list_switch = false;
				for( String date : p_date_lists){
					if(date.equals(pro_date)){
						add_list_switch = true;
					}
				}

				if(!add_list_switch){
					p_date_lists.add(pro_date);
				}
			}%>
			<%-- ヘッダーの日程を出力 --%>
			<%
			for( String date : p_date_lists){%>
				<th><%= date %></th>
			<%} %>
	    </tr>


		<%-- 1ユーザーに対して1列のデータを出力 --%>
		<%-- ユーザー名が同じかを判定し、trタグつける --%>
		<%
		int i = 0;
		for (int j = 0; j < user_participation_list.size(); j++){

			UserParticipationDto  work_dto = user_participation_list.get(i);
			UserParticipationDto  dto = user_participation_list.get(j);
			String work_name = work_dto.getUserName();
			String user_name = dto.getUserName();
			String p_name = dto.getProjectName();
			String p_date = dto.getProjectDate();
			String work_participation="";
			//出力内容の分岐（性別）
			switch( dto.getParticipation() ){
				case 1:
		        	work_participation = "参加";
					break;
				case 2:
					work_participation = "不参加";
					break;
				case 3:
					work_participation = "未定";
					break;
			}

			//参加可否表参加者と参加可否を出力(表のメイン部分)
			 if(j == 0){%>
			  <tr>
			  	<td><%= dto.getUserName()%> </td>
				<td><%= work_participation%> </td>
			<%}else if( work_name.equals(user_name)){%>
				<td><%= work_participation %></td>
			<%}else{%>
			</tr>
			<tr>
				<td><%= dto.getUserName()%> </td>
				<td><%= work_participation%> </td>
			<%}%>
			<%  i = j; %>
		<%}%>

		</tr>
    </table>

  <br>
  </form>
  <br>
  <a href="InputLoginMenu">メニューに戻る</a>
  <br>
  <a href="ExecuteLogout">ログアウト</a>
  <script type="text/javascript" src="js/login-check.js"></script>
</body>
</html>
