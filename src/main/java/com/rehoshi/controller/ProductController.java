package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.ProductPageSearch;
import com.rehoshi.model.Product;
import com.rehoshi.service.ProductService;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("product")
@Controller
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public PageData<Product> listInPage(@RequestParam(required = false) String name,
                                        @RequestParam(value = "startTime",required = false) String startTimeStr,
                                        @RequestParam(value = "endTime",required = false) String endTimeStr,
                                        @RequestParam("page") int pageIndex,
                                        @RequestParam("limit") int pageSize) {

        ProductPageSearch search = new ProductPageSearch() ;
        search.setName(name);
        search.setStartTime(DateUtil.toDate(startTimeStr));
        search.setEndTime(DateUtil.addTime(DateUtil.toDate(endTimeStr), 1, DateUtil.Unit.DAY));

        return productService.productInPage(search, pageIndex, pageSize) ;
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RespData<Product> get(@PathVariable String id) {
        return productService.getById(id);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RespData<Boolean> delete(@PathVariable String id) {
        return productService.deleteById(id);
    }

    @RequestMapping(value = "delete/all", method = RequestMethod.DELETE)
    @ResponseBody
    public RespData<Boolean> deleteAll(@RequestBody(required = false) List<String> ids) {
        return productService.deleteInIds(ids);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public RespData<Boolean> update(@RequestBody Product product) {
        return productService.update(product);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public RespData<String> add(@RequestBody Product product) {
        return productService.packing(product) ;
    }

}
