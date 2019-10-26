package com.rehoshi.dto.excel.adapt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableInfo {

    private List<String> columns = new ArrayList<>();

    private List<List<Object>> values = new ArrayList<>() ;

}
