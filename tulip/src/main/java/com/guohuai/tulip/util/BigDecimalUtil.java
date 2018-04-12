package com.guohuai.tulip.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 单位换算
 * 以元为单位，保留到分
 * @author star.zhu
 * 2016年7月13日
 */
public class BigDecimalUtil {

	public static final BigDecimal init0 	= BigDecimal.ZERO;
	public static final BigDecimal NUM1 	= new BigDecimal("1");
	public static final BigDecimal NUM1_1 	= new BigDecimal("1.1");
	public static final BigDecimal NUM100 	= new BigDecimal("100");
	public static final BigDecimal NUM10000 = new BigDecimal("10000");
	public static final BigDecimal NUM365 	= new BigDecimal("365");
	
	/**
	 * 百分比换算，保留4位百分比小数 xx.xxxx%
	 * @param data
	 * @return
	 */
	public static BigDecimal formatForMul100(BigDecimal data) {
		if (null == data) {
			return init0;			
		} else {
			return data.multiply(NUM100).setScale(4, BigDecimal.ROUND_HALF_UP);			
		}
	}
	
	/**
	 * 百分比还原，保留4位百分比小数 xx.xxxx%
	 * @param data
	 * @return
	 */
	public static BigDecimal formatForDivide100(BigDecimal data) {
		if (null == data) {
			return init0;
		} else {
			return data.divide(NUM100).setScale(6, BigDecimal.ROUND_HALF_UP);			
		}
	}
	
	/**
	 * 万元换算成元，保留到分
	 * @param data
	 * @return
	 */
	public static BigDecimal formatForMul10000(BigDecimal data) {
		if (null == data) {
			return init0;			
		} else {
			return data.multiply(NUM10000).setScale(2, BigDecimal.ROUND_HALF_UP);			
		}
	}
	
	/**
	 * 元换算成万元，保留到分
	 * @param data
	 * @return
	 */
	public static BigDecimal formatForDivide10000(BigDecimal data) {
		if (null == data) {
			return init0;			
		} else {
			return data.divide(NUM10000).setScale(6, BigDecimal.ROUND_HALF_UP);			
		}
	}
	/**
	 * 格式化BigDecimal
	 * @param obj
	 * @param format
	 * @return
	 */
	public static BigDecimal formatBigDecimal(BigDecimal obj,String format){
		DecimalFormat df =new DecimalFormat(format); 
		return new BigDecimal(df.format(obj));
	}
}
