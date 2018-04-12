package com.guohuai.payment.ucfpay.cmd;

import lombok.Builder;
import lombok.Data;


/**
 * 先锋 认证支付 请求参数.
 */
@Data
@Builder
public class ReqCommon4_0_0Request {
    String service;//*接口名称
    String secId;//*签名算法
    String version;//*接口版本
    String merchantId;//*商户号
    String reqSn;//本次请求序列号，需调用先锋支付类库生成 *序列 号
    String data="";//加密业务数据
    String sign;//签名字段值
}


