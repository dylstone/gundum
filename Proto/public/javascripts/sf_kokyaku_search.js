function w_searchContents_close(){
	$('#searchContents').css('display','none');
}

// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){
	$('#searchContents').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
});

/*
 * 処理名：検索ボタン押下時イベント
 * 処理概要：検索ボタンの処理を行う。
 * author：那須
 */
function searchEvent(formId){

	// 検索処理
	doSearch(formId);
	
	// テーブルのレイアウト調整
	$(".searchResultKokyakuId").css("display","none");
	$(".searchResultKeiyakuId").css("display","none");
	$("#searchResult table tbody tr:even").addClass('even');
    $("#searchResult table tbody tr:odd").addClass('odd');
}

/*
 * 処理名：検索結果行の選択処理
 * 処理概要：検索結果より詳細画面へ遷移する際に使用。
 * author：那須
 */
$("#searchResult table tbody tr td a").click(function(){

	// 顧客情報参照画面に必要なキー情報を取得
	var kokyakuId = $(this).parent().parent().find('input[name=kokyakuId]').val();
	var keiyakuId = $(this).parent().parent().find('input[name=keiyakuId]').val();

	// formに詰め直す
	$("input[name=postKokyakuId]").val(kokyakuId);
	$("input[name=postKeiyakuId]").val(keiyakuId);
	
	// サブフレームを削除
//	$('.jqDnR').each(function(){
//		$(this).remove();
//	});
	
	// 顧客検索を行う
	Update('formKokyakuInfoCond','getKokyakuInfo');
	
	// リサイズを行う（強引だが・・・）
	$.getScript("javascripts/fn_winsize.js");
	$.getScript("javascripts/sf_kokyaku_info.js");

});

