<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：logout.jsp■■■
概要：JSP
詳細：HTML文書（ログアウト画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>

<html>
<head>
  <title>ログアウト完了</title>
</head>
<body>
 <h1>ログアウト完了画面</h1>
  <p>ログアウトしました。</p>
  <a href="<%=request.getContextPath()%>/InputLogin">ログイン画面に戻る</a>
</body>
</html>

