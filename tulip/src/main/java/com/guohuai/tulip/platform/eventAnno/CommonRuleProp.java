package com.guohuai.tulip.platform.eventAnno;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guohuai.rules.event.BaseEvent;
import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 签到事件
 * 
 * @author hugo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("公共规则属性")
public abstract class CommonRuleProp extends BaseEvent{
	
	/**
	 * 用户编号
	 */
	@EventAnno("用户编号")
	private String userId;

	// 匹配规则的字段
	/**
	 * 累计推荐人数量
	 */
	@EventAnno("累计推荐人数量")
	private Integer friends = 0;
	/**
	 * 累计投资额度
	 */
	@EventAnno("累计投资额度")
	private BigDecimal investAmount = BigDecimal.ZERO;
	/**
	 * 累计投资次数
	 */
	@EventAnno("累计投资次数")
	private Integer investCount = 0;
	/**
	 * 投资额度
	 */
	@EventAnno("投资额度")
	private BigDecimal orderAmount = BigDecimal.ZERO;
	/**
	 * 首投金额
	 */
	@EventAnno("首投金额")
	private BigDecimal firstInvestAmount = BigDecimal.ZERO;
	
	@EventAnno("产品列表")
	private String productId;
	
	@EventAnno("单笔投资额度")
	private BigDecimal singleInvestAmount = BigDecimal.ZERO;
	
	@EventAnno("用户所在组")
	private List<String> userGroup = new ArrayList<String>();
	/**
	 * 注册时间
	 */
	@EventAnno("注册时间")
	private Date registerTime;
}
