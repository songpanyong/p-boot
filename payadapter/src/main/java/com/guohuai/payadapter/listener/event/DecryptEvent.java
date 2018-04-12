package com.guohuai.payadapter.listener.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DecryptEvent extends AbsEvent {
    private static final long serialVersionUID = 34965434353807341L;
    /**
     * 加密字段
     */
    private String data;
    /**
     * 3.0.0版本：3
     * 4.0.0版本：4
     */
    private String version;
    /**
     * 订单号
     */
    private String payNo;
    /**
     * S：成功
     * F：失败
     */
    private String status;

    /**
     * 交易流水号
     */
    private String tradeNo;

}