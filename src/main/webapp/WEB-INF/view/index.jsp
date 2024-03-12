<%@ page import="java.sql.*" %>

<!-- 1. 基本 JSP 作成 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
<title>test</title>
</head>
<body>
	<h2>test DB_connect 2024/3/9</h2>

<%
//JDBCドライバの相対パス
//※バージョンによって変わる可能性があります（MySQL5系の場合は「com.mysql.jdbc.Driver」）
String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

//接続先のデータベース
//String JDBC_URL    = "jdbc:mysql://localhost/sankaku_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";

String JDBC_URL = "jdbc:mysql://localhost/sankaku_db?characterEncoding=UTF-8&useSSL=false";	//接続するユーザー名
//接続するユーザー名
String USER_ID     = "sankaku_user";

//接続するユーザーのパスワード
String USER_PASS   = "sankaku_Pass1234@";

Connection con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

Statement st = con.createStatement();

/** 5-2.SQL文の作成 **/
String sql = "SELECT * FROM USER" ;
ResultSet res =st.executeQuery(sql);

while(res.next()){
out.println("<td>" + res.getString("user_id") + "</td>");
out.println("<td>" + res.getString("user_name") + "</td>");

}

st.close();
con.close();



%>
</body>
</html>

