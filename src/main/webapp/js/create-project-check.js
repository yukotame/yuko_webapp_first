'use strict';
{

	var elmADD = document.getElementById("ID_ADD");
	var elmProjectName = document.getElementById("ID_PROJECT_NAME");
	var elmProjectDate = document.getElementsByClassName('CLASS_PROJECT_DATE');
	var date_alert=true;


	elmADD.onclick = function(){

	  var canSubmit = true;
	  var len = elmProjectDate.length;

	  if(elmProjectName.value == "" ){
		    alert("案件名を入力してください。");
		    canSubmit = false;
	  }

	  //日付が１カ所入力されていればOK。入力がなければアラート出力
	  for (let i = 0; i < len; i++){
		  if(elmProjectDate[i].value != "" ){
			    date_alert= false;
		  }
	  }

	  if(date_alert){
		  alert("日付を入力してください。");
		    canSubmit = false;
	  }


	  return canSubmit;
	}
}
