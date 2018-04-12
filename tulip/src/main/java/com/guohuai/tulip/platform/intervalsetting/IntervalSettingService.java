package com.guohuai.tulip.platform.intervalsetting;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 基数等级Service
 */
@Service
@Transactional
public class IntervalSettingService {
	Logger logger = LoggerFactory.getLogger(IntervalSettingService.class);
	@Autowired
	private IntervalSettingDao intervalSettingDao;

	public PageResp<IntervalSettingRep> query(Specification<IntervalSettingEntity> spec, Pageable pageable) {
		Page<IntervalSettingEntity> cas = this.intervalSettingDao.findAll(spec, pageable);
		PageResp<IntervalSettingRep> pageResp = new PageResp<IntervalSettingRep>();
		IntervalSettingRep rep = null;
		for (IntervalSettingEntity entity : cas) {
			rep = new IntervalSettingRep();
			BeanUtils.copyProperties(entity, rep);
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(cas.getTotalElements());
		return pageResp;
	}

	public List<IntervalSettingRep> findAll() {
		List<IntervalSettingRep> returnList = new ArrayList<IntervalSettingRep>();
		List<IntervalSettingEntity> all = intervalSettingDao.listIntervalSetting();
		for (IntervalSettingEntity entity : all) {
			IntervalSettingRep rep = new IntervalSettingRep();
			BeanUtils.copyProperties(entity, rep);
			returnList.add(rep);
		}
		return returnList;
	}

	public BaseResp saveIntervalSetting(IntervalSettingReq req,String uid) {
		BaseResp rep=new BaseResp();
		IntervalSettingEntity entity =new IntervalSettingEntity();
		IntervalSettingEntity check=intervalSettingDao.checkIntervalSettingEntity(req.getStartMoney(),req.getEndMoney());
		if(null == check || (check.getOid().equals(req.getOid()) && check.getEndMoney().compareTo(req.getEndMoney())==0 &&  check.getStartMoney().compareTo(req.getStartMoney())==0)){
			if(StringUtils.isBlank(req.getOid())){
				req.setCreateUser(uid);
				req.setUpdateUser(uid);
				BeanUtils.copyProperties(req, entity);
				intervalSettingDao.save(entity);
			}else{
				entity=intervalSettingDao.findOne(req.getOid());
				req.setUpdateUser(uid);
				req.setUpdateTime(DateUtil.getSqlCurrentDate());
				BeanUtils.copyProperties(req, entity,"createUser","createTime");
				intervalSettingDao.save(entity);
			}
		}else{
			rep.setErrorCode(-1);
			rep.setErrorMessage("金额范围冲突!!!");
		}
		return rep;
	}


	public BaseResp activeIntervalSetting(String oid, String fromStatus) {
		BaseResp rep = new BaseResp();
		String toStatus = IntervalSettingEntity.ISDEL_YES.equals(fromStatus) ? IntervalSettingEntity.ISDEL_NO : IntervalSettingEntity.ISDEL_YES;
		int num = intervalSettingDao.activeIntervalSetting(oid, toStatus, fromStatus);
		if (num < 1) {
			rep.setErrorCode(-1);
			rep.setErrorMessage("启用或失效操作失败!!");
		}
		return rep;
	}

	/**
	 * 获取基数实体
	 *
	 * @param amount 金额
	 * @return IntervalSettingEntity
	 */
	public IntervalSettingEntity getIntervalLevel(BigDecimal amount) {
		IntervalSettingEntity entity = null;
		try {
			entity = intervalSettingDao.findIntervalSettingByAmount(amount);
		} catch (GHException e) {
			e.printStackTrace();
		}
		return entity;
	}


}
