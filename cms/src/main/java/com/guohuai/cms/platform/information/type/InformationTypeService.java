package com.guohuai.cms.platform.information.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.platform.information.type.InformationTypeSelectRep.InformationTypeSelectRepBuilder;

@Service
@Transactional
public class InformationTypeService {

	@Autowired
	private InformationTypeDao informationTypeDao;
	
	/**
	 * 获取资讯类型下拉记录
	 * @return
	 */
	@Transactional
	public Map<String, List<InformationTypeSelectRep>> allSelectInfoTypes() {
		Map<String, List<InformationTypeSelectRep>> map = new HashMap<String, List<InformationTypeSelectRep>>();
		List<InformationTypeSelectRep> selects = new ArrayList<InformationTypeSelectRep>();
		
		List<InformationTypeEntity> types = this.informationTypeDao.getInfoByStatus();
		
		for (InformationTypeEntity en : types) {
			InformationTypeSelectRep rep = new InformationTypeSelectRepBuilder().id(en.getName())
					.text(en.getName())
					.build();
			selects.add(rep);
		}
		map.put("infoTypes", selects);
		return map;
	}
		
	/**
	 * 获取资讯类型信息
	 * @return
	 */
	public List<InformationTypeEntity> getInformationTypeList() {
		
		List<InformationTypeEntity> products = this.informationTypeDao.findAllTypeList();
		return products;
	}
	
	/**
	 * 设置资讯类型排序
	 */
	public void setInfoTypeSort(){
		List<InformationTypeEntity> types = this.informationTypeDao.getInfoByStatus();
		for (int i = 0; i < types.size(); i++) {
			this.informationTypeDao.setInfoTypeSort(types.get(i).getOid(), i+1);
		}
	}	
	
	/**
	 * 新增资讯类型
	 * @param req
	 */
	public void addInformationType(InformationTypeAddReq req) {
		InformationTypeEntity informationType=new InformationTypeEntity();
		informationType.setName(req.getName());
		//排序默认等于0,状态开启以后为正整数
		informationType.setSort(0);
		//默认关闭
		informationType.setStatus(0);
		this.informationTypeDao.save(informationType);
	}
	
	/**
	 * 删除资讯类型
	 * @param oid
	 * @return
	 */
	public BaseRep delInformationType(String oid) {
		BaseRep rep = new BaseRep();
		this.informationTypeDao.delete(oid);
		//删除之后进行排序
		this.setInfoTypeSort();
		return rep;
	}
	
	/**
	 * 查询资讯类型信息
	 * @param oid
	 * @return
	 */
	public InformationTypeEntity findInformationTypeOne(String oid) {
		return this.informationTypeDao.findOne(oid);
	}
	
	/**
	 * 上移排序
	 * @param oid
	 * @return
	 */
	public BaseRep sorInformationType(String oid) {
		BaseRep rep = new BaseRep();
		InformationTypeEntity type=this.findInformationTypeOne(oid);
		Integer sort=type.getSort();
		if(type.getStatus()==1 && sort==1){
			//error.define[50001]=此资讯类型已是第一位位置(CODE:50001)
			throw MoneyException.getException(50001);
		}
		InformationTypeEntity typeLast=this.informationTypeDao.findInformationTypeBySort(sort - 1);
		if(typeLast!=null){
			typeLast.setSort(sort);
			this.informationTypeDao.save(typeLast);
		}
		type.setSort(sort - 1);
		this.informationTypeDao.save(type);
		return rep;
	}
	
	/**
	 * 下移排序
	 * @param oid
	 * @return
	 */
	public BaseRep sorInformationTypeDown(String oid) {
		BaseRep rep = new BaseRep();
		InformationTypeEntity type=this.findInformationTypeOne(oid);
		Integer sort=type.getSort();
		//获取最大的排序号
		Integer maxSort = this.informationTypeDao.findMaxSort();
		if(type.getStatus()==1 && sort == maxSort){
			//error.define[50002]=此资讯类型已是最后一位位置(CODE:50002)
			throw MoneyException.getException(50002);
		}
		
		InformationTypeEntity typeDown=this.informationTypeDao.findInformationTypeBySort(sort + 1);
		if(typeDown!=null){
			typeDown.setSort(sort);
			this.informationTypeDao.save(typeDown);
		}
		type.setSort(sort + 1);
		this.informationTypeDao.save(type);
		return rep;
	}
	
	/**
	 * 获取开启状态的且排序大于0的资讯类型
	 * @return
	 */
	public List<InformationTypeEntity> getInformationType() {
		return this.informationTypeDao.getInformationTypeSort();
	}
	
	/**
	 * 对资讯类型排--启用/关闭
	 * @param oid
	 * @return
	 */
	public BaseRep dealInformationTypeStatus(String oid) {
		BaseRep rep = new BaseRep();
		InformationTypeEntity type=this.informationTypeDao.findOne(oid);
		if(type.getStatus()==1){
			type.setStatus(0);
			type.setSort(0);
		}else if(type.getStatus()==0){
			type.setStatus(1);
		}
		this.informationTypeDao.save(type);
		//开启/关闭之后进行排序
		this.setInfoTypeSort();
		return rep;
	}
	
}
