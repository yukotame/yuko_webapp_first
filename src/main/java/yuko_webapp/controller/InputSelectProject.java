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
 *■■■InputSelectProjectクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（プロジェクト検索画面）を出力する。
 *----------------------------------------------------------------------**/
@WebServlet("/InputSelectProject")
public class InputSelectProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputSelectProject() {
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
			//ログイン済：プロジェクト検索画面を出力


			//project_result.jspでサーブレットの振り分けをするためMENU_IDを取得
			String menu_id = request.getParameter("MENU");
			request.setAttribute("MENU" , menu_id) ;

			System.out.println("InputSelectProject menu_id: " + menu_id);




			//HTML文書（プロジェクト検索画面）の出力
			//Viewにフォワード（フォワード先：select_project.jsp）
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/select_project.jsp");
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
