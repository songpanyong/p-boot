package com.guohuai.operate.role;

import com.guohuai.operate.component.util.DateUtil;
import com.guohuai.operate.component.web.view.BaseResp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RoleResp extends BaseResp {

	private String oid;
	private String name;
	private String systemOid;
	private String systemName;
	private String updateTime;
	private String createTime;
	
	/** 用户数 */
	private int arc;
	
	/** 权限数 */
	private int rac;

	public RoleResp(Role role) {
		super();
		this.oid = role.getOid();
		this.name = role.getName();
		this.systemOid = role.getSystem().getOid();
		this.systemName = role.getSystem().getName();
		this.updateTime = DateUtil.formatDatetime(role.getUpdateTime().getTime());
		this.createTime = DateUtil.formatDatetime(role.getCreateTime().getTime());
	}

}
