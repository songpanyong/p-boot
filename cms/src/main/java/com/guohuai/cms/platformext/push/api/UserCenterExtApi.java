package com.guohuai.cms.platformext.push.api;

import java.util.List;

import com.guohuai.cms.platform.mail.api.UserInfoRep;

import feign.Param;
import feign.RequestLine;

public interface UserCenterExtApi {
	  /**
	   * 根据标签code获取标签下的投资人列表
	   * @param labelCode
	   * @return
	   */
	  @RequestLine("POST /mimosa/labelInvestor/lilist?labelCode={labelCode}")
	  public abstract UserLabelExtResp getUserLabelList(@Param("labelCode") String labelCode);
}
