package com.guohuai.cms.platform.mail.api;

import feign.Param;
import feign.RequestLine;

public interface UserCenterApi {
	  @RequestLine("POST /mimosa/boot/investor/baseaccount/cashuserinfo?uoid={uoid}")
	  public abstract UserInfoRep getLoginUserInfo(@Param("uoid") String uoid);
	  
	  @RequestLine("POST /mimosa/client/investor/baseaccount/isregist?phoneNum={phoneNum}")
	  public abstract UserInfoRep isregist(@Param("phoneNum") String phoneNum);
	  
	  /**
	   * 根据标签code获取标签下的投资人列表
	   * @param labelCode
	   * @return
	   */
	  @RequestLine("POST /mimosa/labelInvestor/lilist?labelCode={labelCode}")
	  public abstract UserLabelResp getUserLabelList(@Param("labelCode") String labelCode);
	  /**
		* 根据标签编码查询名称
		*/
	  @RequestLine("POST /mimosa/investorlabel/findLabelByCode?labelCode={labelCode}")
	  public abstract InvestorLabelResp getInvestorLabel(@Param("labelCode") String labelCode);
}
