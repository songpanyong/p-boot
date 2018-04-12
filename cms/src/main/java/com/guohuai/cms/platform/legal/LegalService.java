package com.guohuai.cms.platform.legal;

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

import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.DateUtil;
import com.guohuai.cms.platform.legal.file.LegalFileService;

@Service
@Transactional
public class LegalService {

	@Autowired
	private LegalDao legalDao;
	@Autowired
	private LegalFileService legalFileService;
	
	/**
	 * 修改
	 * @param en
	 * @return
	 */
	public LegalEntity updateEntity(LegalEntity en){
		en.setUpdateTime(DateUtil.getSqlCurrentDate());
		return this.legalDao.save(en);
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public LegalEntity findByOid(String oid){
		LegalEntity entity = this.legalDao.findOne(oid);
		if(null == entity){
			//error.define[16002]=法律文件类型不存在！(CODE:16002)
			throw MoneyException.getException(16002);
		}
		return entity;
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public LegalEntity findByCode(String code){
		LegalEntity entity = this.legalDao.findByCode(code);
		return entity;
	}

	/**
	 * 分页查找
	 * @param spec
	 * @param pageable
	 * @return
	 */
	@Transactional
	public PageResp<LegalResp> queryPage(Specification<LegalEntity> spec, Pageable pageable) {
		Page<LegalEntity> enchs = this.legalDao.findAll(spec, pageable);
		
		PageResp<LegalResp> pageResp = new PageResp<>();		
		
		List<LegalResp> list = new ArrayList<LegalResp>();
		for (LegalEntity en : enchs) {
			LegalResp resp = getDetail(en);
			list.add(resp);
		}
		
		pageResp.setTotal(enchs.getTotalElements());
		pageResp.setRows(list);
		return pageResp;
	}

	// 获取类型文件数量
	public int getFileNum(LegalEntity en) {
		return legalFileService.getFileNum(en);
	}

	/**
	 * 添加
	 * @param form
	 * @param operator 
	 */
	public void add(LegalAddReq req, String operator) {
		checkName(req.getName());	// 检测类型名称重复
		
		this.legalDao.createEntity(req.getName(), operator);
	}

	/**
	 * 修改
	 * @param form
	 * @param operator
	 */
	public void update(LegalAddReq req, String operator) {
		LegalEntity en = this.findByOid(req.getOid());
		if (req.getName().equals(en.getName())){
			return;
		}
		checkName(req.getName());	// 检测类型名称重复
		en.setName(req.getName());
		en.setOperator(operator);
		
		this.updateEntity(en);
	}

	/**
	 * 删除
	 * @param oid
	 */
	public void delete(String oid) {
		LegalEntity entity = this.findByOid(oid);
		 
		legalDao.delete(entity);
	}

	/**
	 * 停启用
	 * @param oid
	 * @param isDisplay   enabled  disabled
	 * @param operator 
	 */
	public void isDisplay(String oid, String isDisplay, String operator) {
		LegalEntity en = this.findByOid(oid);
		if (LegalEntity.LEGAL_STATUS_enabled.equals(isDisplay) && getFileNum(en) == 0){
			//当前未上传任何模板文件，请先去上传模板文件！(CODE:16004)
			throw MoneyException.getException(16004);
		}
		
		en.setStatus(isDisplay);
		en.setOperator(operator);
		
		this.updateEntity(en);
	}

	/**
	 * 检查name重复
	 * @param code
	 */
	public void checkName(String name) {
		List<LegalEntity> en = legalDao.findByName(name);
		if (en != null && !en.isEmpty()){
			// error.define[16003]=法律文件类型名称已存在！(CODE:16003)
			throw MoneyException.getException(16003);
		}
	}

	public Map<String, List<LegalResp>> queryEnabledTypes() {
		Map<String, List<LegalResp>> map = new HashMap<String, List<LegalResp>>();
		List<LegalResp> selects = new ArrayList<LegalResp>();
		
		List<LegalEntity> legals = this.legalDao.findAll();
		
		for (LegalEntity en : legals) {
			LegalResp rep = new LegalResp();
			rep.setOid(en.getOid());
			rep.setCode(en.getCode());
			rep.setName(en.getName());
			rep.setStatus(en.getStatus());
			
			selects.add(rep);
		}
		map.put("data", selects);
		return map;
	}

	public LegalResp getDetail(LegalEntity en) {
		LegalResp resp = new LegalResp(en);
		resp.setFileNum(getFileNum(en));
		return resp;
	}

	public LegalResp getDetail(String oid) {
		LegalEntity entity = this.findByOid(oid);
		LegalResp resp = this.getDetail(entity);
		return resp;
	}

}

