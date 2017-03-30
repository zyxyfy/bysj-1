/// <reference path="../../static/msunsoftlib.js" />
/// <reference path="../../static/easyui/jquery.min.js" />

var timeLineObj;

$(function () {
    msun$.setDivMax(document.getElementById("divall"));
    initDivContent();
    $("#spanage").html(msun$.CountAge(birthday));
    timeLineObj = new TimeLine(timelinestart + "至今", "divRight");
    //  timeLineObj.addEventList([{ id: "a1", timeText: "2016-09-01 12:11:59", htmlText: "患者在中医院出院患者在中医院出院.", type: "outhos", css: "", attribute: {} }, { id: "a4", timeText: "2016-03-02 12:11:59", htmlText: "患者在中医院出院患者在中医院出院.", type: "outhos", css: "", attribute: {} }, { id: "a2", timeText: "2016-03-02", htmlText: "患者在中医院出院.", clickfunction: timeLineClick, type: "outhos", css: "", attribute: {} }, { id: "a5", timeText: "2015-09-01 12:11:59", htmlText: "患者在中医院出院患者在中医院出院.", type: "outhos", css: "", attribute: {} }]);
    // timeLineObj.addEventtoEnd({ id: "a3", timeText: "2016-03-02 23:33:21", htmlText: "患者在中医院出院患者在中医院出院患者在中医院出院.", type: "outhos", css: "", attribute: {} });
    dealPatTreatInfo();

    timeLineObj.addEventList(alleventdatalist);
});

/**
 * 初始化divcontent内容
 */
function initDivContent() {
    var h = $("#divall").height() - $("#divpatinfo").height() - $("#pathealthInfo").height() - 10;
    $("#divContent").height(h);
    $("#divLeft").height(h - 1);
    $("#divRight").height(h - 1);
    $("#patClinicinfo").height(parseInt(h / 3 - 55));
    $("#patSignandmb").height(parseInt(h / 3 - 55));
    $("#patpubhealthy").height(parseInt(h / 3 - 55));

}

/**
 * 时间轴控件
 */
function TimeLine(startTimeStr, id) {
    var othis = this;
    var datademo = { id: "a1", timeText: "03-02", htmlText: "患者在中医院出院.", clickfunction: timeLineClick, type: "outhos", css: "", attribute: {} };
    this.dataDic = [];
    this.datalist = [];
    this.dom1 = null;
    this.dom1$ = null;
    this.init = function () {
        document.getElementById(id).innerHTML = '<ul class="event_list"><div id="timeLineContent1"><h3 id="startTitle">' + startTimeStr + '</h3></div></ul>';
        othis.dom1 = document.getElementById("timeLineContent1");
        othis.dom1$ = $(othis.dom1);
    };
    this.init();
    this.getshortTimeStr = function (timestr) {
        if (timestr.length > 16) {
            return timestr.substring(0, 16)
        } else {
            return timestr;
        }
    };
    this.addEventtoEnd = function (data) {
        if (othis.dataDic[data.id]) {
            alert("存在重复的id");
            return;
        }
        othis.dataDic[data.id] = data;
        othis.datalist.push(data);
        var html = '<li><span>' + othis.getshortTimeStr(data.timeText) + '</span><p><span class="link" id="timespan_' + data.id + '" >' + data.htmlText + '</span></p></li>';
        othis.dom1$.append($(html));
        $("#timespan_" + data.id).click(function () {
            timeLineClick(data);
        });
    };
    /**
     * 对datalist按时间进行排序并添加
     */
    this.addEventList = function (datalist) {
        datalist.sort(function (a, b) {
            if (a.timeText > b.timeText) {
                return -1;
            }
            if (a.timeText == b.timeText) {
                return 0;
            }
            return 1;
        });
        var htmllist = [];
        for (var i = 0; i < datalist.length; i++) {
            var data = datalist[i];
            if (othis.dataDic[data.id]) {
                alert("存在重复的id");
                return;
            }
            othis.dataDic[data.id] = data;
            othis.datalist.push(data);
            htmllist.push('<li><span>' + othis.getshortTimeStr(data.timeText) + '</span><p><span class="link" id="timespan_' + data.id + '" >' + data.htmlText + '</span></p></li>');
        }
        othis.dom1$.append($(htmllist.join("")));
        for (var i = 0; i < datalist.length; i++) {
            var data = datalist[i];
            $("#timespan_" + data.id).click(function () {
                timeLineClick(data);
            });
        }
    }


}


function timeLineClick(data) {
    alert(data);
}


var alleventdatalist = [];

function dealPatTreatInfo() {
    var mzhoslist = [];
    var zyhoslist = [];
    var mzdialist = [];
    var zydialist = [];
    var ishasmzhoslist = false;
    var ishasmzdialist = false;
    var ishaszyhoslist = false;
    var ishaszydialist = false;
    for (var i = 0; i < patTreatInfo.length; i++) {
        var obj = patTreatInfo[i];
        var info = "患者";
        var type = "";
        if (obj.type == "1") {
            ishasmzhoslist = true;
            info += "在" + obj.oraName + "因" + obj.diagnosisName + "就诊";
            type = "treatout";
            if (mzhoslist[obj.oraName]) {
                mzhoslist[obj.oraName]++;
            } else {
                mzhoslist[obj.oraName] = 1;
            }
            if (obj.diagnosisName != "") {
                ishasmzdialist = true;
                if (mzdialist[obj.diagnosisName]) {
                    mzdialist[obj.diagnosisName]++;
                } else {
                    mzdialist[obj.diagnosisName] = 1;
                }
            }


        }
        if (obj.type == "2") {
            ishaszyhoslist = true;
            info += "在" + obj.oraName + "因" + obj.diagnosisName + "住院";
            type = "inhos";
            if (zyhoslist[obj.oraName]) {
                zyhoslist[obj.oraName]++;
            } else {
                zyhoslist[obj.oraName] = 1;
            }
            if (obj.diagnosisName != "") {
                ishaszydialist = true;
                if (zydialist[obj.diagnosisName]) {
                    zydialist[obj.diagnosisName]++;
                } else {
                    zydialist[obj.diagnosisName] = 1;
                }
            }

        }
        var eventobj = {
            id: "zl_" + obj.id,
            timeText: obj.time.substring(0, 10),
            htmlText: info,
            type: type,
            css: "",
            attribute: {}

        };
        alleventdatalist.push(eventobj);
    }
    //近期就诊信息
    var mzinfo = "";
    if ( !ishasmzhoslist) {
        mzinfo = "患者近期没有门诊就诊记录！";
    } else {
        mzinfo = "患者近期在:";
        for (var obj in mzhoslist) {
            mzinfo += (obj + "(" + mzhoslist[obj] + "次),");
        }
        mzinfo = mzinfo.substring(0, mzinfo.length - 1);
        mzinfo += "。";
    }

    if (ishasmzdialist) {
        mzinfo += "诊断包括：";
        for (var obj in mzdialist) {
            mzinfo += (obj + ",");
        }
        mzinfo = mzinfo.substring(0, mzinfo.length - 1);
    }
    $("#nearMzinfo").html(mzinfo);
    //住院
    var zyinfo = "";
    if (!ishaszyhoslist) {
        zyinfo = "患者近期没有住院记录！";
    } else {
        zyinfo = "患者近期在:";
        for (var obj in zyhoslist) {
            zyinfo += (obj + "(" + zyhoslist[obj] + "次),");
        }
        zyinfo = zyinfo.substring(0, zyinfo.length - 1);
        zyinfo += "住院。";
    }

    if (ishaszydialist) {
        zyinfo += "诊断包括：";
        for (var obj in zydialist) {
            zyinfo += (obj + ",");
        }
        zyinfo = zyinfo.substring(0, zyinfo.length - 1);

    }
    $("#nearzyinfo").html(zyinfo);

}


//打开患者健康档案
function openPatHeath() {

    var width = screen.width;
    var height = screen.height;
    var url = healthService + "/common/main.action?id=" + patCardId;
    window.open(url, '', 'height=' + height + ',width=' + width + ',top=0,left=0,scrollbars=0,resizable=yes,titlebar=1');
}


function TestArrPro2342323223423420() {
    var zytestarr982398732894704329727480423 = [];
    for (var $$$testitem$$$$ in zytestarr982398732894704329727480423) {
        alert("数组中出现扩展属性，程序会出现严重数据错误，请及时改正。客户请速与管理员联系，不要使用系统！");
    }
}

setTimeout(TestArrPro2342323223423420, 10000);