package yuko_webapp.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import yuko_webapp.model.UserInfoDto;
import yuko_webapp.model.UserRegisterBL;

/**----------------------------------------------------------------------*
 *■■■UserRegisterServletクラス■■■
 *概要：サーブレット
 *詳細：リクエスト（ユーザー登録データ）を「USER」テーブルに登録し、画面遷移する。
 *　　　＜遷移先＞登録成功：回答完了画面（finish.html）／登録失敗：エラー画面（error.html）
 *----------------------------------------------------------------------**/
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserRegisterServlet() {
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
		if (userInfoOnSession != null) {
			//ログイン済：ログインメニュー画面に遷移
			response.sendRedirect("InputLoginMenu");

		} else {

			//未ログイン：
			boolean succesFlg = true;  //成功フラグ（true:成功/false:失敗）

			//パラメータのバリデーションチェック
			/*if( !(validatePrmName(request.getParameter("NAME"))                &&
					validatePrmAge(request.getParameter("AGE"))                  &&
					validatePrmSex(request.getParameter("SEX"))                  &&
					validatePrmSatLv(request.getParameter("SATISFACTION_LEVEL")) &&
					validatePrmMessage(request.getParameter("MESSAGE"))               ) ) {

				//バリデーションNGの場合
				succesFlg = false ;

			}else {

			}*/

				//バリデーションOKの場合

				//リクエストパラメータを取得
				String name              = request.getParameter("NAME");
				String userId              = request.getParameter("USER_ID");
				String password              = request.getParameter("PASSWORD");
				String email              = request.getParameter("EMAIL");

				//ユーザー登録データ（UserInfoDto型）の作成
				UserInfoDto dto = new UserInfoDto();
				dto.setUserId( userId );
				dto.setUserName( name );
				dto.setPassWord( password );
				dto.setEmail( email );


				//USER」テーブルに登録
				UserRegisterBL logic = new UserRegisterBL();
				succesFlg          = logic.executeInsert(dto);  //成功フラグ（true:成功/false:失敗）



			//成功/失敗に応じて表示させる画面を振り分ける
			if (succesFlg) {
				//成功した場合、回答完了画面（finish.html）を表示する
				response.sendRedirect("htmls/finish.html");

			} else {
				//失敗した場合、エラー画面（error.html）を表示する
				response.sendRedirect("htmls/error.html");

			}
		}
	}


}
