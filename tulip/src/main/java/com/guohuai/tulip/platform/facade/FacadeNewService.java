package com.guohuai.tulip.platform.facade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.points.request.AccountTradeRequest;
import com.guohuai.points.response.AccountTradeResponse;
import com.guohuai.points.service.AccountTradeService;
import com.guohuai.tulip.platform.commissionorder.CommissionOrderEntity;
import com.guohuai.tulip.platform.commissionorder.CommissionOrderService;
import com.guohuai.tulip.platform.coupon.CouponEntity;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderEntity;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderService;
import com.guohuai.tulip.platform.coupon.redpacket.RedpacketDetailEntity;
import com.guohuai.tulip.platform.coupon.redpacket.RedpacketDetailService;
import com.guohuai.tulip.platform.coupon.sms.MessageReq;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponEntity;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisInfo;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.intervalsetting.IntervalSettingEntity;
import com.guohuai.tulip.platform.intervalsetting.IntervalSettingService;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;
import com.guohuai.tulip.util.DateUtil;
import com.guohuai.tulip.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FacadeNewService {
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private CouponOrderService couponOrderService;
	
	@Autowired
	private UserCouponService userCouponService;
	
	@Autowired
	UserInvestService userInvestService;
	
	@Autowired
	private CommissionOrderService commissionOrderService;

	@Autowired
	private IntervalSettingService intervalSettingService;
	
	@Autowired
	private AccountTradeService accountTradeService;
	
	@Autowired
	private RedpacketDetailService redpacketDetailService;
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Value("${system.sourceName:tulip}")
	private String systemName;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	/**
	 * 下发卡券
	 * 
	 * @param user
	 * @param eventType
	 * @param couponEntity
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void generateCoupon(UserInvestEntity user, String eventType, CouponEntity couponEntity) {
		Timestamp currentDate=DateUtil.getSqlCurrentDate();
		UserCouponEntity userCoupon = new UserCouponEntity();
		BeanUtils.copyProperties(couponEntity, userCoupon);
		BigDecimal amount = BigDecimal.valueOf(toRandInt(couponEntity, user.getUserId()));
		//toRandInt返回的double是一位小数，需要给amount保留两位小数补一个0
		amount = amount.setScale(2,BigDecimal.ROUND_HALF_UP);
		userCoupon.setAmount(amount);
		userCoupon.setCouponBatch(couponEntity.getOid());
		userCoupon.setStatus(UserCouponEntity.COUPON_STATUS_NOUSED);
		userCoupon.setUserId(user.getUserId());
		userCoupon.setLeadTime(currentDate);
		userCoupon.setStart(currentDate);
		userCoupon.setPhone(user.getPhone());
		Date finish = DateUtil.getCurrDate(); 
		if(null == couponEntity.getDisableTime()){
			Date date = DateUtil.addSQLDays(DateUtil.getSqlCurrentDate(), couponEntity.getDisableDate());
			if(couponEntity.getDisableType().equals(CouponEntity.DISABLETYPE_DAY)){
				String strDate=DateUtil.format(date,DateUtil.datePattern)+" 23:59:59";
				finish = DateUtil.parseDate(strDate, DateUtil.datetimePattern);
			}else if(couponEntity.getDisableType().equals(CouponEntity.DISABLETYPE_HOUR)){
				finish= DateUtil.parseDate(DateUtil.format(date,DateUtil.datetimePattern), DateUtil.datetimePattern);
			}
		}else{
			String strDate=DateUtil.format(couponEntity.getDisableTime(),DateUtil.datePattern)+" 23:59:59";
			finish = DateUtil.parseDate(strDate, DateUtil.datetimePattern);
		}
		userCoupon.setFinish(new Timestamp(finish.getTime()));
		userCoupon.setEventType(eventType);
		userCoupon = userCouponService.createUserCoupon(userCoupon);
		couponService.updateRemainCount(couponEntity.getOid(), amount);
		log.info("===============下发卡券成功,用户:{}已经领取活动:{}下发的卡券:{}!", user.getUserId(), eventType, couponEntity.getOid());
		
		UserCouponRedisInfo couponRedis = new UserCouponRedisInfo();
		couponRedis.setCouponOid(userCoupon.getOid());
		couponRedis.setUserId(user.getUserId());
		couponRedis.setName(userCoupon.getName());
		if (CouponEntity.COUPON_TYPE_redPackets.equals(couponEntity.getType())) {
			couponRedis.setCouponType(userCoupon.getAmountType());
		} else {				
			couponRedis.setCouponType(couponEntity.getType());
		}
		couponRedis.setCouponAmount(userCoupon.getAmount());
		couponRedis.setFinish(userCoupon.getFinish());
		//下发卡券之后将下发的卡券信息保存到redis中，便于前端显示当前获得的卡券列表
//		if(!eventType.equals(EventConstants.EVENTTYPE_CUSTOM) && !eventType.equals(EventConstants.EVENTTYPE_BIRTHDAY) 
//				&& !eventType.equals(EventConstants.EVENTTYPE_SCHEDULE) && !eventType.equals(EventConstants.EVENTTYPE_FIRSTFRIENDINVEST) 
//				&& !eventType.equals(EventConstants.EVENTTYPE_FRIEND) && !eventType.equals(EventConstants.EVENTTYPE_REGISTER)){
//			//自定义、生日、定时派发赠送卡券、推荐人活动、推荐人首投 、分享链接注册 时不用给用户弹屏，其他通过app端触发的下发场景才将卡券弹屏
//			UserCouponRedisUtil.lPush(redis, UserCouponRedisUtil.USER_NEW_COUPON_REDIS_KEY + user.getUserId(), couponRedis);
//		}else{
//			//将其他特殊的送券（自定义、生日、定时派发赠送卡券、推荐人活动、推荐人首投 、分享链接注册）存放在redis key=USER_NEW_COUPON_LOGIN_REDIS_KEY里面
//			UserCouponRedisUtil.lPush(redis, UserCouponRedisUtil.USER_NEW_COUPON_LOGIN_REDIS_KEY + user.getUserId(), couponRedis);
//		}
		
		if(eventType.equals(EventConstants.EVENTTYPE_CUSTOM) || eventType.equals(EventConstants.EVENTTYPE_BIRTHDAY) 
				|| eventType.equals(EventConstants.EVENTTYPE_SCHEDULE) || eventType.equals(EventConstants.EVENTTYPE_FIRSTFRIENDINVEST) 
				|| eventType.equals(EventConstants.EVENTTYPE_FRIEND) || eventType.equals(EventConstants.EVENTTYPE_REGISTER)
				|| eventType.equals(EventConstants.EVENTTYPE_CASH)){
			//将非实时送券场景（自定义、生日、定时派发赠送卡券、推荐人活动、推荐人首投 、分享链接注册、提现）存放在登录获取的redis中
			UserCouponRedisUtil.lPush(redis, UserCouponRedisUtil.USER_NEW_COUPON_LOGIN_REDIS_KEY + user.getUserId(), couponRedis);
		}else{
			//其他实时的送券
			UserCouponRedisUtil.lPush(redis, UserCouponRedisUtil.USER_NEW_COUPON_REDIS_KEY + user.getUserId(), couponRedis);
		}
		
		MessageReq msgreq = new MessageReq();
		msgreq.setUserId(user.getUserId());
		msgreq.setUserPhone(user.getPhone());
		msgreq.setEventType(eventType);
		msgreq.setCouponType(couponRedis.getCouponType());
		msgreq.setCouponOid(couponRedis.getCouponOid());
		msgreq.setCouponAmount(couponRedis.getCouponAmount());
		//异步事件处理各种消息（站内信、推送等）
		eventPublisher.publishEvent(msgreq);
	}
	/**
	 * 下发电影票
	 * @param userId
	 * @param leadTime
	 * @param finish
	 * @param content
	 * @param batchNo
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void generateTicket(String userId, Timestamp leadTime,Timestamp finish ,String content , String batchNo) {
			UserCouponEntity userCoupon = new UserCouponEntity();
			userCoupon.setCouponBatch(batchNo);
			userCoupon.setStatus(UserCouponEntity.COUPON_STATUS_NOUSED);
			userCoupon.setUserId(userId);
			userCoupon.setLeadTime(leadTime);
			userCoupon.setStart(leadTime);
			userCoupon.setFinish(finish);
			userCoupon.setEventType("investment");
			userCoupon.setMoiveTicketContent(content);
			userCoupon.setType(CouponEntity.COUPON_TYPE_moiveTicket);
			userCouponService.createUserCoupon(userCoupon);
			log.info("下发电影票成功,用户:{}已经领取活动:{}下发的电影票:{}!", userId, "investment", batchNo);
	}
	/**
	 * 重发校验
	 * 
	 */
	private boolean isRepeat(String userId, String eventId, String couponId) {
		return true;
		//return CollectionUtils.isEmpty(userCouponService.checkUserCoupon(userId, eventId, couponId));
	}
	
	/**
	 * 获取卡券金额
	 * 
	 * @param couponEntity
	 * @param userId
	 * @return
	 */
	public double toRandInt(CouponEntity couponEntity, String userId) {
		double randNumber=0d;
		if (CouponEntity.COUPON_TYPE_redPackets.equals(couponEntity.getType()) 
				&& CouponEntity.AMOUNTTYPE_random.equals(couponEntity.getAmountType()) 
				&& couponEntity.getRemainAmount() != null ) {
			//随机红包改成从已经生成好的库中获取。
			randNumber = getRandomRedpacket(couponEntity.getOid(), userId);
		} else {
			randNumber = couponEntity.getCouponAmount().doubleValue();
		}
		
		return randNumber;
	}
	
	/**
	 * 控制并发：从redis获取自增index去redis数组中获取一个红包id
	 * <p>并去数据库中查询对应的红包信息,同时修改该红包的发放状态
	 * @param couponEntity
	 * @return
	 */
	public double getRandomRedpacket(String couponEntityOid, String userId){
		double randomAmount = 0d;
		//当前红包批次数组的redis_key
		String couponRedisKey = UserCouponRedisUtil.COUPON_REDPACKET_REDIS_KEY + couponEntityOid;
		//当前批次的自增id
		Long redpacketIndex = redis.opsForValue().increment(UserCouponRedisUtil.COUPON_REDPACKET_INDEX_REDIS_KEY+couponEntityOid, 1);
		redpacketIndex = redpacketIndex - 1;//默认从1开始的，这里是当成数组下标，因此要从0开始
		log.info("获取redis.redpacketIndex: {}", redpacketIndex);
		String redpacketId = redis.opsForList().index(couponRedisKey, redpacketIndex);
		log.info("当前操作获得的随机红包id： {}", redpacketId);
		if(!StringUtil.isEmpty(redpacketId)){
			RedpacketDetailEntity redEntity = redpacketDetailService.findByOid(redpacketId);
			if(null != redEntity){
				randomAmount = redEntity.getRandomAmount().doubleValue();
				//修改该红包的领用状态
				redEntity.setStatus(RedpacketDetailEntity.RED_STATUS_USED);
				redEntity.setUserId(userId);//获得者
				redpacketDetailService.saveRedpacket(redEntity);
			}
		} else {
			throw new GHException("随机红包已经发完，不再下发红包!!");
		}
		return randomAmount;
	}
	
	/**
	 * 佣金审核--下发比例红包
	 * 1，生成佣金
	 * 2，下发卡券（比例红包）
	 * 
	 * @param couponId 当前使用的卡券Id
	 * @param userEntity 用户信息
	 * @param eventType 事件类型
	 * @param couponEntity 需要下发的卡券信息
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void generatePercentCoupon(String commissionOrderOid) {
		CommissionOrderEntity commissionOrderEntity=commissionOrderService.findCommissionOrderByOid(commissionOrderOid);
		CouponEntity couponEntity=couponService.findCouponByOid(commissionOrderEntity.getCouponOid());
		//下发卡券
		Timestamp currentDate = DateUtil.getSqlCurrentDate();
		UserCouponEntity userCoupon = new UserCouponEntity();
		BeanUtils.copyProperties(couponEntity, userCoupon);
		userCoupon.setAmount(commissionOrderEntity.getOrderAmount());
		userCoupon.setCouponBatch(couponEntity.getOid());
		userCoupon.setStatus(UserCouponEntity.COUPON_STATUS_NOUSED);
		userCoupon.setUserId(commissionOrderEntity.getUserId());
		userCoupon.setLeadTime(currentDate);
		userCoupon.setStart(currentDate);
		userCoupon.setPhone(commissionOrderEntity.getPhone());
		userCoupon.setValidPeriod(couponEntity.getValidPeriod());
		userCoupon.setMaxRateAmount(couponEntity.getMaxRateAmount());
		Date finish = DateUtil.getCurrDate(); 
		if(null == couponEntity.getDisableTime()){
			if(couponEntity.getDisableType().equals(CouponEntity.DISABLETYPE_DAY)){
				Date d=DateUtil.addSQLDays(DateUtil.getSqlCurrentDate(), couponEntity.getDisableDate());
				String strDate=DateUtil.format(d,DateUtil.datePattern)+" 23:59:59";
				finish = DateUtil.parseDate(strDate, DateUtil.datetimePattern);
			}else if(couponEntity.getDisableType().equals(CouponEntity.DISABLETYPE_HOUR)){
				Date date = DateUtil.addSQLDays(DateUtil.getSqlCurrentDate(), couponEntity.getDisableDate());
				finish= DateUtil.parseDate(DateUtil.format(date,DateUtil.datetimePattern), DateUtil.datetimePattern);
			}
		}else{
			String strDate=DateUtil.format(couponEntity.getDisableTime(),DateUtil.datePattern)+" 23:59:59";
			finish = DateUtil.parseDate(strDate, DateUtil.datetimePattern);
		}
		
		userCoupon.setFinish(new Timestamp(finish.getTime()));
		userCoupon.setEventType(EventConstants.EVENTTYPE_INVESTMENT);
		userCouponService.createUserCoupon(userCoupon);
		//比例红包每次累加红包总额，见少剩余数量
		//其他的卡券类型是见少剩余金额减少剩余数量
		couponService.updateRemainAndTotalAmountCount(couponEntity.getOid(), commissionOrderEntity.getOrderAmount());
		log.info("佣金审核下发比例红包卡券开始,用户:{}领取活动:{}下发的卡券:{}!", commissionOrderEntity.getFriendPhone(), EventConstants.EVENTTYPE_INVESTMENT, commissionOrderEntity.getCouponOid());
	}
	
	/**
	 * 生成比例红包的佣金
	 * 
	 * @param userEntity
	 * @param couponOrderEntity
	 * @param redScale
	 * @return
	 */
	private BigDecimal generateCommissionAmount(UserInvestEntity userEntity, CouponOrderEntity couponOrderEntity, CouponEntity couponEntity){
		//0 生成比例红包金额：根据推广人的推广渠道类型设置不同的金额算法
		String channelType = userEntity.getType(); //推广人:referee,渠道:channel
		BigDecimal commissionAmount = BigDecimal.ZERO;
		if(UserInvestEntity.TYPE_CHANNEL.equals(channelType)){
			//1.1：渠道佣金算法：投资金额 / 基数 * 金额 * 月份
			BigDecimal investAmount = couponOrderEntity.getOrderAmount();
			IntervalSettingEntity ise = intervalSettingService.getIntervalLevel(investAmount);
			if(ise==null){
				throw new GHException("查询不到投资金额对应的基数!");
			}
			commissionAmount = investAmount.divide(ise.getIntervalLevel()).multiply(new BigDecimal(couponOrderEntity.getDueTime()+""))
									.multiply(userEntity.getChannelAmount()).setScale(2, BigDecimal.ROUND_DOWN);
			
		} else if(UserInvestEntity.TYPE_REFEREE.equals(channelType)){
			//1.2：推广人佣金算法：投资金额 * 百分比 * 月份
			BigDecimal newRedScale = couponEntity.getScale();
			if(null == newRedScale || newRedScale.compareTo(BigDecimal.ZERO)==0){
				throw new GHException("卡券比例异常无法生成比佣金!");
			}
			commissionAmount = couponOrderEntity.getOrderAmount().multiply(new BigDecimal(couponOrderEntity.getDueTime()+""))
					.multiply(newRedScale).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
		}
		if(couponEntity.getUpperAmount().compareTo(commissionAmount)<0){
			commissionAmount=couponEntity.getUpperAmount();
		}
		log.info("1.生成比例红包的佣金成功,金额为:{}  !", commissionAmount);
		return commissionAmount;
	}
	
	/**
	 * 生成佣金订单
	 * 
	 * @param userEntity
	 * @param couponId 当前使用的卡券Id
	 * @param entity
	 * @param orderAmount 生成的佣金
	 */
	public void generateCommissionOrder(UserInvestEntity userEntity, String eventType, CouponEntity couponEntity){
		//获得推荐人用户ID
		String friendId = userEntity.getFriendId();
		//查询该用户未生成佣金订单的申购订单
		UserInvestEntity friendEntity = userInvestService.findUserInvestByUserId(friendId);
		List<CouponOrderEntity> couponOrderList = couponOrderService.findIsMakedCommisOrderByUserId(userEntity.getUserId());
		List<CommissionOrderEntity> coList=new ArrayList<CommissionOrderEntity>();
		for(CouponOrderEntity couponOrder : couponOrderList){
			//佣金订单金额
			BigDecimal commissionAmount = this.generateCommissionAmount(friendEntity, couponOrder, couponEntity);
			CommissionOrderEntity coe = new CommissionOrderEntity();
			coe.setOrderAmount(commissionAmount);
			coe.setUserId(friendId);
			coe.setType(friendEntity.getType());
			coe.setOrderStatus(CommissionOrderEntity.ORDERSTATUS_PENDING);
			coe.setOrderCode(couponOrder.getOrderCode());
			coe.setCreateTime(new Timestamp(System.currentTimeMillis()));
			coe.setPhone(friendEntity.getPhone());
			coe.setFriendPhone(userEntity.getPhone());
			coe.setFriendInvest(couponOrder.getOrderAmount());
			coe.setFriendInvestTime(couponOrder.getCreateTime());
			coe.setCouponOid(couponEntity.getOid());
			coList.add(coe);
			//卡券订单数据标识已生成佣金订单
			couponOrder.setIsMakedCommisOrder(CouponOrderEntity.isMakedCommisOrder_yes);
			couponOrderService.createCouponOrder(couponOrder);
		}
		commissionOrderService.createCommissionOrder(coList);
		log.info("2.生成佣金订单成功!");
	}

	/**
	 * 兑换积分券到积分账户
	 * 
	 * @param userCoupon
	 */
	private void tradeCouponToPoints(UserCouponEntity userCoupon){
		log.info("积分券积分额度直接给到用户积分账户，tulip系统调用方法，用户积分卡券参数：{}", JSONObject.toJSONString(userCoupon));
		AccountTradeRequest tradeRequest = new AccountTradeRequest();
		tradeRequest.setSystemSource(systemName);
		tradeRequest.setRequestNo(StringUtil.uuid());
		tradeRequest.setOldOrderNo(StringUtil.uuid());
		tradeRequest.setUserOid(userCoupon.getUserId());
		tradeRequest.setRelationProductNo(userCoupon.getOid());
		tradeRequest.setRelationProductName(userCoupon.getName());
		tradeRequest.setBalance(userCoupon.getAmount());
		tradeRequest.setOrderType(userCoupon.getType());
		tradeRequest.setOverdueTime(userCoupon.getFinish());
		tradeRequest.setOrderDesc(userCoupon.getDescription());
		AccountTradeResponse tradeResponse = accountTradeService.trade(tradeRequest);	
		log.info("积分券到积分账户，最终返回结果：{}", JSONObject.toJSONString(tradeResponse));
	}
}
