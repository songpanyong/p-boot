package com.guohuai.tulip.platform.rule.ruleProp;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
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

@Service
@Transactional
public class RulePropService {

	@Autowired
	private RulePropDao rulePropDao;

	
	/**
	 * 说明：条件查询
	 * @param spec
	 * @param pageable
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:27:40
	 */
	public PageResp<RulePropRep> rulepropList(Specification<RulePropEntity> spec, Pageable pageable) {
		PageResp<RulePropRep> pageResp = new PageResp<RulePropRep>();
		Page<RulePropEntity> enchs = this.rulePropDao.findAll(spec,pageable);
		for (RulePropEntity ench : enchs) {
			RulePropRep rep = new RulePropRep();
			rep.setOid(ench.getOid());
			rep.setName(ench.getName());
			rep.setField(ench.getField());
			rep.setUnit(ench.getUnit());
			rep.setType(ench.getType());
			rep.setUnitvalue(ench.getUnitvalue());
			rep.setIsdel(ench.getIsdel());
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}
	
	/**
	 * 说明：查询所有
	 * @param spec
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:27:52
	 */
	public PageResp<RulePropRep> findAllRuleProp(Specification<RulePropEntity> spec) {
		PageResp<RulePropRep> pageResp = new PageResp<RulePropRep>();
		Specification<RulePropEntity> nameSpec = null;
		nameSpec = new Specification<RulePropEntity>() {
			@Override
			public Predicate toPredicate(Root<RulePropEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("isdel").as(String.class), RulePropEntity.ISDEL_NO);
			}
		};
		spec = Specifications.where(spec).and(nameSpec);
		List<RulePropEntity> enchs = this.rulePropDao.findAll(spec);
		for (RulePropEntity ench : enchs) {
			RulePropRep rep = new RulePropRep();
			rep.setOid(ench.getOid());
			rep.setName(ench.getName());
			rep.setField(ench.getField());
			rep.setUnit(ench.getUnit());
			rep.setType(ench.getType());
			rep.setUnitvalue(ench.getUnitvalue());
			pageResp.getRows().add(rep);
		}
		return pageResp;
	}
	
	/**
	 * 说明：保存
	 * @param rulePropAddReq
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:28:16
	 */
	public BaseResp saveRuleProp(RulePropReq req) {
		BaseResp rep = new BaseResp();
		try{
			RulePropEntity entity = new RulePropEntity();
			if(null != req.getOid() && !"".equals(req.getOid())){
				entity=rulePropDao.findOne(req.getOid());
				BeanUtils.copyProperties(req, entity,"oid");
			}else{
				BeanUtils.copyProperties(req, entity);
				entity.setIsdel(RulePropEntity.ISDEL_NO);
			}
			this.rulePropDao.save(entity);
		}catch(GHException e){
			e.printStackTrace();
			rep.setErrorCode(-1);
			rep.setErrorMessage("保存规则属性异常!");
		}
		return rep;
	}
	
	/**
	 * 说明：删除
	 * @param oid
	 * @author ddyin
	 * @time：2017年2月23日 下午1:28:37
	 */
	public BaseResp activeRuleprop(String oid,String isdel) {
		BaseResp rep=new BaseResp();
		String fromStatus=RulePropEntity.ISDEL_YES.equals(isdel)?RulePropEntity.ISDEL_NO:RulePropEntity.ISDEL_YES;
		int count=this.rulePropDao.activeRuleprop(oid,fromStatus ,isdel);
		if(count<1){
			rep.setErrorCode(-1);
			rep.setErrorMessage("启用或删除异常!");
		}
		return rep;
	}

	
	/**
	 * 说明：根据oid查询规则属性
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月28日 下午2:00:06
	 */
	public RulePropRep getRulePropDetail(String oid) {
		RulePropRep rep =new RulePropRep();
		RulePropEntity rulePropEntity = this.rulePropDao.getOne(oid);
		BeanUtils.copyProperties(rulePropEntity, rep);
		return rep;
	}
	/**
	 * 根据规则属性Oid查询
	 * @param oid
	 * @return
	 */
	public RulePropEntity findRulePropByOid(String oid) {
		return rulePropDao.findOne(oid);
	}

}
