package com.guohuai.cms.platform.information;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.guohuai.cms.component.util.DateUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.channel.ChannelEntity;
import com.guohuai.cms.platform.channel.ChannelService;
import com.guohuai.cms.platform.information.InformationQueryRep.InformationQueryRepBuilder;
import com.guohuai.cms.platform.information.type.InformationTypeDao;
import com.guohuai.cms.platform.information.type.InformationTypeEntity;

@Service
@Transactional
public class InformationService {
	
	@Autowired
	private InformationDao informationDao;
	@Autowired
	private InformationTypeDao informationTypeDao;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private AdminUtil adminUtil;
	
	/**
	 * 理财资讯列表信息查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PagesRep<InformationQueryRep> informationQuery(Specification<InformationEntity> spec, Pageable pageable) {
		Page<InformationEntity> products = this.informationDao.findAll(spec, pageable);
		PagesRep<InformationQueryRep> pagesRep = new PagesRep<InformationQueryRep>();
		
		for (InformationEntity pe : products) {
			InformationQueryRep rep = new InformationQueryRepBuilder()
					.oid(pe.getOid())
					.channelOid(pe.getChannel().getOid())
					.title(pe.getTitle())
					.type(pe.getType())
					.status(pe.getStatus())
					.createTime(pe.getCreateTime())
					.publisher(this.adminUtil.getAdminName(pe.getPublisher()))
					.editTime(pe.getEditTime())
					.url(pe.getUrl())
					.publishTime(pe.getPublishTime())
					.isHome(pe.getIsHome())
					.content(pe.getContent())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(products.getTotalElements());	
		return pagesRep;
	}

	/**
	 * 新增/修改 资讯信息
	 * @param req
	 * @param operator
	 * @return
	 */
	public InformationEntity addInformation(InformationAddReq req, String operator) {
		ChannelEntity channel = this.channelService.findByOid(req.getChannelOid());
		
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		InformationEntity information = null;
		if(req.getOid() != null && !"".equals(req.getOid())){
			information = this.getOne(req.getOid());
		}else{
			information = new InformationEntity();
		}
		information.setChannel(channel);
		information.setCreateTime(now);
		information.setEditor(operator);
		information.setTitle(req.getTitle());
		information.setType(req.getType());
		information.setSummary(req.getSummary());
		information.setContent(req.getContent());
		information.setThumbnailUrl(req.getThumbnailUrl());
		information.setOrigin(req.getOrigin());
		information.setEditTime(now);
		information.setStatus(InformationEntity.INFORMATION_status_pending);
		information.setUrl(req.getUrl());
		information.setIsHome(0);
		information=this.informationDao.save(information);
		return information;
		
	}
	
	/**
	 * 获取资讯实体
	 * @param oid
	 * @return
	 */
	public InformationEntity getOne(String oid){
		InformationEntity en = this.informationDao.findOne(oid);
		if(en==null){
			//error.define[50000]=此资讯不存在(CODE:50000)
			throw MoneyException.getException(50000);
		}
		return en;
	}
	
	/**
	 * 获取资讯详情
	 * @param oid
	 * @return
	 */
	public InformationAppRep getOneInformation(String oid){
		InformationAppRep rep = new InformationAppRep();
		InformationEntity info = this.getOne(oid);
		rep.setOid(info.getOid());
		rep.setType(info.getType());
		rep.setTitle(info.getTitle());
		rep.setSummary(info.getSummary());
		rep.setUrl(info.getUrl());
		rep.setThumbnailUrl(info.getThumbnailUrl());
		rep.setContent(info.getContent());
		rep.setPublishTime(DateUtil.format(info.getPublishTime(), DateUtil.defaultDatePattern));
		return rep;
	}
	
	/**
	 * 资讯详情全字段
	 * @param oid
	 * @return
	 */
	public InformationQueryRep getInformation(String oid){
		InformationEntity info = this.getOne(oid);
	
		InformationQueryRep rep = new InformationQueryRepBuilder()
				.oid(info.getOid())
				.channelOid(info.getChannel().getOid())
				.title(info.getTitle())
				.type(info.getType())
				.status(info.getStatus())
				.createTime(info.getCreateTime())
				.publisher(this.adminUtil.getAdminName(info.getPublisher()))
				.editTime(info.getEditTime())
				.url(info.getUrl())
				.publishTime(info.getPublishTime())
				.isHome(info.getIsHome())
				.content(info.getContent())
				.summary(info.getSummary())
				.thumbnailUrl(info.getThumbnailUrl())
				.editor(this.adminUtil.getAdminName(info.getEditor()))
				.review(this.adminUtil.getAdminName(info.getReview()))
				.origin(info.getOrigin())
				.reviewRemark(info.getReviewRemark())
				.reviewTime(info.getReviewTime())
				.build();
		return rep;
	}
	
	/**
	 * 删除资讯信息
	 * @param oid
	 * @return
	 */
	public BaseRep delInformation(String oid) {
		BaseRep rep = new BaseRep();
		this.informationDao.delete(oid);
		return rep;
	}
	/**
	 * 资讯审核
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep dealReview(InformatinReviewRep req, String operator) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		InformationEntity information =  this.getOne(req.getOid());
		information.setReview(operator);
		information.setReviewTime(now);
		if(req.getApprResult().equals(InformationEntity.INFORMATION_reviewStatus_pass)){
			information.setStatus(InformationEntity.INFORMATION_status_publishing);
		}else if(req.getApprResult().equals(InformationEntity.INFORMATION_reviewStatus_refused)){
			information.setStatus(InformationEntity.INFORMATION_status_refused);
		}
		information.setReviewRemark(req.getRemark());
		this.informationDao.save(information);
		return rep;
	}
	
	/**
	 * 上架资讯信息
	 * @param oid
	 * @param operator
	 * @return
	 */
	public BaseRep publishInformation(String oid,String operator) {
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		InformationEntity information =  this.getOne(oid);
		InformationTypeEntity infoType = this.informationTypeDao.findByName(information.getType());
		if(InformationTypeEntity.INFORMATIONTYPE_status_off.equals(infoType.getStatus()+"")){
			//error.define[50003]=此条资讯的类型已关闭(CODE:50003)
			throw MoneyException.getException(50003);
		}
		information.setStatus(InformationEntity.INFORMATION_status_published);
		information.setPublisher(operator);
		information.setPublishTime(now);
		this.informationDao.save(information);
		return rep;
	}
	
	/**
	 * 是否首页推荐资讯
	 * @param oid
	 * @return
	 */
	public BaseRep isHome(String oid) {
		BaseRep rep = new BaseRep();
		InformationEntity type = this.informationDao.findOne(oid);
		if(type.getIsHome() == 0){
			type.setIsHome(1);		
		}else  if(type.getIsHome() == 1){
			type.setIsHome(0);			
		}
		this.informationDao.save(type);
		return rep;
	}
	
	/**
	 * 根据渠道获取推荐资讯信息
	 * @param channelOid
	 * @return
	 */
	public List<InformationAppRep> getHomeInformation(String channelOid) {
		List<InformationEntity> entitys = this.informationDao.getHomeInformation(channelOid);
		List<InformationAppRep> info = new ArrayList<InformationAppRep>();
		if(entitys!=null&&entitys.size() > 0){
			for(int i=0; i<entitys.size(); i++){
				InformationEntity entity = entitys.get(i);
				InformationAppRep rep = new InformationAppRep();
				rep.setOid(entity.getOid());
				rep.setTitle(entity.getTitle());
				rep.setType(entity.getType());
				rep.setSummary(entity.getSummary());
				rep.setThumbnailUrl(entity.getThumbnailUrl());
				rep.setUrl(entity.getUrl());
				rep.setContent(entity.getContent());
				rep.setPublishTime(DateUtil.format(entity.getPublishTime(), DateUtil.defaultDatePattern));
				rep.setPublishTimeFull(DateUtil.format(entity.getPublishTime(), DateUtil.fullDatePattern));
				info.add(rep);
			}
		}
		return info;
	}
	
	/**
	 * 获取资讯列表--接口
	 * @return
	 */
	public PagesRep<InformationAppRep> getInformationList(Specification<InformationEntity> spec, Pageable pageable) {
		Page<InformationEntity> products = this.informationDao.findAll(spec, pageable);
		PagesRep<InformationAppRep> pagesRep = new PagesRep<InformationAppRep>();
		if(products != null && products.getSize() > 0){
			for (InformationEntity pe : products) {
				InformationAppRep rep = new InformationAppRep();
				rep.setOid(pe.getOid());
				rep.setTitle(pe.getTitle());
				rep.setType(pe.getType());
				rep.setSummary(pe.getSummary());
				rep.setThumbnailUrl(pe.getThumbnailUrl());
				rep.setUrl(pe.getUrl());
				rep.setContent(pe.getContent());
				rep.setPublishTime(DateUtil.format(pe.getPublishTime(), DateUtil.defaultDatePattern));
				pagesRep.add(rep);
			}
			pagesRep.setTotal(products.getTotalElements());	
		}
		return pagesRep;
	}
	
	
	/**
	 * 获取资讯信息--接口
	 * @param type
	 * @return
	 */
	public PagesRep<InformationAppRep> findInformationtoApp(Specification<InformationEntity> spec, Pageable pageable) {
		Page<InformationEntity> products = this.informationDao.findAll(spec, pageable);
		PagesRep<InformationAppRep> resp = new PagesRep<InformationAppRep>();
		List<InformationAppRep> info = new ArrayList<InformationAppRep>();
		if(products != null && products.getSize() > 0){
			for (InformationEntity pe : products) {
				InformationAppRep rep = new InformationAppRep();
				rep.setOid(pe.getOid());
				rep.setTitle(pe.getTitle());
				rep.setType(pe.getType());
				rep.setSummary(pe.getSummary());
				rep.setThumbnailUrl(pe.getThumbnailUrl());
				rep.setUrl(pe.getUrl());
				rep.setContent(pe.getContent());
				rep.setPublishTime(DateUtil.format(pe.getPublishTime(), DateUtil.defaultDatePattern));
				info.add(rep);
			}
		}
		
		resp.setTotal(products.getTotalElements());
		resp.setRows(info);
		
		return resp;
	}
	
	public PagesRep<InformationAppRep> getInformationsAlltoApp(Specification<InformationEntity> spec, Pageable pageable) {
		Page<InformationEntity> list = this.informationDao.findAll(spec, pageable);
		
		PagesRep<InformationAppRep> pagesRep = new PagesRep<InformationAppRep>();
		
		if(list != null && list.getSize() > 0){
			for (InformationEntity pe : list) {
				InformationAppRep rep = new InformationAppRep();
				rep.setOid(pe.getOid());
				rep.setTitle(pe.getTitle());
				rep.setType(pe.getType());
				rep.setSummary(pe.getSummary());
				rep.setThumbnailUrl(pe.getThumbnailUrl());
				rep.setUrl(pe.getUrl());
				rep.setContent(pe.getContent());
				rep.setPublishTime(DateUtil.format(pe.getPublishTime(), DateUtil.defaultDatePattern));
				pagesRep.add(rep);
			}
			pagesRep.setTotal(list.getTotalElements());
			pagesRep.setPages(list.getTotalPages());
		}
		return pagesRep;
	}
	
	/**
	 * 下架资讯信息
	 * @param oid
	 * @return
	 */
	public BaseRep informationOff(String oid) {
		BaseRep rep = new BaseRep();
		InformationEntity information =  this.getOne(oid);
		information.setStatus(InformationEntity.INFORMATION_status_off);
		this.informationDao.save(information);
		return rep;
	}
    /**
     * 资讯类型下是否包含有资讯信息
     * @param name
     * @return
     */
	public int isHasInfo(String type) {
		return informationDao.isHasInfo(type.trim());
	}
    /**
     * 资讯类型是否有相同
     * @param name
     * @return
     */
	public int infoTypeNameIsSame(String name) {
		return this.informationTypeDao.infoTypeNameIsSame(name);
	}

}
