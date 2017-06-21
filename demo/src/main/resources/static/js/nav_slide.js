// JavaScript Document
$(function(){
	var $liCur = $(".nav ul li.cur"),
		$slider = $(".curBg"),
		 $navBox = $(".nav"),
		 $targetEle = $(".nav ul li a");
	var curP = $liCur.position().left,
		  	curW = $liCur.outerWidth(true);
			$slider.animate({
			  "left":curP,
			  "width":curW
			});
			$targetEle.mouseenter(function () {
			  var $_parent = $(this).parent(),
				_width = $_parent.outerWidth(true),
				posL = $_parent.position().left;
			  $slider.stop(true, true).animate({
				"left":posL,
				"width":_width
			  }, "fast");
			});
			$navBox.mouseleave(function (cur, wid) {
			  cur = curP;
			  wid = curW;
			  $slider.stop(true, true).animate({
				"left":cur,
				"width":wid
			  }, "fast");
			});
		window.onresize = function(){
			curP = $liCur.position().left;
		  	curW = $liCur.outerWidth(true);
			$slider.animate({
			  "left":curP,
			  "width":curW
			}, "fast");
		}
})
//用户名的更改
$.get("user/user_code",function(data) {
	$(".nav .user_n label").html("用户名"+data+"");
})

