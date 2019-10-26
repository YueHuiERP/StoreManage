package com.rehoshi.dto.echart;

import java.util.List;

/**
 * 封装返回页面的echart数据
 */
public class Echart {

    private List<String> legend;

    private List<String> xAxis;

    private List<Series> series;


    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
