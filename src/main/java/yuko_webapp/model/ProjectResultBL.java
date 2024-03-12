package yuko_webapp.model;

import java.util.ArrayList;

/**----------------------------------------------------------------------*
 *■■■ProjectResultBLクラス■■■
 *概要：ビジネスロジック（プロジェクトデータの抽出）
 *----------------------------------------------------------------------**/
public class ProjectResultBL {

	/**----------------------------------------------------------------------*
	 *■executeSelectProjectInfoメソッド
	 *概要  ：入力されたproject_idに紐づくプロジェクトデータを「PROJECT」テーブルから抽出する
	 *引数  ：プロジェクトID（ユーザー入力）
	 *戻り値：「PROJECT」テーブルから抽出したプロジェクトデータ（ProjectInfoDto型）
	 *----------------------------------------------------------------------**/
	public ProjectInfoDto executeSelectProjectInfo(int project_id) {

		//DAOクラスをインスタンス化＆「PROJECT」テーブルからプロジェクト情報を抽出するよう依頼
		ProjectInfoDao dao = new ProjectInfoDao();
		ProjectInfoDto dto  = dao.doProjectSelect(project_id);

		//抽出したプロジェクト情報を戻す
		return dto;
	}

	public ArrayList<ProjectInfoDto> executeSelectProjectInfoALL() {

		//DAOクラスをインスタンス化＆「PROJECT」テーブルからプロジェクト情報を抽出するよう依頼
		ProjectInfoDao dao = new ProjectInfoDao();
		ArrayList<ProjectInfoDto> dto_list  = dao.doProjectSelectALL();

		//抽出したプロジェクト情報を戻す
		return dto_list;
	}

}
