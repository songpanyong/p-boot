package com.guohuai.cms.platform.bankcard;

import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BankCardCTRep extends BaseResp{

	private List<BankCardCTBaseResp> datas;
}
