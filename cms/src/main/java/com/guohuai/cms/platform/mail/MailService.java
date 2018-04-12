package com.guohuai.cms.platform.mail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.Clock;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.mail.MailContEntity;
import com.guohuai.cms.component.mail.MailUtil;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.RedisUtil;
import com.guohuai.cms.component.util.StrRedisUtil;
import com.guohuai.cms.platform.mail.MailBTResp.MailBTRespBuilder;
import com.guohuai.cms.platform.mail.MailCTResp.MailCTRespBuilder;
import com.guohuai.cms.platform.mail.MailNoNumCTResp.MailNoNumCTRespBuilder;
import com.guohuai.cms.platform.mail.api.InvestorLabelResp;
import com.guohuai.cms.platform.mail.api.UserCenterApi;
import com.guohuai.cms.platform.mail.api.UserInfoRep;
import com.guohuai.cms.platform.mail.api.UserLabelResp;
import com.guohuai.cms.platform.push.PushEntity;
import com.guohuai.cms.platform.push.PushService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MailService {
	
	@Autowired
	private MailDao mailDao;
	@Autowired
	private AdminUtil AdminUtil;
	@Autowired
	private UserCenterApi userCenterApi;
	@Autowired
	private PushService pushService;
	@Autowired
	private RedisTemplate<String, String> redis;
	
	/**
	 * 新增
	 * @param en
	 * @return
	 */
	@Transactional
	public MailEntity saveEntity(MailEntity en){
		en.setCreateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
		return this.updateEntity(en);
	}
	
	/**
	 * 修改
	 * @param en
	 * @return
	 */
	@Transactional
	public MailEntity updateEntity(MailEntity en){
		en.setUpdateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
		return this.mailDao.save(en);
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public MailEntity findByOid(String oid){
		MailEntity entity = this.mailDao.findOne(oid);
		if(null == entity){
			// 站内信不存在！
			throw MoneyException.getException(12000);
		}
		return entity;
	}
	
//	/**
//	 * 后台详细页列表
//	 * @param pageable
//	 * @param userOid
//	 * @return
//	 */
//	public PageResp<MailBTResp> queryBTPage4Detail(Pageable pageable, final String userOid) {
//		if (userOid == null){
//			return null;
//		}
//		
//		Specification<MailEntity> sa = new Specification<MailEntity>() {
//			@Override
//			public Predicate toPredicate(Root<MailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate b = cb.equal(root.get("userOid").as(String.class), userOid);
//				Predicate c = cb.equal(root.get("mailType").as(String.class), "site");
//				return cb.or(b, c);
//			}
//		};
//		
//		Page<MailEntity> enchs = this.mailDao.findAll(sa, pageable);
//		PageResp<MailBTResp> pageResp = new PageResp<>();		
//		
//		List<MailBTResp> list = new ArrayList<MailBTResp>();
//		for (MailEntity en : enchs) {
//			String requester = AdminUtil.getAdminName(en.getRequester());
//			String approver = AdminUtil.getAdminName(en.getApprover());
//
//			MailBTResp rep = new MailBTRespBuilder().oid(en.getOid()).userOid(en.getUserOid())
//					.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent())
//					.isRead(en.getIsRead()).status(en.getStatus()).requester(requester).approver(approver).remark(en.getRemark())
//					.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
//			list.add(rep);
//		}
//		
//		pageResp.setTotal(enchs.getTotalElements());
//		pageResp.setRows(list);
//		return pageResp;
//	}
	
	/**
	 * 后台分页查询
	 * @return
	 */
	public PageResp<MailBTResp> queryBTPage4List(Specification<MailEntity> mailspec, Pageable pageable) {
		// 判断用户是否存在分情况组装参数
		Page<MailEntity> enchs = this.mailDao.findAll(mailspec, pageable);
		
		PageResp<MailBTResp> pageResp = new PageResp<>();		
		
		List<MailBTResp> list = new ArrayList<MailBTResp>();
		for (MailEntity en : enchs) {
			String requester = AdminUtil.getAdminName(en.getRequester());
 			String approver = AdminUtil.getAdminName(en.getApprover());
			String labelCodeName = null;
			if (en.getLabelCode() != null && !en.getLabelCode().isEmpty()){
				InvestorLabelResp label = userCenterApi.getInvestorLabel(en.getLabelCode()); 
					labelCodeName = label.getLabelName();
			}
			
			MailBTResp rep = new MailBTRespBuilder().oid(en.getOid()).userOid(en.getUserOid())
					.phone(en.getPhone()).requester(requester).approver(approver).approveRemark(en.getApproveRemark()).remark(en.getRemark())
					.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle())
					.mesContent(en.getMesContent()).isRead(en.getIsRead()).status(en.getStatus())
					.labelCode(en.getLabelCode()).labelCodeName(labelCodeName)
					.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
			list.add(rep);
		}
		
		pageResp.setTotal(enchs.getTotalElements());
		pageResp.setRows(list);
		return pageResp;
	}
	
	/**
	 * 手动生成站内信
	 * @param isPerson
	 * @param oid
	 * @param type
	 * @param title
	 * @param mesContent
	 */
	public void createMail(String mailType, String phone, String userOid, String mesType, String title, String requester, String mesContent, String remark,String labelCode){
		createMail(mailType, phone, userOid, mesType, title, requester, mesContent, MailEntity.MAIL_status_toApprove, remark, labelCode);
	}
	
	/**
	 * 系统生成站内信
	 * @param mailType
	 * @param phone
	 * @param userOid
	 * @param mesType
	 * @param title
	 * @param mesContent
	 */
	private void createMail(String mailType, String phone, String userOid, String mesType, String title, String mesContent,String labelCode) {
		createMail(mailType, phone, userOid, mesType, title, "", mesContent, MailEntity.MAIL_status_pass, "", "");
	}
	
	/**
	 * 生成站内信基础
	 * @param isPerson
	 * @param oid
	 * @param type
	 * @param title
	 * @param mesContent
	 * @param status
	 */
	public void createMail(String mailType, String phone, String userOid, String mesType, String title, String requester, String mesContent, String status, String remark,String labelCode){
		if (mesContent != null && !mesContent.isEmpty()){
			MailEntity mail = new MailEntity();
			mail.setMailType(mailType);
			mail.setPhone(phone);
			mail.setUserOid(userOid);
			mail.setMesTitle(title);	
			mail.setMesContent(mesContent);
			mail.setMesType(mesType);
			mail.setIsRead(MailEntity.MAIL_isRead_no);
			mail.setStatus(status);
			mail.setRequester(requester);
//			mail.setReadUserNote("");
			mail.setRemark(remark);
			mail.setLabelCode(labelCode);
			this.saveEntity(mail);
		}
	}
	
	/**
	 * 前端获取站内信列表
	 * @param pageable
	 * @param userOid
	 * @return
	 */
	public PageResp<MailCTResp> queryCTPage(int page, int rows, final String isRead, final String userOid) {
		Specification<MailEntity> sa = new Specification<MailEntity>() {
			@Override
			public Predicate toPredicate(Root<MailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate b = cb.equal(root.get("status").as(String.class), "pass");
				
				Predicate c = cb.equal(root.get("userOid").as(String.class), userOid);
				Predicate d = cb.equal(root.get("mailType").as(String.class), MailEntity.MAIL_mailType_all);
				Predicate g = cb.equal(root.get("isRead").as(String.class), isRead);
				
				Predicate e = null;
				if (isRead != null && (isRead.equals(MailEntity.MAIL_isRead_is) || isRead.equals(MailEntity.MAIL_isRead_no))){
					e = cb.and(b,c,g);
				}else{
					e = cb.and(b,c);
				}
				Predicate f = cb.and(b,d);
				query.where(cb.or(e,f));
				query.orderBy(cb.desc(root.get("createTime")));
				
				return query.getRestriction();
			}
		};
		
		List<MailEntity> mails = this.mailDao.findAll(sa);
		
		List<MailCTResp> list = new ArrayList<MailCTResp>();
		for (MailEntity en : mails) {
			if (en.getMailType().equals(MailEntity.MAIL_mailType_all)){
				if (isRead != null && (isRead.equals(MailEntity.MAIL_isRead_is) || isRead.equals(MailEntity.MAIL_isRead_no))){
					if (isRead.equals(MailEntity.MAIL_isRead_is)){
						String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//						String hadReadStr= StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
						if (hadReadStr != null && !hadReadStr.isEmpty()){
							@SuppressWarnings("unchecked")
							Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
							if (oidSet.contains(en.getOid())){
								MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
										.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(isRead)
										.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
								list.add(rep);
							}
						}
					}else{
						String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//						String hadReadStr= StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
						if (hadReadStr == null || hadReadStr.isEmpty()){
							MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
									.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(isRead)
									.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
							list.add(rep);
						}else{
							@SuppressWarnings("unchecked")
							Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
							if (!oidSet.contains(en.getOid())){
								MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
										.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(isRead)
										.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
								list.add(rep);
							}
						}
					}
				}else{
					String read = MailEntity.MAIL_isRead_is;
					String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//					String hadReadStr= StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
					if (hadReadStr == null || hadReadStr.isEmpty()){
						read = MailEntity.MAIL_isRead_no;
					}else{
						@SuppressWarnings("unchecked")
						Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
						if (!oidSet.contains(en.getOid())){
							read = MailEntity.MAIL_isRead_no;
						}
					}
					MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
							.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(read)
							.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
					list.add(rep);
				}
			}else{
				MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
						.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(en.getIsRead())
						.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
				list.add(rep);
			}
		}
		
		List<MailCTResp> repList = new ArrayList<MailCTResp>();
		
		int start = (page - 1) * rows;
		int max = page * rows;
		int last = max > list.size() ? list.size() : max;
		
		for (int i = start; i < last; i++){
			repList.add(list.get(i));
		}
		
		PageResp<MailCTResp> pageResp = new PageResp<>();
		pageResp.setTotal(list.size());
		pageResp.setRows(repList);
		
		return pageResp;
	}
	
	/**
	 * 前端获取站内信列表
	 * @param pageable
	 * @param userOid
	 * @return
	 */
	public PageResp<MailCTResp> queryCTPage(Pageable pageable, final String userOid) {
		
		Specification<MailEntity> sa = new Specification<MailEntity>() {
			@Override
			public Predicate toPredicate(Root<MailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate b = cb.equal(root.get("status").as(String.class), "pass");
				
				Predicate c = cb.equal(root.get("userOid").as(String.class), userOid);
				Predicate d = cb.equal(root.get("mailType").as(String.class), MailEntity.MAIL_mailType_all);
				
				Predicate e = cb.and(b,c);
				Predicate f = cb.and(b,d);
				
				return cb.or(e,f);
			}
		};
		
		Page<MailEntity> enchs = this.mailDao.findAll(sa, pageable);
		PageResp<MailCTResp> pageResp = new PageResp<>();		
		
		List<MailCTResp> list = new ArrayList<MailCTResp>();
		for (MailEntity en : enchs) {
			if (en.getMailType().equals(MailEntity.MAIL_mailType_all)){
				String isRead = MailEntity.MAIL_isRead_is;
				String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//				String hadReadStr= StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
				if (hadReadStr == null || hadReadStr.isEmpty()){
					isRead = MailEntity.MAIL_isRead_no;
				}else{
					@SuppressWarnings("unchecked")
					Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
					if (!oidSet.contains(en.getOid())){
						isRead = MailEntity.MAIL_isRead_no;
					}
				}
				MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
						.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(isRead)
						.updateTime(en.getUpdateTime()).build();
				list.add(rep);
			}else{
				MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
						.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent()).isRead(en.getIsRead())
						.updateTime(en.getUpdateTime()).build();
				list.add(rep);
			}
		}
		
		pageResp.setTotal(enchs.getTotalElements());
		pageResp.setRows(list);
		return pageResp;
	}
	
	/**
	 * 获取该站点该用户未读站内信
	 * @param siteOid
	 * @param userOid
	 * @return
	 */
	public List<MailEntity> queryNotReadMail2One(String siteOid, final String userOid){
		Specification<MailEntity> sa = new Specification<MailEntity>() {
			@Override
			public Predicate toPredicate(Root<MailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate b = cb.equal(root.get("status").as(String.class), "pass");
				
				Predicate d = cb.equal(root.get("isRead").as(String.class), "no");
				
				Predicate e = cb.notLike(root.get("readUserNote").as(String.class), "%"+userOid+"%");
				Predicate f = cb.equal(root.get("mailType").as(String.class), "site");
				
				Predicate g = cb.and(b,d);
				
				Predicate h = cb.and(b,e,f);
				
				return cb.or(g,h);
			}
		};
		
		List<MailEntity> enchs = this.mailDao.findAll(sa);
		
		return enchs;
	}

	/**
	 * 前端获取站内信内容
	 * @param mailOid
	 * @param loginAdmin
	 * @return
	 */
	public MailCTResp getCTDetail(String mailOid, String userOid) {
		MailEntity en = this.findByOid(mailOid);
		
		if (en.getUserOid() != null && !en.getUserOid().isEmpty() && !en.getUserOid().equals(userOid)){
			// 没有权限查看该站内信！
			throw MoneyException.getException(12001);
		}
		
		MailCTResp rep = new MailCTRespBuilder().oid(en.getOid())
				.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle()).mesContent(en.getMesContent())
				.updateTime(en.getUpdateTime()).build();
		
		en.setIsRead(MailEntity.MAIL_isRead_is);	
		
		if(en.getMesType().equals(MailEntity.MAIL_mailType_all)){
			String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//			String hadReadStr = StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
			
			if (hadReadStr == null || hadReadStr.isEmpty()){
				Set<String> oidSet = new HashSet<String>();
				oidSet.add(en.getOid());
				RedisUtil.hset(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid, JSONObject.toJSONString(oidSet));
//				StrRedisUtil.set(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid, oidSet);
			}else{
				@SuppressWarnings("unchecked")
				Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
				oidSet.add(en.getOid());
				RedisUtil.hset(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid, JSONObject.toJSONString(oidSet));
//				StrRedisUtil.set(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid, oidSet);
			}
		}
			
		this.updateEntity(en);
		
		return rep;
	}

	/**
	 * 后台获取站内信内容
	 * @param mailOid
	 * @return
	 */
	public MailBTResp getBTDetail(String mailOid) {
		MailEntity en = this.findByOid(mailOid);
		String requester = AdminUtil.getAdminName(en.getRequester());
		String approver = AdminUtil.getAdminName(en.getApprover());
		
		MailBTResp rep = new MailBTRespBuilder().oid(en.getOid()).userOid(en.getUserOid())
				.phone(en.getPhone()).requester(requester).approver(approver).remark(en.getRemark())
				.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle())
				.mesContent(en.getMesContent()).isRead(en.getIsRead()).status(en.getStatus()).labelCode(en.getLabelCode())
				.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
		return rep;
	}

	/**
	 * 审核
	 * @param approveResult
	 * @param aoid
	 * @param approveNote
	 * @param loginAdmin
	 */
	public void approve(String approveResult, String aoid, String approveNote, String loginAdmin) {
		MailEntity mail = this.findByOid(aoid);
		if (approveResult.equals(MailEntity.MAIL_status_pass)){
			mail.setApprover(loginAdmin);
			mail.setApproveRemark(approveNote);
			mail.setStatus(MailEntity.MAIL_status_pass);
			this.updateEntity(mail);
			
			createPush(mail);
		}else if (approveResult.equals(MailEntity.MAIL_status_refused)){
			mail.setApprover(loginAdmin);
			mail.setApproveRemark(approveNote);
			mail.setStatus(MailEntity.MAIL_status_refused);
			this.updateEntity(mail);
		}else{
			//审核有误！
			throw MoneyException.getException(12004);
		}
	}

	// 生成推送
	private void createPush(MailEntity mail) {
		String cont = mail.getMesContent();
		
		if (cont != null && cont.length() > 200){
			cont = cont.substring(0, 200);
		}
		pushService.groupAndSave(mail.getMesTitle(), PushEntity.PUSH_type_mail, PushEntity.PUSH_type_mail, mail.getRequester(), mail.getMesContent(), mail.getMailType(), mail.getUserOid(), mail.getPhone(),mail.getLabelCode());
	}

	/**
	 * 后台添加修改站内信
	 * @param req
	 * @param loginAdmin
	 */
	public void addBT(MailBTAddReq req, String loginAdmin) {
		if (req.getOid() != null && !req.getOid().isEmpty()){
			MailEntity mailEntity = this.findByOid(req.getOid());
			
			if (req.getMailType().equals(MailEntity.MAIL_mailType_person)){
				if (req.getPhone() == null || req.getPhone().isEmpty()){
					// 未选择收信人！(CODE:12002)
					throw MoneyException.getException(12002);
				}
				
				UserInfoRep user = userCenterApi.isregist(req.getPhone());
				if (user == null || !user.isRegist() || user.getInvestorOid() == null || user.getInvestorOid().isEmpty()){
					// 会员不存在！(CODE:13000)
					throw MoneyException.getException(13000);
				}
				
				updateMail(mailEntity, MailEntity.MAIL_mailType_person, user.getInvestorOid(), req.getPhone(),
						MailEntity.MAIL_mesType_person, req.getMesTitle(), req.getMesContent(),
						loginAdmin, req.getRemark(), req.getLabelCode());
				
			}else if (req.getMailType().equals(MailEntity.MAIL_mailType_all)){
				updateMail(mailEntity, MailEntity.MAIL_mailType_all, null, null,
						MailEntity.MAIL_mesType_all, req.getMesTitle(), req.getMesContent(),
						loginAdmin, req.getRemark(), req.getLabelCode());
			}else if (req.getMailType().equals(MailEntity.MAIL_mailType_team)){
				updateMail(mailEntity, MailEntity.MAIL_mailType_team, null, null,
						MailEntity.MAIL_mesType_person, req.getMesTitle(), req.getMesContent(),
						loginAdmin, req.getRemark(), req.getLabelCode());
			}else{
				//站内信类型不存在！(CODE:12003)
				throw MoneyException.getException(12003);
			}
		}else{
			if (req.getMailType().equals(MailEntity.MAIL_mailType_person)){
				if (req.getPhone() == null || req.getPhone().isEmpty()){
					// 未选择收信人！(CODE:12002)
					throw MoneyException.getException(12002);
				}
				
				UserInfoRep user = userCenterApi.isregist(req.getPhone());
				if (user == null || !user.isRegist() || user.getInvestorOid() == null || user.getInvestorOid().isEmpty()){
					// 会员不存在！(CODE:13000)
					throw MoneyException.getException(13000);
				}
				
				createMail(MailEntity.MAIL_mailType_person, req.getPhone(), user.getInvestorOid(), MailEntity.MAIL_mesType_person, req.getMesTitle(), loginAdmin, req.getMesContent(), req.getRemark(), req.getLabelCode());
			}else if (req.getMailType().equals(MailEntity.MAIL_mailType_all)){
				createMail(MailEntity.MAIL_mailType_all, null, null, MailEntity.MAIL_mesType_all, req.getMesTitle(), loginAdmin, req.getMesContent(), req.getRemark(), req.getLabelCode());
			}else if (req.getMailType().equals(MailEntity.MAIL_mailType_team)){
				createMail(MailEntity.MAIL_mailType_team, null, null, MailEntity.MAIL_mesType_person, req.getMesTitle(), loginAdmin, req.getMesContent(), req.getRemark(), req.getLabelCode());
			}else{
				//站内信类型不存在！(CODE:12003)
				throw MoneyException.getException(12003);
			}
		}
	}

	private void updateMail(MailEntity mailEntity, String mailtype, String userOid, String userPhone, String mestype,
			String mesTitle, String mesContent, String loginAdmin, String remark,String labelCode) {
		mailEntity.setUserOid(userOid);
		mailEntity.setPhone(userPhone);
		mailEntity.setMailType(mailtype);
		mailEntity.setMesType(mestype);
		mailEntity.setMesTitle(mesTitle);
		mailEntity.setMesContent(mesContent);
		mailEntity.setStatus(MailEntity.MAIL_status_toApprove);
		mailEntity.setRequester(loginAdmin);
		mailEntity.setRemark(remark);
		mailEntity.setLabelCode(labelCode);
		
		this.updateEntity(mailEntity);
	}

	/**
	 * 系统后台添加站内信
	 * @param userOid
	 * @param mesType 信息类型
	 * @param mesParam 参数 ["aaa","bbb","aaa"]
	 * @return 
	 */
	public BaseResp sendMail(String userOid, String mesTempCode, String mesParam) {
		BaseResp resp = new BaseResp();
		try {
			log.info("后台请求站内信参数：用户oid："+userOid+",站内信模板code："+mesTempCode+",站内信参数："+mesParam);
			if (userOid == null || userOid.isEmpty()){
				// 会员id不能为空！(CODE:13002)
				throw MoneyException.getException(13002);
			}
			
			String phone = "";
			try {
				UserInfoRep user = userCenterApi.getLoginUserInfo(userOid);
				if (user == null || user.getPhoneNum() == null){
					// 会员不存在！(CODE:13000)
					throw MoneyException.getException(13000);
				}
				phone = user.getPhoneNum();
			} catch (Exception e) {
				e.printStackTrace();
				// 会员信息访问失败！(CODE:13001)
				throw MoneyException.getException(13001);
			}
			
			MailContEntity cont = MailUtil.mailContentsMap.get(mesTempCode);
			if (cont == null){
				// 站内信内容类型不存在！(CODE:12005)
				throw MoneyException.getException(12005);
			}
			
			String content = cont.getSmsContent();
			if (mesParam != null && !mesParam.isEmpty()){
				String[] param = JSON.parseObject(mesParam, String[].class);
				content = MailUtil.replaceComStrArr(cont.getSmsContent(), param);
			}
			
			log.info("后台请求站内信参数处理后：用户oid："+userOid+",手机："+phone+",站内信标题："+cont.getSmsTitle()+",站内信内容："+content);
			createMail(MailEntity.MAIL_mailType_person, phone, userOid, MailEntity.MAIL_mesType_system, cont.getSmsTitle(), content, null);
//			log.info("后台请求站内信：用户oid："+userOid+",手机："+phone+",站内信保存成功！");
		} catch (Exception e) {
			resp.setErrorCode(-1);
			resp.setErrorMessage(e.getMessage());
			log.info("后台请求站内信异常错误：用户oid："+userOid+",异常内容:"+e.getMessage());
		}
		
		return resp;
	}

	/**
	 * 删除站内信
	 * @param aoid
	 * @param loginUser
	 * @param remark 
	 */
	public void delete(String aoid, String loginUser, String remark) {
		MailEntity mail = this.findByOid(aoid);
		
		mail.setStatus(MailEntity.MAIL_status_delete);
		mail.setRemark(mail.getRemark()+"  (删除原因："+remark+")");
		
		this.updateEntity(mail);
	}

	/**
	 * 获取未读数量
	 * @param loginUser
	 * @return
	 */
	public MailNoNumCTResp getNoReadNum(final String userOid) {
		Specification<MailEntity> sa = new Specification<MailEntity>() {
			@Override
			public Predicate toPredicate(Root<MailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate b = cb.equal(root.get("status").as(String.class), "pass");
				
				Predicate c = cb.equal(root.get("userOid").as(String.class), userOid);
				Predicate d = cb.equal(root.get("mailType").as(String.class), MailEntity.MAIL_mailType_all);
				Predicate g = cb.equal(root.get("isRead").as(String.class), MailEntity.MAIL_isRead_no);
//				Predicate h = cb.notLike(root.get("readUserNote").as(String.class), "%"+userOid+"%");
				
				Predicate e = cb.and(b,c,g);
				Predicate f = cb.and(b,d);
				
				return cb.or(e,f);
			}
		};
		
		List<MailEntity> enchs = this.mailDao.findAll(sa);
		
		String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//		String hadReadStr= StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
		
		MailNoNumCTResp resp = null;
		if (hadReadStr == null || hadReadStr.isEmpty()){
			resp = new MailNoNumCTRespBuilder().num(enchs.size()).build();
		}else{
			@SuppressWarnings("unchecked")
			Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
			
			int num = 0;
			for (MailEntity mail : enchs){
				if (mail.getMailType().equals(MailEntity.MAIL_mailType_all)){
					if (!oidSet.contains(mail.getOid())){
						num++;
					}
				}else{
					num++;
				}
			}
			
			resp = new MailNoNumCTRespBuilder().num(num).build();
		}
		
		return resp;
	}

	/**
	 * 全部置为已读
	 * @param loginUser
	 * @return
	 */
	public void allread(final String userOid) {
		Specification<MailEntity> sa = new Specification<MailEntity>() {
			@Override
			public Predicate toPredicate(Root<MailEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate b = cb.equal(root.get("status").as(String.class), "pass");
				
				Predicate c = cb.equal(root.get("userOid").as(String.class), userOid);
				Predicate d = cb.equal(root.get("mailType").as(String.class), MailEntity.MAIL_mailType_all);
				Predicate g = cb.equal(root.get("isRead").as(String.class), MailEntity.MAIL_isRead_no);
				
				Predicate e = cb.and(b,c,g);
				Predicate f = cb.and(b,d);
				query.where(cb.or(e,f));
//				query.orderBy(cb.desc(root.get("createTime")));
				
				return query.getRestriction();
			}
		};
		
		List<MailEntity> mails = this.mailDao.findAll(sa);
		
		for (MailEntity en : mails) {
			if (en.getMailType().equals(MailEntity.MAIL_mailType_all)){
				String hadReadStr = RedisUtil.hget(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid);
//				String hadReadStr = StrRedisUtil.get(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid);
				
				if (hadReadStr == null || hadReadStr.isEmpty()){
					Set<String> oidSet = new HashSet<String>();
					oidSet.add(en.getOid());
					RedisUtil.hset(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid, JSONObject.toJSONString(oidSet));
//					StrRedisUtil.set(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid, oidSet);
				}else{
					@SuppressWarnings("unchecked")
					Set<String> oidSet = JSON.parseObject(hadReadStr, Set.class);
					oidSet.add(en.getOid());
					RedisUtil.hset(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY, userOid, JSONObject.toJSONString(oidSet));
//					StrRedisUtil.set(redis, StrRedisUtil.VERI_NOREAD_REDIS_KEY + userOid, oidSet);
				}
			}else{
				en.setIsRead(MailEntity.MAIL_isRead_is);
				this.updateEntity(en);
			}
		}
	}

}
