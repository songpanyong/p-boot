package com.guohuai.cms.platform.version;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.util.DateUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.version.VersionQueryRep.VersionQueryRepBuilder;

@Service
@Transactional
public class VersionService {
	
	@Autowired
	private VersionDao versionDao;
	@Autowired
	private AdminUtil adminUtil;
	
   /**
    * 页面查询信息
    * @param spec
    * @param pageable
    * @return
    */
	public PagesRep<VersionQueryRep> versionFindAll(Specification<VersionEntity> spec, Pageable pageable) {
		Page<VersionEntity> products = this.versionDao.findAll(spec, pageable);
		PagesRep<VersionQueryRep> pagesRep = new PagesRep<VersionQueryRep>();
		
		for (VersionEntity pe : products) {
			VersionQueryRep rep = new VersionQueryRepBuilder()
					.oid(pe.getOid())
					.versionNo(pe.getVersionNo())
					.versionSize(pe.getVersionSize())
					.status(pe.getStatus())
					.publishTime(pe.getPublishTime())
					.fileUrl(pe.getFileUrl())
					.description(pe.getDescription())
					.system(pe.getSystem())
					.fileName(pe.getFileName())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(products.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增/修改版本
	 * @param req
	 * @param operator
	 * @return
	 */
    public BaseRep addVersion(VersionAddReq req, String operator) {
    	BaseRep rep = new BaseRep();
    	Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
    	VersionEntity version = null;
    	if(StringUtils.isNotEmpty(req.getOid())){
    		version = this.getOne(req.getOid());
		}else{
			version = new VersionEntity();
		}
    	version.setCreateTime(now);
    	version.setVersionNo(req.getVersionNo());
    	version.setStatus(VersionEntity.VERSION_status_reviewed);
    	version.setVersionSize(req.getVersionSize());
    	version.setCreator(operator);
    	version.setFileUrl(req.getFileUrl());
    	version.setDescription(req.getDescription());
    	version.setCompulsory(req.getCompulsory());
    	version.setCheckInterval(req.getCheckInterval());
    	version.setSystem(req.getSystem());
    	version.setUpgradeType(req.getUpgradeType());
    	version.setFileName(req.getFileName());
    	try {
			version.setExpectPublishTime(DateUtil.parse(req.getExpectPublishTime(), DateUtil.defaultDatePattern));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	this.versionDao.save(version);
    	return rep;
    }
    
    /**
	 * 获取版本实体
	 * @param oid
	 * @return
	 */
	public VersionEntity getOne(String oid){
		VersionEntity en = this.versionDao.findOne(oid);
		if(en==null){
			//error.define[80001]=此版本不存在(CODE:80001)
			throw MoneyException.getException(80001);
		}
		return en;
	}
	
	/**
	 * 删除版本
	 * @param oid
	 * @return
	 */
	public BaseRep versionDelete(String oid) {
		BaseRep rep = new BaseRep();
		this.versionDao.delete(oid);
		return rep;
	}
	
	/**
	 * 获取版本信息详情
	 * @param oid
	 * @return
	 */
	public VersionQueryRep getVersion(String oid) {
		VersionEntity pe=this.versionDao.getOne(oid);
		VersionQueryRep rep=new VersionQueryRepBuilder()
				.oid(pe.getOid())
				.versionNo(pe.getVersionNo())
				.versionSize(pe.getVersionSize())
				.status(pe.getStatus())
				.publishTime(pe.getPublishTime())
				.fileUrl(pe.getFileUrl())
				.review(this.adminUtil.getAdminName(pe.getReview()))
				.description(pe.getDescription())
				.reviewTime(pe.getReviewTime())
				.publisher(this.adminUtil.getAdminName(pe.getPublisher()))
				.creator(this.adminUtil.getAdminName(pe.getCreator()))
				.createTime(pe.getCreateTime())
				.expectPublishTime(pe.getExpectPublishTime())
				.reviewRemark(pe.getReviewRemark())
				.system(pe.getSystem())
				.compulsory(pe.getCompulsory())
				.checkInterval(pe.getCheckInterval())
				.upgradeType(pe.getUpgradeType())
				.fileName(pe.getFileName())
				.build();
		return rep;
		
	}
		
	/**
	 * 版本审核
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep versionReview(VersionReviewRep req, String operator) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		VersionEntity version =  this.getOne(req.getOid());
		version.setReview(operator);
		version.setReviewTime(now);
		if(req.getApprResult().equals(VersionEntity.VERSION_reviewStatus_pass)){
			version.setStatus(VersionEntity.VERSION_status_reviewed);
		}else if(req.getApprResult().equals(VersionEntity.VERSION_reviewStatus_refused)){
			version.setStatus(VersionEntity.VERSION_status_refused);
		}
		version.setReviewRemark(req.getRemark());
		this.versionDao.save(version);
		return rep;
	}
	
	/**
	 * 版本上/下架
	 * @param oid
	 * @return
	 */
	public BaseRep versionPubilsh(String oid,String operator) {
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		VersionEntity version =  this.getOne(oid);
		if(version.getStatus().equalsIgnoreCase(VersionEntity.VERSION_status_reviewed)){
			version.setStatus(VersionEntity.VERSION_status_on);
			version.setPublisher(operator);
			version.setPublishTime(now);
		}
		this.versionDao.save(version);
		return rep;
	}
	
	/**
	 * 获取增量版本的现有版本号
	 * @return
	 */
	public String getVersionNoByIncrement() {
		return this.versionDao.getVersionNoByIncrement();
	}
	
	/**
	 * 获取升级版本的现有版本号
	 * @return
	 */
	public Map<String, Object> getVersionNoByVersion(String system) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ios", this.versionDao.getVersionNoByVersion(system));
		return map;
	}
	
	/**
	 * 接口---向外提供版本更新信息
	 * @return
	 */
	public Map<String,Object> getVersionUpdateInfo() {
		//获取最近的增量版本信息
		VersionEntity incrementEn = this.versionDao.getIncremmentLast();
		//获取最近的IOS的升级版本的版本信息
		VersionEntity iosVersion = this.versionDao.getVersionLast(VersionEntity.VERSION_system_ios);
		//获取最近的android的升级版本的版本信息
		VersionEntity androidVersion = this.versionDao.getVersionLast(VersionEntity.VERSION_system_android);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appid", "");
		if(incrementEn != null){
			map.put("wgturl",incrementEn.getFileUrl());
			map.put("wgtsize", incrementEn.getVersionSize());
			map.put("wgtversionNO", incrementEn.getVersionNo());
		}
		Map<String,Object> iosMap = new HashMap<String,Object>();
		if(iosVersion != null){
			iosMap.put("version", iosVersion.getVersionNo());
			iosMap.put("note", iosVersion.getVersionNo());
			iosMap.put("url", iosVersion.getFileUrl());
			iosMap.put("Compulsory", iosVersion.getCompulsory());
			iosMap.put("checkInterval", iosVersion.getCheckInterval());
			iosMap.put("description", iosVersion.getDescription());
			iosMap.put("fileName", iosVersion.getFileName());
		}
		map.put("ios", iosMap);
		
		Map<String,Object> androidMap = new HashMap<String,Object>();
		if(androidVersion != null){
			androidMap.put("version", androidVersion.getVersionNo());
			androidMap.put("note", androidVersion.getVersionNo());
			androidMap.put("url", androidVersion.getFileUrl());
			androidMap.put("Compulsory", androidVersion.getCompulsory());
			androidMap.put("checkInterval", androidVersion.getCheckInterval());	
			androidMap.put("description", androidVersion.getDescription());
			androidMap.put("fileName", androidVersion.getFileName());
		}
		map.put("Android", androidMap);
		System.out.println(map);
		return map;
	}
	
	/**
	 * 获取最新安卓版本信息的下载地址
	 * @return
	 */
	public Map<String, Object> getVersionByAndroid() {
		VersionEntity androidVersion = this.versionDao.getVersionLast(VersionEntity.VERSION_system_android);
		Map<String,Object> map = new HashMap<String,Object>();
		if(androidVersion != null){
			map.put("url", androidVersion.getFileUrl());
		}
		return map;
	}
	
   
	/**
	 * 是否有重复版本号
	 * @param system
	 * @return
	 */
	public int isHasSameVersion(String system,String versionNo,String oid) {
		return this.versionDao.isHasSameVersion(system,versionNo,oid);
	}

	// 获取最新版本信息
	public VersionResp getLastVersion(String version, String system) {
		VersionEntity en = this.versionDao.getMaxVersion(system ,version);
		
		if (en == null){
			en = this.versionDao.getMaxVersion(VersionEntity.VERSION_system_increment ,version);
		}
		VersionResp resp= new VersionResp();
		if (en != null){
			resp.setNew(true);
			resp.setVersion(en.getVersionNo());
			if (en.getUpgradeType().equals(VersionEntity.VERSION_upgradeType_increment)){
				resp.setWholePackage(false);
			}else{
				resp.setWholePackage(true);
			}
			resp.setCheckInterval(en.getCheckInterval());
			resp.setFileName(en.getFileName());
			if (en.getCompulsory() == null || en.getCompulsory() == 0 ){
				resp.setCompulsory(false);
			}else{
				resp.setCompulsory(true);
			}
			resp.setDescription(en.getDescription());
			resp.setDownLoadUrl(en.getFileUrl());
		}else{
			resp.setNew(false);
		}
		return resp;
	}

}
