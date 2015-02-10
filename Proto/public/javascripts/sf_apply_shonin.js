function w_shoninContents_close(){
	$('#shoninContents').css('display','none');
}
// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){
	$('#shoninContents').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
});


var shoninApply = function(formId, key) {

	var form = $("#" + formId);
	var options = {
		url: form.attr('action'),
        data: { 'name': key }
	};

	doSearchByOption(options);
}

var selectApply = function(actionId, id, pwd) {

	var options = {
		url: actionId,
		data: { 'operatorId': id, 'operatorPwd': pwd }
	};

	doSearchByOption(options);
}

$(document).ready(function(){
	// テーブルのレイアウト調整
	$(".searchResultApplyId").css("display","none");
	$(".searchResultKokyakuId").css("display","none");
	$(".searchResultKeiyakuId").css("display","none");
	$(".searchResultQueueId").css("display","none");
	$(".searchResultActivityId").css("display","none");
	$("#searchResultShonin table tbody tr:even").addClass('even');
    $("#searchResultShonin table tbody tr:odd").addClass('odd');
});

$("#searchResultShonin table tbody tr td a").click(function(){

	// 承認詳細画面に必要なキー情報を取得
	var applyId = $(this).parent().parent().find('input[name=applyId]').val();
	var kokyakuId = $(this).parent().parent().find('input[name=kokyakuId]').val();
	var keiyakuId = $(this).parent().parent().find('input[name=keiyakuId]').val();
	var queueId = $(this).parent().parent().find('input[name=queueId]').val();
	var activityId = $(this).parent().parent().find('input[name=activityId]').val();

	// formに詰め直す
	$("input[name=postApplyId]").val(applyId);
	$("input[name=postKokyakuId]").val(kokyakuId);
	$("input[name=postKeiyakuId]").val(keiyakuId);
	$("input[name=postQueueId]").val(queueId);
	$("input[name=postActivityId]").val(activityId);
	
	// 顧客検索を行う
	Update('formApplyDetailCond','getApplyDetail');
	
//	// リサイズを行う（強引だが・・・）
//	if(0 < $("#SSCR0001").size()){
//		$.getScript("javascripts/fn_winsize.js");
//	}else{
//		$("#SSCR0001").remove()
//		$.getScript("javascripts/fn_winsize3.js");
//	}
//
//	$.getScript("javascripts/sf_kokyaku_info.js");

});
