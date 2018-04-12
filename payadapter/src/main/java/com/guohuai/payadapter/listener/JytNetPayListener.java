package com.guohuai.payadapter.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.guohuai.payadapter.bankutil.MapHelper;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通网关支付
 * 
 * @author hans
 *
 */
@Slf4j
@Component
public class JytNetPayListener {

	@Autowired
	JytNetPayConfig config;

	@EventListener(condition = "#event.channel =='9' && #event.tradeType == '01'")
	public void send(TradeEvent event) {
		Map<String, String> map = new HashMap<String, String>();
		String sign = "";
		String curType = "CNY";// 币种
		String payMode = "00";// 支付客户端类型，00:PC端，01:手机端，目前只支持00。
		String bankCardType = "01";// 01纯借记卡,
									// 02信用卡,99:企业账户。支付模式为00，此处可选01,02；若支付模式为01，则此处只能是99。
		String merTranTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		String orderDesc = event.getOrderDesc();// 订单描述
		String prodInfo = event.getProdInfo();// 产品信息
		String prodDetailUrl = event.getProdDetailUrl();// 产品路径
		if (StringUtil.isEmpty(prodDetailUrl)) {
			prodDetailUrl = "";
		}

		String bankCode = "";// 银行代码(01030000)
		String merUserId = event.getUserOid();// 商户系统支付用户ID
		String merOrderId = event.getPayNo();// 商户订单号，订单号重复，页面显示“E999999,支付异常”
		String tranAmt = StringUtil.getMoneyStr(event.getAmount());
		String action = config.jytAction;
		map.put("tranCode", config.tranCode);
		map.put("version", config.version);
		map.put("charset", config.charset);
		map.put("uaType", config.uaType);
		map.put("merchantId", config.merchantId);// 订单号
		map.put("merOrderId", merOrderId);// 订单号
		map.put("merTranTime", merTranTime);// 订单时间
		map.put("merUserId", merUserId);
		map.put("orderDesc", orderDesc);
		map.put("prodInfo", prodInfo);
		map.put("prodDetailUrl", prodDetailUrl);
		map.put("tranAmt", tranAmt);
		map.put("curType", curType);
		map.put("payMode", payMode);
		map.put("bankCode", bankCode);
		map.put("bankCardType", bankCardType);
		map.put("notifyUrl", config.notifyUrl);
//		map.put("backUrl", config.backUrl);
		map.put("backUrl", prodDetailUrl);
		map.put("signType", config.signType);
		map.put("key", config.key);

		Map<String, String> reqMaps = new HashMap<String, String>();
		try {
			reqMaps = signMap(map);
		} catch (Exception e) {
			e.printStackTrace();
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("金运通网关支付签名失败");
		}
		sign = reqMaps.get("sign");
		map.put("sign", sign);
		log.info("上送签名:" + sign);
		map.remove("key");
		log.info("上送参数:" + map);
		if (!StringUtil.isEmpty(sign)) {
			String response = getHtmlStr(map, action);
			event.setReturnCode(Constant.SUCCESS);
			event.setRespHTML(response);
		} else {
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("金运通网关支付签名失败");
		}
		log.info("tradeEvent,{}", event);
	}

	private Map<String, String> signMap(Map<String, String> reqMap) {

		Map<String, String> maps = MapHelper.sortMapByKey(reqMap);
		StringBuffer reqTex = new StringBuffer();

		Iterator<Entry<String, String>> it = maps.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> ent = it.next();
			if ("key".equals(ent.getKey())) {
				continue;
			}
			if (StringUtil.isNotBlank(ent.getValue()))
				reqTex.append(ent.getKey()).append("=").append(ent.getValue()).append("&");
		}
		reqTex.deleteCharAt(reqTex.length() - 1);

		log.info("原串：" + reqTex.toString());
		String key = reqMap.get("key");
		log.info("key：" + key);
		String sign = DigestUtils.sha256Hex(reqTex.toString() + key);
		log.info("签名：" + sign);
		maps.put("sign", sign);

		return maps;
	}

	public String getHtmlStr(Map<String, String> reqMap, String action) {
		String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<title>金运通 商户支付订单信息确认</title>"
				+ "<script type=\"text/javascript\">window.onload=function(){ document.getElementById(\"payOrderForm\").submit();};</script></head>"
				+ "<body hidden=\"hdden\"><form name=\"payOrderForm\" id=\"payOrderForm\" action=\"" + action + "\" method=\"post\">"
				+ "<div>商户支付订单信息确认:<table border=\"1\" width=\"500\" \"hdden\">"
				+ "<tr><td>交易码</td><td><input name=\"tranCode\" type=\"text\" id=\"tranCode\" value=\""
				+ reqMap.get("tranCode") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>版本号</td><td><input name=\"version\" type=\"text\" id=\"version\" value=\""
				+ reqMap.get("version") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>字符集</td><td><input name=\"charset\" type=\"text\" id=\"charset\" value=\""
				+ reqMap.get("charset") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>客户端类型</td><td><input name=\"uaType\" type=\"text\" id=\"uaType\" value=\""
				+ reqMap.get("uaType") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>商户号</td><td><input name=\"merchantId\" type=\"text\" id=\"merchantId\" value=\""
				+ reqMap.get("merchantId") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>订单号</td><td><input name=\"merOrderId\" type=\"text\" id=\"merOrderId\" value=\""
				+ reqMap.get("merOrderId") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>订单时间yyyyMMddHHmmss</td><td><input name=\"merTranTime\" type=\"text\" id=\"merTranTime\" value=\""
				+ reqMap.get("merTranTime") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>merUserId</td><td><input name=\"merUserId\" type=\"text\" id=\"merUserId\" value=\""
				+ reqMap.get("merUserId") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>订单描述</td><td><input name=\"orderDesc\" type=\"text\" id=\"orderDesc\" value=\""
				+ reqMap.get("orderDesc") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>商品信息</td><td><input name=\"prodInfo\" type=\"text\" id=\"prodInfo\" value=\""
				+ reqMap.get("prodInfo") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>商品详情地址</td><td><input name=\"prodDetailUrl\" type=\"text\" id=\"prodDetailUrl\" value=\""
				+ reqMap.get("prodDetailUrl") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>交易金额</td><td><input name=\"tranAmt\" type=\"text\" id=\"tranAmt\" value=\""
				+ reqMap.get("tranAmt") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>币种</td><td><input name=\"curType\" type=\"text\" id=\"curType\" value=\""
				+ reqMap.get("curType") + "\" readonly=\"true\"/></td></tr>"
				+ "<tr><td>支付模式</td><td><input name=\"payMode\" type=\"text\" id=\"payMode\" value=\""
				+ reqMap.get("payMode") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>银行编号</td><td><input name=\"bankCode\" type=\"text\" id=\"bankCode\" value=\""
				+ "\" readonly=\"true\" hidden=\"hidden\"/></td></tr>"
				+ "<tr><td>银行卡类型</td><td><input name=\"bankCardType\" type=\"text\" id=\"bankCardType\" value=\""
				+ reqMap.get("bankCardType") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>通知地址</td><td><input name=\"notifyUrl\" type=\"text\" id=\"notifyUrl\" value=\""
				+ reqMap.get("notifyUrl") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>页面通知地址</td><td><input name=\"backUrl\" type=\"text\" id=\"backUrl\" value=\""
				+ reqMap.get("backUrl") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>签名类型</td><td><input name=\"signType\" type=\"text\" id=\"signType\" value=\""
				+ reqMap.get("signType") + "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>签名</td><td><input name=\"sign\" type=\"text\" id=\"sign\" value=\"" + reqMap.get("sign")
				+ "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>银行卡类型</td><td><input name=\"validTime\" type=\"text\" id=\"validTime\" value=\""
				+ "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>银行卡类型</td><td><input name=\"reserve1\" type=\"text\" id=\"reserve1\" value=\""
				+ "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td>银行卡类型</td><td><input name=\"reserve2\" type=\"text\" id=\"reserve2\" value=\""
				+ "\" readonly=\"true\" /></td></tr>"
				+ "<tr><td><input type=\"submit\" value=\"支付\" /></td><td></td></tr>"
				+ "</table></div></form></body></html>";
		return html;

	}

}
