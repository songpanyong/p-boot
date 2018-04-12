package com.guohuai.payment.ucfpay.api;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.guohuai.payment.ucfpay.UcfPayClient;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guohuai.payment.ucfpay.UcfPayConfig;
import com.guohuai.payment.ucfpay.cmd.ReqWithdrawQueryRequest;
import com.guohuai.payment.ucfpay.cmd.ReqWithdrawQueryRequest.ReqWithdrawQueryRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.ReqWithoidingQueryRequest;
import com.guohuai.payment.ucfpay.cmd.ReqWithoidingQueryRequest.ReqWithoidingQueryRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.request.WithdrawRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithdrawRequest.WithdrawRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.response.WithdrawResp;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest.WithoidingRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;
import com.guohuai.payment.ucfpay.util.QueryString;
import com.ucf.sdk.CoderException;
import com.ucf.sdk.UcfForOnline;
import com.ucf.sdk.util.UnRepeatCodeGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UcfpayServiceImpl implements UcfpayService {

	@Autowired
	UcfPayConfig config;


	@Override
	public WithoidingResp withoiding(WithoidingRequest request) {
		WithoidingRequestBuilder nowRequest = getBaseRequest();

		nowRequest.merchantNo(request.getMerchantNo()).amount(request.getAmount()).transCur(request.getTransCur()).userType(request.getUserType())
				.accountType(request.getAccountType()).accountNo(request.getAccountNo()).accountName(request.getAccountName())
				.bankId(request.getBankId()).certificateType(request.getCertificateType()).certificateNo(request.getCertificateNo())
				.mobileNo(request.getMobileNo()).branchProvince(request.getBranchProvince()).branchCity(request.getBranchCity())
				.branchName(request.getBranchName()).productName(request.getProductName()).productInfo(request.getProductInfo())
				.expireTime(request.getExpireTime()).memo(request.getMemo()).reqSn(request.getReqSn())
				.sign(request.getSign());

		WithoidingRequest myreq = nowRequest.build();
		//获取reqSn
		String reqSn = createReqSn(request.getMerchantNo());
		myreq.setReqSn(reqSn);
		//获取sign
		String sign = createSign(myreq);
		myreq.setSign(sign);
		UcfPayClient client=config.createNowPayClient();
			log.info("client.withoiding:"+myreq);
			String resp = client.withoiding(myreq.getService(), myreq.getSecId(), myreq.getVersion(), myreq.getReqSn(),
					myreq.getMerchantId(), myreq.getMerchantNo(), myreq.getAmount(),myreq.getTransCur(),
					myreq.getUserType(), myreq.getAccountType(), myreq.getAccountNo(), myreq.getAccountName(),
					myreq.getBankId(), myreq.getCertificateType(), myreq.getCertificateNo(), myreq.getMobileNo(),
					myreq.getBranchProvince(), myreq.getBranchCity(), myreq.getBranchName(), myreq.getProductName(),
					myreq.getProductInfo(), myreq.getNoticeUrl(), myreq.getExpireTime(), myreq.getMemo(), myreq.getSign());

//			log.info("待处理返回结果："+resp);
			return this.decodeAsBean(resp, WithoidingResp.class);
	}

	@Override
	public WithoidingResp withoidingQuery(ReqWithoidingQueryRequest request) {
		ReqWithoidingQueryRequestBuilder nowRequest = getQueryBaseRequest();

		nowRequest.merchantNo(request.getMerchantNo());
		ReqWithoidingQueryRequest myreq = nowRequest.build();
		//获取reqSn
		String reqSn = createReqSn(request.getMerchantNo());
		myreq.setReqSn(reqSn);
		//获取sign
		String sign = createSign(myreq);
		myreq.setSign(sign);
		UcfPayClient client=config.createNowPayClient();
			log.info("client.withoidingQuery:"+myreq);
			String resp = client.withoidingQuery(myreq.getService(), myreq.getSecId(),
					myreq.getVersion(), myreq.getReqSn(), myreq.getMerchantId(), myreq.getMerchantNo(), myreq.getSign());

			log.info("待处理返回结果："+resp);
			return this.decodeAsBean(resp, WithoidingResp.class);
	}

	@Override
	public WithdrawResp withdrawing(WithdrawRequest request) {
		WithdrawRequestBuilder withdrawRequest = getBasewithdrawRequest();

		withdrawRequest.reqSn(request.getReqSn()).merchantNo(request.getMerchantNo()).amount(request.getAmount()).transCur(request.getTransCur()).userType(request.getUserType()).
		accountName(request.getAccountName()).accountNo(request.getAccountNo()).accountType(request.getAccountType()).mobileNo(request.getMobileNo()).bankNo(request.getBankNo()).
		issuer(request.getIssuer()).branchCity(request.getBranchCity()).branchName(request.getBranchName()).branchProvince(request.getBranchProvince()).
		memo(request.getMemo()).sign(request.getSign());

		WithdrawRequest myreq=withdrawRequest.build();
		//获取reqSn
		String reqSn = createReqSn(request.getMerchantNo());
		myreq.setReqSn(reqSn);
		//获取sign
		String sign = createSign(myreq);
		myreq.setSign(sign);
		UcfPayClient client=config.createNowPayClient();
			log.info("client.withdraw:"+myreq);
			String resp = client.withdraw(myreq.getService(), myreq.getSecId(),
					myreq.getVersion(), myreq.getReqSn(), myreq.getMerchantId(), myreq.getMerchantNo(),
					myreq.getSource(),myreq.getAmount(),myreq.getTransCur(),myreq.getUserType(),myreq.getAccountNo(),myreq.getAccountName(),myreq.getAccountType(),myreq.getMobileNo(),myreq.getBankNo(),
					myreq.getIssuer(),myreq.getBranchProvince(),myreq.getBranchCity(),myreq.getBranchName(),myreq.getNoticeUrl(),
					myreq.getMemo(),myreq.getSign());

			log.info("待处理返回结果："+resp);
			return this.decodeAsBean(resp, WithdrawResp.class);
	}
	@Override
	public WithdrawResp withdrawingQuery(ReqWithdrawQueryRequest request) {
		ReqWithdrawQueryRequestBuilder nowRequest = getwithdrawQueryBaseRequest();

		nowRequest.merchantNo(request.getMerchantNo());
		ReqWithdrawQueryRequest myreq = nowRequest.build();
		//获取reqSn
		String reqSn = createReqSn(request.getMerchantNo());
		myreq.setReqSn(reqSn);
		//获取sign
		String sign = createSign(myreq);
		myreq.setSign(sign);
		UcfPayClient client=config.createNowPayClient();
			log.info("client.withdrawQuery:"+myreq);
			String resp = client.withoidingQuery(myreq.getService(), myreq.getSecId(),
					myreq.getVersion(), myreq.getReqSn(), myreq.getMerchantId(), myreq.getMerchantNo(), myreq.getSign());

			resp=resp.replace("\"withdrawList\":[],","");
			log.info("待处理返回结果："+resp);
			return this.decodeAsBean(resp, WithdrawResp.class);
	}
	public <T> T decodeAsBean(String resp, Class<T> type) {
		Map<String, String> respMap = this.decodeResponse(resp);
		ObjectMapper mapper = new ObjectMapper();
		T value = mapper.convertValue(respMap, type);
		return value;
	}

	/**
	 * 解析返还参数
	 * @param resp
	 * @return
	 */
	private Map<String, String> decodeResponse(String resp) {
		//String转json
		JSONObject jsonObject=null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			jsonObject = new JSONObject(resp);

	       Iterator<?> it = jsonObject.keys();
	       // 遍历jsonObject数据，添加到Map对象
	       while (it.hasNext())
	       {
	           String key = String.valueOf(it.next());
	           String value = (String) jsonObject.get(key);
	           data.put(key, value);
	       }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return data;
	}

	/**
	 * 生成基本的请求对象, 把默认的参数值都填充进去.
	 * @return request builder对象
	 *
	 */
	private WithoidingRequestBuilder getBaseRequest() {
		WithoidingRequestBuilder withoidingRequest = WithoidingRequest.builder()
				.service(config.getWithoidingService()).secId(config.getSecId()).version(config.getVersion())
				.merchantId(config.getMerId()).noticeUrl(config.getNoticeUrl());//.returnUrl(config.getReturnUrl())

		return withoidingRequest;
	}
	/**
	 * 单笔代付
	 * 生成基本的请求对象, 把默认的参数值都填充进去.
	 * @return request builder对象
	 *
	 */
	private WithdrawRequestBuilder getBasewithdrawRequest() {
		WithdrawRequestBuilder withrawRequest = WithdrawRequest.builder()
				.service(config.getWithdrawService()).secId(config.getSecId()).version(config.getVersion())
				.merchantId(config.getMerId()).source(config.getSource()).noticeUrl(config.getNoticeUrl());//.returnUrl(config.getReturnUrl())

		return withrawRequest;
	}
	/**
	 * 单笔代付查询
	 * 生成基本的请求对象, 把默认的参数值都填充进去.
	 * @return request builder对象
	 *
	 */
	private ReqWithdrawQueryRequestBuilder getwithdrawQueryBaseRequest() {
		ReqWithdrawQueryRequestBuilder withoidingRequest = ReqWithdrawQueryRequest.builder()
				.service(config.getWithdrawQueryService()).secId(config.getSecId()).version(config.getVersion())
				.merchantId(config.getMerId());

		return withoidingRequest;
	}
	/**
	 * 生成基本的请求对象, 把默认的参数值都填充进去.
	 * @return request builder对象
	 *
	 */
	private ReqWithoidingQueryRequestBuilder getQueryBaseRequest() {
		ReqWithoidingQueryRequestBuilder withoidingRequest = ReqWithoidingQueryRequest.builder()
				.service(config.getWithoidingQueryService()).secId(config.getSecId()).version(config.getVersion())
				.merchantId(config.getMerId());

		return withoidingRequest;
	}

	/**
	 * 生成序列号reqSn
	 * @param merchantNo
	 * @return
	 */
	private String createReqSn(String merchantNo){
		String reqSn = "";
		//先锋生成序列号
		try {
			reqSn = UnRepeatCodeGenerator.createUnRepeatCode(config.getMerId(), config.getWithoidingService(), merchantNo);
		} catch (CoderException e) {
			e.printStackTrace();
		}
//		log.info("生成的序列号："+reqSn);
		return reqSn;
	}

	/**
	 * 生成签名
	 */
	private String createSign(Object obj){
		String signValue = "";//用于保存签名字段
//		log.info(obj.toString());
		Map<String, String> params = beanToMap(obj);//所有业务字段集合
		params.remove("sign");//去掉参数
//		log.info("生成签名参数params+"+params);

		//调用先锋支付类库生成签名
		try {
			signValue =UcfForOnline.createSign(config.getMerRSAKey(), "sign", params, "RSA");
		} catch (GeneralSecurityException | CoderException e) {
			e.printStackTrace();
		}
		log.info("生成的sign："+signValue);
		try {
			signValue = URLEncoder.encode(signValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return signValue;
	}
	
	/**
	 * params转map
	 * @param obj
	 * @return
	 */
	public static Map<String, String> beanToMap(Object obj) { 
        Map<String, String> params = new TreeMap<String, String>(); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, (String) propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
	}
	
	public static QueryString encode(WithoidingRequest request){
		
		QueryString qs = new QueryString("AccountName",request.getAccountName());
		qs.add("kl", "XX");
		
		return qs;
	}
	
}
