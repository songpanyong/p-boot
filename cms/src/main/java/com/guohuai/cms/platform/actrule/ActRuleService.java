package com.guohuai.cms.platform.actrule;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.util.StringUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.actrule.ActRuleInfoRep.ActRuleInfoRepBuilder;
import com.guohuai.cms.platform.actrule.ActRuleQueryRep.ActRuleQueryRepBuilder;
import com.guohuai.cms.platform.actrule.type.ActRuleTypeEntity;
import com.guohuai.cms.platform.actrule.type.ActRuleTypeService;

@Service
@Transactional
public class ActRuleService {

	@Autowired
	private ActRuleDao actRuleDao;
	@Autowired
	private ActRuleTypeService actRuleTypeService;
	
	public ActRuleEntity findByOid(String oid) {
		ActRuleEntity actRule = this.actRuleDao.findOne(oid);
		if (null == actRule) {
			// 此活动规则不存在(CODE:11000)
			throw MoneyException.getException(11000);
		}
		return actRule;
	}
	
	/**
	 * 判断活动规则类型是否有活动规则记录
	 * @param typeId
	 * @return
	 */
	public boolean checkActRule(String typeId) {
		ActRuleEntity actRule = this.actRuleDao.findByTypeId(typeId);
		if (null != actRule) {
			// 此活动规则类型已有对应的活动规则记录(CODE:11001)
			throw MoneyException.getException(11001);
		}
		return false;
	}
	
	/**
	 * 活动规则列表查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PagesRep<ActRuleQueryRep> actRuleQuery(Specification<ActRuleEntity> spec, Pageable pageable) {		
		
		Page<ActRuleEntity> actRules = this.actRuleDao.findAll(spec, pageable);
		PagesRep<ActRuleQueryRep> pagesRep = new PagesRep<ActRuleQueryRep>();

		for (ActRuleEntity en : actRules) {
			ActRuleQueryRep rep = new ActRuleQueryRepBuilder()
					.oid(en.getOid())
					.typeId(en.getActRuleType().getId())
					.content(en.getContent())
					.createTime(en.getCreateTime())
					.updateTime(en.getUpdateTime())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(actRules.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增/编辑活动规则
	 * @param req
	 */
	public BaseRep addActRule(ActRuleAddReq req) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		
		ActRuleEntity actRule;
		if (null != req && !"".equals(req.getOid())) {
			actRule = this.findByOid(req.getOid());
		} else {
			actRule = new ActRuleEntity();
			actRule.setCreateTime(now);
			if (!this.checkActRule(req.getTypeId())) {
				ActRuleTypeEntity actRuleType = this.actRuleTypeService.findById(req.getTypeId());
				actRule.setActRuleType(actRuleType);
			}
		}
		actRule.setContent(req.getContent());
		actRule.setUpdateTime(now);
		this.actRuleDao.save(actRule);
		
		return rep;
	}
	
	/**
	 * 删除活动规则
	 * @param actRuleOid
	 * @return
	 */
	public BaseRep delActRule(String actRuleOid) {
		BaseRep rep = new BaseRep();
		ActRuleEntity actRule = this.findByOid(actRuleOid);
		this.actRuleDao.delete(actRule);
		return rep;
	}
	
	/**
	 * 活动规则详情-APP
	 * @param typeId
	 * @return
	 */
	public ActRuleInfoRep getActRuleInfo(String typeId) {
		
		ActRuleEntity actRule = this.actRuleDao.findByTypeId(typeId);
		
		ActRuleInfoRep rep = new ActRuleInfoRepBuilder().build();
		
		if (null == actRule) {
			rep.setContent(StringUtil.EMPTY);
		} else {
			rep.setContent(actRule.getContent());
		}
		return rep;
	}
}
