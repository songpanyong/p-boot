package com.guohuai.cms.platform.advice.tab;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.advice.AdviceService;
import com.guohuai.cms.platform.advice.tab.TabQueryRep.TabQueryRepBuilder;
import com.guohuai.cms.platform.advice.tab.TabSelectRep.TabSelectRepBuilder;

@Service
@Transactional
public class TabService {

	@Autowired
	private TabDao tabDao;
	@Autowired
	private AdviceService adviceService;
	
	@Transactional
	public Map<String, List<TabSelectRep>> allSelectTabs() {
		Map<String, List<TabSelectRep>> map = new HashMap<String, List<TabSelectRep>>();
		List<TabSelectRep> selects = new ArrayList<TabSelectRep>();
		
		List<TabEntity> tabs = this.tabDao.findAll();
		
		for (TabEntity en : tabs) {
			TabSelectRep rep = new TabSelectRepBuilder().id(en.getOid())
					.text(en.getName())
					.build();
			selects.add(rep);
		}
		map.put("tabTypes", selects);
		return map;
	}
	
	@Transactional
	public PagesRep<TabQueryRep> tabQuery(Specification<TabEntity> spec, Pageable pageable) {		
		Page<TabEntity> tabs = this.tabDao.findAll(spec, pageable);
		PagesRep<TabQueryRep> pagesRep = new PagesRep<TabQueryRep>();

		for (TabEntity en : tabs) {
			TabQueryRep rep = new TabQueryRepBuilder().oid(en.getOid())
					.name(en.getName())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(tabs.getTotalElements());	
		return pagesRep;
	}
	
	public BaseRep addTab(TabAddReq req, String operator){
		BaseRep rep = new BaseRep();
		
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		
		TabEntity tab = this.tabDao.findByName(req.getName());		
		if(tab==null){
			tab = new TabEntity();
			tab.setCreateTime(now);
		}else{
			//error.define[40003]=此标签已存在(CODE:40003)
			throw MoneyException.getException(40003);
		}
		tab.setName(req.getName());
		tab.setDelStatus(TabEntity.TAB_delStatus_no);
		tab.setOperator(operator);		
		tab.setUpdateTime(now);
		this.tabDao.save(tab);
		return rep;
	}
	
	/**
	 * 删除标签
	 * @param oid
	 * @param operator
	 * @return
	 */
	public BaseRep delTab(String oid, String operator){
		BaseRep rep = new BaseRep();
		TabEntity tab = this.getTabByOid(oid);
		//更新意见反馈的标签值为null
		this.adviceService.updateAdviceOfTab(this.adviceService.getAdvicesByTab(tab));
		//删除标签
		this.tabDao.delete(tab);
		return rep;
	}
	
	public TabEntity getTabByOid(String oid){
		TabEntity tab = this.tabDao.findOne(oid);
		if(tab==null){
			//error.define[40001]=此标签不存在(CODE:40001)
			throw MoneyException.getException(40001);
		}
		return tab;
	}
}
