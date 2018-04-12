package com.guohuai.tulip.platform.signin;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class SignInRep extends BaseResp{
	private String oid;
	private String userId;                     //用户oid
	private Timestamp signInTime;              //签到时间
	
	public SignInRep(SignInEntity sign){
		this.oid = sign.getOid();
		this.userId = sign.getUserId();
		this.signInTime = sign.getSignInTime();
	}
}
