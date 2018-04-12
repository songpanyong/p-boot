package com.guohuai.payment.ucfpay.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guohuai.payment.ucfpay.UcfPayConfig;
import com.guohuai.payment.ucfpay.cmd.ReqCommon4_0_0Request;
import com.guohuai.payment.ucfpay.cmd.request.*;
import com.guohuai.payment.ucfpay.cmd.response.*;
import com.guohuai.payment.ucfpay.util.AESCoder;
import com.ucf.sdk.CoderException;
import com.ucf.sdk.UcfForOnline;
import com.ucf.sdk.util.UnRepeatCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.*;

import static feign.Util.UTF_8;

@Service
@Slf4j
public class UcfCertPayServiceImpl implements UcfCertPayService {

    @Autowired
    UcfPayConfig config;

    /**
     * 认证支付（预支付）
     */
    @Override
    public CertPayResponse certPayPrepare(CertPayRequest request) {
        log.info("认证支付-预支付 请求参数：{}", request);
        final String service = config.getCertPayPrepareService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-预支付 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayResponse.class);
    }

    /**
     * 认证支付（确认支付）
     */
    @Override
    public CertPayResponse certPayConfirm(CertPayRequest request) {
        log.info("认证支付-确认支付 请求参数：{}", request);
        final String service = config.getCertPayConfirmService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-确认支付 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayResponse.class);
    }

    /**
     * 订单查询
     */
    @Override
    public CertPayQueryOrderResponse certPayQueryOrder(CertPayQueryOrderRequest request) {
        log.info("认证支付- 订单查询：{}", request);
        final String service = config.getCertPayQueryOrderStatusService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-订单查询 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayQueryOrderResponse.class);
    }

    /**
     * 解绑银行卡
     */
    @Override
    public CertPayUnbindCardResponse certPayUnbindCard(CertPayUnbindCardRequest request) {
        log.info("认证支付-解绑银行卡 请求参数：{}", request);
        final String service = config.getCertPayUnbindCardService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-解绑银行卡 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayUnbindCardResponse.class);
    }

    /**
     * 查询卡bin
     */
    @Override
    public CertPayQueryCardBinResponse certPayQueryCardBin(CertPayQueryCardBinRequest request) {
        log.info("认证支付-查询卡bin 请求参数：{}", request);
        final String service = config.getCertPayQueryCardBinService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-查询卡bin 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayQueryCardBinResponse.class);
    }

    /**
     * 查询支持银行卡列表
     */
    @Override
    public CertPayQueryBankListResponse certPayQueryBankList() {
        log.info("认证支付-查询银行卡列表 ");
        final String service = config.getCertPayGetBankListService();
        String resp = getTripartiteResponse(new Object(), service, UUID.randomUUID().toString());
        log.info("认证支付-查询银行卡列表 返回结果：{}", resp);
        CertPayQueryBankListResponse response = decodeAsBean(resp, CertPayQueryBankListResponse.class);
        Map<String, String> banks = new HashMap<>();
        try {
            final JSONArray array = new JSONArray(response.getBankList());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);
                banks.put(jo.getString("bankCode"), String.valueOf(jo.get("bankName")));
            }
            response.setBanks(banks);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 重发短信验证码
     */
    @Override
    public CertPayResendSmsResponse certPayResendSms(CertPayResendSmsRequest request) {
        log.info("认证支付-重发短信验证码 请求参数：{}", request);
        final String service = config.getCertPayResendSmsService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-重发短信验证码 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayResendSmsResponse.class);
    }

    /**
     * 银行卡鉴权
     */
    @Override
    public BankCardAuthResponse bankCardAuth(BankCardAuthRequest request) {
        log.info("认证支付-银行卡鉴权 请求参数：{}", request);
        final String service = config.getBankCardAuthService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-银行卡鉴权 返回结果：{}", resp);
        return decodeAsBean(resp, BankCardAuthResponse.class);
    }

    /**
     * 单独绑卡
     */
    @Override
    public CertPayBindBankCardResponse certPayBindBankCard(CertPayBindBankCardRequest request) {
        log.info("认证支付-单独绑卡 请求参数：{}", request);
        final String service = config.getCertPayBindBankCardService();
        String resp = getTripartiteResponse(request, service, request.getMerchantNo());
        log.info("认证支付-单独绑卡 返回结果：{}", resp);
        return decodeAsBean(resp, CertPayBindBankCardResponse.class);
    }

    /**
     * 解密异步通知报文
     */
    @Override
    public JSONObject AESCoderDecrypt(String data) {
        try {
            String resp = AESCoder.decrypt(data, config.getMerRSAKey());//ASE解密返回数据
            JSONObject object = new JSONObject(resp);
            final Iterator keys = object.keys();
            Map<String, String> params = new HashMap<>();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                params.put(key, object.getString(key));
            }
            Boolean verify = UcfForOnline.verify(config.getMerRSAKey(), "sign", params.get("sign"), params, "RSA");
            if (verify) {
                return object;
            }
        } catch (Exception e) {
            log.error("AES算法解密业务数据异常：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取三方返回内容
     *
     * @param request    请求业务数据
     * @param service    请求类型
     * @param merchantNo 商户订单号
     */
    private String getTripartiteResponse(Object request, String service, String merchantNo) {
        ReqCommon4_0_0Request vpReq = ReqCommon4_0_0Request.builder()
                .service(service)
                .secId(config.getSecId())
                .version(config.getVersion_4_0_0())
                .merchantId(config.getMerId()).build();
        // 处理业务数据，转换成 json
        final JSONObject jsonObject = new JSONObject(request);
        jsonObject.remove("class"); // 移除不需要的属性
        String data = jsonObject.toString();
        log.info("业务数据参数：" + data);

        final String rsaKey = config.getMerRSAKey();
        try {
            //AES算法加密业务数据
            String encryptDate = AESCoder.encrypt(data, rsaKey);
            vpReq.setData(encryptDate);
        } catch (Exception e) {
            log.error("AES算法加密业务数据异常：{}", e.getMessage());
        }
        //获取reqSn
        vpReq.setReqSn(createReqSn(merchantNo, service));
        //获取sign
        vpReq.setSign(createSign4_0_0(vpReq));
        // urlEncode
        String urlEncodeData = urlEncode(vpReq.getData());
        String urlEncodeSign = urlEncode(vpReq.getSign());
        log.info("请求三方参数：" + vpReq);
        String resp = config.createNowPayClient().common4_0_0(vpReq.getService(), vpReq.getSecId(),
                vpReq.getVersion(), vpReq.getReqSn(), vpReq.getMerchantId(), urlEncodeData, urlEncodeSign);
        log.info("返回加密结果：" + resp);
        try {
            resp = AESCoder.decrypt(resp, rsaKey);//ASE解密返回数据
        } catch (Exception e) {
            log.error("AES算法解密业务数据异常：{}", e.getMessage());
        }
        log.info("返回解密结果：" + resp);
        return resp;
    }

    private static String urlEncode(Object arg) {
        try {
            return URLEncoder.encode(String.valueOf(arg), UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T decodeAsBean(String resp, Class<T> type) {
        Map<String, String> respMap = this.decodeResponse(resp);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(respMap, type);
    }

    /**
     * 解析返还参数
     */
    private Map<String, String> decodeResponse(String resp) {
        Map<String, String> data = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(resp);
            Iterator<?> it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                String value = jsonObject.get(key).toString();
                data.put(key, value);
            }
        } catch (JSONException e) {
           log.error("Json To Map Exception："+e.getMessage());
        }
        return data;
    }

    /**
     * 生成序列号reqSn
     */
    private String createReqSn(String merchantNo, String service) {
        String reqSn = "";
        //先锋生成序列号
        try {
            reqSn = UnRepeatCodeGenerator.createUnRepeatCode(config.getMerId(), service, merchantNo);
        } catch (CoderException e) {
            log.error("生成序列号异常："+e.getMessage());
        }
        log.info("生成序列号：" + reqSn);
        return reqSn;
    }

    /**
     * 生成签名
     * 适用于version=4.0.0的版本请求
     */
    private String createSign4_0_0(Object obj) {
        String signValue = "";//用于保存签名字段
        Map<String, String> params = beanToMap(obj);//所有业务字段集合
        params.remove("sign");//去掉参数
        log.info("生成签名参数：" + params);

        //调用先锋支付类库生成签名
        try {
            signValue = UcfForOnline.createSign(config.getMerRSAKey(), "sign", params, "RSA");
        } catch (GeneralSecurityException | CoderException e) {
            log.error("生成签名异常：" + e.getMessage());
        }
        log.info("生成签名sign：" + signValue);
        return signValue;
    }

    /**
     * params转map
     */
    private static Map<String, String> beanToMap(Object obj) {
        Map<String, String> params = new TreeMap<>();
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
}
