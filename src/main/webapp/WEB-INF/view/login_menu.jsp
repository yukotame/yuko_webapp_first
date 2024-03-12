<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import="yuko_webapp.model.UserInfoDto"      %>
<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：login_menu.jsp■■■
概要：JSP
詳細：HTML文書（ログインメニュー画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<%
//セッションからユーザーデータを取得
UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");
String UserName  = userInfoOnSession.getUserName();
String UserId  = userInfoOnSession.getUserId();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログインメニュー</title>
</head>
<body>
<h1>ログインメニュー画面</h1>

  <p>
  	<input type="hidden" name="UserId"  value="<%= UserId %>">
  </p>

  <p><%= UserName %>さん　ようこそ
  	<input type="hidden" name="UserName"  value="<%= UserName %>">
  </p>

  <br>
  	<a href="InputCreateProject?MENU=1">プロジェクトの作成</a>

  <br>
  	<a href="InputSelectProject?MENU=2">参加可否入力</a>

  <br>
  	<a href="InputSelectProject?MENU=3">参加可否確認</a>

  <br>
  <br>
	<a href="ExecuteLogout">ログアウト</a>


</body>
</html>