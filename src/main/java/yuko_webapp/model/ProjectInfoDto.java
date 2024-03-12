package yuko_webapp.model;

/**----------------------------------------------------------------------*
 *■■■ProjectInfoDtoクラス■■■
 *概要：DTO（「PROJECT」テーブル）
 *----------------------------------------------------------------------**/
public class ProjectInfoDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------

	private int       project_id;           //プロジェクトID
	private String    project_name;         //プロジェクト名
	private String    originator_id ;       //発案者

	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------



	//getter/setter（対象フィールド：project_id）
	public int getProjectId() { return project_id; }
	public void setProjectId(int project_id) { this.project_id = project_id; }



	//getter/setter（対象フィールド：project_name）
	public String getProjectName() { return project_name; }
	public void setProjectName(String project_name) { this.project_name = project_name; }

	//getter/setter（対象フィールド：originator_id）
	public String getOriginatorId() { return originator_id; }
	public void setOriginatorId(String originator_id) { this.originator_id = originator_id; }

}
