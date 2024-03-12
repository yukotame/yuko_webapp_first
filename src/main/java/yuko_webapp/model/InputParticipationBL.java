
package yuko_webapp.model;


import java.util.List;

public class InputParticipationBL {
	/**----------------------------------------------------------------------*
	 *■executeSelectProjectDateメソッド
	 *概要　：対象のプロジェクト日程データを抽出する
	 *引数　：プロジェクトID（int型）
	 *戻り値：プロジェクト日程リスト（List<ProjectDateDto>型）
	 *----------------------------------------------------------------------**/
	public List<ProjectDateDto> executeSelectProjectDate(int projectId) {

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のプロジェクトの日程を取得するように依頼
		ProjectDateDao dao = new ProjectDateDao();
		List<ProjectDateDto> date_dtolist = dao.doProjectDateSelect(projectId);



		return date_dtolist;
	}

}
