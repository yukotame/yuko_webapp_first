<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="yuko_webapp.model.UserInfoDto"      %>
<%--
-------------------------------------------------------------------------------------------------
■■■ファイル名：user_register.jsp■■■
概要：JSP
詳細：HTML文書（ユーザー登録画面）を出力する。
-------------------------------------------------------------------------------------------------
--%>
<html>
<head>
  <title>ユーザー登録画面</title>
</head>
<body>
  <h2>ユーザー登録画面</h2>
  <form action="<%=request.getContextPath()%>/UserRegisterServlet" method="post">
  <% UserInfoDto udto = (UserInfoDto)request.getAttribute("userInfo");%>

  <p>ユーザーID：<br>
  	<input type="text" name="USER_ID" maxlength="20" id="ID_USER_ID">
  </p>

  <p>名前： <br>
  	<input type="text" name="NAME" maxlength = "20" id="ID_USER_NAME">
  </p>

  <p>パスワード：<br>
  	<input type="password" name="PASSWORD" maxlength="20" id="ID_PASSWORD">
  </p>

  <p>メールアドレス： <br>
  	<input type="text" name="EMAIL" maxlength = "20" id="ID_EMAIL">
  </p>


  <input type="submit" value="ユーザー登録する" id ="USE_REGISTER_SUBMIT">
  </form>


  <script type="text/javascript" src="js/user-register-check.js"></script>
</body>
</html>


