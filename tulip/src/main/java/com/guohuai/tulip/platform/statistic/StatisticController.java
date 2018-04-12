package com.guohuai.tulip.platform.statistic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
@RestController
@RequestMapping(value = "/tulip/boot/statistic", produces = "application/json")
public class StatisticController extends BaseController {
	@Autowired
	private StatisticService statisticService;
	@RequestMapping(value = "statisticChartsData", method = RequestMethod.POST)
	@ResponseBody
	public StatisticChartsRep statisticChartsData(){
		return statisticService.getStatisticChartsData();
	}
}
