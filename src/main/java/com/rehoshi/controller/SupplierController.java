package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.GoodPageSearch;
import com.rehoshi.dto.search.SupplierPageSearch;
import com.rehoshi.model.Goods;
import com.rehoshi.model.Supplier;
import com.rehoshi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("supplier")
public class SupplierController extends BaseController {
    @Autowired
    private SupplierService supplierService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public RespData<Supplier> getById(@PathVariable String id) {
        return supplierService.getById(id);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageData<Supplier> listIdnPage(
            @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "page") int pageIndex
            , @RequestParam(value = "limit") int pageSize) {


        SupplierPageSearch search = new SupplierPageSearch();
        search.setName(name);
        search.setPageIndex(pageIndex);
        search.setPageSize(pageSize);
        return supplierService.listInPage(search);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RespData<String> add(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public RespData deleteById(@PathVariable String id) {
        return supplierService.deleteById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public RespData<Boolean> update(@RequestBody Supplier supplier) {
        return supplierService.update(supplier);
    }

    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public RespData<Boolean> deleteAll(@RequestBody List<String> ids) {
        return supplierService.deleteInIds(ids);
    }
}
