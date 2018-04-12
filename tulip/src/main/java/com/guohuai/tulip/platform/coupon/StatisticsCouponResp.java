package com.guohuai.tulip.platform.coupon;

import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 运营兑付统计
 * @author mr_gu
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class StatisticsCouponResp extends BaseResp {
	
	List<StatisticsCouponDetailResp> operateCashList1;
	
	List<StatisticsCouponDetailResp> operateCashList2;
	
}
