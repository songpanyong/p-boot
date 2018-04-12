package com.guohuai.cms.platform.element;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.cms.component.util.DateUtil;

@Service
@Transactional
public class ElementService {

	@Autowired
	private ElementDao elementDao;
	
	/**
	 * 新增
	 * @param en
	 * @return
	 */
	@Transactional
	public ElementEntity saveEntity(ElementEntity en){
		en.setCreateTime(DateUtil.getSqlCurrentDate());
		return this.updateEntity(en);
	}
	
	/**
	 * 修改
	 * @param en
	 * @return
	 */
	@Transactional
	public ElementEntity updateEntity(ElementEntity en){
		en.setUpdateTime(DateUtil.getSqlCurrentDate());
		return this.elementDao.save(en);
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public ElementEntity findByOid(String oid){
		ElementEntity entity = this.elementDao.findOne(oid);
		if(null == entity){
			throw GHException.getException("元素不存在！");
		}
		return entity;
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public ElementEntity findByCode(String code){
		ElementEntity entity = this.elementDao.findByCode(code);
		return entity;
	}

	/**
	 * 分页查找
	 * @param spec
	 * @param pageable
	 * @return
	 */
	@Transactional
	public Page<ElementEntity> queryPage(Specification<ElementEntity> spec, Pageable pageable) {
		Page<ElementEntity> enchs = this.elementDao.findAll(spec, pageable);
		
		return enchs;
	}

	/**
	 * 添加
	 * @param form
	 * @param operator 
	 */
	public void add(ElementForm form, String operator) {
		checkCode(form.getCode());	// 检测code重复
		ElementEntity en = new ElementEntity();
		BeanUtils.copyProperties(form, en);
		en.setOperator(operator);
		en.setCreator(operator);
		
		this.saveEntity(en);
	}

	/**
	 * 修改
	 * @param form
	 * @param operator
	 */
	public void update(ElementForm form, String operator) {
		ElementEntity en = this.findByOid(form.getOid());
		BeanUtils.copyProperties(form, en);
		en.setOperator(operator);
		
		this.updateEntity(en);
	}

	/**
	 * 删除
	 * @param oid
	 */
	public void delete(String oid) {
		ElementEntity entity = this.findByOid(oid);
		 
		elementDao.delete(entity);
	}

	/**
	 * 前端根据code组获取详细
	 * @param codes
	 * @return
	 */
	public ElementCTResp queryByCode(String codes) {
		ElementCTResp resp = new ElementCTResp();
		List<ElementCTBaseResp> list = new ArrayList<>();
		if (codes != null && !codes.isEmpty()){
			List<String> codeList = JSON.parseArray(codes, String.class);
			
			if (codeList != null && codeList.size()>0){
				for (String code: codeList){
					ElementEntity en = this.findByCode(code);
					ElementCTBaseResp it = new ElementCTBaseResp();
					it.setCode(code);
					if (en!=null){
						it.setContent(en.getContent());
						it.setIsDisplay(en.getIsDisplay());
					}else{
						it.setContent("");
						it.setIsDisplay(ElementEntity.ELEMENT_ISDISPLAY_YES);
					}
					list.add(it);
				}
			}
		}
		resp.setDatas(list);
		return resp;
	}

	/**
	 * 元素开关
	 * @param oid
	 * @param elementIsdisplayYes
	 * @param operator 
	 */
	public void isDisplay(String oid, String isDisplay, String operator) {
		ElementEntity en = this.findByOid(oid);
		
		en.setIsDisplay(isDisplay);
		en.setOperator(operator);
		
		this.updateEntity(en);
	}

	/**
	 * 检查code重复
	 * @param code
	 */
	public void checkCode(String code) {
		ElementEntity en = this.findByCode(code);
		if (en != null){
			throw GHException.getException("元素code已存在！");
		}
	}
}

