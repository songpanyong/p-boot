package com.guohuai.tulip.platform.signin;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_TULIP_SIGN_IN")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignInEntity extends UUID{
	
	private static final long serialVersionUID = 3306885985332037083L;
	
	public static final String HMS_START = " 00:00:00";     //每天的开始时间时分秒
	public static final String HMS_END = " 23:59:59";       //每天的结束时间时分秒
	public static final BigDecimal money = new BigDecimal("2.00");          //红包金额

	private String userId;                                  //用户oid
	private Timestamp signInTime;                           //签到时间
	private Date signDate;
	/**
	 * 获取 userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 signInTime
	 */
	public Timestamp getSignInTime() {
		return signInTime;
	}
	/**
	 * 设置 signInTime
	 */
	public void setSignInTime(Timestamp signInTime) {
		this.signInTime = signInTime;
	}
	/**
	 * 获取 signDate
	 */
	public Date getSignDate() {
		return signDate;
	}
	/**
	 * 设置 signDate
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	
}
