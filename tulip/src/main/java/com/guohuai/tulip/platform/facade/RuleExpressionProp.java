package com.guohuai.tulip.platform.facade;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2018/1/1
 */
@Data
public class RuleExpressionProp {

    private String expression;
    List<String> expressionList = new ArrayList<String>();// 表达式列表
    List<String> propList = new ArrayList<String>();// 属性列表

}
