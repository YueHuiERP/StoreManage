
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