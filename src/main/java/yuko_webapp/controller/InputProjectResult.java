
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

import yuko_webapp.model.ProjectInfoDto;
import yuko_webapp.model.ProjectResultBL;
import yuko_webapp.model.UserInfoDto;

/**----------------------------------------------------------------------*
 *■■■InputProjectResultクラス■■■
 *概要：サーブレット
 *詳細：ユーザー入力値と合致するプロジェクトデータを「PROJECT」テーブルから抽出し、フォワードする。
 *　　　＜フォワード先＞project_result.jsp *　　　　　　　　　　　）
 *----------------------------------------------------------------------**/
@WebServlet("/InputProjectResult")
public class InputProjectResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InputProjectResult() {
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
			//ログイン済：プロジェクト情報検索処理＆検索結果画面への遷移を実施
			boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

			//リクエストパラメータからユーザー入力値を取得
			String project_id = request.getParameter("PROJECT_ID");
			String menu_id = request.getParameter("MENU");
			String search_id  = request.getParameter("SEARCH");//project_idで検索：１、全件検索：２

			System.out.println("InputProjectResult project_id: " + project_id);
			System.out.println("InputProjectResult menu_id: " + menu_id);
			System.out.println("InputProjectResult search_id: " + search_id);

			//入力されたプロジェクトIDと一致するプロジェクト情報を「PROJECT」テーブルから抽出
			//List<ProjectInfoDto> list  = new ArrayList<ProjectInfoDto>();

			if(search_id.equals("1")) {
				System.out.println("InputProjectResult search_id =１ ☆");
				ProjectInfoDto dto = new ProjectInfoDto();

				ProjectResultBL logic =  new ProjectResultBL();
				dto = logic.executeSelectProjectInfo(Integer.parseInt(project_id));

				//抽出結果をプロジェクトリストをリクエストスコープに保存
				request.setAttribute( "PROJECT_LIST" , dto );
				//login_menu.jspから引き継いだメニューIDをリクエストスコープに保存
				request.setAttribute( "MENU" , menu_id );
				//プロジェクトの検索の方法により、検索結果の方法を分岐するためレクエストに格納
				request.setAttribute( "SEARCH" , search_id );
			}else if(search_id.equals("2")){
				System.out.println("InputProjectResult search_id =２ ☆");
				List<ProjectInfoDto> list  = new ArrayList<ProjectInfoDto>();

				ProjectResultBL logic =  new ProjectResultBL();
				list = logic.executeSelectProjectInfoALL();

				//抽出結果をプロジェクトリストをリクエストスコープに保存
				request.setAttribute( "PROJECT_LIST" , list );
				//login_menu.jspから引き継いだメニューIDをリクエストスコープに保存
				request.setAttribute( "MENU" , menu_id );
				//プロジェクトの検索の方法により、検索結果の方法を分岐するためレクエストに格納
				request.setAttribute( "SEARCH" , search_id );

			}


			//HTML文書（プロジェクト検索結果画面）の出力
			//Viewにフォワード（フォワード先：project_result.jsp）
			RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/project_result.jsp");
			dispatch.forward(request, response);



		} else {
			//未ログイン：ログイン処理を実施
			response.sendRedirect("InputLogin");


		}
	}


}
