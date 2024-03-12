package yuko_webapp.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuko_webapp.model.LoginBL;
import yuko_webapp.model.UserInfoDto;


/**----------------------------------------------------------------------*
 *■■■LoginServletクラス■■■
 *概要：サーブレット
 *詳細：「USER」テーブルからユーザー入力値と合致するユーザーデータを抽出し、リダイレクトする。
 *　　　＜リダイレクト先＞合致データあり（ログインOK）：InputLoginMenuサーブレット
 *　　　　　　　　　　　　合致データなし（ログインNG）：InputLoginサーブレット（前画面に戻る）
 *----------------------------------------------------------------------**/
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
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

			//ログイン済：ログインメニュー画面に転送
			response.sendRedirect("InputLoginMenu");

		} else {
			//未ログイン：ログイン処理を実施

			boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

			//パラメータのバリデーションチェック
			//★★★★★★★★★★★★★★★★★
			//バリデーションNGの場合ログイン画面へ転送



			//リクエストパラメータからユーザー入力値を取得
			String userId   = request.getParameter("USER_ID");      //リクエストパラメータ（USER_ID）
			String passWord = request.getParameter("PASSWORD");     //リクエストパラメータ（PASSWORD）

			//「USER」テーブルからユーザー入力値と合致するユーザーデータ（UserInfoDto型）を抽出
			// ※合致するデータがなかった場合、各フィールドがnullのDTOを得る
			LoginBL logic = new LoginBL();
			UserInfoDto   dto   = logic.executeSelectUserInfo(userId, passWord);



			//ユーザーデータの抽出成功/失敗に応じて表示させる画面を振り分ける
			if (dto.getUserId() != null) {
				//ユーザーデータの抽出に成功：ログインOKとしてセッションにユーザーデータをセット＆ログインメニュー画面へ

				//「USER」テーブルから抽出したユーザデータをセッションにセット
				session.setAttribute("LOGIN_INFO", dto);

				//ログインメニュー画面へ転送
				response.sendRedirect("InputLoginMenu");

			} else {
				//ユーザーデータの抽出に失敗：ログインNGとしてログイン画面へ転送
				response.sendRedirect("InputLogin");

			}

		}
	}
}
