package com.guohuai.cms.platform.images;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.images.ImagesQueryRep.ImagesQueryRepBuilder;

@Service
@Transactional
public class ImagesService {
	
	@Autowired
	private ImagesDao imagesDao;
	
	@Transactional
	public PagesRep<ImagesQueryRep> imagesQuery(Specification<ImagesEntity> spec, Pageable pageable) {		
		Page<ImagesEntity> images = this.imagesDao.findAll(spec, pageable);
		PagesRep<ImagesQueryRep> pagesRep = new PagesRep<ImagesQueryRep>();

		for (ImagesEntity en : images) {
			ImagesQueryRep rep = new ImagesQueryRepBuilder()
					.oid(en.getOid())
					.imgName(en.getImgName())
					.imgUrl(en.getImgUrl())
					.createTime(en.getCreateTime())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(images.getTotalElements());	
		return pagesRep;
	}
	
	
	/**
	 * 新增图片
	 * @param req
	 * @return
	 */
	public BaseRep addImages(ImagesAddReq req){
		BaseRep rep = new BaseRep();
		
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		ImagesEntity images = new ImagesEntity();
		images.setImgName(req.getImgName());
		images.setImgUrl(req.getImgUrl());
		images.setCreateTime(now);
		
		this.imagesDao.save(images);
		return rep;
	}
	
	/**
	 * 删除图片
	 * @param oid
	 * @return
	 */
	public BaseRep delImages(String oid){
		BaseRep rep = new BaseRep();
	
		this.imagesDao.delete(oid);
		return rep;
	}
	
}
