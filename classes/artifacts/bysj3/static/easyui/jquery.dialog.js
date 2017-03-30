/**
 * Dialog对话框插件
 * 基于jQuery文件
 *
 * Author: Loading  sunqinye@gmail.com
 * Version: 1.0
 * Charset: utf-8
 * Date: 2012-6-22
 *
 * Example: $.dialog.alert({title:"dialog"});
 */

$.dialog = {
    /*信息提示*/
    alert: function (set) {
        d = defaults(set);
        $(".dialog").remove();
        $(".mask").remove();
        if (d.mask == true) {
            addMask();
        }
        $("body").append('<div class="dialog" style="width:' + d.width + 'px; height:' + d.height + 'px; position:fixed; top:' + getTop(d.height) + '%; left:' + getLeft(d.width) + '%; z-index:1000;"></div>');
        $(".dialog").append('<div class="dialog_body"><div class="dialog_top">' + d.title + '</div><div class="dialog_main">' + d.content + '</div><div class="dialog_bottom"><span><input type="button" value="确定" onclick="' + d.callback + '$.dialog.close();" /></span></div></div>');
    },
    /*信息确定*/
    confirm: function (set) {
        d = defaults(set);
        $(".dialog").remove();
        $(".mask").remove();
        if (d.mask == true) {
            addMask();
        }
        $("body").append('<div class="dialog" style="width:' + d.width + 'px; height:' + d.height + 'px; position:fixed; top:' + getTop(d.height) + '%; left:' + getLeft(d.width) + '%; z-index:1000;"></div>');
        $(".dialog").append('<div class="dialog_body"><div class="dialog_top">' + d.title + '</div><div class="dialog_main">' + d.content + '</div><div class="dialog_bottom"><span><input type="button" value="确定" onclick="' + d.callback + '$.dialog.close();" />&nbsp;<input type="button" value="取消" onclick="$.dialog.close();" /></span></div></div>');
    },
    /*加载页面*/
    load: function (set) {
        d = defaults(set);
        $(".dialog").remove();
        $(".mask").remove();
        if (d.mask == true) {
            addMask();
        }
        $("body").append('<div class="dialog" style="width:' + d.width + 'px; height:' + d.height + 'px; position:fixed; top:' + getTop(d.height) + '%; left:' + getLeft(d.width) + '%; z-index:1000;"></div>');
        $(".dialog").load(d.url, d.data, d.callback);
    },
    /*关闭对话框*/
    close: function (set) {
        d = defaults(set);
        $(".dialog").fadeOut(d.closetime);
        $(".mask").fadeOut(d.closetime);
    }

};
/*设值默认参数*/
function defaults(set) {
    var defaults = {
        width: 380,			//对话框宽度
        height: 150,			//对话框高度
        opentime: 280,			//对话框打开所用时间，单位为毫秒
        closetime: 280,		//对话框关闭所用时间，单位为毫秒
        mask: true,			//是否使用遮罩
        title: '消息',			//标题
        content: null,			//内容
        callback: '',			//回调函数
        url: null,				//加载路径
        data: null			//传送数据
    };
    return $.extend(defaults, set);
}
/*添加遮罩*/
function addMask() {
    $("body").append('<div class="mask" style="width:100%; height:100%; position:fixed; top:0; left:0; z-index:999;" onclick="$.dialog.close();"></div>');
}
/*计算定位距左*/
function getLeft(width) {
    return 50 - 50 * width / $(document).width();
}
/*计算定位距右*/
function getTop(height) {
    return 50 - 50 * height / $(document).height();
}