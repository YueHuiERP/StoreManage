package com.rehoshi.dto.excel;

import com.rehoshi.dto.excel.adapt.TableInfo;
import com.rehoshi.model.Product;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.util.CollectionUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class ProductTable {

    public static TableInfo createTable(List<Product> productList) {

        TableInfo tableInfo = new TableInfo();
        tableInfo.getColumns().add("成品名称");
        tableInfo.getColumns().add("成品数量");
        tableInfo.getColumns().add("成品剩余");
        tableInfo.getColumns().add("生产时间");
        tableInfo.getColumns().add("打包费用");
        tableInfo.getColumns().add("原料名称");
        tableInfo.getColumns().add("原料批次");
        tableInfo.getColumns().add("批次价格");
        tableInfo.getColumns().add("入库数量");
        tableInfo.getColumns().add("原料数量");


        AtomicReference<Integer> mainCount = new AtomicReference<>(1);

        AtomicReference<Integer> supCount = new AtomicReference<>(0);

        AtomicInteger maxSize = new AtomicInteger();

        CollectionUtil.foreach(productList, (data, index) -> {
            List<Object> values = new ArrayList<>();
            values.add(data.getName());
            values.add(data.getAmount());
            values.add(data.getRemainAmount());
            values.add(data.getCreateTimeStr());
            values.add(data.getPackFee());

            //按照是否是原材料分组
            Map<Boolean, List<ProductComposition>> group = CollectionUtil.group(data.getCompositions(), ProductComposition::isMain);
            List<ProductComposition> mainList = group.get(true);
            List<ProductComposition> supList = group.get(false);
            if (mainList != null) {
                for (int i = mainCount.get(); i < mainList.size(); i++) {
                    tableInfo.getColumns().add("原料" + i + "名称");
                    tableInfo.getColumns().add("原料" + i + "批次");
                    tableInfo.getColumns().add("批次价格");
                    tableInfo.getColumns().add("入库数量");
                    tableInfo.getColumns().add("原料" + i + "数量");
                }

                assembleCops(values, mainList, supCount.get());

                int size = mainList.size();
                if(size > mainCount.get()){
                    mainCount.set(size);
                }
            }
            if(!CollectionUtil.isNullOrEmpty(supList)){
                for (int i = supCount.get(); i < supList.size(); i++) {
                    tableInfo.getColumns().add("辅助材料" + (i + 1) + "名称");
                    tableInfo.getColumns().add("辅助材料" + (i + 1) + "批次");
                    tableInfo.getColumns().add("批次价格");
                    tableInfo.getColumns().add("入库数量");
                    tableInfo.getColumns().add("辅助材料" + (i + 1) + "数量");
                }

                assembleCops(values, supList, supCount.get());

                int size = supList.size();
                if(size > supCount.get()){
                    supCount.set(size);
                }
            }

            int size = values.size();
            if(maxSize.get() < size){
                maxSize.set(size);
            }

            tableInfo.getValues().add(values);
        });

        //填充空格
        CollectionUtil.foreach(tableInfo.getValues(), data -> {
            for (int i = data.size(); i < maxSize.get(); i++){
                data.add("") ;
            }
        });

        return tableInfo;
    }

    private static void assembleCops(List<Object> values, List<ProductComposition> copsList, int maxCount) {
        CollectionUtil.foreach(copsList, cops -> {
            values.add(cops.getStock().getGoods().getName());
            values.add(cops.getStock().getBatch());
            values.add(cops.getStock().getAmount());
            values.add(cops.getStock().getPrice());
            values.add(cops.getAmount());
        } );
    }

}
