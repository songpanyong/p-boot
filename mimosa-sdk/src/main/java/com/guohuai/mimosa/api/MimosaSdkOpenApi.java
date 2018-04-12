package com.guohuai.mimosa.api;

import java.lang.reflect.Array;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.mimosa.api.obj.*;
import feign.Headers;
import feign.Param.Expander;
import feign.RequestLine;

/**
 * mimosa sdk 提供的接口声明
 * 
 * @author wanglei
 *
 */
public interface MimosaSdkOpenApi {

	/** 查询产品列表信息 */
	@RequestLine("POST /mimosa/sdk/product/queryall")
	@Headers(MimosaSdkConfig.CONTENT_TYPE_JSON)
	public MimosaSdkPages<ProductSDKRep> queryProductList(ProductSDKReq req);

	/** 查询产品标签 */
	@RequestLine("POST /mimosa/sdk/product/proLabel")
	@Headers(MimosaSdkConfig.CONTENT_TYPE_JSON)
	public MimosaSdkPages<ProductLabelRep> queryProductLabel();
	
	/** 根据产品标签类型查询产品标签 */
	@RequestLine("POST /mimosa/sdk/product/getProductLabelNames")
	@Headers(MimosaSdkConfig.CONTENT_TYPE_JSON)
	public MimosaSdkPages<ProductLabelRep> getProductLabelNames(ProductLabelReq param);

    /** 发送短信 */
    @RequestLine("POST /mimosa/sdk/sms/send")
    @Headers(MimosaSdkConfig.CONTENT_TYPE_JSON)
	public BaseResp sendSms(SmsSendReq sendReq);

    /** 状态通知 */
    @RequestLine("POST /mimosa/sdk/order/notify")
    @Headers(MimosaSdkConfig.CONTENT_TYPE_JSON)
    public BaseResp notifyOrderStatusChange(OrderStatusChangeNotifyReq req);
	
	final class ArrayExpander implements Expander {

		@Override
		public String expand(Object value) {
			if (null == value) {
				return "";
			}
			if (value.getClass().isArray()) {
				if (Array.getLength(value) > 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < Array.getLength(value); i++) {
						sb.append(",").append(Array.get(value, i).toString());
					}
					return sb.substring(1);
				} else {
					return "";
				}
			}
			return value.toString();
		}

	}

	
}
