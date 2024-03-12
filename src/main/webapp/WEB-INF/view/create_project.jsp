<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="yuko_webapp.model.UserInfoDto"      %>
<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：create_project.jsp■■■
概要：JSP
詳細：HTML文書（プロジェクト作成画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<%
//セッションからユーザーデータを取得
UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");
String originatorName  = userInfoOnSession.getUserName();
String originatorId  = userInfoOnSession.getUserId();
int i = 1;
%>

<html>
<head>
  <title>プロジェクト作成画面</title>
</head>
<body>
 <h1>プロジェクト作成画面</h1>
  <form action="CreateProjectServlet" method="post">

    <p>発案者：<%= originatorName %>
      <input type="hidden" name="ORIGINATOR_NAME"  value="<%= originatorName %>">
    </p>
     <p>発案者ID：<%= originatorId %>
      <input type="hidden" name="ORIGINATOR_ID"  value="<%= originatorId %>">
    </p>

    <p>プロジェクト名：<br>
      <input type="text" name="PROJECT_NAME" maxlength="20" id="ID_PROJECT_NAME">
    </p>

    <p>日程：<br>
      <input type="date" name="PROJECT_DATE" maxlength="20" class="CLASS_PROJECT_DATE">
    </p>

    <p>日程：<br>
      <input type="date" name="PROJECT_DATE" maxlength="20" class="CLASS_PROJECT_DATE">
    </p>

    <p>日程：<br>
      <input type="date" name="PROJECT_DATE" maxlength="20" class="CLASS_PROJECT_DATE">
    </p>



    <input type="submit" value="プロジェクト登録" id="ID_ADD">
  </form>
  <br>
  <a href="InputLoginMenu">ログインメニュー画面へ</a>
  <br>
  <a href="ExecuteLogout">ログアウト</a>
  <script type="text/javascript" src="js/create-project-check.js"></script>
</body>
</html>
