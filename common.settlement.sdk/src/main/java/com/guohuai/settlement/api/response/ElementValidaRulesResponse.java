package com.guohuai.settlement.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: ElementValidaRulesResponse
 * @Description: 绑卡规则获取
 * @author chendonghui
 * @date 2018年1月18日 上午9:02:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ElementValidaRulesResponse extends BaseResponse {
	private static final long serialVersionUID = -4038818697787570701L;

	/**
	 * 用户最大绑卡数量
	 */
	private int userMaxBindNum;
	/**
	 * 卡最大绑定用户数量
	 */
	private int cardMaxBindNum;
	/**
	 * 用户是否可绑定其他用户卡 Y.N
	 */
	private String bindOtherUserCard;
}