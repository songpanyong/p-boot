
package com.guohuai.payadapter.listener.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对账Event
 * @author chendonghui
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReconciliationPassEvent extends AbsEvent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 对账日期YYYYMMDD
	 */
	private String checkDate;

	/**
	 * 对账文件名(绝对路径)
	 */
	private String fileName;
}
