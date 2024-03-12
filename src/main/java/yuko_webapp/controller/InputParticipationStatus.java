package yuko_webapp.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuko_webapp.model.ParticipationStatusBL;
import yuko_webapp.model.UserInfoDto;
import yuko_webapp.model.UserParticipationDto;

/**----------------------------------------------------------------------*
 *■■■InputParticipationStatusクラス■■■
 *概要：サーブレット
 *詳細：入力されたプロジェクトIDと合致するユーザー参加可否データ「USER_PARTICIPATION」テーブルから抽出し、フォワードする。
 *      ＜フォワード先＞participation_status.jsp *　　　　　　　　　　　）
 **----------------------------------------------------------------------**/
@WebServlet("/InputParticipationStatus")
public class InputParticipationStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputParticipationStatus() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
			//ログイン済：入力されたプロジェクトIDに一致する、「USER_PARTICIPATION」テーブルを検索＆
			//参加状況確認画面に転送


			//ユーザーに選択されたプロジェクトIDを取得する
			String pro_id = request.getParameter("PRO_ID");


			//「USER_PARTICIPATION」テーブルのプロジェクトIDが一致するデータを抽出
			List<UserParticipationDto> user_participation_list = new ArrayList<UserParticipationDto>();
			ParticipationStatusBL logic =  new ParticipationStatusBL();

			//抽出結果をプロジェクト参加者リスト(user_participation_list)に格納
			//抽出結果は 第1ソートキー：user_id、第2ソートキー：project_date　でソート済み
			user_participation_list = logic.executeSelectUserParticipation(Integer.parseInt(pro_id));


			//プロジェクト参加者リストをリクエストスコープに保存
			request.setAttribute( "PARTICIPATION_LIST" , user_participation_list );

			//HTML文書（参加可否確認画面）の出力
			//Viewにフォワード（フォワード先：participation_status.jsp）
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/participation_status.jsp");
			dispatch.forward(request, response);


		} else {
			//未ログイン：ログイン処理を実施
			response.sendRedirect("InputLogin");


		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
