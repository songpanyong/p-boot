package com.guohuai.mimosa.api.obj;

import lombok.Data;

import java.io.Serializable;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2018/1/1
 */
@Data
public class OrderStatusChangeNotifyReq implements Serializable {

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 订单状态
     */
    private OrderStatusNotifyEnum statusNotifyEnum;

}
