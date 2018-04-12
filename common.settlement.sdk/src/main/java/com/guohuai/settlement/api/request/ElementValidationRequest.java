package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName: ElementValidationRequest
 * @Description: 四要素验证
 * @author xueyunlong
 * @date 2016年11月7日 下午6:26:39
 *
 */
@Data
public class ElementValidationRequest implements Serializable{

	private static final long serialVersionUID = 7393871476225025334L;
	
	  /**
	   * 会员ID
	   */
	  private String userOid;
	  /**
	   * 鉴权系统来源
	   * 例如：mimosa weiji
	   */
	  private String systemSource;
	  /**
	   * 请求流水号
	   * 表明一次请求，不允许重复 建议使用当前日期+交易唯一标示
	   * 例如 20161107183459000000001
	   */
	  private String requestNo;
	  /**
	   	* 银行行别
	   	* 如:
	   	*/
	  private String bankCode;      
	  /**
	   * 客户姓名
	   */
	  private String realName;
	  /**
	   * 银行卡号
	   */
	  private String cardNo; 
	  /**
	   * 证件号码
	   */
	  private String certificateNo;
	  /**
	   * 手机号
	   */
	  private String phone;    
	  
	  /**
	   * 业务标签
	   */
	  private String busiTag;
	  
	  /**
	   * 银行名称
	   */
	  private String bankName;
	  
	  
	  /**
	   * 短信验证码
	   */
	  private String smsCode;
	  
	  /**
	   * 绑卡编号
	   */
	  private String cardOrderId;

//=======================================zby新加属性	  
	  
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
	   * 绑卡类型
	   */
	  private String cardType;
	  
	  /**
	   * 证件类型
	   */
	  private String certificates;
	  
	  /**
	   * 开户名称
	   */
	  private String accountName;
}
