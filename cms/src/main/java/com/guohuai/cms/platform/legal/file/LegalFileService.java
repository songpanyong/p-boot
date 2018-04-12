package com.guohuai.cms.platform.legal.file;

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
import com.guohuai.cms.platform.legal.LegalEntity;
import com.guohuai.cms.platform.legal.LegalService;

@Service
@Transactional
public class LegalFileService {

	@Autowired
	private LegalFileDao legalFileDao;
	@Autowired
	private LegalService legalService;
	
	
	/**
	 * 新建
	 * @param en
	 * @return
	 */
	public LegalFileEntity createEntity(LegalFileEntity en){
		en.setUploadTime(DateUtil.getSqlCurrentDate());
		en.setCreateTime(DateUtil.getSqlCurrentDate());
		return this.updateEntity(en);
	}
	
	/**
	 * 修改
	 * @param en
	 * @return
	 */
	public LegalFileEntity updateEntity(LegalFileEntity en){
		en.setUpdateTime(DateUtil.getSqlCurrentDate());
		return this.legalFileDao.save(en);
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public LegalFileEntity findByOid(String oid){
		LegalFileEntity entity = this.legalFileDao.findOne(oid);
		if(null == entity){
			//error.define[16000]=法律文件不存在！(CODE:16000)
			throw MoneyException.getException(16000);
		}
		return entity;
	}
	
	/**
	 * 分页查找
	 * @param spec
	 * @param pageable
	 * @return
	 */
	@Transactional
	public PageResp<LegalFileResp> queryPage(Specification<LegalFileEntity> spec, Pageable pageable) {
		Page<LegalFileEntity> enchs = this.legalFileDao.findAll(spec, pageable);
		
		PageResp<LegalFileResp> pageResp = new PageResp<>();		
		
		List<LegalFileResp> list = new ArrayList<LegalFileResp>();
		for (LegalFileEntity en : enchs) {
			list.add(new LegalFileResp(en));
		}
		
		pageResp.setTotal(enchs.getTotalElements());
		pageResp.setRows(list);
		return pageResp;
	}

	/**
	 * 添加
	 * @param form
	 * @param operator 
	 */
	public void add(LegalFileAddReq req, String operator) {
		checkName(req.getName());	// 检测文件名称重复
		
		LegalEntity type = legalService.findByOid(req.getTypeOid());
		
		LegalFileEntity en = new LegalFileEntity();
		en.setType(type);
		en.setFileUrl(req.getFileUrl());
		en.setName(req.getName());
		en.setOperator(operator);
		en.setStatus(LegalFileEntity.LEGAL_STATUS_enabled);
		
		this.createEntity(en);
	}

	/**
	 * 修改
	 * @param form
	 * @param operator
	 */
	public void update(LegalFileUpdateReq req, String operator) {
		LegalFileEntity en = this.findByOid(req.getOid());
		
		if (!req.getName().equals(en.getName())){
			checkName(req.getName());	// 检测文件名称重复
		}
		en.setFileUrl(req.getFileUrl());
		en.setName(req.getName());
		en.setOperator(operator);
		en.setUploadTime(DateUtil.getSqlCurrentDate());
		
		this.updateEntity(en);
	}

	/**
	 * 删除
	 * @param oid
	 */
	public void delete(String oid) {
		LegalFileEntity entity = this.findByOid(oid);
		if (LegalFileEntity.LEGAL_STATUS_enabled.equals(entity.getType().getStatus()) 
				&& legalService.getFileNum(entity.getType()) < 2){
			//当前法律文件类型为启用状态，请上传新的模板或停用类型之后再删除当前模板文件(CODE:16005)
			throw MoneyException.getException(16005);
		}
		 
		legalFileDao.delete(entity);
	}

	/**
	 * 停启用
	 * @param oid
	 * @param isDisplay   enabled  disabled
	 * @param operator 
	 */
	public void isDisplay(String oid, String isDisplay, String operator) {
		LegalFileEntity en = this.findByOid(oid);
		en.setStatus(isDisplay);
		en.setOperator(operator);
		
		this.updateEntity(en);
	}

	/**
	 * 检查name重复
	 * @param code
	 */
	public void checkName(String name) {
		List<LegalFileEntity> en = legalFileDao.findByName(name);
		if (en != null && !en.isEmpty()){
			//error.define[16001]=法律文件名称已存在！(CODE:16001)
			throw MoneyException.getException(16001);
		}
	}

	/**
	 * 获取某类型下可用文件 （下拉列表用）
	 * @param typeOid
	 * @return
	 */
	public Map<String, List<LegalFileResp>> queryEnabledFiles(String typeOid) {
		LegalEntity type = legalService.findByOid(typeOid);
		
		Map<String, List<LegalFileResp>> map = new HashMap<String, List<LegalFileResp>>();
		List<LegalFileResp> selects = new ArrayList<LegalFileResp>();
		
		List<LegalFileEntity> legals = this.legalFileDao.findByTypeAndStatus(type, LegalEntity.LEGAL_STATUS_enabled);
		
		for (LegalFileEntity en : legals) {
			LegalFileResp rep = new LegalFileResp();
			rep.setOid(en.getOid());
			rep.setTypeCode(en.getType().getCode());
			rep.setTypeOid(en.getType().getOid());
			rep.setTypeName(en.getType().getName());
			rep.setName(en.getName());
			rep.setFileUrl(en.getFileUrl());
			rep.setStatus(en.getStatus());
			
			selects.add(rep);
		}
		map.put("data", selects);
		return map;
	}

	/**
	 * 获取某类型文件数量
	 * @param en
	 * @return
	 */
	public int getFileNum(LegalEntity en){
		List<LegalFileEntity> list = this.legalFileDao.findByType(en);
		
		return list.size();
	}
}

