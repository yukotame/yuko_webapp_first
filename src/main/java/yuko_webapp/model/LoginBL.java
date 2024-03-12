package yuko_webapp.model;

/**----------------------------------------------------------------------*
 *■■■LoginBLクラス■■■
 *概要：ビジネスロジック（ユーザーデータの抽出）
 *----------------------------------------------------------------------**/
public class LoginBL {

	/**----------------------------------------------------------------------*
	 *■executeSelectUserInfoメソッド
	 *概要　：引数のユーザー情報に紐づくユーザーデータを「USER」テーブルから抽出する
	 *引数①：ユーザーID（ユーザー入力）
	 *引数②：ユーザーパスワード（ユーザー入力）
	 *戻り値：「USER」テーブルから抽出したユーザーデータ（UserInfoDto型）
	 *----------------------------------------------------------------------**/
	public UserInfoDto executeSelectUserInfo(String inputUserId, String inputPassWord) {

		UserInfoDto dto = new UserInfoDto();  //ユーザーデータ（UserInfoDto型）

		//DAOクラスをインスタンス化＆「USER」テーブルからユーザーデータを抽出するよう依頼
		UserInfoDao dao = new UserInfoDao();
		dto             = dao.doUserSelect(inputUserId, inputPassWord);

		//抽出したユーザーデータを戻す
		return dto;
	}

}
