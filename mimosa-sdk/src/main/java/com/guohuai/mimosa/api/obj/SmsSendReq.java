package com.guohuai.mimosa.api.obj;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2017/12/29
 */
@Data
public class SmsSendReq implements Serializable{

    /**
     * 通知手机号
     */
    private String phone;

    /**
     * 短信模板
     */
    private SmsTemplateEnum smsTemplateEnum;

    /**
     * 短信参数
     */
    private List<Object> msgParams;

}
