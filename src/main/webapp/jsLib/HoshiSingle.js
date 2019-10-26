
let HoshiSingle ={
    obtain:function (id) {
        return getSingle(id) ;
    }
}

const hoshiKey = "__hoshiSingle__" ;
function getSingle(id) {
    let obj = new Proxy({},{
        id:hoshiKey + id,//保存id
        set:function(target,key,value,receiver){
            window.sessionStorage.setItem(getPropertyName(id, key), value) ;
            //处理代码
           return  target[key] = value;
        },
        get:function (target, key, receiver) {
            let value = window.sessionStorage.getItem(getPropertyName(id, key)) ;
            target[key] = value ;
           return target[key];
        }
    }) ;
    return obj ;
}

function getPropertyName(id, property) {
    return id + "_" + property ;
}

/**
 * 一个网站在整个session中的单例
 * @constructor
 */
