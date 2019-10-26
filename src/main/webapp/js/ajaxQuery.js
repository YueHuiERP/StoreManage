var RespCode = {
    SUCCESS: 200,
    FAIL: 0,
    TOKEN_TIMEOUT: 1,
}

function showError(msg) {
    layui.use(['']);
    layer.alert(msg, {icon: 2});
}

function showSuccess(msg) {
    layer.alert(msg, {icon: 6});
}

function showToast(msg, layui) {
    if(layui != null){
        layui.layer.msg(msg, {time: 1000,  shade: 0.01});
    }else {
        layer.msg(msg, {time: 1000, shade: 0.01});
    }
}

function showShake(msg, layui) {
    if(layui != null){
        layui.layer.msg(msg, {time: 1000, anim: 6, shade: 0.01});
    }else {
        layer.msg(msg, {time: 1000, anim: 6, shade: 0.01});
    }
}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURIComponent(r[2]);
    return null;
}

function confirmBatchOperation(callback, msg) {
    if (msg == null) {
        msg = "确认删除勾选数据吗？";
    }
    confirmOperation(callback, msg);
}

function confirmOperation(callback, msg) {
    if (msg == null) {
        msg = "确认删除当前数据吗？";
    }
    layer.confirm(msg, function () {
        callback() ;
    });
}

/**
 * 回显列表查询的参数
 * @param inputId 输入框的id
 */
function bindInputParam(inputId) {
    var input = $("#" + inputId);
    input.val(getQueryString(input.attr("name")));
}

function backToLogin() {
    getRootWindow(window).location.href = "/";
}

//针对有内联框架的页面 获取最底层窗口
function getRootWindow(curWindow, preWindow) {
    if (curWindow !== preWindow) {
        return getRootWindow(curWindow.parent, curWindow);
    }
    return curWindow;
}

/**
 * 请求管理器
 * 根据请求发起对象 进行缓存
 * 不让请求重复发起
 * @type {{}}
 */
var queryManager = {
    cache: {},
    getKey: function (sender) {
        if (isString(sender)) {
            return sender;
        }
        //返回元素id
        return sender == null ? "null" : sender.attr("id");
    },
    put: function (sender) {
        //插入缓存
        var key = this.getKey(sender);
        this.cache[key] = true;
    },
    duringQuery: function (sender) {
        //查找是否有缓存
        var key = this.getKey(sender);
        var query = this.cache[key];
        return query === true;
    },
    remove: function (sender) {
        //删除缓存
        delete this.cache[this.getKey(sender)];
    },
};

function isString(obj) {
    return 'string' === typeof (obj);
}

function isNum(val) {
    if (val === "" || val == null) {
        return false;
    }
    if (!isNaN(val)) {
        return true;
    } else {
        return false;
    }
}


function toJson(obj) {
    if (obj == null) {
        return null;
    }
    if (isString(obj)) {
        return "\"" + obj + "\"";
    } else if (obj instanceof Number) {
        return obj.toString();
    } else {
        var open = "{";
        var close = "}";
        var isArray = false;
        if (obj instanceof Array) {
            isArray = true;
            open = "[";
            close = "]";
        }
        var json = open;
        for (var i in obj) {
            var o = obj[i];
            if (o != null) {
                var j = toJson(o);
                if (j != null) {
                    if (json != open) {
                        json += ",";
                    }
                    if (!isArray) {
                        json += "\"" + i + "\":";
                    }
                    json += j;
                }
            }
        }
        json += close;
        return json;
    }
}

var FORM_TYPE = "application/x-www-form-urlencoded";
var JSON_TYPE = "application/json";


/**
 * 请求json的请求器
 * @constructor
 */
function HoshiQueryJson() {

    //发送请求的对象
    var querySender = null;

    //业务成功的回调
    var onSuccessCallback = null;

    //业务失败回调
    var onFailCallback = null;

    //请求出错的回调
    var onErrorCallback = null;

    //请求被响应的回调
    var onResponseCallback = null;

    //登录过期的回调
    var onTokenTimeOutCallback = null;


    //默认的参数
    var ajaxQueryParam = {
        //默认get请求
        type: "GET",
        //默认是表单类型
        contentType: FORM_TYPE,
        //数据返回类型 定死是 Json
        dataType: "JSON",
        //成功的回调
        success: function (data) {
            var handled = false;
            switch (data.code) {
                case RespCode.SUCCESS:
                    if (onSuccessCallback != null) {
                        handled = onSuccessCallback(data.data, data.msg, data.code);
                    }
                    break;
                case RespCode.TOKEN_TIMEOUT:
                    if (onTokenTimeOutCallback != null) {
                        handled = onTokenTimeOutCallback(data.data, data.msg, data.code);
                    }
                    if (!handled) {
                        //token过期重新登录
                        layui.use(['layer'], function () {
                            layui.layer.alert("登录过期，请重新登录", {icon: 0}, function () {
                                //重新登录
                                backToLogin();
                            });
                        })
                    }
                    break;
                case RespCode.FAIL:
                    if (onFailCallback != null) {
                        handled = onFailCallback(data.data, data.msg, data.code);
                    }
                    if (!handled) {
                        showError(data.msg);
                    }
                    break;
            }
        },
        //网络错误的回调
        error: function (xhr) {
            var handled = false;
            if (onErrorCallback != null) {
                handled = onErrorCallback(data.data, data.msg, data.code);
            }
            if (!handled) {
                showError("网络错误")
            }
        },
        //请求完成之后的回调
        complete: function (xhr) {
            if (onResponseCallback != null) {
                onResponseCallback();
            }
            //完成请求之后删除缓存
            queryManager.remove(querySender);
        }
    };
    //设置请求url
    this.url = function (url) {
        ajaxQueryParam.url = url;
        return this;
    }
    //发送get请求
    this.get = function (url) {
        this.method("GET").url(url);
        return this;
    }
    //发送post请求
    this.post = function (url) {
        this.method("POST").url(url);
        return this;
    }
    //发送put请求
    this.put = function (url) {
        this.method("PUT").url(url);
        return this;
    }
    //发送delete请求
    this.delete = function (url) {
        this.method("DELETE").url(url);
        return this;
    }
    //设置请求参数
    this.param = function (data, arrayName) {
        switch (ajaxQueryParam.contentType) {
            case JSON_TYPE:
                //字符类型 不做json转换
                if (!isString(data)) {
                    data = JSON.stringify(data);
                }
                break
            case FORM_TYPE:
            default:
                //字符类型 不做form转换
                if (!isString(data)) {
                    var p = "";
                    if (data instanceof Array) {//特殊处理数组对象
                        p = {};
                        p[arrayName] = data;
                    } else {
                        for (var key in data) {
                            var val = data[key];
                            if (val != null) {
                                if (p !== "") {
                                    p += "&";
                                }
                                p += key;
                                p += "=" + val;
                            }
                        }
                    }
                    data = p;
                }

        }
        ajaxQueryParam.data = data;
        return this;
    }
    //设置表单参数
    this.formParam = function (data, arrayName) {
        this.contentType("application/x-www-form-urlencoded").param(data, arrayName);
        return this;
    }
    //设置json参数
    this.jsonParam = function (data) {
        this.contentType("application/json").param(data);
        return this;
    }
    //设置参数类型
    this.contentType = function (contentType) {
        ajaxQueryParam.contentType = contentType;
        return this;
    }
    //设置参数方法
    this.method = function (type) {
        ajaxQueryParam.type = type;
        return this;
    }

    this.onSuccess = function (callback) {
        onSuccessCallback = callback;
        return this;
    }
    this.onFail = function (callback) {
        onFailCallback = callback;
        return this;
    }
    this.onResponse = function (callback) {
        onResponseCallback = callback;
        return this;
    }
    this.onError = function (callback) {
        onErrorCallback = callback;
        return this;
    }
    this.onTokenTimeOut = function (callback) {
        onTokenTimeOutCallback = callback;
        return this;
    }

    /**
     * 发送请求
     * @param sender
     */
    this.linkStart = function (sender) {
        if (sender == null) {
            sender = ajaxQueryParam.url;
        }
        //如果没有正在查询的缓存
        if (!queryManager.duringQuery(sender)) {
            querySender = sender;
            queryManager.put(sender);
            //发送ajax请求
            $.ajax(ajaxQueryParam);
        }
    }
}

$.hoshi = function () {
    return new HoshiQueryJson();
}


function exportExcel(tableName, formId) {
    location.href = "/excel/export/" + tableName + "?" + $("#" + formId).serialize();
}
