package com.rehoshi.service.impl;

import com.rehoshi.dao.StatisticsMapper;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.echart.ChartData;
import com.rehoshi.dto.echart.Echart;
import com.rehoshi.service.StatisticsService;
import com.rehoshi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    /**
     * 出库统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public RespData<List<ChartData>> outBound(Date startDate, Date endDate) {
        RespData<List<ChartData>> respData = new RespData<List<ChartData>>().fail().setMsg("查询错误");
        List<ChartData> outBoundBetween = statisticsMapper.getOutBoundBetween(startDate, endDate);
        if (!CollectionUtil.isNullOrEmpty(outBoundBetween)) {
            respData.success().setData(outBoundBetween).setMsg("查询成功");
        }
        return respData;
    }

    @Override
    public RespData<Echart> count(Date startDate, Date endDate) {
        return null;
    }


}
