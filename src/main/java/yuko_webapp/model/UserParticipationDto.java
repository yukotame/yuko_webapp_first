package yuko_webapp.model;

/**----------------------------------------------------------------------*
 *■■■UserParticipationDtoクラス■■■
 *概要：DTO（「USER_PARTICIPATION」テーブル）
 *----------------------------------------------------------------------**/
public class UserParticipationDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------

	private int    		project_id;       //プロジェクトID
	private String    	project_name;     //プロジェクト名
	private String     project_date;     //日付
	private String     user_id;          //ユーザー名(参加候補者ID)
	private String     user_name;        //ユーザー名(参加候補者)
	private int        participation;    //参加可否

	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------


	//getter/setter（対象フィールド：project_id）
	public int getProjectId() { return project_id; }
	public void setProjectId(int project_id) { this.project_id = project_id; }

	//getter/setter（対象フィールド：project_name）
	public String getProjectName() { return project_name; }
	public void setProjectName(String project_name) { this.project_name = project_name; }

	//getter/setter（対象フィールド：date）
	public String getProjectDate() { return project_date; }
	public void setProjectDate(String project_date) { this.project_date = project_date; }

	//getter/setter（対象フィールド：user_id）
	public String getUserId() { return user_id; }
	public void setUserId(String user_id) { this.user_id = user_id; }

	//getter/setter（対象フィールド：user_name）
	public String getUserName() { return user_name; }
	public void setUserName(String user_name) { this.user_name = user_name; }

	//getter/setter（対象フィールド：project_id）
	public int getParticipation() { return participation; }
	public void setParticipation(int participation) { this.participation = participation; }


}
