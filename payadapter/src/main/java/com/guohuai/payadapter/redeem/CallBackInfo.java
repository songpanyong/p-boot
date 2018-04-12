package com.guohuai.payadapter.redeem;

import java.io.Serializable;
import java.util.Date;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate(true)
@Table(
    name = "t_bank_callback"
)
public class CallBackInfo extends UUID implements Serializable{
	  private static final long serialVersionUID = -3286518029485346965L;
	  private String orderNO;
	  private String payNo;
	  /**
	   * 银行返回流水号
	   */
	  private String bankReturnSerialId;
	  private String channelNo;
	  private String tradeType;//交易类型，用交易码如4004
	  private int minute;//回调间隔时间
	  private Date callbackDate;//回调的当前时间
	  private int count;//回调次数
	  private int totalCount;//最大回调次数
	  private String status;//0,未处理,1交易成功,2交易失败,3交易处理中,4超时(超时的放到处理中,等人工处理)
	  private String returnCode;//4005接口返回的Yhcljg
	  private String returnMsg;//4005接口返回的BackRem
	  private Date createTime;
	  private Date updateTime;
	  private String type;
	  private int countMin=0;//每分钟定时的回掉次数
	  private int totalMinCount=20;//每分钟定时的最大回调次数
}
