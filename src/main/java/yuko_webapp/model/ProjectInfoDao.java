package yuko_webapp.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**----------------------------------------------------------------------*
 *■■■ProjectInfoDaoクラス■■■
 *概要：DAO（「PROJECT」テーブル）
 *----------------------------------------------------------------------**/
public class ProjectInfoDao {
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
	//接続するユーザー名
	String USER_ID     = "sankaku_user";

	//接続するユーザーのパスワード
	String USER_PASS   = "sankaku_Pass1234@";


	//----------------------------------------------------------------
	//メソッド
	//----------------------------------------------------------------

	/**----------------------------------------------------------------------*
	 *■doProjectInsertメソッド
	 *概要　：「PROJECT」テーブルと「PROJECT_DATE」テーブルに対象のプロジェクトデータを挿入する
	 *引数　：対象のプロジェクトデータ（ProjectPackDto型）
	 *戻り値：実行結果（成功：プロジェクトID、例外発生：-9999）
	 *----------------------------------------------------------------------**/
	public int doProjectInsert(ProjectPackDto pro_pack) {

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

		//実行結果（成功：プロジェクトID、例外発生：-9999）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		int  projectCreateResult = -1;

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

			ProjectInfoDto pro_info_dto = pro_pack.getProjectInfo();

			ArrayList<ProjectDateDto> project_date_array = pro_pack.getProjectDateArray();

			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------

			//発行するSQL文の生成（「PROJECT」テーブル INSERT）
			StringBuffer buf1 = new StringBuffer();
			buf1.append("INSERT INTO sankaku_db.PROJECT (  ");
			buf1.append("  project_name,                ");
			buf1.append("  originator_id                ");
			buf1.append(") VALUES (            ");
			buf1.append("  ?,                  ");
			buf1.append("  ?                  ");
			buf1.append(")                     ");

			//PreparedStatementオブジェクトを生成＆発行するSQLをセット
			ps = con.prepareStatement(buf1.toString() , java.sql.Statement.RETURN_GENERATED_KEYS);

			//パラメータをセット
			ps.setString(    1, pro_info_dto.getProjectName()               ); //第1パラメータ：更新データ（プロジェクト名）
			ps.setString(    2, pro_info_dto.getOriginatorId()              ); //第2パラメータ：更新データ（発案者）

			//SQL文の実行
			ps.executeUpdate();

	        int autoIncKeyFromApi = -1;
	        // getGeneratedKeys()により、Auto_IncrementされたIDを取得する
	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	             autoIncKeyFromApi = rs.getInt(1);
	        } else {
	             // throw an exception from here
	        }

			System.out.println("--- 確認 ProjectInfoDao ---");
	        System.out.println("発番されたProject_id:"  + autoIncKeyFromApi);

			pro_info_dto.setProjectId(autoIncKeyFromApi);

			for(ProjectDateDto project_date : project_date_array) {
					ProjectDateDto pro_date_dto = project_date;

				pro_date_dto.setProjectId(autoIncKeyFromApi);


				System.out.println("--- パラメータ確認 ProjectInfoDao ---");
				System.out.println("autoIncKeyFromApi :" + autoIncKeyFromApi);
				System.out.println("pro_date_dto.getDate1() :" + pro_date_dto.getProjectDate());
				System.out.println("pro_date_dto.getProjectName() :" + pro_date_dto.getProjectName());

				//発行するSQL文の生成（「PROJECT_DATE」テーブル INSERT）
				StringBuffer buf2 = new StringBuffer();
				buf2.append("INSERT INTO sankaku_db.PROJECT_DATE (  ");
				buf2.append("  project_id,                ");
				buf2.append("  project_date,                ");
				buf2.append("  project_name                ");
				buf2.append(") VALUES (            ");
				buf2.append("  ?,                  ");
				buf2.append("  ?,                  ");
				buf2.append("  ?                  ");
				buf2.append(")                     ");

				//PreparedStatementオブジェクトを生成＆発行するSQLをセット
				ps = con.prepareStatement(buf2.toString() );

				//パラメータをセット
				ps.setInt(    1, autoIncKeyFromApi              ); //第1パラメータ：更新データ（発案者）
				ps.setString( 2, pro_date_dto.getProjectDate()  ); //第2パラメータ：更新データ（日程）
				ps.setString( 3, pro_date_dto.getProjectName()  ); //第3パラメータ：更新データ（プロジェクト名）

				//SQL文の実行
				ps.executeUpdate();

			}
			System.out.println("コミット処理を実施しました。");
			con.commit();

			//プロジェクトテーブルを登録できた場合は、プロジェクトIDをreturnする
			projectCreateResult = autoIncKeyFromApi;


		} catch (SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			//SQLでエラーになった場合は、projectCreateResultに"-9999"をセットしreturnする
			projectCreateResult = -9999;

		} finally {
			//-------------------------------------------
			//トランザクションの終了
			//-------------------------------------------
			if(projectCreateResult > 0){
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


		return projectCreateResult;
	}

	//----------------------------------------------------------------
	//メソッド
	//----------------------------------------------------------------

	/**----------------------------------------------------------------------*
	 *■doProjectSelectALLメソッド
	 *概要　：「project」テーブルのレコードをすべて抽出する
	 *引数　：なし
	 *戻り値：抽出データ（List<ProjectInfoDto>型）
	 *----------------------------------------------------------------------**/

	public ArrayList<ProjectInfoDto> doProjectSelectALL() {

		System.out.println("doProjectSelectALLメソッド開始：");
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
		ArrayList<ProjectInfoDto> dtoList = new ArrayList<ProjectInfoDto>();

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
			buf.append("   project_name  ,    ");
			buf.append("   originator_id    ");
			buf.append(" FROM          ");
			buf.append("   PROJECT ");

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());


			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				ProjectInfoDto dto = new ProjectInfoDto();

				dto.setProjectId(   rs.getInt(    "project_id"    ));
				dto.setProjectName( rs.getString( "project_name"   ));
				dto.setOriginatorId(rs.getString( "originator_id" ));

				System.out.println("doProjectSelectALL project_id ：" + rs.getInt(    "project_id"    ));
				System.out.println("doProjectSelectALL project_name ：" + rs.getString( "project_name"   ));

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


	/**----------------------------------------------------------------------*
	 *■doProjectSelectメソッド
	 *概要　：入力のproject_idと一致する「PROJECT」テーブルのデータを抽出する
	 *引数　：project_id(入力データ)
	 *戻り値：抽出データ（ProjectInfoDto型）
	 *----------------------------------------------------------------------**/

	public ProjectInfoDto doProjectSelect(int project_id) {
		System.out.println("doProjectSelectメソッド開始：");
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

		//抽出データ（ProjectInfoDto型）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		ProjectInfoDto dto = new ProjectInfoDto();

		try {

			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------

			System.out.println("SQL文の生成test");

			Statement st = con.createStatement();


			//発行するSQL文の生成（SELECT）
			StringBuffer buf = new StringBuffer();
			buf.append(" SELECT        ");
			buf.append("   project_id ,    ");
			buf.append("   project_name  ,    ");
			buf.append("   originator_id    ");
			buf.append(" FROM          ");
			buf.append("   PROJECT ");
			buf.append(" WHERE          ");
			buf.append("   project_id = ");
			buf.append("   ? ");
			System.out.println(buf.toString());

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
			ps = con.prepareStatement(buf.toString());

			ps.setInt(    1, project_id               ); //第1パラメータ：更新データ（プロジェクトID）

			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			if(rs.next()){

				dto.setProjectId(    rs.getInt(    "project_id"    ) );
				dto.setProjectName(   rs.getString( "project_name"   ) );
				dto.setOriginatorId(rs.getString( "originator_id" ));

				System.out.println("doProjectSelect project_id ：" + rs.getInt(    "project_id"    ));
				System.out.println("doProjectSelect project_name ：" + rs.getString( "project_name"   ));

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
		return dto;
	}


}
