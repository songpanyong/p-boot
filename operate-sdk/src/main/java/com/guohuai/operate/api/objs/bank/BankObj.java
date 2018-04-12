package com.guohuai.operate.api.objs.bank;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.operate.api.objs.BaseObj;

public class BankObj extends BaseObj {

	private static final long serialVersionUID = 6373200864564423163L;

	public static final int STATUS_Activating = 1;
	public static final int STATUS_Deactivation = 2;

	public static final int CO_SUPPORT_On = 1;
	public static final int CO_SUPPORT_Off = 2;

	public static final int PERSON_SUPPORT_On = 1;
	public static final int PERSON_SUPPORT_Off = 2;

	public static final int SOLO_CHECKING_On = 1;
	public static final int SOLO_CHECKING_Off = 2;

	private String oid;
	private String code;
	private String name;
	private String inter;
	private int coSupport;
	private int personSupport;
	private BigDecimal payMax;
	private BigDecimal payLimit;
	private BigDecimal payMin;
	private int payTimes;
	private BigDecimal drawMax;
	private BigDecimal drawLimit;
	private BigDecimal drawMin;
	private int drawTimes;
	private BigDecimal coDrawMax;
	private BigDecimal coPayMax;
	private BigDecimal bindingPay;
	private int soloChecking;
	private int receiveDelay;
	private int boxReceiveDelay;
	private int boxClearedReceiveDelay;
	private String clearTime;
	private int status;
	private String operator;
	private Timestamp updateTime;
	private Timestamp createTime;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInter() {
		return inter;
	}

	public void setInter(String inter) {
		this.inter = inter;
	}

	public int getCoSupport() {
		return coSupport;
	}

	public void setCoSupport(int coSupport) {
		this.coSupport = coSupport;
	}

	public int getPersonSupport() {
		return personSupport;
	}

	public void setPersonSupport(int personSupport) {
		this.personSupport = personSupport;
	}

	public BigDecimal getPayMax() {
		return payMax;
	}

	public void setPayMax(BigDecimal payMax) {
		this.payMax = payMax;
	}

	public BigDecimal getPayLimit() {
		return payLimit;
	}

	public void setPayLimit(BigDecimal payLimit) {
		this.payLimit = payLimit;
	}

	public BigDecimal getPayMin() {
		return payMin;
	}

	public void setPayMin(BigDecimal payMin) {
		this.payMin = payMin;
	}

	public int getPayTimes() {
		return payTimes;
	}

	public void setPayTimes(int payTimes) {
		this.payTimes = payTimes;
	}

	public BigDecimal getDrawMax() {
		return drawMax;
	}

	public void setDrawMax(BigDecimal drawMax) {
		this.drawMax = drawMax;
	}

	public BigDecimal getDrawLimit() {
		return drawLimit;
	}

	public void setDrawLimit(BigDecimal drawLimit) {
		this.drawLimit = drawLimit;
	}

	public BigDecimal getDrawMin() {
		return drawMin;
	}

	public void setDrawMin(BigDecimal drawMin) {
		this.drawMin = drawMin;
	}

	public int getDrawTimes() {
		return drawTimes;
	}

	public void setDrawTimes(int drawTimes) {
		this.drawTimes = drawTimes;
	}

	public BigDecimal getCoDrawMax() {
		return coDrawMax;
	}

	public void setCoDrawMax(BigDecimal coDrawMax) {
		this.coDrawMax = coDrawMax;
	}

	public BigDecimal getCoPayMax() {
		return coPayMax;
	}

	public void setCoPayMax(BigDecimal coPayMax) {
		this.coPayMax = coPayMax;
	}

	public BigDecimal getBindingPay() {
		return bindingPay;
	}

	public void setBindingPay(BigDecimal bindingPay) {
		this.bindingPay = bindingPay;
	}

	public int getSoloChecking() {
		return soloChecking;
	}

	public void setSoloChecking(int soloChecking) {
		this.soloChecking = soloChecking;
	}

	public int getReceiveDelay() {
		return receiveDelay;
	}

	public void setReceiveDelay(int receiveDelay) {
		this.receiveDelay = receiveDelay;
	}

	public int getBoxReceiveDelay() {
		return boxReceiveDelay;
	}

	public void setBoxReceiveDelay(int boxReceiveDelay) {
		this.boxReceiveDelay = boxReceiveDelay;
	}

	public int getBoxClearedReceiveDelay() {
		return boxClearedReceiveDelay;
	}

	public void setBoxClearedReceiveDelay(int boxClearedReceiveDelay) {
		this.boxClearedReceiveDelay = boxClearedReceiveDelay;
	}

	public String getClearTime() {
		return clearTime;
	}

	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
