package com.guohuai.settlement.api.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.guohuai.settlement.api.response.entity.ProtocolDTO;

/**
 * @ClassName: UserProtocolResponse
 * @Description: 查询用户绑卡信息
 * @author chendonghui
 * @date 2018年2月3日 下午3:22:18
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserProtocolResponse extends BaseResponse {
	
	private static final long serialVersionUID = 5563251903304566866L;
	/**
	 * 用户id
	 */
	private String userOid;
	/**
	 * 绑卡信息
	 */
	private List<ProtocolDTO> cardList;

}
