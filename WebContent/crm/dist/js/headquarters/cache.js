//简单设置JS缓存
//1. 将数据设置到缓存：
//JsCache.set(key,value,expirs), expirs也可以不设置，默认是60秒
//读取缓存：JsCache._cacheData.key
var JsCache = {
    set: function (key, value, timeOut) {
        var getDefaultDeadline = function () {
            var nextDay = new Date();
            nextDay.setDate(nextDay.getDate() + 1);
            var deadLine = new Date(nextDay.getFullYear(), nextDay.getMonth(), nextDay.getDate(), 0, 0, 0);
            return deadLine.getTime();
        } ;
        var nameSpace = encode64(window.location.pathname) + "_";
        if (!localStorage.getItem(nameSpace)) {
            localStorage.setItem(nameSpace, getDefaultDeadline());
        }
        var lKey = nameSpace + key;
        var curTime = new Date().getTime();
        localStorage.setItem(lKey, encode64(JSON.stringify(value)))
        if (timeOut) {
            localStorage.setItem(lKey + '_timeout', (curTime + (timeOut * 1000)).toString())
        }
    },

    get: function (key) {
        var nameSpace = encode64(window.location.pathname) + "_";
        var lKey = nameSpace + key;
        var currTime = new Date().getTime();
        var timeOut = localStorage.getItem(lKey + '_timeout');
        if (timeOut) {
            if (timeOut < currTime) {
                this.del(key);
                return null;
            } else {
                return JSON.parse(decode64(localStorage.getItem(lKey)));
            }
        } else {
            var globalTimeOut = localStorage.getItem(nameSpace);
            if (globalTimeOut) {
                if (globalTimeOut < currTime) {
                    this.del(key);
                    return null;
                } else {
                    return JSON.parse(decode64(localStorage.getItem(lKey)));
                }
            } else {
                return null;
            }
        }
    },

    del: function (key) {
        var nameSpace = encode64(window.location.pathname) + "_";
        var lKey = nameSpace + key;
        if (localStorage.getItem(lKey + '_timeout')) {
            localStorage.removeItem(lKey);
            localStorage.removeItem(lKey + '_timeout');
        } else {
            localStorage.removeItem(nameSpace);
            var iLen = localStorage.length;
            for (var i = 0; i < iLen; i++) {
                if (localStorage.key(0).indexOf(nameSpace) == 0 && localStorage.key(0).indexOf('_timeout') < 0) {
                    localStorage.removeItem(localStorage.key(0));
                }
            }
        }
    }
};