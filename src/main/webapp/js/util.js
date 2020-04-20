
function isDouble(val) {
    return isMoney(val) ;
}

function moneyVerifier(val) {
    if(val != 0 && !isMoney(val)){
        return "请输入正确金额" ;
    }
}

function isMoney(val) {
    val = val.toString()
    let reg = /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/;
    return isNaturalNum(val) || reg.test(val) ;
}

function isNaturalNum(val) {
    val = val.toString() ;
    var regPos = /^\d+$/; // 非负整数
    return regPos.test(val) ;
}

function isIntNum(val){
    var regNeg = /^\-[1-9][0-9]*$/; // 负整数
    if(isNaturalNum(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }
}

function chooseStock(callback) {
    x_admin_show("选择库存", "../stock/choose.html?minRemain=0", 900, 600, parent, null, null, function () {
        if (parent.chooseStock != null) {
            parent.chooseStock.specs = parent.chooseStock.goods.specs;
            parent.chooseStock.specsUnit = parent.chooseStock.goods.specsUnit;
            callback(parent.chooseStock) ;
        }
    });
    return false;
}

function chooseGoods(callback, minSpecs) {
    if(minSpecs == null){
        minSpecs = 0 ;
    }
    x_admin_show("选择商品", "../goods/chooseGoodsType.html?type=0&minSpecs=" + minSpecs, 900, 600, parent, null, null, function () {
        callback(parent.goods);
    });
    return false;
}