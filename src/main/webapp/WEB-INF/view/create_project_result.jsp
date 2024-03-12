<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：create_project_result.jsp■■■
概要：JSP
詳細：HTML文書（プロジェクト作成結果画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<%
//リクエストからユーザーデータを取得
int projectId = (int)request.getAttribute("PROJECT_INFO");
%>


<html>

<head>
<meta charset="UTF-8">
<title>プロジェクト作成結果</title>
</head>
<body>
<h1>プロジェクト作成結果画面</h1>


  <br>
  <p>プロジェクト登録完了しました。</p>
  <br>
  <p>発番されたプロジェクトID: <%=projectId %></p>

  <br>
  <a href="InputLoginMenu">ログインメニュー画面へ</a>


</body>
</html>