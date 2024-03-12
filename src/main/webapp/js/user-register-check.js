'use strict';
{

	var elmSUBMIT = document.getElementById("USE_REGISTER_SUBMIT");
	var elmUserID = document.getElementById("ID_USER_ID");
	var elmUserName = document.getElementById("ID_USER_NAME");
	var elmPassword = document.getElementById("ID_PASSWORD");
	var elmEmail = document.getElementById("ID_EMAIL");



	elmSUBMIT.onclick = function(){

	  var canSubmit = true;

	  if(elmUserID.value == "" ){
		    alert("ユーザーIDを入力してください。");
		    canSubmit = false;
	  }

	  if(elmUserName.value == "" ){
		    alert("ユーザー名を入力してください。");
		    canSubmit = false;
	  }

	  if(elmPassword.value == "" ){
		    alert("パスワードを入力してください。");
		    canSubmit = false;
	  }

	  if(elmEmail.value == "" ){
		    alert("メールアドレスを入力してください。");
		    canSubmit = false;
	  }




	  return canSubmit;
	}
}
