package com.guohuai.payment.ucfpay.api;

import com.guohuai.payment.ucfpay.cmd.ReqWithdrawQueryRequest;
import com.guohuai.payment.ucfpay.cmd.ReqWithoidingQueryRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithdrawRequest;
import com.guohuai.payment.ucfpay.cmd.response.WithdrawResp;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;

public interface UcfpayService {
	/**
	 * 单笔代扣请求
	 * @param request
	 * @return
	 */
	WithoidingResp withoiding(WithoidingRequest request);

	/**
	 * 单笔订单查询请求
	 * @param request
	 * @return
	 */
	WithoidingResp withoidingQuery(ReqWithoidingQueryRequest request);

	/**
	 * 单笔代付请求
	 * @param request
	 * @return
	 */
	WithdrawResp withdrawing(WithdrawRequest request);
	/**
	 * 单笔代付查询请求
	 * @param request
	 * @return
	 */
	WithdrawResp withdrawingQuery(ReqWithdrawQueryRequest request);
}
