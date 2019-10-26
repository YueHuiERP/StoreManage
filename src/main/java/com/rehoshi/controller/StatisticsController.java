package com.rehoshi.controller;

import com.rehoshi.dto.RespData;
import com.rehoshi.dto.echart.ChartData;
import com.rehoshi.service.StatisticsService;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {


    @Autowired
    private StatisticsService statisticsService;

    /**
     * 出库统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/outBound", method = RequestMethod.GET)
    public RespData<List<ChartData>> queryOutBound(@RequestParam(name = "startDate", required = false) String startDate
            , @RequestParam(name = "endDate", required = false) String endDate) {

        Date start, end;
        if (startDate == null) {
            start = DateUtil.today();
        } else {
            start = DateUtil.toDate(startDate);
        }
        if (endDate == null) {
            end = DateUtil.today();
        } else {
            end = DateUtil.toDate(endDate);
        }
        return statisticsService.outBound(start, end);
    }


}
