/*
 * 処理名：対応履歴サブフレームJS
 * 処理概要：対応履歴サブフレームに関わるJS処理
 * author：那須
 */


// 対応履歴情報サブフレームのCloseイベント
function w_sscr0002_close(){
	$('#SSCR0002').css('display','none');
}

function DoUpdateTaiouRireki(formId, url){
	
	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm('登録します。よろしいでしょうか？')){
		// 確認サブフレームで顧客サブフレームを上書くために、IDを振り返る
		Update(formId, url);
		// リサイズを行う
		setWindowSize();
		setReadyWin();
		setResizeBox();
		alert("登録が完了しました。");
	}
}
// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){
	
	$('#SSCR0002').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	
	/*
	 * 処理名：可視/非可視処理
	 * 処理概要：あらかじめ非可視と断定できるものを非可視状態にしておく
	 * author：那須
	 */
	$("#TaiouRireki").css('display','none');
	
	/*
	 * 処理名：対応履歴詰め替えイベント
	 * 処理概要：表示用に置き換える
	 * author：那須
	 */
	 $("#TaiouRireki table tr").each(function() {
		 
		 // 対応履歴・対応結果に表示する値を取得
		 var taiouNichiji = $('.taiouNichiji', this).text();
		 var taiouOperatorId = $('.taiouOperatorId', this).text();
		 var operatorName = $('.operatorName', this).text();
		 var operatorDepartment = $('.operatorDepartment', this).text();
		 var taiouNaiyou = $('.taiouNaiyou', this).text();
		 var taiouKekka = $('.taiouKekka', this).text();
		 
		 $('#txtAreaTaiouKekka').append("\n");
		 $('#txtAreaTaiouKekka').append("■対応日時■\r\n").append(taiouNichiji).append("\r\n");
		 $('#txtAreaTaiouKekka').append("■オペレータ名■\r\n").append(taiouOperatorId).append("：").append(operatorName);
		 $('#txtAreaTaiouKekka').append("[").append(operatorDepartment).append("]").append("\r\n");
		 $('#txtAreaTaiouKekka').append("■対応内容■\r\n").append(taiouNaiyou).append("\r\n");
		 $('#txtAreaTaiouKekka').append("\r\n");
		 $('#txtAreaTaiouKekka').append("-------------------------------------------------------\r\n")
		 
	 });

});

// イベント機能
$(function(){

 	/*
	 * 処理名：タブボタン制御イベント
	 * 処理概要：タブボタン押下時に、画面部品を切り替える処理
	 * author：那須
	 */
	 $(".tabButton").each(function() {
		 
		 var id = $(this).attr('id').substr(3,8);

		 if($('#'+ $(this).attr('id')).hasClass('active')){
			 $('#'+id).show();
		 }else{
			 $('#'+id).hide();
		 }
	 });
	 
	 /*
	 * 処理名：対応内容テキスト変更時イベント
	 * 処理概要：対応内容テキスト変更時に確定ボタンの制御を行う。
	 * author：那須
	 */
	$('#txtAreaTaiouNaiyou').bind('keyup',function(){   
		if($('#txtAreaTaiouNaiyou').val().length != 0){
			$("#ITEM0014").attr('disabled',false).addClass('kakutei').removeClass('kakuteiDisabled');
		}else{
			$("#ITEM0014").attr('disabled',true).addClass('kakuteiDisabled').removeClass('kakutei');
		}
	});  
});
