package com.guohuai.cms.platform.share;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;

@Service
@Transactional
public class ShareConfigService {

	@Autowired
	private ShareConfigDao shareConfigDao;

	public BaseResp add(ShareConfigAddReq req) {
		
		ShareConfigEntity repeateEn = this.findByPageCode(req.getPageCode());
		if (null != repeateEn) {
			throw new GHException("此分享配置已经存在");
		}
		
		ShareConfigEntity en = new ShareConfigEntity();
		en.setPageCode(req.getPageCode());
		en.setPageType(req.getPageType());
		en.setPageName(req.getPageName());
		en.setShareUrl(req.getShareUrl());
		en.setShareTitle(req.getShareTitle());
		en.setShareWords(req.getShareWords());
		this.shareConfigDao.save(en);
		return new BaseResp();
	}
	
	
	public ShareConfigEntity findByPageCode(String pageCode) {
		return this.shareConfigDao.findByPageCode(pageCode);
	}


	public BaseResp modify(ShareConfigModifyReq req) {
		
		ShareConfigEntity en = this.shareConfigDao.findOne(req.getShareOid());
		en.setShareUrl(req.getShareUrl());
		en.setShareTitle(req.getShareTitle());
		en.setShareWords(req.getShareWords());
		this.shareConfigDao.save(en);
		return new BaseResp();
	}


	public BaseResp delete(String shareOid) {
		this.shareConfigDao.delete(shareOid);
	
		return new BaseResp();
	}


	public ShareConfigSingleRep getShareConfig(String pageCode) {
		ShareConfigEntity en = this.findByPageCode(pageCode);
		if (en == null) {
			throw new GHException("分享配置不存在");
		}
		ShareConfigSingleRep irep = new ShareConfigSingleRep();
		irep.setShareTitle(en.getShareTitle());
		irep.setShareUrl(en.getShareUrl());
		irep.setShareWords(en.getShareWords());
		return irep;
	}


	public PageResp<ShareConfigQueryRep> query(Specification<ShareConfigEntity> spec, Pageable pageable) {
		
			
			Page<ShareConfigEntity> page = this.shareConfigDao.findAll(spec, pageable);
			PageResp<ShareConfigQueryRep> pagesRep = new PageResp<ShareConfigQueryRep>();

			for (ShareConfigEntity en : page.getContent()) {
				ShareConfigQueryRep irep = new ShareConfigQueryRep();
				irep.setShareOid(en.getOid());
				irep.setPageCode(en.getPageCode());
				irep.setPageName(en.getPageName());
				irep.setPageType(en.getPageType());
				irep.setShareTitle(en.getShareTitle());
				irep.setShareUrl(en.getShareUrl());
				irep.setShareWords(en.getShareWords());
				pagesRep.getRows().add(irep);
			}
			pagesRep.setTotal(page.getTotalElements());	
		
		return pagesRep;
	}
}

