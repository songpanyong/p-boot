package com.guohuai.tulip.component.api;

import com.guohuai.basic.component.ext.web.PageResp;

import feign.Param;
import feign.RequestLine;

public interface InvestorlabelAPI {

	/**
	 * 获取所有可用投资人标签
	 * @return
	 */
	@RequestLine("POST /mimosa/investorlabel/mng")
	public PageResp<InvestorLabelResp> getInvestorlabelList();
	
	/**
	 * 根据标签编码查询名称
	 * @return
	 */
	@RequestLine("POST /mimosa/investorlabel/findLabelByCode?labelCode={labelCode}")
	public InvestorLabelResp findLabelByCode(@Param("labelCode") String labelCode);
	
	/**
	 * 根据投资人标签获取投资人列表
	 * @param labelCode
	 * @return
	 */
	@RequestLine("POST /mimosa/labelInvestor/lilist?labelCode={labelCode}")
	public PageResp<InvestorInfoResp> getInvestorByLabelCode(@Param("labelCode") String labelCode);
	
	/**
	 * 根据投资人获取其所在的组
	 * @param investorOid
	 * @return
	 */
	@RequestLine("POST /mimosa/labelInvestor/investorlabellist?investorOid={investorOid}")
	public PageResp<InvestorLabelResp> getInvestorlabellistByUserId(@Param("investorOid") String investorOid);
	
}
