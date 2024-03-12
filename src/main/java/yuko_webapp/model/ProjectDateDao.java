package yuko_webapp.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**----------------------------------------------------------------------*
 *■■■ProjectDateDao■■■
 *概要：DAO（「PROJECT_DATE」テーブル）
 *----------------------------------------------------------------------**/
public class ProjectDateDao {
	//-------------------------------------------
	//データベースへの接続情報
	//-------------------------------------------



	//JDBCドライバの相対パス
	//※バージョンによって変わる可能性があります（MySQL5系の場合は「com.mysql.jdbc.Driver」）
	String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	//接続先のデータベース
	//Windowsで実行する場合
	//String JDBC_URL    = "jdbc:mysql://localhost/sankaku_db?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";

	//Linuxで実行する場合
	String JDBC_URL = "jdbc:mysql://localhost/sankaku_db?characterEncoding=UTF-8&useSSL=false";



	//接続するユーザー名
	String USER_ID     = "sankaku_user";

	//接続するユーザーのパスワード
	String USER_PASS   = "sankaku_Pass1234@";


	//----------------------------------------------------------------
	//メソッド
	//----------------------------------------------------------------

	/**----------------------------------------------------------------------*
	 *■doProjectDateSelectメソッド
	 *概要　：projectIdに一致する「PROJECT_DATE」テーブルのレコードを抽出する
	 *引数　：プロジェクトID
	 *戻り値：抽出データ（List<ProjectInfoDto>型）
	 *----------------------------------------------------------------------**/

	public List<ProjectDateDto> doProjectDateSelect(int projectId ) {
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//-------------------------------------------
		//SQL発行
		//-------------------------------------------

		//JDBCの接続に使用するオブジェクトを宣言
		//※finallyブロックでも扱うためtryブロック内で宣言してはいけないことに注意
		Connection        con = null ;   // Connection（DB接続情報）格納用変数
		PreparedStatement ps  = null ;   // PreparedStatement（SQL発行用オブジェクト）格納用変数
		ResultSet         rs  = null ;   // ResultSet（SQL抽出結果）格納用変数

		//抽出データ（List型）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		List<ProjectDateDto> dtoList = new ArrayList<ProjectDateDto>();

		try {

			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------

			//発行するSQL文の生成（SELECT）
			StringBuffer buf = new StringBuffer();
			buf.append(" SELECT        ");
			buf.append("   project_id ,    ");
			buf.append("   project_date  ,    ");
			buf.append("   project_name    ");
			buf.append(" FROM          ");
			buf.append("   PROJECT_DATE ");
			buf.append(" WHERE          ");
			buf.append("   project_id = ");
			buf.append("   ? ");

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());

			ps.setInt(    1, projectId              ); //第1パラメータ：更新データ（プロジェクトID）

			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				ProjectDateDto dto = new ProjectDateDto();
				dto.setProjectId(    rs.getInt(    "project_id"    ) );
				dto.setProjectName(   rs.getString( "project_name"   ) );
				dto.setProjectDate(rs.getString( "project_date" ));
				dtoList.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

			//ResultSetオブジェクトの接続解除
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//PreparedStatementオブジェクトの接続解除
			if (ps != null) {    //接続が確認できている場合のみ実施
				try {
					ps.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//Connectionオブジェクトの接続解除
			if (con != null) {    //接続が確認できている場合のみ実施
				try {
					con.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		//抽出データを戻す
		return dtoList;
	}


}
