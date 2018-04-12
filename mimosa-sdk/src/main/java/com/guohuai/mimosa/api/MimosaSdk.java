package com.guohuai.mimosa.api;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.mimosa.api.ext.SqlDateTypeAdapter;
import com.guohuai.mimosa.api.ext.TimestampTypeAdapter;
import com.guohuai.mimosa.api.obj.*;

import feign.Feign;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * mimosa sdk 提供的接口实现
 * 
 * @author wanglei
 *
 */
public class MimosaSdk {

	private MimosaSdkOpenApi api;

	public MimosaSdk(String apihost, Level level) {
		super();

		List<TypeAdapter<?>> adapters = new ArrayList<TypeAdapter<?>>();
		adapters.add(new TimestampTypeAdapter());
		adapters.add(new SqlDateTypeAdapter());

		this.api = Feign.builder().encoder(new GsonEncoder(adapters)).decoder(new GsonDecoder(adapters))
				.logger(new Slf4jLogger()).logLevel(level)
				.target(MimosaSdkOpenApi.class, apihost);
	}
	
	/**
	 * 查询产品列表
	 */
	public MimosaSdkPages<ProductSDKRep> queryProductList(ProductSDKReq param) {
		return this.api.queryProductList(param);
	}

	/**
	 * 查询产品标签列表
	 */
	public MimosaSdkPages<ProductLabelRep> queryProductLabel() {
		return this.api.queryProductLabel();
	}
	/**
	 * 根据产品标签类型查询产品标签
	 * @param param
	 * @return
	 */
	public MimosaSdkPages<ProductLabelRep> getProductLabelNames(ProductLabelReq param){
		return this.api.getProductLabelNames(param);
	}

    /**
     * 发送短信
     * @param smsSendReq
     * @return
     */
	public BaseResp sendSMS(SmsSendReq smsSendReq){
        return this.api.sendSms(smsSendReq);
    }

    /**
     * 订单状态通知
     * @param smsSendReq
     * @return
     */
    public BaseResp notifyOrderStatusChange(OrderStatusChangeNotifyReq req){
        return this.api.notifyOrderStatusChange(req);
    }
}
