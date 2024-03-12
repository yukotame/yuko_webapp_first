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
 *■■■InputUserRegisterクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（ユーザー登録画面）を出力する。
 *----------------------------------------------------------------------**/
@WebServlet("/InputUserRegister")
public class InputUserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputUserRegister() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {



		//セッションからユーザーデータを取得
		HttpSession session           = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("LOGIN_INFO");

		//ログイン状態によって表示画面を振り分ける
		// ※ログイン状態はセッション上からユーザーデータを取得できたか否かで判断
		//    ユーザーデータを取得できた　　　→既にログインされている
		//    ユーザーデータを取得できなかった→まだログインされていない
		if (userInfoOnSession != null) {

			//ログイン済：ログインメニュー画面へ転送
			response.sendRedirect("InputLoginMenu");

		}else{
			//未ログイン：HTML文書（ユーザー登録画面）の出力
			//Viewにフォワード（フォワード先：user_register.jsp）
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/user_register.jsp");
			dispatch.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
