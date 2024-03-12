package yuko_webapp.model;

import java.util.List;

/**----------------------------------------------------------------------*
 *■■■ParticipationStatusBLクラス■■■
 *概要：ビジネスロジック（ユーザー参加可否データの抽出）
 *----------------------------------------------------------------------**/
public class ParticipationStatusBL {

	/**----------------------------------------------------------------------*
	 *■executeSelectUserInfoメソッド
	 *概要：引数のプロジェクトIDに紐づくユーザー参加可否情報を「USER_PARTICIPATION」テーブルから抽出する
	 *引数：プロジェクトID（ユーザー入力）
	 *戻り値：「USER_PARTICIPATION」テーブルから抽出したユーザー参加可否情報（List<UserParticipationDto>型）
	 *         ※ 第1ソートキー：user_id、第2ソートキー：project_date　でソート済み
	 *----------------------------------------------------------------------**/
	public List<UserParticipationDto> executeSelectUserParticipation(int projectId) {

		//DAOクラスをインスタンス化＆「USER_PARTICIPATION」テーブルからユーザー参加可否情報を抽出するよう依頼
		UserParticipationDao dao = new UserParticipationDao();
		List<UserParticipationDto> user_participation_list  = dao.doUserParticipationSelect(projectId);

		//抽出したユーザーデータを戻す
		return user_participation_list;
	}

}
