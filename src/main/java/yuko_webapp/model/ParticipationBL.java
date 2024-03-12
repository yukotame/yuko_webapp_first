package yuko_webapp.model;

import java.util.ArrayList;

/**----------------------------------------------------------------------*
 *■■■ParticipationBLクラス■■■
 *概要：ビジネスロジック（ユーザー参加可否情報の登録）
 *----------------------------------------------------------------------**/
public class ParticipationBL {

	/**----------------------------------------------------------------------*
	 *■executeInsertUserPaticipationメソッド
	 *概要　：引数の参加可否情報を「USER_PARTICIPATION」テーブルに登録する
	 *引数  ：ユーザー参加可否情報（ユーザー入力  ArrayList<UserParticipationDto>型）
	 *戻り値：DB操作成功フラグ（true:成功/false:失敗）
	 *----------------------------------------------------------------------**/
	public boolean executeInsertUserPaticipation(ArrayList<UserParticipationDto> user_participation_list) {

		boolean succesInsert = false ;  //DB操作成功フラグ（true:成功/false:失敗）

		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------

		//DAOクラスをインスタンス化＆対象のユーザー参加可否情報を登録するよう依頼
		UserParticipationDao dao = new UserParticipationDao();

		/*for(int i =0 ; i < 2 ; i++) {
			UserParticipationDto dto = user_participation_list.get(i);
			System.out.println("--- 確認 ParticipationBL ---");
			System.out.println("CNT: " + i);
			System.out.println("project_id: " + dto.getProjectId());
			System.out.println("project_name: " + dto.getProjectName());
			System.out.println("project_date: " + dto.getProjectDate() );
			System.out.println("user_id: " + dto.getUserId());
			System.out.println("user_name: " + dto.getUserName());
			System.out.println("participation: " + dto.getParticipation());
			i = i + 1;
		}*/
		succesInsert = dao.doUserParticipationInsert(user_participation_list);

		return succesInsert;

	}

}
