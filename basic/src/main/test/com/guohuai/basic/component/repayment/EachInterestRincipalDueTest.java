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
import com.guohuai.basic.component.repayment.mode.MonthDaysMode;
import com.guohuai.basic.component.repayment.rate.YearYieldRate;

public class EachInterestRincipalDueTest {

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
		// 2017年1月19日 - 2017年9月26日, 月还款日 31号
		// 借款100万, 年化利率 4%
		// 按自然月天数计息

		Calendar start = Calendar.getInstance();
		start.set(Calendar.YEAR, 2017);
		start.set(Calendar.MONTH, (1) - 1);
		start.set(Calendar.DAY_OF_MONTH, 10);

		Calendar end = Calendar.getInstance();
		end.set(Calendar.YEAR, 2017);
		end.set(Calendar.MONTH, (9) - 1);
		end.set(Calendar.DAY_OF_MONTH, 26);

		int paymentDate = 31;

		List<RepaymentPlan.Plan> plan = RepaymentPlan.eachInterestRincipalDue(start.getTime(), end.getTime(), new BigDecimal(1000000), new YearYieldRate(new BigDecimal(4D / 100), 360), paymentDate, MonthDaysMode.NATURAL_DAYS);

		JSONArray obj = (JSONArray) JSONObject.toJSON(plan);

		System.out.println(obj.toJSONString());
	}
}
