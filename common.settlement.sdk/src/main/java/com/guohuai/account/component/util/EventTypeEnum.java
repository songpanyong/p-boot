package com.guohuai.account.component.util;

/**
 * EventTypeEnum 事件枚举
 *
 * @author xueyunlong
 * @date 2018/01/25
 */
public enum EventTypeEnum {
    /**
     * 使用红包
     */
    USE_REDPACKET("useRedPacket", "使用红包"),
    /**
     * 发放体验金收益
     */
    GRANT_EXPERIENCE_PROFIT("grantExperienceProfit", "发放体验金收益"),
    /**
     * 发放加息券收益
     */
    GRANT_RATE_COUPON_PROFIT("grantRateCouponProfit", "发放加息券收益"),
    /**
     * 续投-发放加息券收益
     */
    GRANT_RATE_COUPON_PROFIT_CONTINUED("grantRateCouponProfitContinued", "续投-发放加息券收益"),
    /**
     * 实时使用代金券
     */
    USE_VOUCHER_T0("useVoucherT0", "实时使用代金券"),
    /**
     * 非实时使用代金券
     */
    USE_VOUCHER_T1("useVoucherT1", "非实时使用代金券"),
    /**
     * 返佣
     */
    REBATE("rebate", "返佣"),
    /**
     * 收取平台服务费
     */
    CHARGING_PLATFORM_FEE("chargingPlatformFee", "收取平台服务费"),
    /**
     * 实时投资
     */
    INVEST_T0("investT0", "实时投资"),
    /**
     * 非实时投资
     */
    INVEST_T1("investT1", "非实时投资"),
    /**
     * 实时兑付
     */
    REDEEM_T0("redeemT0", "实时兑付"),
    /**
     * 非实时兑付
     */
    REDEEM_T1("redeemT1", "非实时兑付"),
    /**
     * 转换-实时兑付
     */
    REDEEM_T0_CHANGE("redeemT0Change", "转换-实时兑付"),
    /**
     * 转换-实时投资
     */
    INVEST_T0_CHANGE("investT0Change", "转换-实时投资"),
    
    /**
     * 续投-实时兑付
     */
    REDEEM_T0_CONTINUED("redeemT0Continued", "续投-实时兑付"),
    /**
     * 续投-非实时兑付
     */
    REDEEM_T1_CONTINUED("redeemT1Continued", "续投-非实时兑付"),
    /**
     * 续投-实时投资
     */
    INVEST_T0_CONTINUED("investT0Continued", "续投-实时投资"),
    /**
     * 续投-非实时投资
     */
    INVEST_T1_CONTINUED("investT1Continued", "续投-非实时投资"),
    /**
     * 续投解冻
     */
    UNFREEZE_CONTINUED("unfreezeContinued", "续投解冻"),
    /**
     * 充值
     */
    RECHARGE("recharge", "充值"),
    /**
     * 提现
     */
    WITHDRAW("withdraw", "提现"),
    /**
     * 提现冻结
     */
    WITHDRAW_FROZEN("withdrawFrozen", "提现冻结"),
    /**
     * 提现解冻
     */
    WITHDRAW_UNFROZEN("withdrawUnfrozen", "提现解冻"),
    /**
     * 实时投资退款
     */
    REFUND_INVEST_T0("reFundInvestT0", "实时投资退款"),
    /**
     * 非实时投资退款
     */
    REFUND_INVEST_T1("reFundInvestT1", "非实时投资退款"),
    /**
     * 实时使用卡券退款
     */
    REFUND_USE_VOUCHER_T0("reFundUseVoucherT0", "实时使用卡券退款"),
    /**
     * 非实时使用卡券退款
     */
    REFUND_USE_VOUCHER_T1("reFundUseVoucherT1", "非实时使用卡券退款"),
    /**
     * 轧差-入款
     */
    NETTING_DEPOSIT("nettingIncome", "轧差-入款"),
    /**
     * 轧差-出款
     */
    NETTING_OUTCOME("nettingOutcome", "轧差-出款"),
    /**
     * 转账-平台基本户
     */
    TRANSFER_PLATFORM_BASIC("transferPlatformBasic", "平台基本户转账"),
    /**
     * 转账-平台备付金转账
     */
    TRANSFER_PLATFORM_PAYMENT("transferPlatformPayment", "平台备付金转账"),
    /**
     * 转账-发行人基本户转账
     */
    TRANSFER_PUBLISHER_BASIC("transferPublisherBasic", "发行人基本户转账"),
    /**
     * 转账-发行人产品户转账
     */
    TRANSFER_PUBLISHER_PRODUCT("transferPublisherProduct", "发行人产品户转账"),
    /**
     * 调额-平台备付金
     */
    ADJUST_AMOUNT_PLATFORM_PAYMENT("adjustAmountPlatformPayment", "平台备付金调额"),
    /**
     * 调额-发行人产品户
     */
    ADJUST_AMOUNT_PUBLISHER_PRODUCT("adjustAmountPublisherProduct", "发行人产品户调额");


    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    EventTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getEnumName(final String value) {
        for (EventTypeEnum accountTpyeEnum : EventTypeEnum.values()) {
            if (accountTpyeEnum.getCode().equals(value)) {
                return accountTpyeEnum.getName();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
