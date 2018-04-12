package com.guohuai.tulip.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.guohuai.basic.component.exception.GHException;

public class RedPacketUtils {

	// 如果金额超过限额，就出错了
	private static boolean isRight(float money, int count) {
		double avg = money / count;
		if (avg < minMoney) {
			return false;
		} else if (avg > maxMoney) {
			return false;
		}
		return true;
	}

	//
	private static float randomRedPacket(float money, float mins, float maxs, int count) {
		if (count == 1) {
			// 只有一个返回
			return (float) (Math.round(money * 100)) / 100;
		}
		if (mins == maxs) {
			return mins;// 如果最大值和最小值一样，就返回mins
		}
		// 如果最大值高于总金额则，最大值取总金额
		float max = maxs > money ? money : maxs;

		float one = ((float) Math.random() * (max - mins) + mins);
		one = (float) (Math.round(one * 100)) / 100;
		float moneyOther = money - one;
		// 判断此次分配后，后续是否合理
		if (isRight(moneyOther, count - 1)) {
			return one;
		} else {
			// 不合理，重新分配
			float avg = moneyOther / (count - 1);
			// 如果本次红包过大，导致下次不够分，走这一条
			if (avg < minMoney) {
				return randomRedPacket(money, mins, one, count);
			} else if (avg > maxMoney) {
				return randomRedPacket(money, one, maxs, count);
			}
		}
		return one;
	}
	/**
     * 这里为了避免某一个红包占用大量资金，我们需要设定非最后一个红包的最大金额，我们把他设置为红包金额平均值的N倍；
     */
	private static final float TIMES = 2.1f;

	public static List<BigDecimal> splitRedPackets(float money, int count) {
		if(minMoney <= 0 || maxMoney <= 0){
			throw new GHException("初始化单个红包区间值异常!");
		}

		if (!isRight(money, count)) {
			return null;
		}
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		float max = (float) (money * TIMES / count);
		max = max > maxMoney ? maxMoney : max;
		for (int i = 0; i < count; i++) {
			float one = randomRedPacket(money, minMoney, max, count - i);
			String p = decimalFormat.format(one);
			list.add(new BigDecimal(p));
			money -= one;
		}
		//洗牌（打乱顺序）
		Collections.shuffle(list);
		return list;
	}
	
	public static float minMoney = 0.00f;
	public static float maxMoney = 0.00f;
	
	public static void main(String[] args) {
		RedPacketUtils util = new RedPacketUtils();
//		SsplitRedPackets.init(10.00f, 100.00f);
		RedPacketUtils.minMoney = 0.01f;
		RedPacketUtils.maxMoney = 1.00f;
//		for (int i = 0; i < 10; i++) {
//
//			float amount = 2.00f;
//			int count = 9;
//			List<BigDecimal> list = RedPacketUtils.splitRedPackets(amount, count);
//			System.out.println("-模拟并发新建红包-=" + i + "-----" + list);
//		}
		
		float amount = 2.00f;
		int count = 9;
		List<BigDecimal> list = RedPacketUtils.splitRedPackets(amount, count);
		
		System.out.println("-模拟并发新建红包-=-----" + list);
		BigDecimal total = BigDecimal.ZERO;
		for (BigDecimal b : list) {
			total = total.add(b);
		}
		
		System.out.println("----"+total);

	}

}
