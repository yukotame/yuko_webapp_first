package yuko_webapp.controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuko_webapp.model.CreateProjectBL;
import yuko_webapp.model.ProjectDateDto;
import yuko_webapp.model.ProjectInfoDto;
import yuko_webapp.model.ProjectPackDto;
import yuko_webapp.model.UserInfoDto;

/**----------------------------------------------------------------------*
 *■■■CreateProjectServletクラス■■■
 *概要：サーブレット
 *詳細：リクエスト（入力されたプロジェクトデータ）を「PRJECT」テーブルと「PRJECT_DATE」テーブルに登録し、画面遷移する。
 *　　　＜遷移先＞登録成功：プロジェクト作成完了画面（create_project_result.jsp）／登録失敗：エラー画面（error.html）
 *----------------------------------------------------------------------**/

@WebServlet("/CreateProjectServlet")
public class CreateProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateProjectServlet() {
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
			//ログイン済：プロジェクト情報登録処理＆結果画面への遷移を実施
			boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

			//パラメータのバリデーションチェック
			//★★★★★★★★★★★★★★★★★
			//バリデーションNGの場合ログイン画面へ転送

			//リクエストパラメータからユーザー入力値を取得
			String originator_name   = request.getParameter("ORIGINATOR_NAME");      //リクエストパラメータ（ORIGINATOR_NAME）
			String originator_id= request.getParameter("ORIGINATOR_ID");     //リクエストパラメータ（ORIGINATOR_ID）
			String project_name   = request.getParameter("PROJECT_NAME");      //リクエストパラメータ（ORIGINATOR_NAME）
			String[] project_dates = request.getParameterValues("PROJECT_DATE");     //リクエストパラメータ（PROJECT_DATE1）

			/*System.out.println("--- 確認 CreateProjectServlet ---");
			System.out.println("originator_name: " + originator_name);
			System.out.println("originator_id: " + originator_id);
			System.out.println("project_name: " + project_name);
			System.out.println("project_date1: " + project_dates[0]);
			System.out.println("project_date2: " + project_dates[1]);*/

			ProjectInfoDto pro_inf_dto = new ProjectInfoDto();
			pro_inf_dto.setOriginatorId( originator_id);
			pro_inf_dto.setProjectName( project_name );

			//プロジェクト日程情報を1日程毎にProjectDateDtoインスタンスを生成し、
			//プロジェクト単位にArrayListに格納する
			ArrayList<ProjectDateDto> project_date_array = new ArrayList<ProjectDateDto>();

	        for (String date : project_dates) {

	        	if(date != "") {
	        		//プロジェクト日程情報を格納
					ProjectDateDto pro_date_dto = new ProjectDateDto();
					pro_date_dto.setProjectName( project_name );
					pro_date_dto.setProjectDate( date );


		            //ArrayListに追加
		            project_date_array.add(pro_date_dto);
	        	}
	        }


	        //プロジェクト情報とArrayListに格納したプロジェクト日程情報を格納する
			ProjectPackDto pro_pack = new ProjectPackDto();
			pro_pack.setProjectInfo(pro_inf_dto);
			pro_pack.setProjectDateArray(project_date_array);


			//DB(プロジェクトテーブルとプロジェクト候補日テーブル)に登録
			CreateProjectBL logic = new CreateProjectBL();
			int projectCreateResult = logic.executeInsertProject(pro_pack);  //DB操作成功フラグ（成功：発番されたプロジェクトID/失敗：-9999）


			if (projectCreateResult > 0) {
				//成功した場合、プロジェクト作成結果完了画面を表示する
				//Viewにフォワード（フォワード先：create_project_result.jsp）
				request.setAttribute("PROJECT_INFO", projectCreateResult);
				RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/view/create_project_result.jsp");
				dispatch.forward(request, response);

			} else {
				//失敗した場合、エラー画面（error.html）を表示する
				response.sendRedirect("htmls/error.html");

			}

		} else {
			//未ログイン：ログイン処理を実施
			response.sendRedirect("InputSurvey");


		}
	}


}
