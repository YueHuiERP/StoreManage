package com.rehoshi.dto.excel;

import com.alibaba.excel.metadata.BaseRowModel;

public abstract class ExcelRow<T> extends BaseRowModel {
    public ExcelRow(T model){

    }
}
