package com.guohuai.tulip.platform.gatewayrequestlog;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 活动-实体
 * 
 * @author tidecc
 */
@Entity
@Table(name = "T_TULIP_GATEWAY_REQUEST_LOG")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GatewayRequestLogEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 3297827842886697187L;
	/** 访问app */
	String appId;
	/** 方法名 */
	String method;
	/** 请求数据 */
	String request;
	/** 响应数据 */
	String response;
	/** 状态 */
	String status;
	/** 响应时间 */
	Long responseTime;
	/** 访问ip */
	String ip;
	/** 请求时间 */
	Timestamp requestTime;
	/** 更新时间 */
	String description;
	/**
	 * 获取 appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置 appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取 method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置 method
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取 request
	 */
	public String getRequest() {
		return request;
	}
	/**
	 * 设置 request
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	/**
	 * 获取 response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * 设置 response
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	/**
	 * 获取 status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取 responseTime
	 */
	public Long getResponseTime() {
		return responseTime;
	}
	/**
	 * 设置 responseTime
	 */
	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}
	/**
	 * 获取 ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置 ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取 requestTime
	 */
	public Timestamp getRequestTime() {
		return requestTime;
	}
	/**
	 * 设置 requestTime
	 */
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	/**
	 * 获取 description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
