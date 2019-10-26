package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.ManifestPageSearch;
import com.rehoshi.model.Manifest;
import com.rehoshi.service.ManifestService;
import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("manifest")
public class ManifestController extends BaseController {

    @Autowired
    private ManifestService manifestService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageData<Manifest> listInPage(@RequestParam(required = false) String name,
                                         @RequestParam(value = "startTime", required = false) String startTimeStr,
                                         @RequestParam(value = "endTime", required = false) String endTimeStr,
                                         @RequestParam(required = false) Integer status,
                                         @RequestParam("page") int pageIndex,
                                         @RequestParam("limit") int pageSize) {
        Date startTime = DateUtil.toDate(startTimeStr);
        Date endTime = DateUtil.toDate(endTimeStr);

        if (endTime != null) {
            endTime = DateUtil.endOfDay(endTime);
        }
        ManifestPageSearch search = new ManifestPageSearch();
        search.setName(name);
        search.setStartTime(startTime);
        search.setEndTime(endTime);
        search.setStatus(status);
        search.setPageIndex(pageIndex);
        search.setPageSize(pageSize);

        return manifestService.listInPage(search);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public RespData<Boolean> delete(@PathVariable String id) {
        return manifestService.deleteById(id);
    }


    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public RespData<Manifest> get(@PathVariable String id) {
        return manifestService.getById(id);
    }

    @RequestMapping(value = "delete/all", method = RequestMethod.DELETE)
    public RespData<Boolean> deleteAll(@RequestBody(required = false) List<String> ids) {
        return manifestService.deleteInIds(ids);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public RespData<Boolean> update(@RequestBody Manifest manifest) {
        manifest.setCreateTime(DateUtil.toDateTime(manifest.getCreateTimeStr()));
        return manifestService.update(manifest);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public RespData<String> add(@RequestBody Manifest manifest) {
        manifest.setCreateTime(DateUtil.toDateTime(manifest.getCreateTimeStr()));
        return manifestService.save(manifest);
    }

    @PostMapping("add/all")
    public RespData<Boolean> addAll(@RequestBody List<Manifest> manifests){
        CollectionUtil.foreach(manifests, data -> {
            data.setCreateTime(DateUtil.toDateTime(data.getCreateTimeStr()));
        });
        return manifestService.save(manifests) ;
    }


    @GetMapping(value = "batch/prepare/list/{gId}")
    public RespData<List<Manifest>> batchPrepareList(@PathVariable String gId){
      return   manifestService.batchPrepareListByGid(gId) ;
    }

}
