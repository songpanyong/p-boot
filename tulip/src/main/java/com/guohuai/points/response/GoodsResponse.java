package com.guohuai.points.response;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GoodsResponse extends BaseResp {

	private String oid;

	private String name;
	/**
	 * 枚举: real实物、virtual虚拟
	 */
	private String type;
	
	/**
	 * 虚拟卡券类型
	 */
	private String virtualCouponType;
	
	/**
	 * 虚拟卡券id（待下发的卡券id）
	 */
	private String issueVirtualCouponId;
	
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
	 * 剩余数量（库存）
	 */
	private BigDecimal remainCount;
	
	/**
	 * 商品状态(0:未上架、1:已上架、2:已下架)
	 */
	private Integer state;
	
	/**
	 * 商品图片id
	 */
	private String fileOid;
	
	/**
	 * 单个图片地址
	 */
	private String fileUrl;
	
	private String files;
	
	private List<String> pointFiles;
	
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
