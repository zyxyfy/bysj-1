/**
 * 基于jquery编写
 */

(function (window) {
    /**
	 * 随机数，用于拼接url防止缓存
	 */
    var randomNum = Math.random() * 100000 * 100000 * 100000 * 100 + "";
    var msunfun = {
        /**
         * 使用window.open打开页面，页面的大小最大
         * @param url 要打开的页面的地址。
         * @param title 要打开的页面的标题
         */
        openNewMaxWindown: function (url, title) {
            var width = window.screen.availWidth;
            var height = window.screen.availHeight;
            window.open(url, title, 'height=' + height + ',width=' + width
					+ ',top=0,left=0,scrollbars=0,resizable=yes,titlebar=1');
        },
        /**
		 * 设置某个dom撑满整个页面
		 * @param dom   要处理的dom对象
		 */
        setDivMax: function (dom) {
            var jdom = $(dom);
            var h = 0;
            if (document.body.clientHeight >= document.documentElement.clientHeight) {
                h = document.body.clientWidth;
            } else {
                h = document.documentElement.clientHeight
            }
            jdom.width(document.body.clientWidth);
            jdom.height(h);

        },
        //para_name 参数名称 para_value 参数值 url所要更改参数的网址
        setUrlParam: function (para_name, para_value, url) {
            if (url === undefined || url === null || url == "") {
                return "";
            }
            if (url.charAt(url.length - 1) == "?") {
                url = url.substring(0, url.length - 1);
            }
            var strNewUrl = new String();
            var strUrl = new String();

            strUrl = url;
            //alert(strUrl);
            if (strUrl.indexOf("?") != -1) {
                strUrl = strUrl.substr(strUrl.indexOf("?") + 1);
                //alert(strUrl);
                if (strUrl.toLowerCase().indexOf(para_name.toLowerCase()) == -1) {
                    strNewUrl = url + "&" + para_name + "=" + para_value;
                    return strNewUrl;
                } else {
                    var aParam = strUrl.split("&");
                    //alert(aParam.length);
                    for (var i = 0; i < aParam.length; i++) {
                        if (aParam[i].substr(0, aParam[i].indexOf("=")).toLowerCase() == para_name.toLowerCase()) {
                            aParam[i] = aParam[i].substr(0, aParam[i].indexOf("=")) + "=" + para_value;
                        }
                    }
                    strNewUrl = url.substr(0, url.indexOf("?") + 1) + aParam.join("&");
                    //alert(strNewUrl);

                    return strNewUrl;
                }
            } else {
                strUrl += "?" + para_name + "=" + para_value;
                return strUrl;

            }
        },

        DateParse: function (datetimestr) {
            /// <summary>通过字符串获取时间</summary>
            /// <param name="name" type="string">时间字符串</param>
            /// <returns type="string" />
            datetimestr = datetimestr.replace(/-/g, "/");
            var date = new Date(datetimestr);
            //date = new Date( Date.parse(datetimestr));
            //date.setTime(Date.parse(datetimestr));
            return date;
        }, //DateParse
        CountAge: function (birthday) {
            if (birthday === undefined || birthday === null) {
                return 0;
            }
            if (typeof (birthday) == "string") {
                birthday = msun$.DateParse(birthday);
            }
            var today = new Date();
            var div = today - birthday;
            if (div < 0) {
                return 0;
            }
            var days = parseInt(div / 1000 / 3600 / 24);
            if (days >= 365) {
                return parseInt(days / 365) + "岁";
            }
            if (days >= 360) {
                return "1岁";
            }
            if (days >= 30) {
                return parseInt(days / 30) + "月";
            }
            return days + "天";

        },
        /** 
         * 格式化日期 
         * @param date 日期 
         * @param format 格式化样式,例如yyyy-MM-dd HH:mm:ss E 
         * @return 格式化后的金额 
         */
        formatDate: function (date, format) {
            var v = "";
            if (typeof (date) == "string" || typeof (date) != "object") {
                return;
            }
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours();
            var minute = date.getMinutes();
            var second = date.getSeconds();
            var weekDay = date.getDay();
            var ms = date.getMilliseconds();
            var weekDayString = "";

            if (weekDay == 1) {
                weekDayString = "星期一";
            } else if (weekDay == 2) {
                weekDayString = "星期二";
            } else if (weekDay == 3) {
                weekDayString = "星期三";
            } else if (weekDay == 4) {
                weekDayString = "星期四";
            } else if (weekDay == 5) {
                weekDayString = "星期五";
            } else if (weekDay == 6) {
                weekDayString = "星期六";
            } else if (weekDay == 7) {
                weekDayString = "星期日";
            }

            v = format;
            //Year 
            v = v.replace(/yyyy/g, year);
            v = v.replace(/YYYY/g, year);
            v = v.replace(/yy/g, (year + "").substring(2, 4));
            v = v.replace(/YY/g, (year + "").substring(2, 4));

            //Month 
            var monthStr = ("0" + month);
            v = v.replace(/MM/g, monthStr.substring(monthStr.length - 2));

            //Day 
            var dayStr = ("0" + day);
            v = v.replace(/dd/g, dayStr.substring(dayStr.length - 2));

            //hour 
            var hourStr = ("0" + hour);
            v = v.replace(/HH/g, hourStr.substring(hourStr.length - 2));
            v = v.replace(/hh/g, hourStr.substring(hourStr.length - 2));

            //minute 
            var minuteStr = ("0" + minute);
            v = v.replace(/mm/g, minuteStr.substring(minuteStr.length - 2));

            //Millisecond 
            v = v.replace(/sss/g, ms);
            v = v.replace(/SSS/g, ms);

            //second 
            var secondStr = ("0" + second);
            v = v.replace(/ss/g, secondStr.substring(secondStr.length - 2));
            v = v.replace(/SS/g, secondStr.substring(secondStr.length - 2));

            //weekDay 
            v = v.replace(/E/g, weekDayString);
            return v;
        }

    };

    window.msun$ = msunfun;
})(window);