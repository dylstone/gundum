var winW,winH,headW,headH;

$(document).ready(function(){
	setWindowSize();
	setReadyWin();
	setResizeBox();
	var jqMove = $('#contents');
	
	$('.jqDrag').mousedown(function(){
		if ($(this).parent().css('z-index') != 15) {
			$('.jqDnR').each(function(){
				if($(this).css('z-index') >= 12) $(this).css('z-index',$(this).css('z-index')-1);
			});
			$(this).parent().css('z-index','15');
			jqMove = $(this).parent();
		}
	});
	
	$('.jqResize').mousedown(function(){
		if ($(this).parent().css('z-index') != 15) {
			$('.jqDnR').each(function(){
				if($(this).css('z-index') >= 12) $(this).css('z-index',$(this).css('z-index')-1);
			});
			$(this).parent().css('z-index','15');
			jqMove = $(this).parent();
		}
	});
	
	$('.jqResize').mousemove(function(){
		setResizeBox();
	});
	
	$('.jqDnR').mousedown(function(){
		if ($(this).css('z-index') != 15) {
			$('.jqDnR').each(function(){
				if($(this).css('z-index') >= 12) $(this).css('z-index',$(this).css('z-index')-1);
			});
			$(this).css('z-index','15');
			jqMove = $(this);
		}
	});
	
	$(document).mouseup(function(){
		if(jqMove.attr('ID') != 'contents') {
			var t = jqMove.css('top');
			var l = jqMove.css('left');
			var cW = $('#contents').width();
			var cH = $('#contents').height();
			var jqW = jqMove.width();
			var jqH = jqMove.height();
			if (jqW > cW) jqMove.css('width',cW-12+'px'); jqW = jqMove.width();
			if (jqH > cH) jqMove.css('height',cH-12+'px'); jqH = jqMove.height();
	
			t = t.replace('px','');
			l = l.replace('px','');
			if (t < 5) jqMove.css('top','5px');
			if (l < 5) jqMove.css('left',l+'px');
			if (t > cH-jqH-7) jqMove.css('top',t-7+'px');
			if (l > cW-jqW-7) jqMove.css('left',l-7+'px');
			
			if (t > cH-50) jqMove.css('top',cH-50+'px');
			if (l > cW-50) jqMove.css('left',cW-50+'px');
			
			if (l < 0-jqW+50) jqMove.css('left',50-jqW+'px');
	
	
		}
	});
	
	
	$('#contents').bind('mouseleave', function() {
    	$('#win00').css('opacity',1);
		$('#SSCR0001').css('opacity',1);
		$('#SSCR0002').css('opacity',1);
		$('#SSCR0003').css('opacity',1);
		$('#win2').css('opacity',1);
		$('#win3').css('opacity',1);
		$('#win4').css('opacity',1);
		$('#win_layer1').css('opacity',1);
		$('#win_layer2').css('opacity',1);
		$('#win_layer3').css('opacity',1);
		$('#win_layer4').css('opacity',1);
		$('#SSCR0004').css('opacity',1);
		$('#win_layer6').css('opacity',1);
		$('#win_layer7').css('opacity',1);
		$('#SSCR9999').css('opacity',1);
		$('#shoninContents').css('opacity',1);
		
    });
		
	
	$('#win-bt1').click(function(){ $('#SSCR0001').css('display','block'); });
	
	$('#win-bt2').click(function(){ $('#win2').css('display','block'); });
	
	$('#win-bt3').click(function(){ $('#win3').css('display','block'); });
	
	$('#win-bt4').click(function(){ $('#win4').css('display','block'); });
	
	$('.array input').click(function(){ setAlignmentSubFrame(); setResizeBox(); });
	
	$('.all-open input').click(function(){ setAllOpenWin(); });
	$('.all-close input').click(function(){ setAllCloseWin(); setResizeBox(); });

});

$(window).resize(function(){
	setWindowSize();
	setReadyWin();
	setResizeBox();
});



function setWindowSize() {
	$('nav').css('height','0px');
	$('#contents').css('height','0px');
	winW = $(window).width();
	winH = $(window).height();
	
	headW = $('#header').width();
	headH = $('#header').height();
	$('nav').css('height',winH-headH-1+'px');
	$('#contents').css('height',winH-headH-1+'px');
}

function setDefaultSubframesSize(id) {
	var contW = $('#contents').width();
	var contH = $('#contents').height();
	
	if(id == "searchContents"){
		$('#searchContents').css('width',contW*0.95+'px');
		$('#searchContents').css('height',contH-12+'px');
		$('#searchContents').css('top','5px');
		$('#searchContents').css('left','5px');
	}
	
	if(id == "shoninContents"){
		$('#shoninContents').css('width',contW*0.95+'px');
		$('#shoninContents').css('height',contH-15+'px');
		$('#shoninContents').css('top','5px');
		$('#shoninContents').css('left','5px');
	}
	
	if(id == "SSCR0001"){
		$('#SSCR0003').remove();
		$('#SSCR0001').css('width',contW*0.5-220+'px');
		$('#SSCR0001').css('height',contH-12+'px');
		$('#SSCR0001').css('top','5px');
		$('#SSCR0001').css('left','5px');
	}
	
	if(id == "SSCR0003"){
		$('#SSCR0001').remove();
		$('#SSCR0003').css('width',contW*0.75-12+'px');
		$('#SSCR0003').css('height',contH-12+'px');
		$('#SSCR0003').css('top','5px');
		$('#SSCR0003').css('left','5px');
	}
	
	if(id == "SSCR0002"){
		$('#SSCR0002').css('width',contW*0.35-200+'px');
		$('#SSCR0002').css('height',contH-12+'px');
		$('#SSCR0002').css('top','5px');
		$('#SSCR0002').css('left',$('#SSCR0001').width()+$('#SSCR0003').width()+12+'px');
	}
	
	if(id == "SSCR0004"){
		$('#SSCR0004').css('z-index','14');
	}
	
	if(id == "SSCR9999"){
		$('#SSCR9999').css('width','420px');
		$('#SSCR9999').css('height','294px');
		$('#SSCR9999').css('top','143px');
		$('#SSCR9999').css('left','330px');
	}
	
	if(id == "win2"){
		$('#win2').css('width',contW*0.35-7+'px');
		$('#win2').css('height',contH/2-7+'px');
		$('#win2').css('top','5px');
		$('#win2').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
	}
	
	if(id == "win2"){
		$('#win3').css('width',contW*0.35-7+'px');
		$('#win3').css('height',contH/2-12+'px');
		$('#win3').css('top',$('#win2').height()+12+'px');
		$('#win3').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
	}
	
	if(id == "win3"){
		$('#win3').css('width',contW*0.35-7+'px');
		$('#win3').css('height',contH/2-12+'px');
		$('#win3').css('top',$('#win2').height()+12+'px');
		$('#win3').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
	}
	
	if(id == "win4"){
		$('#win4').css('width',contW*0.15-7+'px');
		$('#win4').css('height',contH-12+'px');
		$('#win4').css('top','5px');
		$('#win4').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+$('#win2').width()+19+'px');
	}
	
	if(id == "bestPlanContents"){
		$('#bestPlanContents').css('width',contW*0.9+'px');
		$('#bestPlanContents').css('height',contH-12+'px');
		$('#bestPlanContents').css('top','5px');
		$('#bestPlanContents').css('left','5px');
	}
}

function setSubframesSize(id, subFrameWidth, subFrameHeight, subFrameTop, subFrameLeft) {
	var headerHeight = $('#header').height();
	var navWidth = $('nav').width();
	$('#' + id).css('width',subFrameWidth +'px');
	$('#' + id).css('height',subFrameHeight +'px');
	$('#' + id).css('top',subFrameTop-headerHeight +'px');
	$('#' + id).css('left',subFrameLeft-navWidth+'px');
	// 画面部品サイズ（）
	$('#' + id + ' .box').css('width','95%');
	$('#' + id + ' .box').css('height',$('#' + id).height()-129+'px');
}
function setAlignmentSubFrame() {
	var contW = $('#contents').width();
	var contH = $('#contents').height();
	
	$('#searchContents').css('width',contW*0.95+'px');
	$('#searchContents').css('height',contH-12+'px');
	$('#searchContents').css('top','5px');
	$('#searchContents').css('left','5px');
	
	$('#shoninContents').css('width',contW*0.95+'px');
	$('#shoninContents').css('height',contH-12+'px');
	$('#shoninContents').css('top','5px');
	$('#shoninContents').css('left','5px');
	
	$('#SSCR0001').css('width',contW*0.5-220+'px');
	$('#SSCR0001').css('height',contH-12+'px');
	$('#SSCR0001').css('top','5px');
	$('#SSCR0001').css('left','5px');
	
	$('#SSCR0003').css('width',contW*0.75-12+'px');
	$('#SSCR0003').css('height',contH-12+'px');
	$('#SSCR0003').css('top','5px');
	$('#SSCR0003').css('left','5px');
	
	$('#SSCR0002').css('width',contW*0.35-200+'px');
	$('#SSCR0002').css('height',contH-12+'px');
	$('#SSCR0002').css('top','5px');
	$('#SSCR0002').css('left',$('#SSCR0001').width()+$('#SSCR0003').width()+12+'px');
	
	$('#SSCR0004').css('z-index','14');
	
	$('#SSCR9999').css('width','420px');
	$('#SSCR9999').css('height','294px');
	$('#SSCR9999').css('top','143px');
	$('#SSCR9999').css('left','330px');

	$('#win2').css('width',contW*0.35-7+'px');
	$('#win2').css('height',contH/2-7+'px');
	$('#win2').css('top','5px');
	$('#win2').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
	
	$('#win3').css('width',contW*0.35-7+'px');
	$('#win3').css('height',contH/2-12+'px');
	$('#win3').css('top',$('#win2').height()+12+'px');
	$('#win3').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
	
	$('#win4').css('width',contW*0.15-7+'px');
	$('#win4').css('height',contH-12+'px');
	$('#win4').css('top','5px');
	$('#win4').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+$('#win2').width()+19+'px');
	
	$('#bestPlanContents').css('width',contW*0.9+'px');
	$('#bestPlanContents').css('height',contH-12+'px');
	$('#bestPlanContents').css('top','5px');
	$('#bestPlanContents').css('left','5px');
}
function setReadyWin() {
	var contW = $('#contents').width();
	var contH = $('#contents').height();
	
//	$('#searchContents').css('width',contW*0.95+'px');
//	$('#searchContents').css('height',contH-12+'px');
//	$('#searchContents').css('top','5px');
//	$('#searchContents').css('left','5px');
	
//	$('#shoninContents').css('width',contW*0.95+'px');
//	$('#shoninContents').css('height',contH-12+'px');
//	$('#shoninContents').css('top','5px');
//	$('#shoninContents').css('left','5px');
//	
//	$('#SSCR0001').css('width',contW*0.5-220+'px');
//	$('#SSCR0001').css('height',contH-12+'px');
//	$('#SSCR0001').css('top','5px');
//	$('#SSCR0001').css('left','5px');
//	
//	$('#SSCR0003').css('width',contW*0.75-12+'px');
//	$('#SSCR0003').css('height',contH-12+'px');
//	$('#SSCR0003').css('top','5px');
//	$('#SSCR0003').css('left','5px');
//	
//	$('#SSCR0002').css('width',contW*0.35-200+'px');
//	$('#SSCR0002').css('height',contH-12+'px');
//	$('#SSCR0002').css('top','5px');
//	$('#SSCR0002').css('left',$('#SSCR0001').width()+$('#SSCR0003').width()+12+'px');
//	
//	$('#SSCR0004').css('z-index','14');
//	
//	$('#SSCR9999').css('width','420px');
//	$('#SSCR9999').css('height','294px');
//	$('#SSCR9999').css('top','143px');
//	$('#SSCR9999').css('left','330px');
//
//	$('#win2').css('width',contW*0.35-7+'px');
//	$('#win2').css('height',contH/2-7+'px');
//	$('#win2').css('top','5px');
//	$('#win2').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
//	
//	$('#win3').css('width',contW*0.35-7+'px');
//	$('#win3').css('height',contH/2-12+'px');
//	$('#win3').css('top',$('#win2').height()+12+'px');
//	$('#win3').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+18+'px');
//	
//	$('#win4').css('width',contW*0.15-7+'px');
//	$('#win4').css('height',contH-12+'px');
//	$('#win4').css('top','5px');
//	$('#win4').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+$('#win2').width()+19+'px');
	
}

function setResizeBox() {

	$('#SSCR0001 .box').css('width','95%');
	$('#SSCR0001 .box').css('height',$('#SSCR0001').height()-129+'px');
	
	$('#SSCR0002 .box').css('width',$('#SSCR0002').width()-20+'px');
	$('#SSCR0002 .box').css('height',$('#SSCR0002').height()-105+'px');
	
	$('#SSCR0003 .box').css('width','80%');
	$('#SSCR0003 .box').css('height',$('#SSCR0003').height()-129+'px');
	
	$('#win2 .box').css('width',$('#win2').width()-20+'px');
	$('#win2 .box').css('height',$('#win2').height()-110+'px');
	
	$('#win3 .box').css('width',$('#win3').width()-20+'px');
	$('#win3 .box').css('height',$('#win3').height()-70+'px');
	
	$('#win4 .box').css('width',$('#win4').width()-20+'px');
	$('#win4 .box').css('height',$('#win4').height()-70+'px');
	
	$('#SSCR0004 .box3').css('height',$('#win3').height()+60+'px');
	$('#SSCR0004 .box3').css('width','100%');
	
	$('#shoninContents .box').css('width','95%');
	$('#shoninContents .box').css('height',$('#shoninContents').height()-149+'px');
	
	$('#bestPlanContents .box').css('width','98%');
	$('#bestPlanContents .box').css('height',$('#bestPlanContents').height()-55+'px');

}

function setAllOpenWin() {
	$('.jqDnR').each(function(){
		$(this).css('display','block');
	});
}

function setAllCloseWin() {
	$('.jqDnR').each(function(){
		$(this).css('display','none');
	});
}









