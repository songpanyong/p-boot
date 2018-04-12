package com.guohuai.points.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guohuai.basic.common.SeqGenerator;
import com.guohuai.points.entity.AccountInfoEntity;
import com.guohuai.points.service.AccountInfoService;
import com.guohuai.points.service.AccountTradeService;
import com.guohuai.points.component.TradeType;
import com.guohuai.points.request.AccountTradeRequest;

/**
 * 
 * @ClassName: PointsOverdueTaskJob
 * @Description: 积分过期定时任务
 * @author chendonghui
 * @date 2017年4月19日 下午17:22:53
 *
 */
@Slf4j
@Component
public class PointsOverdueTaskJob {

	@Autowired
	private JobControllerService jobControllerService;
	
	@Autowired
	AccountInfoService accountInfoService;
	
	@Autowired
	AccountTradeService accountTradeService;
	
	@Autowired
	private SeqGenerator seqGenerator;
	
	@Scheduled(cron="${jobs.PointsOverdueTaskJob.schedule:0 0 0 1 * ?}")
	public void pointsOverdue() {
		String host = null;
		try {
			host = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			log.info("获取IP异常:{}",e);
			e.printStackTrace();
		}
		String jobName = this.getClass().getName();
		log.info("====================定时任务{}-->start====================",jobName);
		boolean isNeedRunJob = jobControllerService.isNeedRunJob(jobName);
		if(isNeedRunJob){
			log.info("====================定时任务{}-->run on{}====================",jobName,host);
			//查询过期的积分账户
			List<AccountInfoEntity> overdueAccountList = accountInfoService.getOverdueAccount();
			if(overdueAccountList != null){
				log.info("过期积分账户批处理,本次处理{}",overdueAccountList.size());
				for(AccountInfoEntity accountInfoEntity : overdueAccountList){
					AccountTradeRequest accountTradeRequest = creatTradeRequest(accountInfoEntity);
					accountTradeService.trade(accountTradeRequest);
				}
			}
			log.info("====================定时任务{}-->end{}====================",jobName);
		}else{
			log.info("====================定时任务{}-->end with doing nothing====================",jobName);
		}
	}

	/**
	 * trade
	 * @param accountInfoEntity
	 * @return
	 */
	private AccountTradeRequest creatTradeRequest(
			AccountInfoEntity accountInfoEntity) {
		AccountTradeRequest accountTradeRequest = new AccountTradeRequest();
		accountTradeRequest.setUserOid(accountInfoEntity.getUserOid());
		accountTradeRequest.setSystemSource("points");
		accountTradeRequest.setRequestNo(seqGenerator.next(""));
		accountTradeRequest.setOrderType(TradeType.OVERDUE.getValue());
		accountTradeRequest.setOrderNo(seqGenerator.next("OD"));
		accountTradeRequest.setOrderDesc("账户积分过期");
		accountTradeRequest.setRemark(accountInfoEntity.getAccountNo());
		accountTradeRequest.setBalance(accountInfoEntity.getBalance());
		
		return accountTradeRequest;
	}

}
