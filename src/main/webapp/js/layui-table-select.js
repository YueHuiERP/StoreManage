/**
 * 获取选择的对象
 * @param tableId 表的id
 * @param table 表对象
 * @param propName Select 对应的属性名称
 * @param data select回调返回的data
 * @return {object: *, oldValue: *}
 */
function getSelectObject(table, tableId, propName, data) {
    var elem = data.othis.parents('tr');
    var dataindex = elem.attr("data-index");
    var obj = table.cache[tableId][dataindex];
    var oldVal = obj[propName];
    obj[propName] = data.value;
    var returnVal = {object: obj, oldValue: oldVal};
    return returnVal;
}

/**
 * 获取单元格元素当前行的数据
 * @param table layui table对象
 * @param tableId 表格id
 * @param elem 单元格元素
 * @returns {*}
 */
function getCurRowData(table, tableId, elem) {
    var tr = $(elem).parents("tr");
    var index = tr.attr("data-index");
    var obj = table.cache[tableId][index];
    return obj;
}

function reloadTable(searchWin) {
    var win = searchWin ;
    if(win == null){
        win = window ;
    }
    var btnArray = $(".layui-laypage-btn",win.document);
    if (btnArray != null && btnArray.length > 0) {
        btnArray[0].click();
    } else {
        x_admin_father_reload();
    }
}

function getTableWhere(paramArray) {
    var param = {} ;
    for (var i in paramArray){
        var p = $("#" + paramArray[i]) ;
        var val = p.val() ;
        if(val != null && val != ""){
            param[p.attr("name")] = val ;
        }
    }
    return param ;
}