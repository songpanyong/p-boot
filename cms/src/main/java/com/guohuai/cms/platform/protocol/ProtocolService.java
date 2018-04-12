package com.guohuai.cms.platform.protocol;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.util.StringUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.protocol.ProtocolInfoRep.ProtocolInfoRepBuilder;
import com.guohuai.cms.platform.protocol.ProtocolQueryRep.ProtocolQueryRepBuilder;
import com.guohuai.cms.platform.protocol.type.ProtocolTypeEntity;
import com.guohuai.cms.platform.protocol.type.ProtocolTypeService;

@Service
@Transactional
public class ProtocolService {

	@Autowired
	private ProtocolDao protocolDao;
	@Autowired
	private ProtocolTypeService protocolTypeService;
	
	public ProtocolEntity findByOid(String oid) {
		ProtocolEntity protocol = this.protocolDao.findOne(oid);
		if (null == protocol) {
			// 此协议不存在(CODE:90000)
			throw MoneyException.getException(90000);
		}
		return protocol;
	}
	
	public ProtocolEntity findByTypeId(String typeId) {
		ProtocolEntity protocol = this.protocolDao.findByTypeId(typeId);
		if (null == protocol) {
			// 此协议不存在(CODE:90000)
			throw MoneyException.getException(90000);
		}
		return protocol;
	}
	
	/**
	 * 判断协议类型是否有协议记录
	 * @param typeId
	 * @return
	 */
	public boolean checkProtocol(String typeId) {
		ProtocolEntity protocol = this.protocolDao.findByTypeId(typeId);
		if (null != protocol) {
			// 此协议类型已有对应的协议记录(CODE:90001)
			throw MoneyException.getException(90001);
		}
		return false;
	}
	
	/**
	 * 协议列表查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PagesRep<ProtocolQueryRep> protocolQuery(Specification<ProtocolEntity> spec, Pageable pageable) {		
		
		Page<ProtocolEntity> protocols = this.protocolDao.findAll(spec, pageable);
		PagesRep<ProtocolQueryRep> pagesRep = new PagesRep<ProtocolQueryRep>();

		for (ProtocolEntity en : protocols) {
			ProtocolQueryRep rep = new ProtocolQueryRepBuilder()
					.oid(en.getOid())
					.typeId(en.getProtocolType().getId())
					.content(en.getContent())
					.createTime(en.getCreateTime())
					.updateTime(en.getUpdateTime())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(protocols.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增/编辑协议
	 * @param req
	 */
	public BaseRep addProtocol(ProtocolAddReq req) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		
		ProtocolEntity protocol;
		if (null != req && !"".equals(req.getOid())) {
			protocol = this.findByOid(req.getOid());
		} else {
			protocol = new ProtocolEntity();
			protocol.setCreateTime(now);
			if (!this.checkProtocol(req.getTypeId())) {
				ProtocolTypeEntity protocolType = this.protocolTypeService.findById(req.getTypeId());
				protocol.setProtocolType(protocolType);
			}
		}
		protocol.setContent(req.getContent());
		protocol.setUpdateTime(now);
		this.protocolDao.save(protocol);
		
		return rep;
	}
	
	/**
	 * 删除协议
	 * @param protocolOid
	 * @return
	 */
	public BaseRep delProtocol(String protocolOid) {
		BaseRep rep = new BaseRep();
		ProtocolEntity protocol = this.findByOid(protocolOid);
		this.protocolDao.delete(protocol);
		return rep;
	}
	
	/**
	 * 协议详情-APP
	 * @param typeId
	 * @return
	 */
	public ProtocolInfoRep getProtocolInfo(String typeId) {
		
		ProtocolEntity protocol = this.protocolDao.findByTypeId(typeId);
		
		ProtocolInfoRep rep = new ProtocolInfoRepBuilder().build();
		
		if (null == protocol) {
			rep.setContent(StringUtil.EMPTY);
		} else {
			rep.setContent(protocol.getContent());
		}
		return rep;
	}
}
