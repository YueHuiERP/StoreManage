package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Product;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.model.Stock;
import lombok.Data;

/**
 * 财务统计的数据传输类
 */
@Data
public class FinanceRow extends ExcelRow<Stock> {
    @ExcelProperty(value = "原料批次", index = 1)
    private String stockBatch;
    @ExcelProperty(value = "原料名称", index = 2)
    private String stockName;
    @ExcelProperty(value = "数量", index = 3)
    private Integer stockAmount;
    @ExcelProperty(value = "价格", index = 4)
    private Double stockPrice;
    @ExcelProperty(value = "单价", index = 5)
    private Double stockUnitPrice;
    @ExcelProperty(value = "剩余数量", index = 6)
    private Double stockRemainAmount;

    @ExcelProperty(value = "损耗", index = 7)
    private Double stockWasteAmount;
    @ExcelProperty(value = "误差", index = 8)
    private Double stockOffsetAmount;
    @ExcelProperty(value = "原料折合单价", index = 9)
    private Double productUnitPrice;

    @ExcelProperty(value = "生产时间", index = 10)
    private String productTime;
    @ExcelProperty(value = "成品名称", index = 11)
    private String productName;
    @ExcelProperty(value = "成品斤数", index = 12)
    private Double productSpecs;
    @ExcelProperty(value = "成品数量", index = 13)
    private Integer productAmount;
    @ExcelProperty(value = "打包费用", index = 14)
    private Double productPkgFee;
    @ExcelProperty(value = "原料数量", index = 15)
    private Double materialAmount;
    @ExcelProperty(value = "包材批次", index = 16)
    private String pkgMaterialBatch;
    @ExcelProperty(value = "包材名称", index = 17)
    private String pkgMaterialName;
    @ExcelProperty(value = "包材单价", index = 18)
    private Double pkgMaterialUnitPrice;
    @ExcelProperty(value = "单果成本", index = 19)
    private Double singleCost;
    @ExcelProperty(value = "成品成本", index = 20)
    private Double productCost;

    public FinanceRow(Stock stock, Product product) {
        super(stock);

        if (stock != null) {
            Integer amount = stock.getAmount();
            this.setStockBatch(stock.getBatch());
            this.setStockName(stock.getName());
            this.setStockAmount(amount);
            this.setStockPrice(stock.getPrice());
            this.setStockUnitPrice(amount == 0 ? 0 : (stock.getPrice() / amount));
            this.setStockRemainAmount(stock.getRemainAmount());

            Stock parent = stock.getParent();
            if (parent == null) {
                parent = stock;
            }
            this.setStockWasteAmount(parent.getWasteAmount());
            this.setStockOffsetAmount(parent.getOffsetAmount());
            this.setProductUnitPrice(parent.getProductPrice());
        }

        if (product != null) {
            this.setProductTime(product.getCreateTimeInList());
            this.setProductName(product.getName());
            this.setProductSpecs(product.getSpecsValue());
            this.setProductAmount(product.getAmount());
            this.setProductPkgFee(product.getPackFee());
            ProductComposition mainMaterial = product.getMainMaterial();
            if (mainMaterial != null) {
                this.setMaterialAmount(mainMaterial.getAmount());
            }

            ProductComposition packageMaterial = product.getPackageMaterial();
            if (packageMaterial != null) {
                Stock pkgStock = packageMaterial.getStock();
                if (pkgStock != null) {
                    this.setPkgMaterialBatch(pkgStock.getBatch());
                    this.setPkgMaterialName(pkgStock.getName());
                    this.setPkgMaterialUnitPrice(pkgStock.getUnitPrice());
                }
                this.setSingleCost(product.getMainCost());
                this.setProductCost(product.getCost());
            }
        }
    }

}
