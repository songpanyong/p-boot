package com.guohuai.mimosa.api.obj;

import java.util.HashMap;
import java.util.Map;

public enum SmsTemplateEnum {

    msgWithdrawUnfreeze("msgWithdrawUnfreeze","提现解冻短信");

    private String code;
    private String message;

    SmsTemplateEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    private static Map<String, SmsTemplateEnum> codeLookup = new HashMap<String, SmsTemplateEnum>();

    static {
        for (SmsTemplateEnum type : SmsTemplateEnum.values()) {
            codeLookup.put(type.code, type);
        }
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return this.code + "-" + this.message;
    }

    public String getMessageByCode(String code){
        return codeLookup.get(code).getMessage();
    }
}
