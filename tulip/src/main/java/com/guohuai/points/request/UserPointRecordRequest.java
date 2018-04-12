package com.guohuai.points.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPointRecordRequest extends BaseRequest {

	private String oid;

	private String name;
	/**
	 * 枚举: real实物、virtual虚拟
	 */
	private String type;
	
	/**
	 * 所需积分
	 */
	private BigDecimal needPoints;
	/**
	 * 商品总数量
	 */
	private BigDecimal totalCount;
	/**
	 * 已兑换数量
	 */
	private BigDecimal exchangedCount;
	/**
	 * 商品状态(0:未上架、1:已上架、2:已下架)
	 */
	private Integer state;
	
	/**
	 * 商品图片id
	 */
	private String fileOid;
	
	private String files;
	
	/**
	 * 商品介绍
	 */
	private String remark;
	
	/**
	 * 修改人
	 */
	private String updateOperater;

	/**
	 * 添加人
	 */
	private String createOperater;

	private Timestamp updateTime;
	
	private Timestamp createTime;
	
}
