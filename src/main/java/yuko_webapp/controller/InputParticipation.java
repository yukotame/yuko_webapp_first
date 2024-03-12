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

import yuko_webapp.model.InputParticipationBL;
import yuko_webapp.model.ProjectDateDto;
import yuko_webapp.model.UserInfoDto;

/**----------------------------------------------------------------------*
 *■■■InputParticipationクラス■■■
 *概要：サーブレット
 *詳細：ユーザー入力値と合致するプロジェクト日程データを「PROJECT_DATE」テーブルから抽出し、フォワードする。
 *　　　＜フォワード先＞participation.jsp *　　　　　　　　　　　）
 *----------------------------------------------------------------------**/

@WebServlet("/InputParticipation")
public class InputParticipation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputParticipation() {
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
			//ログイン済：参加可否入力画面を出力
			request.setAttribute("userInfo", userInfoOnSession) ;


			//jspからServletへforwardするテスト
			//String aaa = (String)request.getAttribute("test");
			//System.out.println(aaa);
			//String bbb = (String)request.getParameter("pram");
			//System.out.println(bbb);
			//jspからServletへforwardするテスト

			//リクエストパラメータから選択されたプロジェクト情報を取得
			String projectId   = request.getParameter("PRO_ID");      //リクエストパラメータ（PRO_ID）
			String projectName   = request.getParameter("PRO_NAME");  //リクエストパラメータ（PRO_NAME）
			String originatorId   = request.getParameter("ORI_ID");   //リクエストパラメータ（ORI_ID）

			/*System.out.println("InputAddParticipation確認");
			System.out.println("projectId : " + projectId);
			System.out.println("projectName : " + projectName);
			System.out.println("originatorId : " + originatorId);*/


			//「PROJECT_DATE」テーブルのプロジェクトIDが一致するデータを抽出
			List<ProjectDateDto> date_list  = new ArrayList<ProjectDateDto>();
			InputParticipationBL logic =  new InputParticipationBL();
			date_list = logic.executeSelectProjectDate(Integer.parseInt(projectId));


			//抽出結果をparticipation.jspへフォワードする
			request.setAttribute("select_project_dates" , date_list) ;


			//HTML文書（参加可否入力画面）の出力
			//Viewにフォワード（フォワード先：participation.jsp）
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/participation.jsp");
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
