package com.guohuai.tulip.schedule.notify;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_TULIP_ASYNCH_EVENT")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AsynchEventEntity extends UUID implements Serializable {
	
	private static final long serialVersionUID = -7029182977531786241L;
	
	public static final String EVENTSTATUS_UNSENT = "unsent";
	public static final String EVENTSTATUS_BEENSENT = "beensent";
	
	String eventStr;
	
	String eventType;
	
	/**  unsent未发送(默认)、beensent已发送 **/
	String eventStatus;
	
	/** 创建时间 */
	private Timestamp createTime;

	public String getEventStr() {
		return eventStr;
	}

	public void setEventStr(String eventStr) {
		this.eventStr = eventStr;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	
	
}
