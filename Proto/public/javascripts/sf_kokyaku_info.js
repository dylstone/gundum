/*
 * 処理名：顧客情報サブフレームJS
 * 処理概要：顧客情報サブフレームに関わるJS処理
 * author：那須
 */


// 顧客情報サブフレームのCloseイベント
function w_sscr0001_close(){
	$('#SSCR0001').css('display','none');
}
function w_sscr0003_close(){
	$('#SSCR0003').css('display','none');
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
	
	var confirmMessage = "";
	if($("#txtAreaTaiouNaiyou").val() == ""){
		confirmMessage = "対応内容が未入力状態です。それでも登録しますか？";
	}else{
		confirmMessage = "登録します。よろしいでしょうか？";
	}
	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm(confirmMessage)){
		// 確認サブフレームで顧客サブフレームを上書くために、IDを振り返る
    	//$("#SSCR0003").attr("id","SSCR0001");
		var forms = formId.split(',')
		UpdateForms(url, forms);
		//$("#SSCR0001").attr("id","SSCR0003");
		// リサイズを行う
		//setWindowSize();
		//setReadyWin();
		//setResizeBox();
		$.getScript("javascripts/sf_kokyaku_info.js");
		// ErrorSFがRenderされているかどうか確認する。
		if(0 == $("#SSCR9999").size()){
			alert("登録が完了しました。");
		}
	}
}

// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){

	$('#SSCR0001').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	$('#SSCR0003').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	$('#win2').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	$('#win3').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	$('#win4').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	$('#ITEM0013').attr('disabled',true).addClass('kakuteiDisabled').removeClass('kakutei');

	/*
	 * 処理名：タブボタン制御イベント
	 * 処理概要：タブボタン押下時に、画面部品を切り替える処理
	 * author：那須
	 */
	 var cntlFlg = true;
	 $("#kokyakuTab .tabButton").each(function() {
		 
		 var id = $(this).attr('id').substr(3,8);
		 
		// 確認画面の場合
		 if(0 < $("#tabCntl").size()){
			 $("#tabCntl input").each(function() {
				 var cntlId = $(this).attr('id');
				 
				 if($(this).val() == "ON"){
					 var tabId = cntlId.substr(4,11);
					 $("#" + tabId).addClass('orange');
				 }
			 });
		 }
		 
		 $("#kokyakuTab .tabButton:first").addClass('active').removeClass('orange');
		 
		 if($('#'+ $(this).attr('id')).hasClass('active')){
			 $('#'+id).show();
		 }else{
			 $('#'+id).hide();
		 }
	 });
	 
	 /*
	 * 処理名：オプション画面部品表示情報作成
	 * 処理概要：オプション画面部品の申込状態を切り替える処理
	 * author：那須
	 */
	 $("#serviceInfo div").each(function() {
		// perm_security,perm_option・・・
		var serviceInfoId = $(this).attr("id");
		var serviceMasterId = $(this).attr("id");
		var permApplyShubetsu = "";
		
		// perm,apply いずれかを含む場合は取り除く
		if(serviceInfoId.search("perm_") != -1){
			serviceMasterId = serviceMasterId.replace("perm_","");
			permApplyShubetsu = "0";
		}
		if(serviceInfoId.search("apply_") != -1){
			serviceMasterId = serviceMasterId.replace("apply_","");
			permApplyShubetsu = "1";
		}

		$("#" + serviceInfoId + " table tr").each(function() {
			 // 契約しているサービスのサービスコードを取得。
			 var masterServiceCd = $(this).find("input[name=masterServiceCd]").val();
			 // オプション画面部品のセキュリティテーブルを１レコードずつ参照。
			 $("#"+ serviceMasterId + " tbody tr").each(function() {
				 var serviceCd = $(this).find("input[name=" + serviceMasterId + "ServiceCd]").val();
				 // 契約しているサービスがマスタにある場合
				 if(masterServiceCd == serviceCd){
					 // 利用中の文字列を埋め込む
					 if(permApplyShubetsu == "0"){
					 	$(this).find('.permInfoCell').html("利用中");
					 }else if(permApplyShubetsu == "1"){
						 // パーマネントが利用中である場合は、利用中にする
						 if($(this).find('.permInfoCell').html() == "なし"){
							 $(this).find('.applyInfoCell').html("申込中");
							 $(this).find('.red').val("解約")
							 $(this).find('.red').removeClass('red').addClass('glay');
						 }else{
							 $(this).find('.applyInfoCell').html("利用中");
							 $(this).find('.red').val("解約")
							 $(this).find('.red').removeClass('red').addClass('glay');
						 }
					 }
					 return false;
				 }
			 });
			 
		 });
	 });
	 $("#PSCR0011 table").each(function() {
		 var tableId = $(this).attr("id");
		 $("#" + tableId + " tbody tr").each(function() {
			 var permStatus = $(this).find('.permInfoCell').html();
			 if(permStatus == "利用中"){
				 $(this).find('.applyInfoCell').html("利用中");
				 $(this).find('.red').val("解約")
				 $(this).find('.red').removeClass('red').addClass('glay');
			 }
		 });
	 });
	 
//	 $("#optionService table tr").each(function() {
//		 // 契約しているサービスのサービスコードを取得。
//		 var masterServiceCd = $(this).find("input[name=masterServiceCd]").val();
//		 // オプション画面部品のセキュリティテーブルを１レコードずつ参照。
//		 $("#tblOption tbody tr").each(function() {
//			 var serviceCd = $(this).find("input[name=optionServiceCd]").val();
//			 // 契約しているサービスがマスタにある場合
//			 if(masterServiceCd == serviceCd){
//				 // 利用中の文字列を埋め込む
//				 $(this).find('.permInfoCell').html("利用中");
//				 return false;
//			 }
//		 });
//		 
//	 });
//	 $("#applySecurityService table tr").each(function() {
//		 // 契約しているサービスのサービスコードを取得。
//		 var statusFlg = false;
//		 var masterServiceCd = $(this).find("input[name=masterServiceCd]").val();
//		 // オプション画面部品のセキュリティテーブルを１レコードずつ参照。
//		 $("#tblSecurity tbody tr").each(function() {
//			 var securityServiceCd = $(this).find("input[name=securityServiceCd]").val();
//			 // 契約しているサービスがマスタにある場合
//			 if(masterServiceCd == securityServiceCd){
//				 // 利用中の文字列を埋め込む
//				 $(this).find('.applyInfoCell').html("申込中");
//				 $(this).find('.red').val("解約")
//				 $(this).find('.red').removeClass('red').addClass('glay');
//				 return false;
//			 }
//		 });
//		 
//	 });
//	 $("#applyOptionService table tr").each(function() {
//		 // 契約しているサービスのサービスコードを取得。
//		 var masterServiceCd = $(this).find("input[name=masterServiceCd]").val();
//		 // オプション画面部品のセキュリティテーブルを１レコードずつ参照。
//		 $("#tblOption tbody tr").each(function() {
//			 var optionServiceCd = $(this).find("input[name=optionServiceCd]").val();
//			 // 契約しているサービスがマスタにある場合
//			 if(masterServiceCd == optionServiceCd){
//				 // 利用中の文字列を埋め込む
//				 $(this).find('.applyInfoCell').html("申込中");
//				 $(this).find('.red').val("解約")
//				 $(this).find('.red').removeClass('red').addClass('glay');
//				 return false;
//			 }
//		 });
//		 
//	 });
	 
	 
	 $(".stripeTbl tr:even").addClass('even');
     $(".stripeTbl tr:odd").addClass('odd');
	 
});

// イベント機能
$(function(){
	
	// タブ押下イベント
	 $("#kokyakuTab .tabButton").click(function() {
		
		// 活性状態クラスを削除
		$('#kokyakuTab .tabButton').removeClass('active');
		// 押下したボタンのIDを保持しておく
		var buttonId = $(this).attr('id'); 

		// 画面部品切り替え
		 $("#kokyakuTab .tabButton").each(function() {
			 	
			 var id = $(this).attr('id').substr(3,8);
			 
			 // 確認画面がある場合
			 if(0 < $("#tabCntl").size()){
				 $("#tabCntl input").each(function() {
					 var cntlId = $(this).attr('id');
					 if($(this).val() == "ON"){
						 var tabId = cntlId.substr(4,11);
						 $("#" + tabId).addClass('orange');
					 }
				 });
			 }

			// 選択したタブボタンを活性
			 $("#" + buttonId).addClass('active').removeClass('orange');
			 
			 if($('#'+ $(this).attr('id')).hasClass('active')){
				 $('#'+id).show();
			 }else{
				 $('#'+id).hide();
			 }
		 });
	 });
	 
	 // 確認チェックボックスチェックイベント
	 $(".kakuninCheck").change(function(){
		 var obj = $("#" + $(this).attr('id'));
		 var checkId = $(this).attr('id');
		 if(obj.prop('checked')){
			 $("#" + checkId.replace("bottom","up")).prop({'checked':'checked'});
		 }else{
			 $("#" + checkId.replace("bottom","up")).prop({'checked': false}); 
		 }
		 
		 // 確認チェックボックスがすべてチェック状態か確認する
		 var chkFlg = false;
		 $(".kakuninCheck").each(function() {
			 // checkがついてない場合、は確定を活性にしない
			 if(!$(this).prop('checked')){
				 chkFlg=true;
			 }
		 });
		 if(!chkFlg){
			 $("#ITEM0013").attr('disabled',false).addClass('kakutei').removeClass('kakuteiDisabled');
		 }else{
			 $("#ITEM0013").attr('disabled',true).addClass('kakuteiDisabled').removeClass('kakutei');
		 }
	 });
	 
	// 申込ステイタスボタン押下イベント（セキュリティ）
	 $(".securityStatus").click(function() {
		 // ステイタスをコードで持つが、今はボタン名の状態で判断。
		 if($(this).val() == "申込"){
			 // 申込対象として、このオプションのサービスコードにフラグを立てる
			 $(this).parent().parent().parent().find('input[name=securityUpdFlg]').val("1");
			 // 文言修正
			 $(this).val("取消");
			 $(this).removeClass("red").addClass("glay");
			 $(this).parent().parent().parent().find('.applyInfoCell').html("申込中");
			 //procedure("/tourokuOption");
		 }else{
			// 申込対象として、このオプションのサービスコードにフラグを立てる
			 $(this).parent().parent().parent().find('input[name=securityUpdFlg]').val("0");
			 // 文言修正
			 $(this).val("申込");
			 $(this).removeClass("glay").addClass("red");
			 $(this).parent().parent().parent().find('.applyInfoCell').html("解約中");
		 }
		 $("#ITEM0013").attr('disabled',false).addClass('kakutei').removeClass('kakuteiDisabled');
		 
	 });
	 
	// 申込ステイタスボタン押下イベント（オプション）
	 $(".optionStatus").click(function() {
		 // ステイタスをコードで持つが、今はボタン名の状態で判断。
		 if($(this).val() == "申込"){
			 // 申込対象として、このオプションのサービスコードにフラグを立てる
			 $(this).parent().parent().parent().find('input[name=optionUpdFlg]').val("1");
			 // 文言修正
			 $(this).val("取消");
			 $(this).removeClass("red").addClass("glay");
			 $(this).parent().parent().parent().find('.applyInfoCell').html("申込中")
		 }else{
			// 申込対象として、このオプションのサービスコードにフラグを立てる
			 $(this).parent().parent().parent().find('input[name=optionUpdFlg]').val("0");
			 // 文言修正
			 $(this).val("申込");
			 $(this).removeClass("glay").addClass("red");
			 $(this).parent().parent().parent().find('.applyInfoCell').html("解約中")
		 }
		 $("#ITEM0013").attr('disabled',false).addClass('kakutei').removeClass('kakuteiDisabled');
		 
	 });
	 
	// 申込ステイタスボタン押下イベント（設置場所）
	 $(".settingOptionStatus").click(function() {
		 procedure("/tourokuOption");
	 });
	 
	// 確認チェックボックスチェックイベント
	 $(".kakuteiCheck").change(function(){

		 var obj = $("#" + $(this).attr('id'));
		 var checkId = $(this).attr('id');
		 if(obj.prop('checked')){
			 $("#" + checkId.replace("up","bottom")).prop({'checked':'checked'});
		 }else{
			 $("#" + checkId.replace("up","bottom")).prop({'checked': false}); 
		 }
		 
		 // 確認チェックボックスがすべてチェック状態か確認する
		 var chkFlg = false;
		 $(".kakuteiCheck").each(function() {
			 // checkがついてない場合、は確定を活性にしない
			 if(!$(this).prop('checked')){
				 chkFlg=true;
			 }
		 });
		 if(!chkFlg){
			 $("#ITEM0013").attr('disabled',false).addClass('kakutei').removeClass('kakuteiDisabled');
		 }else{
			 $("#ITEM0013").attr('disabled',true).addClass('kakuteiDisabled').removeClass('kakutei');
		 }
	 });
	 
	 // フラグ埋め込み
	 doAttrValue = function(wizardFlg) {
		$("#wizardFlg").val(wizardFlg);
	}
	 
});

