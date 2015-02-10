/*
 * 処理名：ベストプランサブフレームJS
 * 処理概要：ベストプランサブフレームに関わるJS処理
 * author：那須
 */


// ベストプランサブフレームのCloseイベント
function w_bestPlanSearchContents_close(){
	$('#bestPlanContents').css('display','none');
}

/*
 * 処理名：手続き系ボタン押下時イベント
 * 処理概要：手続きを開始するボタンの処理を行う。
 * author：那須
 */
function doProcedure(url){
	procedure(url);
}

function DoUpdate(formId, url){
	
	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm('承認します。よろしいでしょうか？')){
		Update(formId, url);
		// ErrorSFがRenderされているかどうか確認する。
		if(0 == $("#SSCR9999").size()){
			alert("登録が完了しました。");
		}
	}
}

// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){

	$('#bestPlanContents').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);

	 $(".stripeTbl tr:even").addClass('even');
     $(".stripeTbl tr:odd").addClass('odd');
	 
});


