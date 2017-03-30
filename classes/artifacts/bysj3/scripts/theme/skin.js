var basePath;

	/* 
	    保存Cookie 
	    c_name--key 
	    value---value 
	    默认时长为一年 
    */  
    function setCookie(c_name, value, expiredays) {  
        var exdate = new Date();  
        exdate.setDate(exdate.getDate() + expiredays);  
        document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : "; expires=" + exdate.toGMTString());  
    }  
    /* 
    	获取Cookie,根据c_name--key值来获取 
    */  
    function getCookie(c_name,path) {  
    	basePath = path;
        if (document.cookie.length > 0) {  
            var c_start = document.cookie.indexOf(c_name + "=");  
            if (c_start != -1) {  
                c_start = c_start + c_name.length + 1;  
                var c_end = document.cookie.indexOf(";", c_start);  
                if (c_end == -1) {  
                    c_end = document.cookie.length;  
                }  
                return unescape(document.cookie.substring(c_start, c_end));  
            }  
        }  
        return "";  
    }  
    /* 
    	递归换肤 
    */  
    function changeSkin(winObj, cssPath) { 
      var linkObj = document.getElementById("style"); //获取link对象  
      if (linkObj != null) {  
    	  linkObj.href = basePath + "/styles/theme/" + cssPath + "/"+ cssPath +".css"; 
      }
    }  
    /* 
    	下拉框调用此方法 
    */  
    function changeCss(winObj, cssPath) {  
        setCookie("cssPath", cssPath, 365); //设置cookie  
        changeSkin(winObj, cssPath); //换肤  
    }  