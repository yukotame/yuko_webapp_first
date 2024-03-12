package yuko_webapp.model;

import java.util.ArrayList;

/**----------------------------------------------------------------------*
 *■■■ProjectPackDtoクラス■■■
 *概要：DTO（「PROJECT」テーブル,「PROJECT_DATE」テーブル）
 *----------------------------------------------------------------------**/
public class ProjectPackDto {

	//----------------------------------------------------------------
	//フィールド
	//----------------------------------------------------------------

	private ProjectInfoDto    project_info;           //プロジェクト情報
	private ArrayList<ProjectDateDto>    project_date_array;         //プロジェクト日程情報

	//----------------------------------------------------------------
	//getter/setter
	//----------------------------------------------------------------


	//getter/setter（対象フィールド：project_info）
	public ProjectInfoDto getProjectInfo() { return project_info; }
	public void setProjectInfo(ProjectInfoDto project_info) { this.project_info = project_info; }

	//getter/setter（対象フィールド：project_date）
	public ArrayList<ProjectDateDto> getProjectDateArray() { return project_date_array; }
	public void setProjectDateArray(ArrayList<ProjectDateDto> project_date_array) { this.project_date_array = project_date_array; }




}
