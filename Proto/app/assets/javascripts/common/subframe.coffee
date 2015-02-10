$(() =>
	$("body").addClass("gse-baseframe")
	@baseframe = $(".gse-baseframe")
	@baseframe.children("#subframes").addClass("gse-subframes")
	@subframe = (id) ->
		return @baseframe.subframe(id)

	@mypage =
		userId : "opr0000001"
		currentKinouId : "kinou1"
		settings : {}
		load : (option) ->
			if (typeof option) == 'string'
				settingId = option
			else
				settingId = $(option).parent().parent().find("select[name=setting_id]").val()
				
			settingSet = @settings[settingId]
			if settingSet == undefined
				return null
			console.log "設定グループ : [" + settingId + "] の適用 "
			
			settingsToApply = settingSet[@currentKinouId]
			if settingsToApply == undefined
				return null
			console.log "機能ID : [" + @currentKinouId + "]への適用 "
			
			baseframe.subframes().children(".gse-subframe").each (index, element) =>
			
				target = $(element)
				subframeId = target.attr("class").match(/gse-id-[^ ]*/)[0].replace("gse-id-","") 
				setting = settingsToApply[subframeId]

				if setting != undefined
					console.log "サブフレーム : [" + subframeId + "]の設定 "
					target.css("position","absolute");
					target.position( 
						my : "left top"
						at : "left+" + setting.x + " top+" + setting.y
						of : target.parent()
						collision:"none"
						)

					target.height(setting.height)
					console.log "　height : " + setting.height
					target.width(setting.width)
					console.log "　width : " + setting.width
					target.zIndex(setting.zIndex)
					console.log "　zIndex : " + setting.zIndex
		
					console.log "　visiblity : " + setting.visiblity
					if setting.visiblity == "visible"
						target.show()
					else if setting.visiblity == "none"
						target.hide()
			
			return true
		
		reset : () ->
			@load("default")
			return false;
			
		save : (option) -> 
			if (typeof option) == 'string'
				settingId = option
			else
				settingId = $(option).parent().parent().find("select[name=setting_id]").val()

			if @settings[settingId] == undefined
				@settings[settingId] = {}
			settingSet = @settings[settingId]
			console.log "設定グループ : [" + settingId + "] に保存する "
			
			if settingSet[@currentKinouId] == undefined
				settingSet[@currentKinouId] = {} 
			settingsToSave = settingSet[@currentKinouId]

			console.log "機能ID : [" + @currentKinouId + "]への保存 "
			
			parent = baseframe.subframes()
			baseframe.subframes().children(".gse-subframe").each (index, element) =>
				target = $(element)
				subframeId = target.attr("class").match(/gse-id-[^ ]*/)[0].replace("gse-id-","")
				console.log "サブフレーム : [" + subframeId + "]の設定の保存 "

				setting =
					userId : mypage.userId
					kinouId : mypage.currentKinouId
					subframeId : subframeId
					settingId : settingId
					x : Math.round(target.position().left - parent.position().left)
					y : Math.round(target.position().top - parent.position().top)
					width : Math.round(target.width())
					height : Math.round(target.height())
					zIndex : Math.round(target.zIndex())
					visiblity : if target.css("display") == "none" then "none" else "visible"
				
				settingsToSave[subframeId] = $.extend({}, settingsToSave[subframeId], setting) 
			
			$.base2.json("/mypage/save")
			return true
	$.base2.json("/mypage/load")
	)

do ($ = jQuery) ->

	#基本機能	
	$.fn.isBaseframe = () ->
		@hasClass("gse-baseframe")

	$.fn.isSubframes = () ->
		@hasClass("gse-subframes")

	$.fn.isSubframe = () ->
		@hasClass("gse-subframe")
		
	#サブフレームエリア取得処理
	$.fn.subframes = (id) ->
		#この機能はベースフレーム時のみ有効
		if @isBaseframe()
			if id == undefined || id == "" || id==null
				return @children(".gse-subframes:first")
			else
				return @children(".gse-subframes.gse-id-" + id)
		else
			return undefined
		
	#サブフレーム取得処理
	$.fn.subframe = ( id ) ->
		if @isSubframes()
			if id == undefined || id == "" || id==null
				return @children(".gse-subframe:first")
			else
				return @children(".gse-subframe.gse-id-" + id)		
		else if @isBaseframe()
			return @subframes().subframe(id)
		else
			return undefined

	#機能拡張
	$.fn.setup = (options) ->
		if @isSubframe()
			return null
	
	#名前空間の設定
	$.gse = {}
	 
	# Ajax通信 メニュー用IF
	$.gse.action = (actionID) ->
		#メニューログ出力
		console.log "actionが呼ばれました。actionID: " + actionID
		
		#送信対象の選択
		
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
				$('#' + id ).draggable()
				$('#' + id ).resizable()
				$('#' + id ).addClass("gse-subframe")
				$('#' + id ).addClass("gse-id-"+id)
							
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
	
	return null