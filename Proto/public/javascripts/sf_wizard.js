/*
 * 処理名：ウィザードサブフレームJS
 * 処理概要：ウィザードサブフレームJS処理
 * author：那須
 */


// 顧客情報サブフレームのCloseイベント
function w_win_layer5_close(){
	$('#SSCR0004').css('display','none');
}

/*
 * 処理名：「次へ」ボタン押下時処理
 * 処理概要：次へボタン押下時の処理を記述する。
 * author：那須
 */
function doNext(url){
	doNextWizard(url)
}
function doNextForm(formId, url){
	
	// Wizard遷移の準備
	//$("#SSCR0003").attr("id","SSCR0001");
	$("#SSCR9999").remove();
	
	// POSTする項目の準備
	$("#inputRiyouCourseName").val($('#riyouCource option:selected').text());
	
	Update(formId, url);
	
	if(0 == $("#SSCR9999").size()){
		// サブフレームを塗り替える前に、必要な情報は持っておく
	    var wizardFlg = $("#wizardFlg").val();
	
	    // 現ウィザード＝開局チェック
	    if(wizardFlg == "11"){
	    	// 前画面部品を隠す
	        $("#PSCR0006").hide();
	        $("#PSCR0007").show();
	        $("#PSCR0013").hide();
	        $("#PSCR0014").show();
	        // 次の画面状態を表すフラグを埋め込んでおく
			$("#wizardFlg").val("12");
			// 戻る、次へボタンの切り替え
			$("#wizardCtrl1").hide();
			$("#wizardCtrl2").show();
			// パンくずを次の画面にする
			doChangeWizardStatus("next");
			$(".stripeTbl tr:even").addClass('even');
	        $(".stripeTbl tr:odd").addClass('odd');
	        $("#riyouCource").val($("#selRiyouCource").val());

	    }else{
	
	    	// 編集ありだったことを、記憶しておく（tabボタンの制御のため）
			var tabCntlInfo = "";
			tabCntlInfo = tabCntlInfo.concat("<input type=hidden id=\"CntlTabPSCR0008\" value=\"ON\">");
			tabCntlInfo = tabCntlInfo.concat("<input type=hidden id=\"CntlTabPSCR0009\" value=\"ON\">");
			tabCntlInfo = tabCntlInfo.concat("<input type=hidden id=\"CntlTabPSCR0010\" value=\"ON\">");
			tabCntlInfo = tabCntlInfo.concat("<input type=hidden id=\"CntlTabPSCR0011\" value=\"ON\">");
			tabCntlInfo = tabCntlInfo.concat("<input type=hidden id=\"CntlTabPSCR0012\" value=\"ON\">");
			tabCntlInfo = tabCntlInfo.concat("<input type=hidden id=\"CntlTabPSCR0013\" value=\"ON\">");
			$('#tabCntl').append(tabCntlInfo);
			
			// チェックボックスを作成する。
			var checkboxKakuteiCntlInfo = "";
			checkboxKakuteiCntlInfo = checkboxKakuteiCntlInfo.concat("<li><input id=\"upChkKeiyakuInfo\" class=\"kakuteiCheck\" type=checkbox>契約者情報</li>");
			checkboxKakuteiCntlInfo = checkboxKakuteiCntlInfo.concat("<li><input id=\"upChkServiceInfo\" class=\"kakuteiCheck\" type=checkbox>サービス情報</li>");
			checkboxKakuteiCntlInfo = checkboxKakuteiCntlInfo.concat("<li><input id=\"upChkRyoukinInfo\" class=\"kakuteiCheck\" type=checkbox>料金情報</li>");
			checkboxKakuteiCntlInfo = checkboxKakuteiCntlInfo.concat("<li><input id=\"upChkOptionInfo\" class=\"kakuteiCheck\" type=checkbox>オプション情報</li>");
			checkboxKakuteiCntlInfo = checkboxKakuteiCntlInfo.concat("<li><input id=\"upChkSettingOption\" class=\"kakuteiCheck\" type=checkbox>設置場所</li>");
			checkboxKakuteiCntlInfo = checkboxKakuteiCntlInfo.concat("<li><input id=\"upChkShiharaiInfo\" class=\"kakuteiCheck\" type=checkbox>支払情報</li>");
			$('.check_list').append(checkboxKakuteiCntlInfo);
			$("#PSCR0008").append("<p class=\"bg_kakunin\">確認 <input id=\"bottomChkKeiyakuInfo\" class=\"kakuninCheck\" type=checkbox></p>");
			$("#PSCR0009").append("<p class=\"bg_kakunin\">確認 <input id=\"bottomChkServiceInfo\" class=\"kakuninCheck\" type=checkbox></p>");
			$("#PSCR0010").append("<p class=\"bg_kakunin\">確認 <input id=\"bottomChkRyoukinInfo\" class=\"kakuninCheck\" type=checkbox></p>");
			$("#PSCR0011").append("<p class=\"bg_kakunin\">確認 <input id=\"bottomChkOptionInfo\" class=\"kakuninCheck\" type=checkbox></p>");
			$("#PSCR0012").append("<p class=\"bg_kakunin\">確認 <input id=\"bottomChkSettingOption\" class=\"kakuninCheck\" type=checkbox></p>");
			$("#PSCR0013").append("<p class=\"bg_kakunin\">確認 <input id=\"bottomChkShaharaiInfo\" class=\"kakuninCheck\" type=checkbox></p>");
			
	
	        // ウィザード消す
	        $("#SSCR0004").remove();
	        // IDを書き換える
	        $("#SSCR0001").remove();
	        //$("#SSCR0001").attr("id","SSCR0003");
	        
	        $.getScript("javascripts/sf_kokyaku_info.js");
	        
	    }
		// リサイズを行う
		setWindowSize();
		setReadyWin();
		setResizeBox();
	}
}

/*
 * 処理名：「戻る」ボタン押下時処理
 * 処理概要：戻るボタン押下時の処理を記述する。
 * author：那須
 */
function doPrev(url){
	
	var wizardFlg = $("#wizardFlg").val();
	
	procedure(url);
	
	// 現在のウィザードが「開局チェック」
	if(wizardFlg == "11"){
		
		// ウィザードを閉じる
		$("#SSCR0004").remove();
		
	}else if(wizardFlg == "12"){
		
		// 開局チェックを開く
		$("#wizardFlg").val("11");
		$("#PSCR0006").show();
		$("#PSCR0007").hide();
		$("#wizardCtrl1").show();
		$("#wizardCtrl2").hide();
		doChangeWizardStatus("prev");

		$("#riyouCource").val($("#selRiyouCource").val());
	}
	
}

// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){
	
	$('#SSCR0004').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);

	// ウィザード1のボタンだけ活性にしておく。
	// ここをぐるぐる回す。
	$("#wizardCtrl1").hide();
	$("#wizardCtrl2").hide();
	
	$("#wizardCtrl1").show();
	// ウィザードの状態フラグを埋め込んでおく
	$("#wizardFlg").val("11");
	$("#PSCR0007").hide();
	$("#PSCR0014").hide();
	$(".stripeTbl tr:even").addClass('even');
    $(".stripeTbl tr:odd").addClass('odd');
    
    

});

// イベント機能
$(function(){
	
	// Ajax通信本体（サブフレームに画面部品を埋め込む）
	function requestWizard(options) {
		console.log("requestが呼ばれました。");
		console.log($.extend({}, wizards, options));
		$.ajax($.extend({}, defaults, options));
	}
	
	// 手続き更新処理
    doNextWizard = function(url) {
		console.log("linkが呼ばれました。url : " + url);
		var options = {
			url: url
		};
		console.log(options);
		request(options);
	}
    
    // 手続き更新処理
    doNextWizardForm = function(formId, url) {
        console.log("formが呼ばれました。");
		console.log(formId);
		var form = $("#" + formId);
		console.log(form);

		if (form.length == 0) {
			alert('サブフレームが表示されていません。id=' + formId)
			return;
		}

		var options = {
	        url: url,
	        type: form.attr('method'),
	        data: form.serialize()
		};
		alert("sdfasdf")
		console.log(options);
		request(options);
	}
    
    // パンくず制御
    doChangeWizardStatus = function(status) {
    	$('#wizardStatus ul li').each(function() {
			if($(this).hasClass('active')){
				if(status == "next"){
					$(this).next().addClass('active');
				}else if(status == "prev"){
					$(this).prev().addClass('active');
				}else{
					return false;
				}
				$(this).removeClass('active');
				return false;
   			 }
		});
	}
    
});

