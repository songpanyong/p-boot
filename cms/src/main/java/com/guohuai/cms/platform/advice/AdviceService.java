package com.guohuai.cms.platform.advice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.util.StringUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.advice.AdviceQueryRep.AdviceQueryRepBuilder;
import com.guohuai.cms.platform.advice.tab.TabEntity;
import com.guohuai.cms.platform.advice.tab.TabService;

import antlr.StringUtils;

@Service
@Transactional
public class AdviceService {

	@Autowired
	private AdviceDao adviceDao;
	@Autowired
	private TabService tabService;
	@Autowired
	private AdminUtil adminUtil;	
	
	@Transactional
	public PagesRep<AdviceQueryRep> adviceQuery(Specification<AdviceEntity> spec, Pageable pageable) {		
		Page<AdviceEntity> advices = this.adviceDao.findAll(spec, pageable);
		PagesRep<AdviceQueryRep> pagesRep = new PagesRep<AdviceQueryRep>();
		
		for (AdviceEntity en : advices) {
			AdviceQueryRep rep = new AdviceQueryRepBuilder()
					.oid(en.getOid())
					.userID(en.getUserID())
					.userName(en.getUserName())
					.phoneType(en.getPhoneType())
					.content(en.getContent())
					.createTime(en.getCreateTime())	
					.operator(this.adminUtil.getAdminName(en.getOperator()))
					.remark(en.getRemark())
					.dealStatus(en.getDealStatus())
					.dealTime(en.getDealTime())
					.build();
			if(en.getTab() != null){
				rep.setTabOid(en.getTab().getOid());
			}
			pagesRep.add(rep);
		}
		pagesRep.setTotal(advices.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增用户反馈的意见
	 * @param req
	 * @return
	 */
	public BaseRep addAdvice(AdviceAddReq req){
		BaseRep rep = new BaseRep();
		try {
			String content = URLDecoder.decode(req.getContent(), "UTF-8");
			StringUtil.validChineseNumChar(content);
			req.setContent(content);
		} catch (UnsupportedEncodingException e) {
			throw new MoneyException("意见反馈失败！"); 
		}		
		
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		AdviceEntity advice = new AdviceEntity();
		advice.setUserID(req.getUserID());
		advice.setUserName(req.getUserName());
		advice.setPhoneType(req.getPhoneType());
		advice.setContent(req.getContent());
		advice.setCreateTime(now);
		//默认未处理
		advice.setDealStatus(AdviceEntity.ADVICE_dealStatus_no);
		this.adviceDao.save(advice);
		return rep;
	}
	
	
	/**
	 * 给反馈意见加标签
	 * @param oid
	 * @param tabOid
	 * @param operator
	 * @return
	 */
	public BaseRep addAdviceOfTab(String oid, String tabOid, String operator){
		BaseRep rep = new BaseRep();
		AdviceEntity advice = this.getAdviceByOid(oid);
		TabEntity tab = this.tabService.getTabByOid(tabOid);
		advice.setTab(tab);
		advice.setOperator(operator);
		this.adviceDao.save(advice);
		return rep;
	}
	
	/**
	 * 给反馈意见加备注
	 * @param oid
	 * @param remark
	 * @param operator
	 * @return
	 */
	public BaseRep addAdviceOfRemark(String oid, String remark, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		
		BaseRep rep = new BaseRep();
		AdviceEntity advice = this.getAdviceByOid(oid);
		advice.setRemark(remark);
		advice.setOperator(operator);
		advice.setDealTime(now);
		advice.setDealStatus(AdviceEntity.ADVICE_dealStatus_ok);
		this.adviceDao.save(advice);
		return rep;
	}
	
	/**
	 * 根据意见标签获取意见列表
	 * @param tab
	 * @return
	 */
	public List<AdviceEntity> getAdvicesByTab(TabEntity tab){
		return this.adviceDao.getAdvicesByTab(tab);
	}
	
	/**
	 * 更新意见反馈的标签值为null
	 * @param advices 意见反馈列表
	 */
	public void updateAdviceOfTab(List<AdviceEntity> advices){
		for (AdviceEntity en : advices) {
			en.setTab(null);
		}
		this.adviceDao.save(advices);
	}
	
	public AdviceEntity getAdviceByOid(String oid){
		AdviceEntity advice = this.adviceDao.findOne(oid);
		if(advice==null){
			//error.define[40002]=此反馈意见不存在(CODE:40002)
			throw MoneyException.getException(40002);
		}
		return advice;
	}
}
