package yuko_webapp.model;

/**----------------------------------------------------------------------*
 *■■■ProjectDateDtoクラス■■■
 *概要：DTO（「PROJECT_DATE」テーブル）
 *----------------------------------------------------------------------**/
public class ProjectDateDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------

	private int    		project_id;           //プロジェクトID
	private String    	project_name;         //プロジェクト名
	private String     project_date;         //日付


	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------


	//getter/setter（対象フィールド：project_id）
	public int getProjectId() { return project_id; }
	public void setProjectId(int project_id) { this.project_id = project_id; }

	//getter/setter（対象フィールド：project_name）
	public String getProjectName() { return project_name; }
	public void setProjectName(String project_name) { this.project_name = project_name; }

	//getter/setter（対象フィールド：project_date）
	public String getProjectDate() { return project_date; }
	public void setProjectDate(String project_date) { this.project_date = project_date; }


}
