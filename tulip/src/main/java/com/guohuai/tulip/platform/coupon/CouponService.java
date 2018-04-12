package com.guohuai.tulip.platform.coupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.enums.ErrorCodeEnum;
import com.guohuai.tulip.platform.coupon.coupon1.RedPacketsRep;
import com.guohuai.tulip.platform.coupon.coupon1.RedPacketsReq;
import com.guohuai.tulip.platform.coupon.coupon2.CouponRep;
import com.guohuai.tulip.platform.coupon.coupon2.CouponReq;
import com.guohuai.tulip.platform.coupon.coupon3.RateCouponRep;
import com.guohuai.tulip.platform.coupon.coupon3.RateCouponReq;
import com.guohuai.tulip.platform.coupon.coupon4.TasteCouponRep;
import com.guohuai.tulip.platform.coupon.coupon4.TasteCouponReq;
import com.guohuai.tulip.platform.coupon.coupon5.CashCouponRep;
import com.guohuai.tulip.platform.coupon.coupon5.CashCouponReq;
import com.guohuai.tulip.platform.coupon.coupon6.PointsCouponRep;
import com.guohuai.tulip.platform.coupon.coupon6.PointsCouponReq;
import com.guohuai.tulip.platform.coupon.couponRange.CouponRangeEntity;
import com.guohuai.tulip.platform.coupon.couponRange.CouponRangeService;
import com.guohuai.tulip.platform.coupon.couponRule.CouponRuleEntity;
import com.guohuai.tulip.platform.coupon.couponRule.CouponRuleService;
import com.guohuai.tulip.platform.coupon.enums.CouponEnum;
import com.guohuai.tulip.platform.coupon.redpacket.RedpacketDetailService;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.event.PropRep;
import com.guohuai.tulip.platform.rule.RuleEntity;
import com.guohuai.tulip.platform.rule.RuleService;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemEntity;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemService;
import com.guohuai.tulip.platform.rule.ruleProp.RulePropEntity;
import com.guohuai.tulip.platform.rule.ruleProp.RulePropService;
import com.guohuai.tulip.util.RedPacketUtils;
import com.guohuai.tulip.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CouponService {

	@Autowired
	private CouponDao couponDao;
	@Autowired
	private RulePropService rulePropService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private RuleItemService ruleItemService;
	@Autowired
	private CouponRuleService couponRuleService;
	@Autowired
	private CouponRangeService couponRangeService;
	@Autowired
	private RedpacketDetailService redpacketDetailService;
	@Autowired
	private UserCouponService userCouponService;

	
	/**
	 * 查询卡券列表
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PageResp<CouponQueryRep> query(Specification<CouponEntity> spec, Pageable pageable) {
		Page<CouponEntity> enchs = this.couponDao.findAll(spec, pageable);
		PageResp<CouponQueryRep> pageResp = new PageResp<CouponQueryRep>();
		for (CouponEntity ench : enchs) {
			CouponQueryRep rep = new CouponQueryRep();
			rep.setOid(ench.getOid());
			rep.setName(ench.getName());
			rep.setTotalAmount(ench.getTotalAmount());
			rep.setRemainAmount(ench.getRemainAmount());
			rep.setUseCount(ench.getUseCount());
			rep.setInvestAmount(ench.getInvestAmount());
			rep.setValidPeriod(ench.getValidPeriod());
			rep.setRules(ench.getRules());
			rep.setProducts(ench.getProducts());
			rep.setDisableDate(ench.getDisableDate());
			rep.setMaxRateAmount(ench.getMaxRateAmount());
			rep.setIsdel(ench.getIsdel());
			rep.setDescription(ench.getDescription());
			rep.setCreateTime(ench.getCreateTime());
			rep.setCreateUser(ench.getCreateUser());
			rep.setCount(ench.getCount());
			rep.setCouponAmount(ench.getCouponAmount());
			rep.setUpperAmount(ench.getUpperAmount());
			rep.setLowerAmount(ench.getLowerAmount());
			rep.setUpdateTime(ench.getUpdateTime());
			rep.setUpdateUser(ench.getUpdateUser());
			rep.setPayflag(ench.getPayflag());
			rep.setAmountType(ench.getAmountType());
			rep.setOverlap(ench.getOverlap());
			rep.setIssueCount(ench.getCount()-ench.getRemainCount());
			rep.setDisableTime(ench.getDisableTime());
			rep.setDisableType(ench.getDisableType());
			rep.setWriteOffTotalAmount(ench.getWriteOffTotalAmount());
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}
	/**
	 * 删除卡券
	 * @param oid
	 * @return
	 */
	public BaseResp deleteCoupon(String oid) {
		BaseResp rep = new BaseResp();
		try{
			couponDao.deleteCoupon(oid);
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<====================================删除卡券异常!");
			rep.setErrorCode(-1);
			rep.setErrorMessage("删除卡券异常!");
		}
		return rep;
	}
	/**
	 * 格式化卡券属性
	 * @param propId
	 * @param value
	 * @param expression
	 * @return
	 */
	public String joinDescription(String propId, String value, String expression) {
		RulePropEntity ruleProp = rulePropService.findRulePropByOid(propId);
		if (ruleProp == null) {
			return "无使用限制";
		}
		String a[] = {};
		String result;
		if (value.indexOf("(") != -1) {
			a = value.replaceAll("\\(", "").replaceAll("\\)", "").split("\\,");
			result = ruleProp.getName()+ "大于"+a[0] +ruleProp.getUnitvalue() + "小于" + a[1]+ruleProp.getUnitvalue();
		} else {
			result = ruleProp.getName() + expression.replaceAll("\\>", "大于").replaceAll("\\<", "小于").replaceAll("\\=", "等于") + value+ruleProp.getUnitvalue();
		}

		return result;
	}
	/**
	 * 格式化产品结合
	 * @param couponId
	 * @return
	 */
	public String joinProducts(String couponId) {
		List<CouponRangeEntity> list = couponRangeService.findByCouponId(couponId);
		if (CollectionUtils.isEmpty(list)) {
			return "适用全场";
		}
		List<String> strList=new ArrayList<String>();
		for (CouponRangeEntity couponRangeEntity : list) {
			strList.add(couponRangeEntity.getLabelName());
		}
		return StringUtils.join(strList, ",");
	}
	/**
	 * 根据卡券类型查询卡券
	 * @param type
	 * @return
	 */
	public PageResp<CouponRep> counponListByType(String type) {
		List<CouponEntity> couponList = couponDao.counponListByType(type);
		PageResp<CouponRep> pageResp = new PageResp<CouponRep>();
		CouponRep rep = null;
		for (CouponEntity en : couponList) {
			rep = new CouponRep();
			rep.setOid(en.getOid());
			rep.setName(en.getName());
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(couponList.size());
		return pageResp;
	}
	/**
	 * 保存红包类型卡券
	 * @param req
	 * @return
	 */
	public RedPacketsRep saveRedPackets(RedPacketsReq req) {
		RedPacketsRep rep=new RedPacketsRep();
		CouponEntity entity = new CouponEntity();
		BeanUtils.copyProperties(req, entity);
		
		try{
			if (!StringUtil.isEmpty(req.getOid())) {//编辑的情况
				CouponEntity coupon=couponDao.findOne(req.getOid());
				if((coupon.getCount()-coupon.getRemainCount())>req.getCount()){
					rep.setErrorCode(-1);
					rep.setErrorMessage("卡券发行数量不能小于目前已领用的卡券数量!");
					return rep;
				}
				entity.setRemainCount(coupon.getRemainCount()+req.getCount()-coupon.getCount());
				entity.setUpdateTime(DateUtil.getSqlCurrentDate());
				entity.setUpdateUser(coupon.getUpdateUser());
				entity.setUseCount(coupon.getUseCount());
			}else{//新增的情况
				entity.setRemainCount(entity.getCount());
			}
			entity.setRemainAmount(entity.getTotalAmount());
			entity=this.couponDao.save(entity);
			//为当前随机红包生成红包列表
			if(CouponEntity.AMOUNTTYPE_random.equals(entity.getAmountType())){
				RedPacketUtils.minMoney = entity.lowerAmount.floatValue();
				RedPacketUtils.maxMoney = entity.upperAmount.floatValue();
				List<BigDecimal> splitRedList = RedPacketUtils.splitRedPackets(entity.totalAmount.floatValue(), entity.getCount());
				log.info("新建随机红包金额：{}", JSONObject.toJSONString(splitRedList));
				redpacketDetailService.saveRandomRedpacket(splitRedList, entity.getOid());
			}
			BeanUtils.copyProperties(entity, rep);
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<=================================保存红包异常！",e.getMessage());
			rep.setErrorCode(-1);
			rep.setErrorMessage("保存红包异常!");
		}
		return rep;
	}
	public RedPacketsRep findRedPackets(String oid) {
		RedPacketsRep rep=new RedPacketsRep();
		try{
			CouponEntity entity = couponDao.findOne(oid);
			BeanUtils.copyProperties(entity, rep);
			rep.setIssueCount(entity.getCount()-entity.getRemainCount());
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<=================================查询红包详情异常！",e.getMessage());
		}
		return rep;
	}
	/**
	 * 保存代金券
	 * @param rep
	 * @return
	 */
	public CouponRep saveCoupon(CouponReq req) {
		CouponRep rep = new CouponRep();
		CouponEntity entity = new CouponEntity();
		BeanUtils.copyProperties(req, entity);
		if (!StringUtil.isEmpty(req.getOid())) {//编辑的情况
			CouponEntity coupon=couponDao.findOne(req.getOid());
			if((coupon.getCount()-coupon.getRemainCount())>req.getCount()){
				rep.setErrorCode(-1);
				rep.setErrorMessage("卡券发行数量不能小于目前已领用的卡券数量!");
				return rep;
			}
			List<CouponRuleEntity> couponRuleEntityList = couponRuleService.listByCid(req.getOid(), RuleEntity.RULE_TYPE_USE);
			if (!CollectionUtils.isEmpty(couponRuleEntityList)) {
				String[] ruleId = new String[] { couponRuleEntityList.get(0).getRuleId() };
				if (ArrayUtils.isNotEmpty(ruleId)) {
					couponRuleService.deleteCouponRule(ruleId);
					ruleService.deleteRule(ruleId);
					ruleItemService.deleteRuleItem(ruleId);
					couponRangeService.deleteRange(req.getOid());
				}
			}
			entity.setRemainCount(coupon.getRemainCount()+req.getCount()-coupon.getCount());
			entity.setCreateTime(coupon.getCreateTime());
			entity.setCreateUser(coupon.getUpdateUser());
			entity.setUseCount(coupon.getUseCount());
		}else{//新增的情况
			entity.setRemainCount(entity.getCount());
		}
		//代金券总金额=面额*数量
		entity.setTotalAmount(entity.getCouponAmount().multiply(new BigDecimal(entity.getCount())));
		
		CouponEntity couponEntity=this.couponDao.save(entity);
		String cid = couponEntity.getOid();
		List<RulePropRep> list = req.getPropList();
		List<CouponRangeEntity> cRangeList = req.getCouponRangeEntityList();
		String description = "";
		if (!CollectionUtils.isEmpty(list)&&list.size()>0) {// 当传入属性id列表存在时
			RuleEntity ruleEntity = new RuleEntity();
			ruleEntity.setWeight(req.getWeight());
			ruleEntity.setType(RuleEntity.RULE_TYPE_USE);
			String rid = ruleService.createRule(ruleEntity).getOid();
			CouponRuleEntity cRule = new CouponRuleEntity();
			cRule.setRuleId(rid);
			cRule.setCouponId(cid);
			couponRuleService.save(cRule);
			List<String> descList=new ArrayList<String>();
			for (PropRep propRep : list) {
				if(!StringUtil.isEmpty(propRep.getOid())){
					RuleItemEntity ruleItem = new RuleItemEntity();
					ruleItem.setPropId(propRep.getOid());
					ruleItem.setRuleId(rid);
					ruleItem.setValue(propRep.getValue());
					ruleItem.setExpression(propRep.getExpression());
					ruleItemService.createRuleItem(ruleItem);
					description =joinDescription(propRep.getOid(), propRep.getValue(), propRep.getExpression());
					descList.add(description);
				}
			}
			description=StringUtils.join(descList, ",");
		}else{
			description="全场适用";
		}
		description=description==""?"全场适用":description;
		if (!CollectionUtils.isEmpty(cRangeList)) {// 当传入属性id列表存在时
			couponRangeService.deleteRange(cid);
			for (CouponRangeEntity couponRangeEntity : cRangeList) {
				couponRangeEntity.setCouponBatch(cid);
				couponRangeService.createCouponRange(couponRangeEntity);
			}
		}
		String products=joinProducts(cid);
		couponDao.updateDescription(cid, description,products,description);
		BeanUtils.copyProperties(couponEntity, rep);
		return rep;
	}
	/**
	 * 查询代金券相亲
	 * @param oid
	 * @return
	 */
	public CouponRep findCoupon(String oid) {
		CouponRep rep = new CouponRep();
		CouponEntity entity = couponDao.findOne(oid);
		BeanUtils.copyProperties(entity, rep);
		List<CouponRuleEntity> couponRuleEntityList = couponRuleService.listByCid(oid, RuleEntity.RULE_TYPE_USE);
		if (!CollectionUtils.isEmpty(couponRuleEntityList)) {
			List<RuleItemEntity> ruleItemList = ruleItemService.listRuleItemEntityByRID(couponRuleEntityList.get(0).getRuleId());
			if(!CollectionUtils.isEmpty(ruleItemList)){
				List<RulePropRep> propList = new ArrayList<RulePropRep>();
				for (RuleItemEntity ruleItemEntity : ruleItemList) {
					RulePropEntity rEntity = rulePropService.findRulePropByOid(ruleItemEntity.getPropId());
					RulePropRep p = new RulePropRep();
					if(rEntity!=null){
						if (RulePropEntity.UNIT_INTERVAL.equals(rEntity.getUnit())) {
							p.setDesc(rEntity.getName() + ruleItemEntity.getValue() + rEntity.getUnitvalue());
						} else {
							p.setDesc(rEntity.getName() + ruleItemEntity.getExpression() + ruleItemEntity.getValue()+ rEntity.getUnitvalue());
						}
					}
					p.setOid(ruleItemEntity.getPropId());
					p.setValue(ruleItemEntity.getValue());
					p.setExpression(ruleItemEntity.getExpression());
					propList.add(p);
				}
				rep.setPropList(propList);
				
			}
			rep.setWeight(ruleService.findRuleByOid(couponRuleEntityList.get(0).getRuleId()).getWeight());
		}
		List<CouponRangeEntity> couponRangeList = couponRangeService.findByCouponId(oid);
		rep.setCouponRangeEntityList(couponRangeList);
		rep.setIssueCount(entity.getCount()-entity.getRemainCount());
		return rep;
	}
	/**
	 * 保存加息券
	 * @param req
	 * @return
	 */
	public RateCouponRep saveRateCoupon(RateCouponReq req) {
		RateCouponRep rep = new RateCouponRep();
		CouponEntity entity = new CouponEntity();
		BeanUtils.copyProperties(req, entity);
		if (!StringUtil.isEmpty(req.getOid())) {//编辑的情况
			CouponEntity coupon=couponDao.findOne(req.getOid());
			if((coupon.getCount()-coupon.getRemainCount())>req.getCount()){
				rep.setErrorCode(-1);
				rep.setErrorMessage("卡券发行数量不能小于目前已领用的卡券数量!");
				return rep;
			}
			List<CouponRuleEntity> couponRuleEntityList = couponRuleService.listByCid(req.getOid(), RuleEntity.RULE_TYPE_USE);
			if (!CollectionUtils.isEmpty(couponRuleEntityList)) {
				String[] ruleId = new String[] { couponRuleEntityList.get(0).getRuleId() };
				if (ArrayUtils.isNotEmpty(ruleId)) {
					couponRuleService.deleteCouponRule(ruleId);
					ruleService.deleteRule(ruleId);
					ruleItemService.deleteRuleItem(ruleId);
					couponRangeService.deleteRange(req.getOid());
				}
			}
			entity.setRemainCount(coupon.getRemainCount()+req.getCount()-coupon.getCount());
			entity.setCreateTime(coupon.getCreateTime());
			entity.setCreateUser(coupon.getUpdateUser());
			entity.setUseCount(coupon.getUseCount());
		}else{//新增的情况
			entity.setRemainCount(entity.getCount());
		}
		CouponEntity couponEntity=this.couponDao.save(entity);
		String cid = couponEntity.getOid();
		List<RulePropRep> list = req.getPropList();
		List<CouponRangeEntity> cRangeList = req.getCouponRangeEntityList();
		String description = "";
		if (!CollectionUtils.isEmpty(list)&&list.size()>0) {// 当传入属性id列表存在时
			RuleEntity ruleEntity = new RuleEntity();
			ruleEntity.setWeight(req.getWeight());
			ruleEntity.setType(RuleEntity.RULE_TYPE_USE);
			String rid = ruleService.createRule(ruleEntity).getOid();
			CouponRuleEntity cRule = new CouponRuleEntity();
			cRule.setRuleId(rid);
			cRule.setCouponId(cid);
			couponRuleService.save(cRule);
			List<String> descList=new ArrayList<String>();
			for (PropRep propRep : list) {
				if(!StringUtil.isEmpty(propRep.getOid())){
					RuleItemEntity ruleItem = new RuleItemEntity();
					ruleItem.setPropId(propRep.getOid());
					ruleItem.setRuleId(rid);
					ruleItem.setValue(propRep.getValue());
					ruleItem.setExpression(propRep.getExpression());
					ruleItemService.createRuleItem(ruleItem);
					description =joinDescription(propRep.getOid(), propRep.getValue(), propRep.getExpression());
					descList.add(description);
				}
			}
			description=StringUtils.join(descList, ",");
		}else{
			description="全场适用";
		}
		description=description==""?"全场适用":description;
		if (!CollectionUtils.isEmpty(cRangeList)) {// 当传入属性id列表存在时
			couponRangeService.deleteRange(cid);
			for (CouponRangeEntity couponRangeEntity : cRangeList) {
				couponRangeEntity.setCouponBatch(cid);
				couponRangeService.createCouponRange(couponRangeEntity);
			}
		}
		String products=joinProducts(cid);
		couponDao.updateDescription(cid, description,products,description);
		BeanUtils.copyProperties(couponEntity, rep);
		return rep;
	}
	/**
	 * 查询加息券
	 * @param oid
	 * @return
	 */
	public RateCouponRep findRateCoupon(String oid) {
		RateCouponRep rep = new RateCouponRep();
		CouponEntity entity = couponDao.findOne(oid);
		BeanUtils.copyProperties(entity, rep);
		List<CouponRuleEntity> couponRuleEntityList = couponRuleService.listByCid(oid, RuleEntity.RULE_TYPE_USE);
		if (!CollectionUtils.isEmpty(couponRuleEntityList)) {
			List<RuleItemEntity> ruleItemList = ruleItemService.listRuleItemEntityByRID(couponRuleEntityList.get(0).getRuleId());
			if(!CollectionUtils.isEmpty(ruleItemList)){
				List<RulePropRep> propList = new ArrayList<RulePropRep>();
				for (RuleItemEntity ruleItemEntity : ruleItemList) {
					RulePropEntity rEntity = rulePropService.findRulePropByOid(ruleItemEntity.getPropId());
					RulePropRep p = new RulePropRep();
					if(rEntity!=null){
						if (rEntity.getUnit().equals("interval")) {
							p.setDesc(rEntity.getName() + ruleItemEntity.getValue() + rEntity.getUnitvalue());
						} else {
							p.setDesc(rEntity.getName() + ruleItemEntity.getExpression() + ruleItemEntity.getValue()+ rEntity.getUnitvalue());
						}
					}
					p.setOid(ruleItemEntity.getPropId());
					p.setValue(ruleItemEntity.getValue());
					p.setExpression(ruleItemEntity.getExpression());
					propList.add(p);
				}
				rep.setPropList(propList);
				
			}
			rep.setWeight(ruleService.findRuleByOid(couponRuleEntityList.get(0).getRuleId()).getWeight());
		}
		List<CouponRangeEntity> couponRangeList = couponRangeService.findByCouponId(oid);
		rep.setCouponRangeEntityList(couponRangeList);
		rep.setIssueCount(entity.getCount()-entity.getRemainCount());
		return rep;
	}
	/**
	 * 保存体验金
	 * @param req
	 * @return
	 */
	public TasteCouponRep saveTasteCoupon(TasteCouponReq req) {
		TasteCouponRep rep = new TasteCouponRep();
		CouponEntity entity = new CouponEntity();
		BeanUtils.copyProperties(req, entity);
		if (!StringUtil.isEmpty(req.getOid())) {//编辑的情况
			CouponEntity coupon=couponDao.findOne(req.getOid());
			if((coupon.getCount()-coupon.getRemainCount())>req.getCount()){
				rep.setErrorCode(-1);
				rep.setErrorMessage("卡券发行数量不能小于目前已领用的卡券数量!");
				return rep;
			}
			List<CouponRuleEntity> couponRuleEntityList = couponRuleService.listByCid(req.getOid(), RuleEntity.RULE_TYPE_USE);
			if (!CollectionUtils.isEmpty(couponRuleEntityList)) {
				String[] ruleId = new String[] { couponRuleEntityList.get(0).getRuleId() };
				if (ArrayUtils.isNotEmpty(ruleId)) {
					couponRuleService.deleteCouponRule(ruleId);
					ruleService.deleteRule(ruleId);
					ruleItemService.deleteRuleItem(ruleId);
					couponRangeService.deleteRange(req.getOid());
				}
			}
			entity.setRemainCount(coupon.getRemainCount()+req.getCount()-coupon.getCount());
			entity.setCreateTime(coupon.getCreateTime());
			entity.setCreateUser(coupon.getUpdateUser());
			entity.setUseCount(coupon.getUseCount());
		}else{//新增的情况
			entity.setRemainCount(entity.getCount());
		}
		CouponEntity couponEntity=this.couponDao.save(entity);
		String cid = couponEntity.getOid();
		List<RulePropRep> list = req.getPropList();
		List<CouponRangeEntity> cRangeList = req.getCouponRangeEntityList();
		String description = "";
		if (!CollectionUtils.isEmpty(list)&&list.size()>0) {// 当传入属性id列表存在时
			RuleEntity ruleEntity = new RuleEntity();
			ruleEntity.setWeight(req.getWeight());
			ruleEntity.setType(RuleEntity.RULE_TYPE_USE);
			String rid = ruleService.createRule(ruleEntity).getOid();
			CouponRuleEntity cRule = new CouponRuleEntity();
			cRule.setRuleId(rid);
			cRule.setCouponId(cid);
			couponRuleService.save(cRule);
			List<String> descList=new ArrayList<String>();
			for (PropRep propRep : list) {
				if(!StringUtil.isEmpty(propRep.getOid())){
					RuleItemEntity ruleItem = new RuleItemEntity();
					ruleItem.setPropId(propRep.getOid());
					ruleItem.setRuleId(rid);
					ruleItem.setValue(propRep.getValue());
					ruleItem.setExpression(propRep.getExpression());
					ruleItemService.createRuleItem(ruleItem);
					description =joinDescription(propRep.getOid(), propRep.getValue(), propRep.getExpression());
					descList.add(description);
				}
			}
			description=StringUtils.join(descList, ",");
		}else{
			description="全场适用";
		}
		description=description==""?"全场适用":description;
		if (!CollectionUtils.isEmpty(cRangeList)) {// 当传入属性id列表存在时
			couponRangeService.deleteRange(cid);
			for (CouponRangeEntity couponRangeEntity : cRangeList) {
				couponRangeEntity.setCouponBatch(cid);
				couponRangeService.createCouponRange(couponRangeEntity);
			}
		}
		String products=joinProducts(cid);
		couponDao.updateDescription(cid, description,products,description);
		BeanUtils.copyProperties(couponEntity, rep);
		return rep;
	}
	/**
	 * 查询体验金
	 * @param oid
	 * @return
	 */
	public TasteCouponRep findTasteCoupon(String oid) {
		TasteCouponRep rep = new TasteCouponRep();
		CouponEntity entity = couponDao.findOne(oid);
		BeanUtils.copyProperties(entity, rep);
		List<CouponRuleEntity> couponRuleEntityList = couponRuleService.listByCid(oid, RuleEntity.RULE_TYPE_USE);
		if (!CollectionUtils.isEmpty(couponRuleEntityList)) {
			List<RuleItemEntity> ruleItemList = ruleItemService.listRuleItemEntityByRID(couponRuleEntityList.get(0).getRuleId());
			if(!CollectionUtils.isEmpty(ruleItemList)){
				List<RulePropRep> propList = new ArrayList<RulePropRep>();
				for (RuleItemEntity ruleItemEntity : ruleItemList) {
					RulePropEntity rEntity = rulePropService.findRulePropByOid(ruleItemEntity.getPropId());
					RulePropRep p = new RulePropRep();
					if(rEntity!=null){
						if (RulePropEntity.UNIT_INTERVAL.equals(rEntity.getUnit())) {
							p.setDesc(rEntity.getName() + ruleItemEntity.getValue() + rEntity.getUnitvalue());
						} else {
							p.setDesc(rEntity.getName() + ruleItemEntity.getExpression() + ruleItemEntity.getValue()+ rEntity.getUnitvalue());
						}
					}
					p.setOid(ruleItemEntity.getPropId());
					p.setValue(ruleItemEntity.getValue());
					p.setExpression(ruleItemEntity.getExpression());
					propList.add(p);
				}
				rep.setPropList(propList);
				
			}
			rep.setWeight(ruleService.findRuleByOid(couponRuleEntityList.get(0).getRuleId()).getWeight());
		}
		List<CouponRangeEntity> couponRangeList = couponRangeService.findByCouponId(oid);
		rep.setCouponRangeEntityList(couponRangeList);
		rep.setIssueCount(entity.getCount()-entity.getRemainCount());
		return rep;
	}
	/**
	 * 保存提现券
	 * @param req
	 * @return
	 */
	public CashCouponRep saveCashCoupon(CashCouponReq req) {
		CashCouponRep rep=new CashCouponRep();
		CouponEntity entity = new CouponEntity();
		BeanUtils.copyProperties(req, entity);
		
		try{
			if (!StringUtil.isEmpty(req.getOid())) {//编辑的情况
				CouponEntity coupon=couponDao.findOne(req.getOid());
				if((coupon.getCount()-coupon.getRemainCount())>req.getCount()){
					rep.setErrorCode(-1);
					rep.setErrorMessage("卡券发行数量不能小于目前已领用的卡券数量!");
					return rep;
				}
				entity.setRemainCount(coupon.getRemainCount()+req.getCount()-coupon.getCount());
				entity.setUpdateTime(DateUtil.getSqlCurrentDate());
				entity.setUpdateUser(coupon.getUpdateUser());
				entity.setUseCount(coupon.getUseCount());
			}else{//新增的情况
				entity.setRemainCount(entity.getCount());
			}
			entity.setRemainAmount(entity.getTotalAmount());
			entity=this.couponDao.save(entity);
			BeanUtils.copyProperties(entity, rep);
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<=================================保存提现券异常！",e.getMessage());
			rep.setErrorCode(-1);
			rep.setErrorMessage("保存提现券异常!");
		}
		
		return rep;
	}
	/**
	 * 查询提现券
	 * @param oid
	 * @return
	 */
	public CashCouponRep findCashCoupon(String oid) {
		CashCouponRep rep=new CashCouponRep();
		try{
			CouponEntity entity = couponDao.findOne(oid);
			BeanUtils.copyProperties(entity, rep);
			rep.setIssueCount(entity.getCount()-entity.getRemainCount());
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<=================================查询提现券详情异常！",e.getMessage());
		}
		return rep;
	}
	/**
	 * 保存积分券
	 * @param req
	 * @return
	 */
	public PointsCouponRep savePointsCoupon(PointsCouponReq req) {
		PointsCouponRep rep=new PointsCouponRep();
		CouponEntity entity = new CouponEntity();
		BeanUtils.copyProperties(req, entity);
		
		try{
			if (!StringUtil.isEmpty(req.getOid())) {//编辑的情况
				CouponEntity coupon=couponDao.findOne(req.getOid());
				if((coupon.getCount()-coupon.getRemainCount())>req.getCount()){
					rep.setErrorCode(-1);
					rep.setErrorMessage("卡券发行数量不能小于目前已领用的卡券数量!");
					return rep;
				}
				entity.setRemainCount(coupon.getRemainCount()+req.getCount()-coupon.getCount());
				entity.setUpdateTime(DateUtil.getSqlCurrentDate());
				entity.setUpdateUser(coupon.getUpdateUser());
				entity.setUseCount(coupon.getUseCount());
			}else{//新增的情况
				entity.setRemainCount(entity.getCount());
			}
			entity.setRemainAmount(entity.getTotalAmount());
			entity=this.couponDao.save(entity);
			BeanUtils.copyProperties(entity, rep);
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<=================================保存积分券异常！",e.getMessage());
			rep.setErrorCode(-1);
			rep.setErrorMessage("保存提现券异常!");
		}
		return rep;
	}
	/**
	 * 查询积分券
	 * @param oid
	 * @return
	 */
	public PointsCouponRep findPointsCoupon(String oid) {
		PointsCouponRep rep=new PointsCouponRep();
		try{
			CouponEntity entity = couponDao.findOne(oid);
			BeanUtils.copyProperties(entity, rep);
			rep.setIssueCount(entity.getCount()-entity.getRemainCount());
		}catch(GHException e){
			e.printStackTrace();
			log.info("<<<<=================================查询积分券详情异常！",e.getMessage());
		}
		return rep;
	}

	/**
	 * 修改使用数量
	 * @param couponId
	 * @param i
	 */
	public void updateUseCount(String couponId, int num) {
		int n = this.couponDao.updateUseCount(couponId, num);
		if(n < 1){
			throw new GHException("修改使用数量异常!");
		}
	}
	/**
	 * 修改剩余数量和金额
	 * @param oid
	 * @param amount
	 */
	public void updateRemainCount(String oid, BigDecimal amount) {
		int num = this.couponDao.updateRemainCount(oid, amount);
		if(num < 1){
			throw new GHException("修改剩余数量和剩余金额异常!");
		}
	}
	/**
	 * 查询卡券实体
	 * @param couponId
	 * @return
	 */
	public CouponEntity findCouponByOid(String oid) {
		CouponEntity entity = this.couponDao.findOne(oid);
		return entity;
	}
	/**
	 * 根据rid和类型查询卡券集合
	 * @param rid
	 * @param type
	 * @return
	 */
	public List<CouponEntity> listByRid(String rid, String type) {
		List<CouponEntity> couponList=this.couponDao.listByRid(rid, type);
		return couponList;
	}
	/**
	 * 修改卡券剩余数量和总份额
	 * @param oid
	 * @param orderAmount
	 */
	public void updateRemainAndTotalAmountCount(String oid, BigDecimal orderAmount) {
		int num = this.couponDao.updateRemainAndTotalAmountCount(oid, orderAmount);
		if(num < 1){
			throw new GHException("修改卡券剩余数量和总份额异常!");
		}
	}
	/**
	 * 根据卡券Oid数组查询卡券集合
	 * @param split
	 * @return
	 */
	public List<CouponEntity> findCouponsByCouponIds(String[] couponIds) {
		List<CouponEntity> couponList=this.couponDao.findCouponsByCouponIds(couponIds);
		return couponList;
	}
	
		/**
	 * 判断需要下发的用户数是否在下发的剩余卡券数范围内
	 * 
	 * @param beenSendUserCount
	 * @param eventId
	 * @return
	 */
	public boolean checkCouponRemainCount(int beenSendUserCount, String eventId){
		boolean checkCouponCount = true;
		List<Integer> remainCounts = couponDao.checkCouponRemainCountByEventId(eventId);
		log.info("赠送用户数={}, 下发的剩余卡券数据量{}", beenSendUserCount, JSONObject.toJSONString(remainCounts));
		for (Integer count : remainCounts) {
			int ncount = count.intValue();
			if(ncount == 0){
				checkCouponCount = false;
				break;
			}else {
				//大于0的判断是否大于需要下发的用户数
				if(beenSendUserCount > ncount){
					checkCouponCount = false;
					break;
				}
			}
		}
		return checkCouponCount;
	}
	
	/**
	 * 检查卡券批次名称是否唯一
	 * @param couponName
	 * @return
	 */
	public BaseResp checkOnlyCouponName(String oid, String couponName, String type){
		BaseResp rep = new BaseResp();
		int nameCount = 0;
		if(StringUtil.isEmpty(oid)){
			nameCount = couponDao.findOnlyCouponName(couponName);			
		}else{
			nameCount = couponDao.findOnlyCouponName(couponName, oid);
		}
		if(nameCount == 0){
			rep.setErrorCode(0);
			rep.setErrorMessage(null);
		} else {
			rep.setErrorCode(-1);
			String typeName = CouponEnum.getTypeNameByTypeCode(type) + "名称";
			rep.setErrorMessage(typeName + "不能重复");			
		}
		
		return rep;
	}
	
	/**
	 * 修改卡券批次中核销总金额
	 * @param couponOid
	 * @param writeOffAmount
	 */
	public void updateWriteOffTotalAmount(String couponOid, BigDecimal writeOffAmount){
		log.debug("卡券核销第二步，卡券批次的核销中金额， 请求参数[couponOid={}, amount={}]", couponOid, writeOffAmount);
		String oid = userCouponService.findCouponBatch(couponOid);
		log.info("卡券核销第二步，卡券批次号={}", oid);
		if(StringUtil.isEmpty(oid)){
			log.info("更新卡券核销金额异常，明细卡券={}对应的卡券批次号为空", couponOid);
			throw new GHException(ErrorCodeEnum.COUPON_BATCH_ID_ISEMPTY.getMessage());
		}
		int num = couponDao.updateUseCouponWriteOffTotalAmount(oid, writeOffAmount);
		if(num < 1){
			throw new GHException("修改卡券批次中核销总金额失败");
		}
	}
	
	/**
	 * 统计各类卡券-已创建总额 
	 * @param types 卡券类型
	 * @return [0]type, [1]卡券总额
	 */
	public List<Object[]> findAllCreateAmount(List<String> types){
		return couponDao.queryAllCreateAmount(types);
	}
	
}
