package com.guohuai.tulip.enums;

/**
 * 活动相关提示信息枚举
 * @author mr_gu
 *
 */
public enum ErrorCodeEnum {
	/** 活动ID为空 */
	EVENT_ID_ISEMPTY(1000,"活动ID为空"),
	
	/** 用户类型错误 */
	EVENT_USERTYPE_ERROR(1001,"用户类型错误"),
	
	/** 用户数据为空 */
	EVENT_USER_ISEMPTY(1002,"用户数据为空"),
	
	/** 该活动已下架，请选择其他活动 */
	EVENT_ISOFF(1003,"该活动已下架，请选择其他活动"),
	
	/** 该手机号码不存在 */
	EVENT_USER_PHONE_NOTEXIST(1004,"该手机号码不存在"),
	
	/** 卡券批次号为空 */
	COUPON_BATCH_ID_ISEMPTY(1005, "卡券批次号为空"),
	
	/** 卡券ID为空 */
	COUPON_ID_ISEMPTY(1006, "卡券ID为空"),
	
	/** 卡券核销金额为空 */
	COUPON_WRITEOFF_AMOUNT_ISEMPTY(1007, "卡券核销金额为空"),
	
	/** 卡券核销时间为空 */
	COUPON_WRITEOFF_TIME_ISEMPTY(1008, "卡券核销时间为空"),
	
	/** 用户卡券核销失败 */
	COUPON_WRITEOFF_FAIL(1008, "用户卡券核销失败"),
	;
	
	private int code;
	private String message;
	
	private ErrorCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
    public String toString() {
        return "[" + this.code + "]" + this.message;
    }
	
}
