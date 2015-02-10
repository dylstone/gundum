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

    	$('#SSCR0003').css('opacity',1);

		$('#win2').css('opacity',1);

		$('#win3').css('opacity',1);

		$('#win4').css('opacity',1);

		$('#win_layer1').css('opacity',1);

		$('#win_layer2').css('opacity',1);

		$('#win_layer3').css('opacity',1);

		$('#win_layer4').css('opacity',1);

		$('#win_layer5').css('opacity',1);

		$('#win_layer6').css('opacity',1);
		
		$('#SSCR9999').css('opacity',1);

    });

	

	

	$('#win-bt1').click(function(){ $('#SSCR0003').css('display','block'); });

	

	$('#win-bt2').click(function(){ $('#win2').css('display','block'); });

	

	$('#win-bt3').click(function(){ $('#win3').css('display','block'); });

	

	$('#win-bt4').click(function(){ $('#win4').css('display','block'); });

	

	$('.array button').click(function(){ setReadyWin(); setResizeBox(); });

	

	$('.all-open button').click(function(){ setAllOpenWin(); });

	$('.all-close button').click(function(){ setAllCloseWin(); setResizeBox(); });



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



function setReadyWin() {

	var contW = $('#contents').width();
	var contH = $('#contents').height();
	$('#SSCR0003').css('width',contW*0.75-12+'px');
	$('#SSCR0003').css('height',contH-12+'px');
	$('#SSCR0003').css('top','5px');
	$('#SSCR0003').css('left','5px');
	$('#SSCR0002').css('width',contW*0.35-200+'px');
	$('#SSCR0002').css('height',contH-12+'px');
	$('#SSCR0002').css('top','5px');
	$('#SSCR0002').css('left',$('#SSCR0003').width()+12+'px');
	$('#win2').css('width',contW*0.35-7+'px');
	$('#win2').css('height',contH/2-7+'px');
	$('#win2').css('top','5px');
	$('#win2').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+12+'px');
	$('#win3').css('width',contW*0.35-7+'px');
	$('#win3').css('height',contH/2-12+'px');
	$('#win3').css('top',$('#win2').height()+12+'px');
	$('#win3').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+12+'px');
	$('#win4').css('width',contW*0.15-7+'px');
	$('#win4').css('height',contH-12+'px');
	$('#win4').css('top','5px');
	$('#win4').css('left',$('#SSCR0001').width()+$('#SSCR0002').width()+$('#SSCR0003').width()+$('#win2').width()+19+'px');
	$('#win_layer5').css('z-index','14');
	$('#SSCR9999').css('width','420px');
	$('#SSCR9999').css('height','294px');
	$('#SSCR9999').css('top','143px');
	$('#SSCR9999').css('left','330px');
}



function setResizeBox() {

	$('#SSCR0003 .box').css('width','80%');
	$('#SSCR0003 .box').css('height',$('#SSCR0003').height()-129+'px');

	

	$('#win2 .box').css('width',$('#win2').width()-20+'px');

	$('#win2 .box').css('height',$('#win2').height()-80+'px');

	

	$('#win3 .box').css('width',$('#win3').width()-20+'px');

	$('#win3 .box').css('height',$('#win3').height()-70+'px');

	

	$('#win4 .box').css('width',$('#win4').width()-20+'px');

	$('#win4 .box').css('height',$('#win4').height()-70+'px');

	

	$('#win_layer5 .box3').css('height',$('#win3').height()+60+'px');

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
