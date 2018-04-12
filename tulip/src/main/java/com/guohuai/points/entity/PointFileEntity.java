package com.guohuai.points.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_TULIP_FILE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PointFileEntity extends UUID {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4561872254473098052L;
	
	/**
	 * 积分商品Id
	 */
	private String goodsOid;
	
	/**
	 * '枚举值:\r\n            PLATFORM: 平台文件\r\n            DOCUMENT: 资料文件\r\n            
	 * AGREEMENT: 合同文件\r\n            AGMFEEDBACK: 合同反馈\r\n            AGMPATCH: 补充合同(资管方>管理人)\r\n
	 * INVESTCMD: 投资指令\r\n            REMITCMD: 划款指令\r\n            DECISION: 产品决议书\r\n            
	 * GMAGMFEEDBACK: 资管合同反馈\r\n            GMCMDFEEDBACK: 资管指令反馈\r\n   GMCMDRECEIPT: 资管指令回执\r\n            
	 * BANKCMDRECERPT: 托管行回执', POINT: 积分商城
	 */
	private String cate;
	
	/**
	 * 商品介绍
	 */
	private String fkey;
	
	/**
	 * 修改人
	 */
	private String operator;

	private Timestamp updateTime;
	
	private Timestamp createTime;
}
