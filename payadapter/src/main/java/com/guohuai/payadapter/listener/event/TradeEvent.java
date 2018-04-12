package com.guohuai.payadapter.listener.event;

import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xueyunlong
 * @ClassName: TradeEvent
 * @Description: 交易事件
 * @date 2016年11月8日 下午6:01:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TradeEvent extends AbsEvent {
    private static final long serialVersionUID = 6047514142746460173L;
    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 产品编号
     */
    private String productId;
    /**
     * 支付定单号
     */
    private String payNo;
    /**
     * 请求交易流水号
     */
    private String orderNo;
    /**
     * 金额
     */
    private String amount;//两位小数或整数
    private String realName;
    /**
     * 协议号
     */
    private String protocolNo;
    /**
     * 付款人开户银行名称
     */
    private String bankName;
    /**
     * 开户行省份, 平安银行大额跨行时需要
     */
    private String accountProvince;
    /**
     * 开户行城市，平安银行大额跨行时需要
     */
    private String accountCity;
    /**
     * 付款人支行
     */
    private String accountDept;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 加急标志 平安银行用，特急S走超级网银，加急Y走T+1
     */
    private String emergencyMark;
    /**
     * 付款人账号 平安银行用叫付款人账号，快付通叫商户银行账号
     */
    private String platformAccount;
    /**
     * 付款人名称
     */
    private String platformName;
    /**
     * 付款人地址
     */
    private String payAddress;

    /**
     * 银行返回流水号
     * 先锋支付，支付流水号
     */
    private String hostFlowNo;
    /**
     * 平安银行，对账标识
     * 先锋支付，交易流水号
     */
    private String bankReturnSeriNo;
    /**
     * 银行返回传票号
     */
    private String soaVoucher;
    /**
     * 收款人开户银行名
     */
    private String accountBankName;
    /**
     * 行内跨行标志;1:行内;0:跨行
     */
    private String unionFlag;
    /**
     * 同城/异地标志;1:同城;2:异地;
     */
    private String addrFlag;
    /**
     * 支付方式
     */
    private String treatmentMethod;
    /**
     * 货币类型:RMB
     */
    private String CcyCode = "RMB";
    /**
     * 交易时间
     */
    private Date tradeTime;
    /**
     * 银行类型
     */
    private String custBankNo;
    /**
     * 身份证号
     */
    private String custID;
    /**
     * 银行返回时间
     */
    private String bankReturnTime;
    /**
     * 交易发起平台
     * 后台 或者 前端
     */
    private String launchplatform;
    /**
     * 收款账户银行开户省代码或省名称
     */
    private String inAcctProvinceCode;
    /**
     * 收款账户开户市
     */
    private String inAcctCityName;
    /**
     * 收款人开户行机构名；如:XX支行
     */
    private String inAcctDept;
    /**
     * 账户类型-00：对私 01：对公（暂时只支持民生银行）--金运通
     * 查询商户余额时，0:全部；1:基本账户;2:未结算账户;3:冻结账户;4:保证金账户;5:资金托管账户；
     * 1：对私 2：对公  --先锋认证支付
     */
    private String accountType;
    /**
     * 业务类型代码--金运通
     */
    private String bsnCode;
    /**
     * 开户证件类型--金运通 01：身份证
     */
    private String certType;
    /**
     * 手机号--金运通
     */
    private String mobile;
    /**
     * 二次交易鉴权验证码--金运通
     */
    private String verifyCode;
    /**
     * 客户号
     */
    private String userOid;
    /**
     * 订单描述-金运通网关支付
     */
    private String orderDesc;
    /**
     * 产品信息-金运通网关支付
     */
    private String prodInfo;
    /**
     * 产品路径-金运通网关支付
     */
    private String prodDetailUrl;
    /**
     * 返回页面字符串-金运通网关支付
     */
    private String respHTML;
    /**
     * 宝付--绑定标识号
     */
    private String bindId;
    /**
     * 宝付--风险控制参数
     */
    private String clientIp;
    /**
     * 宝付预支返回的参数,用于支付推进
     */
    private String businessNo;
    /**
     * 金运通交易实时返回状态
     */
    private String tradeStatus;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 商户各账户余额
     */
    private Map<String, String> accBalance;
    /**
     * 银行编号
     */
    private String bankCode;
    /**
     * 证件号码
     */
    private String certificate_no;
    /**
     * 成功时间
     */
    private String succTime;
    /**
     *
     */
    private String signature;
    /**
     * 先锋支付，订单关闭时间（分）
     */
    private String closeOrderTime;
    /**
     * 先锋支付，银行卡类型
     * 1:借记卡 2:贷记卡
     * UNKNOW:未知
     */
    private String cardType;
    /**
     * 先锋支付，支付流水
     */
    private String outPaymentId;
    /***
     * 先锋支付，交易流水
     */
    private String outTradeNo;
    /**
     * 先锋支付，联行号
     */
    private String issuer;
}
