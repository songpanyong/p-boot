package com.guohuai.tulip.platform.sceneprop;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.rule.ruleProp.RulePropEntity;

@Service
@Transactional
public class ScenePropService {

	@Autowired
	private ScenePropDao scenePropDao;


	
	/**
	 * 说明：查询所有
	 * @param spec
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:30:51
	 */
	public PageResp<ScenePropRep> findAllSceneProp(Specification<ScenePropEntity> spec) {

		PageResp<ScenePropRep> pageResp = new PageResp<ScenePropRep>();
		Specification<ScenePropEntity> nameSpec = null;
		nameSpec = new Specification<ScenePropEntity>() {
			@Override
			public Predicate toPredicate(Root<ScenePropEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("isdel").as(String.class), ScenePropEntity.ISDEL_NO);
			}
		};
		spec = Specifications.where(spec).and(nameSpec);
		List<ScenePropEntity> enchs = this.scenePropDao.findAll(spec);
		for (ScenePropEntity ench : enchs) {
			ScenePropRep rep = new ScenePropRep();
			rep.setOid(ench.getOid());
			rep.setCode(ench.getCode());
			rep.setDescription(ench.getDescription());
			rep.setIsdel(ench.getIsdel());
			rep.setName(ench.getName());
			pageResp.getRows().add(rep);
		}
		return pageResp;
	}
	

	
	/**
	 * 说明：条件查询
	 * @param spec
	 * @param pageable
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:31:06
	 */
	public PageResp<ScenePropRep> scenePropList(Specification<ScenePropEntity> spec, Pageable pageable) {

		PageResp<ScenePropRep> pageResp = new PageResp<ScenePropRep>();
		Page<ScenePropEntity> enchs = this.scenePropDao.findAll(spec,pageable);
		for (ScenePropEntity ench : enchs) {
			ScenePropRep rep = new ScenePropRep();
			rep.setOid(ench.getOid());
			rep.setCode(ench.getCode());
			rep.setDescription(ench.getDescription());
			rep.setIsdel(ench.getIsdel());
			rep.setName(ench.getName());
			rep.setType(ench.getType());
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}



	
	/**
	 * 说明：保存
	 * @param scenePropAddReq
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:31:21
	 */
	public BaseResp saveSceneProp(ScenePropReq req) {
		BaseResp rep=new BaseResp();
		try{
			ScenePropEntity entity = new ScenePropEntity();
			if(StringUtils.isNotBlank(req.getOid())){
				entity=scenePropDao.findOne(req.getOid());
				BeanUtils.copyProperties(req, entity,"oid");
			}else{
				BeanUtils.copyProperties(req, entity);
				entity.setIsdel(ScenePropEntity.ISDEL_NO);
			}
			this.scenePropDao.save(entity);
		}catch(GHException e){
			e.printStackTrace();
			rep.setErrorCode(-1);
			rep.setErrorMessage("保存场景属性异常!");
		}
		return rep;
	}

	/**
	 * 说明：根据oid查询场景属性
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月28日 下午7:30:30
	 */
	public ScenePropRep getScenePropDetail(String oid) {
		ScenePropEntity entity = this.scenePropDao.getOne(oid);
		ScenePropRep rep=new ScenePropRep();
		BeanUtils.copyProperties(entity, rep);
		return rep;
	}
	/**
	 * 启用或删除
	 * @param oid
	 * @param isdel
	 * @return
	 */
	public BaseResp activeSceneProp(String oid,String isdel) {
		BaseResp rep=new BaseResp();
		String fromStatus=RulePropEntity.ISDEL_YES.equals(isdel)?RulePropEntity.ISDEL_NO:RulePropEntity.ISDEL_YES;
		int count=this.scenePropDao.activeRuleprop(oid,isdel,fromStatus);
		if(count<1){
			rep.setErrorCode(-1);
			rep.setErrorMessage("启用或删除异常!");
		}
		return rep;
	}
}
