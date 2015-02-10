/*
 * 処理名：エラーサブフレームJS
 * 処理概要：エラーサブフレームに関わるJS処理
 * author：那須
 */

function w_sscr9999_close(){
	$('#SSCR9999').remove();
}

// DOMがロードしたタイミングで動くメソッド
$(document).ready(function(){
	
	$('#SSCR9999').jqDrag('.jqDrag').jqResize('.jqResize',myHandler);
	
});

// イベント機能
$(function(){

});
