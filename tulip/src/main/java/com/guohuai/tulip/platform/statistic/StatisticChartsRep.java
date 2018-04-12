package com.guohuai.tulip.platform.statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticChartsRep {
	private List<String> legendData = new ArrayList<String>();
	private List<String> xAxisData = new ArrayList<String>();
	private List<SeriesRep> seriesData = new ArrayList<SeriesRep>();

	public List<String> getLegendData() {
		return legendData;
	}

	public void setLegendData(List<String> legendData) {
		this.legendData = legendData;
	}

	public List<String> getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(List<String> xAxisData) {
		this.xAxisData = xAxisData;
	}

	public List<SeriesRep> getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(List<SeriesRep> seriesData) {
		this.seriesData = seriesData;
	}

}
