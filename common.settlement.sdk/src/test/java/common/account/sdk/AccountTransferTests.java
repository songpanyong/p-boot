package common.account.sdk;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountBatchTransferRequest;
import com.guohuai.account.api.request.entity.AccountOrderDto;
import com.guohuai.account.api.response.AccountTransferResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class AccountTransferTests {

	public static void main(String[] args) {
		//账户转账交易
//		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		AccountSdk sdk = new AccountSdk("http://114.215.133.84");
		//单笔转账
//		AccountSingleTransferRequest accountSingleTransferRequest = new AccountSingleTransferRequest();
//		String order = StringUtil.uuid();
//		String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//		accountSingleTransferRequest.setBalance(new BigDecimal(11));
//		accountSingleTransferRequest.setFee(new BigDecimal(0));
//		accountSingleTransferRequest.setInputUserOid("827f8b0b24ee4e7d8dbf3780d778996d");
//		accountSingleTransferRequest.setOrderDesc("单笔转账");
//		accountSingleTransferRequest.setOrderNo(order);
//		accountSingleTransferRequest.setOrderType("60");
//		accountSingleTransferRequest.setOutputUserOid("3384UID2017061400000006");
//		accountSingleTransferRequest.setRemark("测试");
//		accountSingleTransferRequest.setRequestNo(order);
//		accountSingleTransferRequest.setSubmitTime(time);
//		accountSingleTransferRequest.setUserType("T1");
//		accountSingleTransferRequest.setVoucher(new BigDecimal(0));
//		accountSingleTransferRequest.setSystemSource("mimosa");
//		log.info("单笔转账请求参数：{}", JSONObject.toJSONString(accountSingleTransferRequest));
//		AccountTransferResponse resp1 = sdk.singleTransfer(accountSingleTransferRequest);
//		log.info("单笔转账返回结果：{}", JSONObject.toJSONString(resp1));
		
		//批量转账冻结
//		AccountBatchTransferFrozenRequest accountBatchTransferFrozenRequest = new AccountBatchTransferFrozenRequest();
//		String order2 = StringUtil.uuid();
//		String time2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//		accountBatchTransferFrozenRequest.setBalance(new BigDecimal(11));
//		accountBatchTransferFrozenRequest.setOrderDesc("批量转账冻结");
//		accountBatchTransferFrozenRequest.setOrderNo(order2);
//		accountBatchTransferFrozenRequest.setOrderType("59");
//		accountBatchTransferFrozenRequest.setOutputUserOid("3384UID2017061400000006");
//		accountBatchTransferFrozenRequest.setRemark("测试");
//		accountBatchTransferFrozenRequest.setRequestNo(order2);
//		accountBatchTransferFrozenRequest.setSubmitTime(time2);
//		accountBatchTransferFrozenRequest.setSystemSource("mimosa");
//		accountBatchTransferFrozenRequest.setTransferType("01");
//		accountBatchTransferFrozenRequest.setUserType("T2");
//		log.info("批量转账冻结请求参数：{}", JSONObject.toJSONString(accountBatchTransferFrozenRequest));
//		AccountTransferResponse resp2 = sdk.batchTransferFrozen(accountBatchTransferFrozenRequest);
//		log.info("批量转账冻结返回结果：{}", JSONObject.toJSONString(resp2));
	
		//批量转账
		AccountBatchTransferRequest accountBatchTransferRequest = new AccountBatchTransferRequest();
		accountBatchTransferRequest.setOutputUserOid("3384UID2017061400000006");
		accountBatchTransferRequest.setRequestNo(StringUtil.uuid());
		accountBatchTransferRequest.setSystemSource("mimosa");
		List<AccountOrderDto> orderList = new ArrayList<AccountOrderDto>();
		AccountOrderDto dto1 = new AccountOrderDto();
		dto1.setBalance(new BigDecimal(5));
		dto1.setFee(BigDecimal.ZERO);
		dto1.setOrderDesc("批量返佣");
		dto1.setOrderNo(StringUtil.uuid());
		dto1.setOrderType("60");
		dto1.setRemark("测试");
		dto1.setSubmitTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		dto1.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");
		dto1.setUserType("T1");
		dto1.setVoucher(BigDecimal.ZERO);
		orderList.add(dto1);
		AccountOrderDto dto2 = new AccountOrderDto();
		dto2.setBalance(new BigDecimal(4));
		dto2.setFee(BigDecimal.ZERO);
		dto2.setOrderDesc("批量返佣");
		dto2.setOrderNo(StringUtil.uuid());
		dto2.setOrderType("60");
		dto2.setRemark("测试");
		dto2.setSubmitTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		dto2.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");//UID2017060600000007
		dto2.setUserType("T1");
		dto2.setVoucher(BigDecimal.ZERO);
		orderList.add(dto2);
		accountBatchTransferRequest.setOrderList(orderList);
		log.info("批量转账请求参数：{}", JSONObject.toJSONString(accountBatchTransferRequest));
		AccountTransferResponse resp3 = sdk.batchTransfer(accountBatchTransferRequest);
		log.info("批量转账返回结果：{}", JSONObject.toJSONString(resp3));
		
		//解冻
//		AccountUnFrozenTransferRequest accountUnFrozenTransferRequest = new AccountUnFrozenTransferRequest();
//		String order4 = StringUtil.uuid();
//		String time4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//		accountUnFrozenTransferRequest.setBalance(new BigDecimal(10));
//		accountUnFrozenTransferRequest.setOrderDesc("解冻");
//		accountUnFrozenTransferRequest.setOrderNo(order4);
//		accountUnFrozenTransferRequest.setOrderType("61");
//		accountUnFrozenTransferRequest.setOutputUserOid("3384UID2017061400000006");
//		accountUnFrozenTransferRequest.setRemark("测试");
//		accountUnFrozenTransferRequest.setRequestNo(order4);
//		accountUnFrozenTransferRequest.setSubmitTime(time4);
//		accountUnFrozenTransferRequest.setSystemSource("mimosa");
//		accountUnFrozenTransferRequest.setUserType("T2");
//		log.info("解冻请求参数：{}", JSONObject.toJSONString(accountUnFrozenTransferRequest));
//		AccountTransferResponse resp4 = sdk.unFrozenTransfer(accountUnFrozenTransferRequest);
//		log.info("解冻返回结果：{}", JSONObject.toJSONString(resp4));
		
	}

}
