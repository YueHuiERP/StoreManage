package com.rehoshi.service;

import com.rehoshi.dto.RespData;
import com.rehoshi.dto.echart.ChartData;
import com.rehoshi.dto.echart.Echart;

import java.util.Date;
import java.util.List;

public interface StatisticsService {

    /**
     * 出库统计
     * @param startDate
     * @param endDate
     * @return
     */
    RespData<List<ChartData>> outBound(Date startDate, Date endDate);


    /**
     * 成本统计
     * @param startDate
     * @param endDate
     * @return
     */
    RespData<Echart> count(Date startDate,Date endDate);

}
