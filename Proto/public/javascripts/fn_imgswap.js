$(document).ready(function(){
	$('.swap').not('[src*="_over."]').each(function() {
		var srcOut = this.src;
		var dotNum = srcOut.lastIndexOf('.');
		var srcOver = srcOut.substring(0,dotNum) + '_over' + srcOut.substring(dotNum);
		$(this).hover(
			function () {
				this.src = srcOver;
			},
			function () {
				this.src = srcOut;
			}
		);
	});
});

