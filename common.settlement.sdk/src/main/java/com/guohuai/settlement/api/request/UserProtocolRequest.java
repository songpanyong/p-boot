package com.guohuai.settlement.api.request;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

/**
 * @ClassName: UserProtocolRequest
 * @Description: 查询用户绑卡信息请求参数
 * @author chendonghui
 * @date 2018年2月3日 下午3:34:28
 *
 */
@Data
public class UserProtocolRequest implements Serializable {
	private static final long serialVersionUID = 6760371127983009342L;
	/**
	 * 会员id
	 */
	private String userOid;

}
