package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.OrderPageSearch;
import com.rehoshi.model.Order;
import com.rehoshi.service.OrderService;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public PageData<Order> listInPage(@RequestParam(required = false) String name,
                                      @RequestParam(value = "startTime", required = false) String startTimeStr,
                                      @RequestParam(value = "endTime", required = false) String endTimeStr,
                                      @RequestParam(required = false) Integer status,
                                      @RequestParam("page") int pageIndex,
                                      @RequestParam("limit") int pageSize) {
        Date startTime = DateUtil.toDate(startTimeStr);
        Date endTime = DateUtil.toDate(endTimeStr);

        if (endTime != null) {
            endTime = DateUtil.addTime(endTime, 1, DateUtil.Unit.DAY);
        }
        OrderPageSearch search = new OrderPageSearch();
        search.setName(name);
        search.setStartTime(startTime);
        search.setEndTime(endTime);
        search.setStatus(status);

        return orderService.orderInPage(search, pageIndex, pageSize);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public RespData<Boolean> delete(@PathVariable String id) {
        return orderService.deleteById(id);
    }


    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RespData<Order> get(@PathVariable String id) {
        return orderService.getById(id);
    }

    @RequestMapping(value = "delete/all", method = RequestMethod.DELETE)
    @ResponseBody
    public RespData<Boolean> deleteAll(@RequestBody(required = false) List<String> ids) {
        return orderService.deleteInIds(ids);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public RespData<Boolean> update(@RequestBody Order order) {
        order.setCreateTime(DateUtil.toDateTime(order.getCreateTimeStr()));
        return orderService.update(order);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public RespData<String> add(@RequestBody Order order) {
        return orderService.save(order);
    }

}
