package yuko_webapp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**----------------------------------------------------------------------*
 *■■■InputInitialMenuクラス■■■
 *概要：サーブレット
 *詳細：HTML文書（初期メニュー画面）を出力する。
 *URL :http://localhost:8080/Sankaku_App/InputInitialMenu
 *----------------------------------------------------------------------**/

@WebServlet("/InputInitialMenu")
public class InputInitialMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//レスポンス（出力データ）の文字コードを設定
		response.setContentType("text/html;charset=UTF-8");  //文字コードをUTF-8で設定

		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		//Viewにフォワード（フォワード先：initial_menu.jsp）
		RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/initial_menu.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);


	}

}
