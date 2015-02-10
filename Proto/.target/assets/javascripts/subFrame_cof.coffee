do (jQuery) ->
	if @console == undefined
		@console
			log : (message) ->
				#if window.console && window.console.clear
				#	window.console.log(message)

	#jQuery設定
	$ = jQuery
	
	#名前空間の設定
	$.base2 = {}
	
	# Ajax通信 メニュー用IF
	$.base2.menu = (url) ->
		#メニューログ出力
		console.log "menuが呼ばれました。url : " + url
		console.log changedForms
		
		#画面切り替え制御
		if  !$.isEmptyObject(changedForms) && !confirm('情報が変更されています。画面を切り替えますか？')
			return
		
		#サブフレーム削除
		$("#subframes").empty()
		
		#遷移処理
		options =
			url: url
		console.log options
		request options

	# Ajax通信 Json用
	$.base2.json = (url) ->
		#メニューログ出力
		console.log "jsonで送るにょ。url : " + url

		settings = []
		for settingId of mypage.settings
			for kinouId of mypage.settings[settingId]
				for subframeId of mypage.settings[settingId][kinouId]
					settings.push(mypage.settings[settingId][kinouId][subframeId])
				
		userInfo =
			userId : mypage.userId
			settings : settings
		
		#遷移処理
		options =
			url: url
			type: "POST"
			data : JSON.stringify(userInfo)
			contentType : 'application/json'
			dataType : "json"
			success : (data) =>
				if data.settings != undefined
					console.log(JSON.parse(data.settings))
					mypage.settings = $.extend({}, mypage.settings, JSON.parse(data.settings))
				if url.match(/save/)
					alert "保存完了"
				
		console.log options
		request options

	
	# Ajax通信 Aタグ用IF
	$.base2.link = (url) ->
		#メニューログ出力
		console.log "linkが呼ばれました。url : " + url

		#遷移処理
		options =
			url: url
		console.log options
		request options

	# Ajax通信 自フォーム用IF
	$.base2.submit = (form) ->
		#メニューログ出力
		console.log "submitが呼ばれました。"
		console.log form
		
		#遷移処理
		options =
			url:	form.attr('action')
			type:	form.attr('method')
			data:	form.serialize()
			
		console.log options
		request options
		
	# Ajax通信 親フォーム用IF
	$.base2.parent = (button) ->
		#メニューログ出力
		console.log "parentが呼ばれました。"
		console.log button
		parent = button.parent("form");
		console.log parent
		
		#遷移処理
		options =
			url:	parent.attr('action')
			type:	parent.attr('method')
			data:	parent.serialize()
		console.log options
		request options

	# Ajax通信 フォーム指定IF
	$.base2.form = (formID) ->
		#メニューログ出力
		console.log "formが呼ばれました。"
		console.log formID
		
		#フォームの取得
		form = $("#" + formID)
		console.log form
		if  form.length == 0
			alert 'サブフレームが表示されていません。id=' + formID
			return
			
		#遷移処理
		options =
			url:	form.attr('action')
			type:	form.attr('method')
			data:	form.serialize()
		console.log options
		request options

	# Ajax通信 複数フォーム指定IF
	$.base2.forms = (url, method, forms) ->
		console.log "formsが呼ばれました。"
		console.log url
		console.log method
		console.log forms
		
		#全サブフレームの情報集約
		data = ""
		
		bFlg = 0
		#フォームＩＤ分ループ
		for formID in forms
			do (formID) ->
				form = $("#"+formID)
				if  form.length == 0
					bFlg = 1
				else
					#フォームがあれば値を追加する。
					data += '&' + form.serialize()
		
		if bFlg == 1
			return false
		
		#遷移処理
		options =
			url:	url
			type:	method
			data:	data
		console.log options
		request options
		
	# Ajax通信 メニュー用IF
	$.base2.close = (button) ->
		#ログ出力
		console.log "closeが呼ばれました。"
		console.log button
		parent = button.parent("form")
		console.log parent


		console.log changedForms
		if  (parent.attr('id') in changedForms) && !confirm('情報が変更されています。画面を切り替えますか？')
			return

		delete changedForms[parent.attr('id')]
		button.parents("div[id^='subframe']").remove()
	
	#内部関数

	# デフォルトAjaxオプション
	defaults = 
		type :		'GET'
		timeout:	30000
		# 通信成功時の処理
		success: (data, status, xhr) =>
			console.log("通信成功");
			# サブフレーム表示
			$(data.replace(/^\s+|\s+$/g, '')).filter('div').each (index, element) =>
				id = $(element).attr('id')
				if $('#' + id).length == 0
					$("#subframes").append($(element))	# 新規追加
				else
					$('#' + id).replaceWith($(element))	# 上書き
				
				# サブフレーム機能の設定
				$('#' + id ).draggable
					handle: ".handler"
					containment: "#subframes"
					
				$('#' + id ).resizable()
				$('#' + id ).addClass("gse-subframe")
				$('#' + id ).addClass("gse-id-"+id)
				
				$('#' + id ).find('form').each (index, element) =>
					$(element).validationEngine()

				# フォームの変更判定追加
				$('#' + id + ' > form').each (index, element) =>
					id = $(element).attr('id')
					delete changedForms[id]
					$(element).unbind('change')
					$(element).change () =>
						changedForms[id] = true
						console.log changedForms
				
				#requireJsの仕組みでonLoadメソッドを実行する。
				require [id],(l)->
					#onLoadが定義されている場合だけ、処理を実行する。
					#本来的には、subframeクラスを定義して、これを継承する形で書き、判定を不要にすべき？
					#性能による。
					if 'onLoad' in l
						l.onLoad()
				
			# Scriptタグ追加
			$(data.replace(/^\s+|\s+$/g, '')).filter('script').each (index, element) ->
				src = $(element).attr('src')
				if $('script[src="' + src + '"]').length == 0
					$("head").append($(this));								# 新規追加
				else
					$('script[src="' + src + '"]').replaceWith($(element))	# 上書き
			
		# 通信失敗時の処理
		error: (xhr, status, error) =>
			console.log "通信失敗"
			console.log xhr
			console.log "status:" + status
			console.log error
			alert("通信失敗:" + error)

	
	# Ajax通信本体
	request = (options) ->
		console.log "requestが呼ばれました。"
		console.log $.extend({}, defaults, options)
		$.ajax($.extend({}, defaults, options))

	# フォーム変更フラグ連想配列
	# {formid1: true, formid2: true, ...}
	changedForms = {}

