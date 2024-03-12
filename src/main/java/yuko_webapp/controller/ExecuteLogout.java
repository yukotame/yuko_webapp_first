package yuko_webapp.controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuko_webapp.model.UserInfoDto;

/**----------------------------------------------------------------------*
 *■■■ExecuteLogoutクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（ログアウト完了画面）を出力する。
 *----------------------------------------------------------------------**/
@WebServlet("/ExecuteLogout")
public class ExecuteLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");  //文字コードをUTF-8で設定


		//セッションからユーザーデータを取得
		HttpSession session           = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");

		//ログイン状態によって表示画面を振り分ける
		// ※ログイン状態はセッション上からユーザーデータを取得できたか否かで判断
		//    ユーザーデータを取得できた　　　→既にログインされている
		//    ユーザーデータを取得できなかった→まだログインされていない
		if (userInfoOnSession != null) {
			//ログイン済：ログアウト処理を実施

			//ログアウトに伴いセッション情報を破棄
			session.invalidate();


			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/logout.jsp");
			dispatch.forward(request, response);

		} else {
			//未ログイン：ログイン画面へ転送
			response.sendRedirect("InputLogin");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
