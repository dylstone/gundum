
function myHandler() {
	var t = $(this).parent().css('top');
	alert(t);
}

/*
 * 処理名：サブフレーム制御表示イベント
 * 処理概要：指定したサブフレームを初期化し、指定したリンクへ遷移。
 * author：那須
 */
var showSubFrame = function(url, subframeIds){
	if(subframeIds != null){
		var subframeIdArray = subframeIds.split(',');
		subframeIdArray.forEach(function(e){
			var subframeId = $("#" + e);
			if(subframeId.size() > 0){
				subframeId.remove();
			}
		});
	}
	
	procedure(url);
}

var doSearch = function(formId){
	search(formId);
}

var doSearchByOption = function(options){
	request(options);
}

// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){
	
	/*
	 * 処理名：Datepickerイベント
	 * 処理概要：JqueryUIによるカレンダー機能
	 * author：那須
	 */
	$.datepicker.setDefaults($.extend($.datepicker.regional['ja']));
	$(".jquery-ui-datepicker").datepicker({
		showOn: 'both',
		buttonImageOnly: true,
		buttonImage: '../images/calendar.gif',
		dateFormat: 'yymmdd',
		changeYear: true,
        changeMonth: true
    });
	

	
	/*
	 * 処理名：画面項目制御イベント
	 * 処理概要：画面項目制御マスタによる、可視/非可視の制御
	 * author：那須
	 */
 	$('#gamenItemControlInfo table tr').each(function() {
 		
 		// レコードが存在した場合、HITしたサブフレームIDを非可視にする。
 		var subFrameId = $('td', this).eq(0).text().trim(); 
 		$('#'+ subFrameId).css('display','none');
 		
 	});
 	
 	// 付箋を隠しておく
 	$("#fusen").hide();
});

// イベント機能
$(function(){
	
	$.base = {}
	
	/*
	 * 処理名：Ajax通信後の判定処理(ベースフレームにADD)
	 * 処理概要：Ajax通信後の判定処理を行う
	 * author：那須
	 */
	var defaults = {
    	type: 'POST',
    	timeout: 10000,
    	async: false,
    	// 通信成功時の処理
        success: function(data, status, xhr) {
        	
            console.log("通信成功");
            
            // サブフレーム表示
            // Divタグをセレクトしていく
            $(data.replace(/^\s+|\s+$/g, '')).filter('div').each(function(){

            	// id取得
            	var id = $(this).attr('id');
            	// 上書き元のサブフレームIDの要素の有無を判断
            	if ($('#' + id).length == 0) {
                	$("#contents").append($(this));	// 新規追加
                	// サブフレームのサイズ幅指定
                	setDefaultSubframesSize(id)
            	} else {
            		// 上書き前の高さ、幅、top、leftを取得しておく
            		var subFrameWidth = $('#' + id).width();
            		var subFrameHeight = $('#' + id).height();
            		var subFramePosition = $('#' + id).offset();
            		var subFrameTop = subFramePosition.top;
            		var subFrameLeft = subFramePosition.left;
            		
            		$('#' + id).replaceWith($(this));	// 上書き
            		
            		setSubframesSize(id, subFrameWidth, subFrameHeight, subFrameTop, subFrameLeft);
            	}

            	if ($(this).css('z-index') != 15) {
        			$('.jqDnR').each(function(){
        				if($(this).css('z-index') >= 12) $(this).css('z-index',$(this).css('z-index')-1);
        			});
        			$(this).css('z-index','15');
        			jqMove = $(this);
        		}
            	
            	// フォームの変更判定追加
//            	$('#' + id + ' > form').each(function(){
//            		alert($(this).html())
//            		var id = $(this).attr('id');
//            		delete changedForms[id];
//                	$(this).unbind('change');
//                	$(this).change(function(){
//                		changedForms[id] = true;
//                    	console.log(changedForms);
//                	});
//                });
            	
            	
            });
            
            // Scriptタグ追加
            $(data.replace(/^\s+|\s+$/g, '')).filter('script').each(function(){
            	var src = $(this).attr('src');

            	if ($('script[src="' + src + '"]').length == 0) {
                	$("head").append($(this));								// 新規追加
            	} else {
                	$('script[src="' + src + '"]').replaceWith($(this));	// 上書き
            	}

            });
            
        },

        // 通信失敗時の処理
        error: function(xhr, status, error) {
        	console.log("通信失敗");
            console.log(xhr);
        	console.log("status:" + status);
        	console.log(error);
        	alert("通信失敗:" + error);
        }
	}
        
	

	// Ajax通信本体（ベースフレームにサブフレームを埋め込む）
	function request(options) {
		console.log("requestが呼ばれました。");
		console.log($.extend({}, defaults, options));
		$.ajax($.extend({}, defaults, options));
	}
	
	// 手続き更新処理
	procedure = function(url) {
		console.log("linkが呼ばれました。url : " + url);
		var options = {
			url: url
		};
		console.log(options);
		request(options);
	}
	
	// 検索処理
	search = function(formId) {
		console.log("formが呼ばれました。");
		console.log(formId);
		var form = $("#" + formId);
		console.log(formId);
		
		if (form.length == 0) {
			alert('サブフレームが表示されていません。id=' + formId)
			return;
		}

		var options = {
	        url: form.attr('action'),
	        type: form.attr('method'),
	        data: form.serialize()
		};

		console.log(options);
		request(options);
	}

	// TODOダイアログ表示機能
	 $(".todo a").hover(function() {
	    $(".todo_box").animate({opacity: "show", top: "330"}, "slow");}, function() {
		
		$(".todo_box").skOuterClick(function() {
	           $(".todo_box").animate({opacity: "hide", top: "330"}, "fast");
		 });
	 });
	 
	// 手続き更新処理
	 Update = function(formId, url) {
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

		console.log(options);
		request(options);
	}
	 
	// Ajax通信 複数フォーム指定IF
	UpdateForms = function(url, forms) {
        console.log("formsが呼ばれました。");
		console.log(url);
		console.log(forms);

		var data = "";
		// [TBD]表示されていないサブフレームが、
		// 表示しないユーザ設定であるか、表示しなければいけないサブフレームか、
		// どのように判別するか。
		forms.forEach(function(e){
			var form = $("#" + e);
			if (form.length == 0) {
				return;
			}

			data += '&' + form.serialize();
		});

		var options = {
			url: url,
			type: 'POST',
			data: data
		};

		console.log(options);
		request(options);
	}
	 
	// 検証（付箋機能）
	 document.onkeydown = function(e) {
		 
		 var keycode;
		 
		 // FF
		 if (e != null) { 
			 keycode = e.which;
		 //	IE
		 }else{
			 keycode = event.keyCode;
		 }
		 
		 // キーコードの文字を取得   
		 //var keychar = String.fromCharCode(keycode).toUpperCase();

		 // 押下キーが「Esc」の場合
		 if(keycode == 27){
			 if ($('#fusen').css('display') == 'block') {
				 $('#fusen').hide();
			 }else{
				 // 付箋の前処理
				 $('#fusen').show();
				 $('#fusen table tbody').empty();
				 
				 // 付箋に書き込む
				 // 付箋表示の項目を検索
				 $('.headerInfo').each(function(){
					 // ヘッダ情報
					 var headerInfo = $(this).text();
					 var bodyInfo = "";
					 var objInputInfo = $(this).next().find('.bodyInfo');
					 var labelId = "";
					 var value = "";
					 
					 // 付箋のラベル名取得
					 labelId = objInputInfo.attr('id');
					 // テキストボックスの場合
					 if(objInputInfo.is('input[type=text]'))
					 {	
						 // テキストボックスの入力値取得
						 bodyInfo = objInputInfo.val();
					 // チェックボックスの場合
					 }else if(objInputInfo.is('input[type=checkbox]')){
						 // チェック項目のラベル名を表示
						 bodyInfo += objInputInfo.parent().find("input[type='checkbox']:checked").map(function() {
						        return $(this).next('label').text();
						      }).get().join(",");
					 // ラジオボタンの場合
					 }else if(objInputInfo.is('input[type=radio]')){
						 // チェック項目のラベル名を表示
						 bodyInfo = $("label[for='"+ objInputInfo.parent().find("input[type='radio']:checked").attr("id")+"']").text();
					 // プルダウンの場合
					 }else if(objInputInfo.is('select')){
						 // 選択項目名を表示
						 bodyInfo = objInputInfo.find(":selected").text();
					 // ラベルの場合
					 }else if(objInputInfo.is('date')){
						// テキストボックスの入力値取得
						 bodyInfo = objInputInfo.val();
					 }else{
						 bodyInfo = $(this).next('.bodyInfo').text();
					 }
					 
					// タグ埋め込み
					 var contents = "";
					 contents = contents.concat("<tr>");
					 contents = contents.concat("<td class=\"headerFusen\">").concat(headerInfo).concat("</td>");
					 contents = contents.concat("<td class=\"headerColon\">").concat(":").concat("</td>");
					 contents = contents.concat("<td class=\"bodyFusen\">").concat("<label for=\"").concat(labelId).concat("\">").concat(bodyInfo).concat("</td>");
					 contents = contents.concat("</tr>");
					 $('#fusen table tbody').append(contents);

				 });
				 
				 $('#fusen').css('position','absolute');
				 $('#fusen').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+290+'px');
				 $('#fusen').css('zIndex','99');
			 }
		 }
	 }
	 
	 // 入力オブジェクト系の変更イベント
	 $('input').change(function(){
		 
		 var labelId = "";
		 var objInput = "";
		 
		 // 入力項目を検索
		 $(this).parent().find("input").each(function(){
			 objInput = $(this).parent();
			 // 入力項目と付箋項目を紐づけるID
			 labelId = objInput.find('.bodyInfo').attr('id');
			 $('#fusen table tbody tr .bodyFusen label').each(function(){
				 var value = "";
				 // 変更項目＝ラジオボタンの場合
				 if(objInput.find('.bodyInfo').is('input[type=radio]')){
					 if(labelId == $(this).attr("for")){
						 // 入力した値を付箋に反映
						 value = $("label[for='" + objInput.find(":checked").attr("id") + "']").html();
						 $('#fusen table tbody tr .bodyFusen label[for="' + labelId + '"]').html(value);
					 }
				 // 変更項目＝チェックボックスの場合
				 }else if(objInput.find('.bodyInfo').is('input[type=checkbox]')){
					 if(labelId == $(this).attr("for")){
						// チェックしたもの
						 value += objInput.find("input[type='checkbox']:checked").map(function() {
						        return $(this).next('label').text();
						      }).get().join(",");
						 $('#fusen table tbody tr .bodyFusen label[for="' + labelId + '"]').html(value);
							
					}
				 // 変更項目＝テキストボックスの場合
				 }else if(objInput.find('.bodyInfo').is('input[type=text]')){
					 if(labelId == $(this).attr("for")){
						 value = objInput.find('.bodyInfo').val();
						 $('#fusen table tbody tr .bodyFusen label[for="' + labelId + '"]').html(value);
					 }
				 }
				 
			 });
		 });
		 
	 });
	 
	 $('select').change(function(){
		 
		 var labelId = "";
		 var objInput = "";

		 $(this).parent().find("select").each(function(){
			 
			// 入力項目INPUT
			 objInput = $(this).parent();
			 
			 // 入力項目のID
			 labelId = $(this).attr("id");
			 
			 $('#fusen table tbody tr .bodyFusen label').each(function(){
				 if(objInput.find('.bodyInfo').is('select')){
					 labelId = objInput.find('.bodyInfo').attr('id');
					 if(labelId == $(this).attr("for")){
						 var select = objInput.find(":selected").text();
						 $('#fusen table tbody tr .bodyFusen label[for="' + labelId + '"]').html(select);
					 }
				 }
			 });
		 });
	 });
});

//以下、Array.forEachをIEでも使えるようにするコード。
//[TBD]別ファイルにするか、共通JavaScriptに統合したい。
if (!Array.prototype.forEach) {

  Array.prototype.forEach = function forEach(callback, thisArg) {
    'use strict';
    var T, k;

    if (this == null) {
      throw new TypeError("this is null or not defined");
    }

    var kValue,
        // 1. Let O be the result of calling ToObject passing the |this| value as the argument.
        O = Object(this),

        // 2. Let lenValue be the result of calling the Get internal method of O with the argument "length".
        // 3. Let len be ToUint32(lenValue).
        len = O.length >>> 0; // Hack to convert O.length to a UInt32

    // 4. If IsCallable(callback) is false, throw a TypeError exception.
    // See: http://es5.github.com/#x9.11
    if ({}.toString.call(callback) !== "[object Function]") {
      throw new TypeError(callback + " is not a function");
    }

    // 5. If thisArg was supplied, let T be thisArg; else let T be undefined.
    if (arguments.length >= 2) {
      T = thisArg;
    }

    // 6. Let k be 0
    k = 0;

    // 7. Repeat, while k < len
    while (k < len) {

      // a. Let Pk be ToString(k).
      //   This is implicit for LHS operands of the in operator
      // b. Let kPresent be the result of calling the HasProperty internal method of O with argument Pk.
      //   This step can be combined with c
      // c. If kPresent is true, then
      if (k in O) {

        // i. Let kValue be the result of calling the Get internal method of O with argument Pk.
        kValue = O[k];

        // ii. Call the Call internal method of callback with T as the this value and
        // argument list containing kValue, k, and O.
        callback.call(T, kValue, k, O);
      }
      // d. Increase k by 1.
      k++;
    }
    // 8. return undefined
  };
}
function w_close2(){
	$('#win2').css('display','none');
}
function w_close3(){
	$('#win3').css('display','none');
}
function w_close4(){
	$('#win4').css('display','none');
}