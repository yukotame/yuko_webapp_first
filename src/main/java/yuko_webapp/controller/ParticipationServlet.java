package yuko_webapp.controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuko_webapp.model.ParticipationBL;
import yuko_webapp.model.UserInfoDto;
import yuko_webapp.model.UserParticipationDto;

/**----------------------------------------------------------------------*
 *■■■PerticipationServletクラス■■■
 *概要：サーブレット
 *詳細：リクエスト（入力されたプロジェクト参加可否データ）を「USER_PARTICIPATION」テーブルに登録し、画面遷移する。
 *　　　＜遷移先＞登録成功：プロジェクト参加可否登録完了（participation_finish.html）／登録失敗：エラー画面（error.html）
 *----------------------------------------------------------------------**/
@WebServlet("/ParticipationServlet")
public class ParticipationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ParticipationServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");     //文字コードをUTF-8で設定
		//リクエスト（受信データ）の文字コードを設定
		request.setCharacterEncoding("UTF-8");                  //文字コードをUTF-8で設定

		//セッションからユーザーデータを取得
		HttpSession session           = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");

		//ログイン状態によって表示画面を振り分ける
		// ※ログイン状態はセッション上からユーザーデータを取得できたか否かで判断
		//    ユーザーデータを取得できた　　　→既にログインされている
		//    ユーザーデータを取得できなかった→まだログインされていない
		if (userInfoOnSession != null) {
			//ログイン済：プロジェクト参加可否の登録処理＆結果画面への遷移を実施

			boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

			//パラメータのバリデーションチェック
			//★★★★★★★★★★★★★★★★★
			//バリデーションNGの場合ログイン画面へ転送

			//リクエストパラメータからユーザー入力値を取得
			String user_id = request.getParameter("USER_ID");
			String user_name   = request.getParameter("USER_NAME");
			String project_id = request.getParameter("PROJECT_ID");
			String project_name   = request.getParameter("PROJECT_NAME");      //リクエストパラメータ（ORIGINATOR_NAME）
			String date_cnt = request.getParameter("DATE_CNT");     //リクエストパラメータ（PROJECT_DATE）

			/*System.out.println("--- 確認1 ParticipationServlet ---");
			System.out.println("project_id: " + project_id);
			System.out.println("project_name: " + project_name);
			System.out.println("user_id: " + user_id);
			System.out.println("user_name: " + user_name);
			System.out.println("date_cnt: " + date_cnt);*/


			//「USER_PARTICIPATION」登録情報を作成
			//user_participation_listに格納し、登録依頼
			ArrayList<UserParticipationDto> user_participation_list = new ArrayList<UserParticipationDto>();

			//リクエストパラメータから日程と参加可否の情報を繰り返し情報として取得&
			//ArrayList user_participation_listに日程数分格納
			for(int i= 0 ; i < Integer.parseInt(date_cnt) ; i++) {
				UserParticipationDto dto = new UserParticipationDto();
				String project_date = request.getParameter("PROJECT_DATE" + i);     //リクエストパラメータ（PROJECT_DATE）
				String participation = request.getParameter("SANKAKU" + i);     //リクエストパラメータ（SANKAKU）

				dto.setProjectId(Integer.parseInt(project_id));
				dto.setProjectName(project_name);
				dto.setProjectDate(project_date);
				dto.setUserId(user_id);
				dto.setUserName(user_name);
				dto.setParticipation(Integer.parseInt(participation));

				//ArrayListに追加
				user_participation_list.add(dto);

			}


			//DB(ユーザー参加可否テーブル「USER_PARTICIPATION」)に登録依頼
			ParticipationBL logic = new ParticipationBL();
			boolean succesInsert = logic.executeInsertUserPaticipation(user_participation_list);  //DB操作成功フラグ（true:成功/false:失敗）

			if (succesInsert) {

				//成功した場合、完了画面（participation_finish.html）を表示する
				response.sendRedirect("htmls/participation_finish.html");

			} else {

				//失敗した場合、エラー画面（error.html）を表示する
				response.sendRedirect("htmls/error.html");

			}

		} else {
			//未ログイン：ログイン処理を実施
			response.sendRedirect("InputLogin");


		}
	}


}
