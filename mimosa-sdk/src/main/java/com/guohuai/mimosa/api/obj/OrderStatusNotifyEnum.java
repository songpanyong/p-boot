package com.guohuai.mimosa.api.obj;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatusNotifyEnum {

    iceOut("iceOut","解冻");

    private String code;
    private String message;

    OrderStatusNotifyEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    private static Map<String, OrderStatusNotifyEnum> codeLookup = new HashMap<String, OrderStatusNotifyEnum>();

    static {
        for (OrderStatusNotifyEnum type : OrderStatusNotifyEnum.values()) {
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
