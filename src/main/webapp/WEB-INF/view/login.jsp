<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：login.jsp■■■
概要：JSP
詳細：HTML文書（ログイン画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<html>
<head>
  <title>ログイン画面</title>
</head>
<body>
 <h1>ログイン画面</h1>
  <form action="LoginServlet" method="post">
    <p>ユーザーID：<br>
      <input type="text" name="USER_ID" maxlength="20" id="ID_USER_ID">
    </p>
    <p>パスワード：<br>
      <input type="password" name="PASSWORD" maxlength="20" id="ID_PASSWORD">
    </p>
    <input type="submit" value="ログイン" id="ID_SUBMIT">
  </form>
  <script type="text/javascript" src="js/login-check.js"></script>
</body>
</html>
