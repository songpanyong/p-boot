package com.guohuai.payadapter.component;

/**
 * @ClassName: Constant
 * @Description: 通用常量
 * @author xueyunlong
 * @date 2016年11月9日 上午10:16:07
 *
 */
public final class Constant {
	public static final  String SUCCESS="0000";  
	public static final  String FAIL="9999";  
	public static final  String INPROCESS="0001";
    public static final  String VERIFY_FAIL="9998";
	
	/**
	 * 支付方式 
	 */
	public static final String TreatmentMethod_AUTO="1"; //自动
	public static final String TreatmentMethod_Manual="2"; //手动
	
	/**
	 * 支付方式 
	 */
	public static final String Launchplatform_front="1"; //前台
	public static final String Launchplatform_Back="2"; //后台
}
