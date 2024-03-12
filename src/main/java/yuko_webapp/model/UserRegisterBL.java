package yuko_webapp.model;

/**----------------------------------------------------------------------*
 *■■■UserRegisterBLクラス■■■
 *概要：ビジネスロジック（ユーザー登録データの登録）
 *----------------------------------------------------------------------**/
public class UserRegisterBL {

	/**----------------------------------------------------------------------*
	 *■executeInsertメソッド
	 *概要　：対象のユーザー情報を登録する
	 *引数　：ユーザー登録データ(UserInfoDto型)
	 *戻り値：DB操作成功フラグ（true:成功/false:失敗）
	 *----------------------------------------------------------------------**/
	public boolean executeInsert(UserInfoDto dto) {

		boolean   succesInsert = false ;  //DB操作成功フラグ（true:成功/false:失敗）

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のユーザーデータを登録するよう依頼
		UserInfoDao dao = new UserInfoDao();
		succesInsert = dao.doUserInsert(dto);

		return succesInsert;
	}

}
