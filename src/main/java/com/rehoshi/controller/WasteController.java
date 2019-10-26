package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.WastePageSearch;
import com.rehoshi.model.Waste;
import com.rehoshi.service.WasteService;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("waste")
public class WasteController extends BaseController {

    @Autowired
    WasteService wasteService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageData<Waste> listInPage(@RequestParam(value = "name",required = false) String name,
                                      @RequestParam("page") int pageIndex,
                                      @RequestParam("limit") int pageSize,
                                      @RequestParam(value = "startTime", required = false) String startTimeStr,
                                      @RequestParam(value = "endTime", required = false) String endTimeStr) {
        Date startDate = DateUtil.toDate(startTimeStr);
        Date endDate = DateUtil.toDate(endTimeStr);

        if (endDate != null) {
            endDate = DateUtil.endOfDay(endDate);
        }
        WastePageSearch search = new WastePageSearch();
        search.setStartTime(startDate);
        search.setEndTime(endDate);
        search.setName(name);
        return wasteService.wasteInPage(search, pageIndex, pageSize) ;
    }

    @RequestMapping(value = "get/{id}",method = RequestMethod.GET)
    public RespData<Waste> get(@PathVariable String id){
        return wasteService.getById(id) ;
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RespData<String> save(Waste waste){
        return wasteService.save(waste) ;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public RespData<Boolean> update(Waste waste){
        return wasteService.update(waste) ;
    }

    @RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
    public RespData<Boolean> delete(@PathVariable String id){
        return wasteService.deleteById(id) ;
    }

    @RequestMapping(value = "delete/all", method = RequestMethod.DELETE)
    public RespData<Boolean> deleteAll(@RequestBody(required = false) List<String> ids){
        return wasteService.deleteInIds(ids) ;
    }
}
