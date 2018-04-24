$(function() {
    if (/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) { //判断iPhone|iPad|iPod|iOS
		mobileOper()
    } else { //pc
       pcOper();
    };
});

function mobileOper(){
   /* $(".logo-lg ul li em").click(function(){
        $(this).next('.TDsec-nav').toggle();
    });*/
	$(".logo-lg ul li em").on("touchend", function(e){
		$(this).next('.TDsec-nav').toggle();

		$(document).one("touchend", function(){
			$('.TDsec-nav').hide();
		});

		e.stopPropagation();
	});
	$('.TDsec-nav').on("touchend", function(e){
		e.stopPropagation();
	});

}

function pcOper(){
    var $t, leftX, newWidth;

    $('.logo-lg ul').append('<div class="block"></div>');
    var $block = $('.block');
    $block.hide();
    $block.width($(".current").width()).css('left', $('.current em').position().left).data('rightLeft', $block.position().left).data('rightWidth', $block.width());

    $('.logo-lg ul li').hover(function() {
        $t = $(this).children('em');
        leftX = $t.position().left;
        newWidth = $t.parent().width();
        $block.show();
        $block.stop().animate({
            left: leftX,
            width: newWidth
        },300);
    },function(){
        $block.hide();
    });

    var mouseover_tid = [];
    var mouseout_tid = [];

    $(document).ready(function() {

        $('.logo-lg ul li').each(function(index) {

            $(this).hover(
                // 鼠标进入
                function() {
                    var _self = this;
                    // 停止卷起事件
                    clearTimeout(mouseout_tid[index]);
                    // 当鼠标进入超过 0.2 秒, 展开菜单, 并记录到线程 ID 中
                    mouseover_tid[index] = setTimeout(function() {
                        $(_self).find('.TDsec-nav').slideDown(500);
                    }, 200);
                },
                // 鼠标离开
                function() {

                    var _self = this;

                    // 停止展开事件
                    clearTimeout(mouseover_tid[index]);

                    // 当鼠标离开超过 0.2 秒, 卷起菜单, 并记录到线程 ID 中
                    mouseout_tid[index] = setTimeout(function() {
                        jQuery(_self).find('.TDsec-nav').slideUp(100);
                    }, 100);
                }
            );
        });
    });
}
