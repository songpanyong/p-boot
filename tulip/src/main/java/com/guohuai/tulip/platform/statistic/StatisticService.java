package com.guohuai.tulip.platform.statistic;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.tulip.util.CommonConstants;
/**
 * 统计分析Service
 * @author suzhicheng
 *
 */
@Service
@Transactional
public class StatisticService {
	@Autowired
	StatisticDao statisticDao;
	/**
	 * 查询报表统计数据
	 * @return
	 */
	public StatisticChartsRep getStatisticChartsData() {
		StatisticChartsRep returnRep=new StatisticChartsRep();
		List<String> legendData = new ArrayList<String>();
		List<Object[]> list=statisticDao.listByCouponType();
		legendData.add("发放数量");
		legendData.add("未用数量");
		legendData.add("使用数量");
		legendData.add("过期数量");
		List<String> xAxisData = new ArrayList<String>();
		List<SeriesRep> seriesData = new ArrayList<SeriesRep>();
		SeriesRep sr=null;
		List<Integer> numList=null;
		for(String s : legendData){
			numList = new ArrayList<Integer>();
			sr=new SeriesRep();
			sr.setName(s);
			sr.setType("line");
			for(Object[] entity : list){
				if(CommonConstants.STATISTIC_FIELD_SEND.equals(s)){
					numList.add(Integer.parseInt(entity[3].toString()));
				}else if(CommonConstants.STATISTIC_FIELD_NOTUSED.equals(s)){
					numList.add(Integer.parseInt(entity[2].toString()));
				}else if(CommonConstants.STATISTIC_FIELD_USED.equals(s)){
					numList.add(Integer.parseInt(entity[1].toString()));
				}else if(CommonConstants.STATISTIC_FIELD_EXPIRED.equals(s)){
					numList.add(Integer.parseInt(entity[0].toString()));
				}
			}
			sr.setData(numList);
			seriesData.add(sr);
		}
		for(Object[] entity : list){
			xAxisData.add(entity[4].toString());
		}
		returnRep.setLegendData(legendData);
		returnRep.setxAxisData(xAxisData);
		returnRep.setSeriesData(seriesData);
		return returnRep;
	}
	
}
