package yuko_webapp.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**----------------------------------------------------------------------*
 *■■■UserParticipationDao ■■■
 *概要：DAO（「USER_PARTICIPATION」テーブル）
 *----------------------------------------------------------------------**/
public class UserParticipationDao {
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
	 *■doUserParticipationInsertメソッド
	 *概要　：「USER_PARTICIPATION」テーブルにデータを挿入する
	 *引数　：プロジェクト参加可否データ（１プロジェクト日数分のデータ：ArrayList<UserParticipationDto>型）
	 *戻り値：実行結果（真：成功、偽：例外発生）
	 *----------------------------------------------------------------------**/

	public boolean doUserParticipationInsert(ArrayList<UserParticipationDto> user_participation_list ) {
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

		//実行結果（真：成功、偽：例外発生）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		boolean isSuccess = true ;

		try {

			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//-------------------------------------------
			//トランザクションの開始
			//-------------------------------------------
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			int cnt = 0;

			//日程数分繰り返し登録
			for(UserParticipationDto user_participation_dto : user_participation_list) {


				//-------------------------------------------
				//SQL文の送信 ＆ 結果の取得
				//-------------------------------------------

				//発行するSQL文の生成（USER_PARTICIPATIONテーブル INSERT）
				StringBuffer buf1 = new StringBuffer();
				buf1.append("INSERT INTO sankaku_db.USER_PARTICIPATION (  ");
				buf1.append("  project_id,                ");
				buf1.append("  project_name,                ");
				buf1.append("  project_date,                ");
				buf1.append("  user_id,                ");
				buf1.append("  user_name,                ");
				buf1.append("  participation                ");
				buf1.append(") VALUES (            ");
				buf1.append("  ?,                  ");
				buf1.append("  ?,                  ");
				buf1.append("  ?,                  ");
				buf1.append("  ?,                  ");
				buf1.append("  ?,                  ");
				buf1.append("  ?                  ");
				buf1.append(")                     ");

				//PreparedStatementオブジェクトを生成＆発行するSQLをセット
				ps = con.prepareStatement(buf1.toString() );

				//パラメータをセット
				ps.setInt(    1, user_participation_dto.getProjectId()           ); //第1パラメータ：更新データ（プロジェクトID）
				ps.setString( 2, user_participation_dto.getProjectName()         ); //第2パラメータ：更新データ（プロジェクト名）
				ps.setString( 3, user_participation_dto.getProjectDate()         ); //第3パラメータ：更新データ（日程）
				ps.setString( 4, user_participation_dto.getUserId()              ); //第4パラメータ：更新データ（ユーザーID）
				ps.setString( 5, user_participation_dto.getUserName()            ); //第5パラメータ：更新データ（ユーザー名）
				ps.setInt(    6, user_participation_dto.getParticipation()       ); //第6パラメータ：更新データ（参加可否プロジェクト名）

				cnt = cnt + 1;

				//SQL文の実行
				ps.executeUpdate();


			}


	         System.out.println(" USER_PARTICIPATION コミット処理を実施しました。");
	         con.commit();


		} catch (SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false ;

		} finally {
			//-------------------------------------------
			//トランザクションの終了
			//-------------------------------------------
			if(isSuccess){
				//明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}else{
				//明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//-------------------------------------------
			//接続の解除
			//-------------------------------------------

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

		//実行結果を返す
		return isSuccess;
	}

	/**----------------------------------------------------------------------*
	 *■doUserParticipationSelectメソッド
	 *概要　：入力のproject_idと一致する「USER_PARTICIPATION」テーブルのユーザー参加可否情報を抽出する
	 *引数　：project_id(入力データ)
	 *戻り値：ユーザー参加可否抽出データ（List<UserParticipationDto>型）
	 *----------------------------------------------------------------------**/

	public List<UserParticipationDto> doUserParticipationSelect(int projectId) {

		System.out.println("UserParticipationDao");
		System.out.println("doUserParticipationSelect projectId: " + projectId);


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
		List<UserParticipationDto> dtoList = new ArrayList<UserParticipationDto>();

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
			buf.append(" SELECT          ");
			buf.append("  project_id,    ");
			buf.append("  project_name,  ");
			buf.append("  project_date,  ");
			buf.append("  user_id,       ");
			buf.append("  user_name,     ");
			buf.append("  participation  ");
			buf.append(" FROM          ");
			buf.append("   USER_PARTICIPATION ");
			buf.append(" WHERE              ");
			buf.append("   project_id  = ?  ");                 //第1パラメータ
			buf.append("   ORDER BY user_id, project_date  ");  //第1ソートキー：user_id、第2ソートキー：project_date

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());

			//パラメータをセット
			ps.setInt( 1, projectId   );  //第1パラメータ：プロジェクトID

			System.out.println("doUserParticipationSelect buf: " + buf);


			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				UserParticipationDto dto = new UserParticipationDto();

				dto.setProjectId(rs.getInt( "project_id"   ));
				dto.setProjectName(rs.getString(    "project_name"    ));
				dto.setProjectDate(rs.getString( "project_date"   ));
				dto.setUserId(rs.getString(    "user_id"    ));
				dto.setUserName(rs.getString( "user_name"   ));
				dto.setParticipation(rs.getInt(    "participation"    ));

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
