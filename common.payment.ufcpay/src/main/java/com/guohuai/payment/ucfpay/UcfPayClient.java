package com.guohuai.payment.ucfpay;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 
 * @ClassName: UfcpayClient
 * @Description: 先锋支付client 
 * @author xueyunlong
 * @date 2016年12月6日 下午7:35:46
 *
 */
public interface UcfPayClient {

	/**
	 * 单笔代扣
	 * 
	 * @param service
	 * @param secId
	 * @param version
	 * @param reqSn
	 * @param merchantId
	 * @param merchantNo
	 * @param amount
	 * @param transCur
	 * @param userType
	 * @param accountType
	 * @param accountNo
	 * @param accountName
	 * @param bankId
	 * @param certificateType
	 * @param certificateNo
	 * @param mobileNo
	 * @param branchProvince
	 * @param branchCity
	 * @param branchName
	 * @param productName
	 * @param productInfo
	 * @param noticeUrl
	 * @param expireTime
	 * @param memo
	 * @param sign
	 * @return
	 */
	@RequestLine("POST")
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@Body("service={service}&secId={secId}&version={version}&reqSn={reqSn}&merchantId={merchantId}&merchantNo={merchantNo}&amount={amount}&transCur={transCur}&userType={userType}"
			+ "&accountType={accountType}&accountNo={accountNo}&accountName={accountName}&bankId={bankId}&certificateType={certificateType}"
			+ "&certificateNo={certificateNo}&mobileNo={mobileNo}&branchProvince={branchProvince}&branchCity={branchCity}&branchName={branchName}"
			+ "&productName={productName}&productInfo={productInfo}&noticeUrl={noticeUrl}&expireTime={expireTime}&memo={memo}"
			+ "&sign={sign}")
	String withoiding(@Param(value = "service") String service, @Param(value = "secId") String secId,
			@Param(value = "version") String version, @Param(value = "reqSn") String reqSn,
			@Param(value = "merchantId") String merchantId, @Param(value = "merchantNo") String merchantNo,
			@Param(value = "amount") String amount, @Param(value = "transCur") String transCur,
			@Param(value = "userType") String userType, @Param(value = "accountType") String accountType,
			@Param(value = "accountNo") String accountNo, @Param(value = "accountName") String accountName,
			@Param(value = "bankId") String bankId, @Param(value = "certificateType") String certificateType,
			@Param(value = "certificateNo") String certificateNo, @Param(value = "mobileNo") String mobileNo,
			@Param(value = "branchProvince") String branchProvince, @Param(value = "branchCity") String branchCity,
			@Param(value = "branchName") String branchName, @Param(value = "productName") String productName,
			@Param(value = "productInfo") String productInfo, @Param(value = "noticeUrl") String noticeUrl,
			@Param(value = "expireTime") String expireTime, @Param(value = "memo") String memo,
			@Param(value = "sign") String sign);

	/**
	 * 单笔代扣订单查询
	 * 
	 * @param service
	 * @param secId
	 * @param version
	 * @param reqSn
	 * @param merchantId
	 * @param merchantNo
	 * @param sign
	 * @return
	 */
	@RequestLine("POST")
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@Body("service={service}&secId={secId}&version={version}&reqSn={reqSn}&merchantId={merchantId}&merchantNo={merchantNo}&sign={sign}")
	String withoidingQuery(@Param(value = "service") String service, @Param(value = "secId") String secId,
			@Param(value = "version") String version, @Param(value = "reqSn") String reqSn,
			@Param(value = "merchantId") String merchantId, @Param(value = "merchantNo") String merchantNo,
			@Param(value = "sign") String sign);

	/**
	 *4.0.0 http请求
	 */
	@RequestLine(value = "POST")
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@Body("data={data}&merchantId={merchantId}&service={service}" +
			"&sign={sign}&version={version}&secId={secId}&reqSn={reqSn}" )
	String common4_0_0(@Param(value = "service") String service, @Param(value = "secId") String secId,
					   @Param(value = "version") String version, @Param(value = "reqSn") String reqSn,
					   @Param(value = "merchantId") String merchantId, @Param(value = "data") String data,
					   @Param(value = "sign") String sign);


	/**
	 * 单笔代付
	 * 
	 * @param service
	 * @param secId
	 * @param version
	 * @param reqSn
	 * @param merchantId
	 * @param merchantNo
	 * @param source
	 * @param amount
	 * @param transCur
	 * @param userType
	 * @param accountNo
	 * @param accountName
	 * @param accountType
	 * @param mobileNo
	 * @param bankNo
	 * @param issuer
	 * @param branchProvince
	 * @param branchCity
	 * @param branchName
	 * @param noticeUrl
	 * @param memo
	 * @param sign
	 * @return
	 */
	@RequestLine("POST")
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@Body("service={service}&secId={secId}&version={version}&reqSn={reqSn}&merchantId={merchantId}&merchantNo={merchantNo}&source={source}&amount={amount}"
			+ "&transCur={transCur}&userType={userType}&accountNo={accountNo}&accountName={accountName}&accountType={accountType}&mobileNo={mobileNo}&bankNo={bankNo}&issuer={issuer}&branchProvince={branchProvince}&branchCity={branchCity}&branchName={branchName}"
			+ "&noticeUrl={noticeUrl}&memo={memo}&sign={sign}")
	String withdraw(@Param(value = "service") String service, @Param(value = "secId") String secId,
			@Param(value = "version") String version, @Param(value = "reqSn") String reqSn,
			@Param(value = "merchantId") String merchantId, @Param(value = "merchantNo") String merchantNo,
			@Param(value = "source") String source, @Param(value = "amount") String amount,
			@Param(value = "transCur") String transCur, @Param(value = "userType") String userType,
			@Param(value = "accountNo") String accountNo, @Param(value = "accountName") String accountName,
			@Param(value = "accountType") String accountType, @Param(value = "mobileNo") String mobileNo,
			@Param(value = "bankNo") String bankNo, @Param(value = "issuer") String issuer,
			@Param(value = "branchProvince") String branchProvince, @Param(value = "branchCity") String branchCity,
			@Param(value = "branchName") String branchName, @Param(value = "noticeUrl") String noticeUrl,
			@Param(value = "memo") String memo, @Param(value = "sign") String sign);
}
