package com.guohuai.payadapter.control;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(
    name = "t_bank_channel_bank"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelBank extends UUID implements Serializable{
	
	   private static final long serialVersionUID = -6004709475834256278L;
	   
	   private String userOid;
	   private String channelNo;//渠道N
	   private String channelName;//渠道Name
	   private String channelbankCode;//渠道银行代码
	   private String channelbankName;//渠道银行名称
	   private BigDecimal singleQuota;//单笔限额
	   private BigDecimal dailyLimit;//日限额
	   private String standardCode;//人行代码
	   private Timestamp createTime;
	   private Timestamp updateTime;

}
