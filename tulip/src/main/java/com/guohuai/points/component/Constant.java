package com.guohuai.points.component;

/**
 * @ClassName: Constant
 * @Description: 通用常量
 * @author xueyunlong
 * @date 2016年11月9日 上午10:16:07
 *
 */
public final class Constant {
	public static final  String SUCCESSED="0000";  
	public static final  String FAIL="9999";  
	public static final  String OVER_TIME="0001";
	
	public static final String RESULT = "result";
	public static final String SUCCESS = "SUCCESS";
	
	
	/**
	 * 交易状态
	 */
	public static final String PAY0="0";//未处理
	public static final String PAY1="1";//交易成功
	public static final String PAY2="2";//交易失败
	public static final String PAY3="3";//交易处理中
	public static final String PAY4="4";//超时
	public static final String PAY5="5";//撤销
	
	/** 是否-- 是 */
	public static final String YES="yes";
	/** 是否-- 否 */
	public static final String NO="no";
	
	/** 积分兑换 事件（涉及到下发卡券，因积分兑换没有走活动，用户使用积分兑换商品也就是卡券时，扣取积分额之后直接下发卡券给用户） */
	public static final String POINTS_EVENTTYPE="pointsExchange";
}
