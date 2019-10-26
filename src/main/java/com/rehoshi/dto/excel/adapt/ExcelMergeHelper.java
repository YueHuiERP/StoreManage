package com.rehoshi.dto.excel.adapt;

import com.rehoshi.dto.excel.ExcelMergeInfo;
import com.rehoshi.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class ExcelMergeHelper {
    public interface Adapter<M,L,R>{
        List<L> adapt(M model) ;
        R createRow(M model, L subModel) ;
    }

    public static <M, L, R> List<ExcelMergeInfo> merge(int rowCount, List<M> modelList, List<R> rowModelList,Adapter<M, L, R> adapter){
        List<ExcelMergeInfo> mergeInfos = new ArrayList<>();
        CollectionUtil.foreach(modelList, (data, index) -> {
            int startRow = 1 + rowModelList.size();
            CollectionUtil.foreach(adapter.adapt(data), cops -> {
                rowModelList.add(adapter.createRow(data, cops));
            });
            int endRow = 1 + rowModelList.size();
            if (endRow - startRow > 1) {
                for (int i = 0; i < rowCount; i++) {
                    ExcelMergeInfo mergeInfo = new ExcelMergeInfo();
                    mergeInfo.setStartRow(startRow);
                    mergeInfo.setEndRow(endRow - 1);
                    mergeInfo.setStartCol(i);
                    mergeInfo.setEndCol(i);
                    mergeInfos.add(mergeInfo);
                }
            }
        });
        return mergeInfos ;
    }
}
