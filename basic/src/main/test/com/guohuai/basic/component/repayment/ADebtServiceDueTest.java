package com.guohuai.basic.component.repayment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.basic.component.repayment.rate.YearYieldRate;

public class ADebtServiceDueTest {

	static {
		SerializeConfig globalInstance = SerializeConfig.getGlobalInstance();
		globalInstance.put(Date.class, new ObjectSerializer() {

			@Override
			public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
				serializer.write(DateUtil.formatDate(((Date) object).getTime()));
			}
		});
	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2017);
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 19);
		List<RepaymentPlan.Plan> plan = RepaymentPlan.aDebtServiceDue(new Date(), c.getTime(), new BigDecimal(1000000), new YearYieldRate(new BigDecimal(4D / 100), 360));

		JSONArray obj = (JSONArray) JSONObject.toJSON(plan);

		System.out.println(obj.toJSONString());
	}

}
