package yuko_webapp.model;


/**----------------------------------------------------------------------*
 *■■■UserInfoDtoクラス■■■
 *概要：DTO（「USER」テーブル）
 *----------------------------------------------------------------------**/
public class UserInfoDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------
	private String userId;         //ユーザーID
	private String userName;       //ユーザー名
	private String passWord;       //ユーザーパスワード
	private String email;          //メールアドレス


	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------

	//getter/setter（対象フィールド：userId）
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	//getter/setter（対象フィールド：userName）
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	//getter/setter（対象フィールド：passWord）
	public String getPassWord() { return passWord; }
	public void setPassWord(String passWord) { this.passWord = passWord; }

	//getter/setter（対象フィールド：email）
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

}
