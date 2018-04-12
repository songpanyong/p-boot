package com.guohuai.payadapter.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.guohuai.payment.baofu.api.BaoFuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.component.TradeChannel;
import com.guohuai.payadapter.listener.event.ReconciliationPassEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ReconciliationPassListener
 * @Description: 宝付下载对账文件并入库
 *
 */
@Slf4j
@Component
public class BaofooDownloadListener {

	@Autowired
	private BaoFuService baoFuService;
	
	@EventListener(condition = "#event.tradeType == 'reconciliationPass'")
	public void reconciliationPassEvent(ReconciliationPassEvent event) {
		log.info("An event reconciliationPassEvent: {},{}", event.getChannel(),event.getCheckDate());
		String checkDate = event.getCheckDate();// 对账日期
		String channel = event.getChannel();// 渠道
		String checkType = "";
		SimpleDateFormat parse = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
	     try {
	    	 checkDate=format.format(parse.parse(checkDate));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (TradeChannel.baofooDkWithoiding.getValue().equals(channel)
				|| TradeChannel.baofoopay.getValue().equals(channel)
				|| TradeChannel.baofooGateway.getValue().equals(channel)) {// 充值
			checkType = "fi";
		} else if (TradeChannel.baofoopayee.getValue().equals(channel)) {// 提现
			checkType = "fo";
		} else {
			log.info("宝付下载对账文件渠道不正确");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("找不到宝付下载对账文件渠道");
			return;
		}
		String result = "";
		try {
			result = baoFuService.downloadFile(checkDate, checkType);
		} catch (Exception e) {
			log.info("下载对账文件异常");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("下载对账文件异常");
			e.printStackTrace();
			return;
		}
		
		if (!StringUtil.isEmpty(result)) {
			if ("Failed".equals(result)) {
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("获取对账流水失败");
			} else {
				event.setReturnCode(Constant.SUCCESS);
				event.setFileName(result);//对账文件名
			}
		}
		
	}

}
