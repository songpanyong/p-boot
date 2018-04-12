package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易流水请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateTransRequest extends PageBase implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 5275341072461572082L;
	
	private String accountOid; //账户号
	private String userOid; //用户ID
	private String userType; //T1--投资人账户、T2--发行人账户、T3--平台账户
	private String requestNo; //请求流水号
	private String accountOrderOid; //收单OID
	private String orderType; //申购:01、赎回:02、派息:03、赠送体验金:04、体验金到期:05
	private String systemSource; //来源系统类型\r 如 mimosaweijin
	private String orderNo; //来源系统单据号
	private String relationProductNo; //关联产品编码
	private String relationProductName; //关联产品编码
	private String direction; //金额方向，借+ 贷-
	private BigDecimal orderBalance; //订单金额
	private String ramark; //备注
	private String orderDesc; //定单描述
	private String accountName; //账户名称
	private Timestamp transTime; //交易时间
	private String dataSource; //数据来源
	private BigDecimal balance; //交易后余额
	private String isDelete; //删除标记
	private String currency; //币种
	private String inputAccountNo; //入账账户，根据单据类型，做转账时用
	private String outpuptAccountNo; //出败账户，根据单据类型，做转账时用
	private String financeMark; //财务入账标识
	/**
	 * 代金券
	 */
	private BigDecimal voucher=new BigDecimal(0);
	
	private String accountType;//账户类型
}
