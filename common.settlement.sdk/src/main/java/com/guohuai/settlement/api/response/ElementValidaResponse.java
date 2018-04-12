package com.guohuai.settlement.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: ElementValidaResponse
 * @Description: 四要素验证返回
 * @author xueyunlong
 * @date 2016年11月7日 下午6:42:08
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ElementValidaResponse extends BaseResponse {

	
	private static final long serialVersionUID = 5563251903304566866L;

	/**
	   * 请求流水号
	   * 表明一次请求，不允许重复 建议使用当前日期+交易唯一标示
	   * 例如 20161107183459000000001
	   */
	  private String requestNo;
	  
	  /**
	   * 校验结果
	   */
	  private String result;
	  
	  /**
	   * 短信验证码
	   */
	  private String smsCode;
	  
	  /**
	   * 绑卡编号
	   */
	  private String cardOrderId;
	  
//-------------------------zby新加字段-------------------	  
	  
	  
	  /**
	   * 绑卡类型
	   */
	  private String cardType;
	  
	  /**
	   * 银行卡号
	   */
	  private String cardNo; 
	  
	  /**
	   * 开户行所属省份
	   */
	  private String province;
	  
	  /**
	   * 开户行所属城市
	   */
	  private String city;
	  
	  /**
	   * 开户行支行名称
	   */
	  private String branch;
	  
	  /**
	   * 开户行所属区、县
	   */
	  private String county;
	  
	  /**
	   * 证件类型
	   */
	  private String certificates;
	  
	  /**
	   * 证件号码
	   */
	  private String certificateNo;
	  
	  /**
	   * 银行编码
	   */
	  private String bankCode;
	  
	  /**
	   * 开户名称
	   */
	  private String accountName;
}
