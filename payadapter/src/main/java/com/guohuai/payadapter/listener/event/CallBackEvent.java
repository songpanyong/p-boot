package com.guohuai.payadapter.listener.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: CallBackEvent
 * @Description: 回调事件
 * @author xueyunlong
 * @date 2016年11月15日 下午3:55:23
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CallBackEvent extends AbsEvent{

	private static final long serialVersionUID = -3239997687133050605L;
    private String orderNo;
    private String status;
    private String requestNo;
    private String payNo;

}
