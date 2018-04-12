package com.guohuai.cms.platformext.mail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.guohuai.basic.common.Clock;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.platform.mail.MailEntity;
import com.guohuai.cms.platformext.mail.MailBTExtResp.MailBTExtRespBuilder;
//import com.guohuai.cms.platformext.push.PushExtService;
import com.guohuai.cms.platformext.push.PushExtService;

import lombok.extern.slf4j.Slf4j;

import com.guohuai.cms.platform.mail.api.UserCenterApi;
import com.guohuai.cms.platform.mail.api.UserInfoRep;
import com.guohuai.cms.platform.push.PushEntity;

@Slf4j
@Service
@Transactional
public class MailExtService{

	@Autowired
	private MailExtDao mailExtDao;
	@Autowired
	private UserCenterApi userCenterApi;
	@Autowired
	private AdminUtil AdminUtil;
	@Autowired
	private PushExtService pushExtService;
	
	
	/**
	 * 后台分页查询
	 * @return
	 */
	public PageResp<MailBTExtResp> queryBTExtPage4List(Specification<MailExtEntity> mailspec, Pageable pageable) {
		// 判断用户是否存在分情况组装参数
		Page<MailExtEntity> enchs = this.mailExtDao.findAll(mailspec, pageable);
		
		PageResp<MailBTExtResp> pageResp = new PageResp<>();		
		
		List<MailBTExtResp> list = new ArrayList<MailBTExtResp>();
		for (MailExtEntity en : enchs) {
			String requester = AdminUtil.getAdminName(en.getRequester());
			String approver = AdminUtil.getAdminName(en.getApprover());
			
			MailBTExtResp rep = new MailBTExtRespBuilder().oid(en.getOid()).userOid(en.getUserOid())
					.phone(en.getPhone()).requester(requester).approver(approver).approveRemark(en.getApproveRemark()).remark(en.getRemark())
					.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle())
					.mesContent(en.getMesContent()).isRead(en.getIsRead()).status(en.getStatus()).labelCode(en.getLabelCode())
					.updateTime(en.getUpdateTime()).createTime(en.getCreateTime()).build();
			list.add(rep);
		}
		
		pageResp.setTotal(enchs.getTotalElements());
		pageResp.setRows(list);
		return pageResp;
	}
	
	/**添加或修改站内信
	 * @param req
	 * @param loginAdmin
	 */
	public void addExtBT(MailBTAddExtReq req, String loginAdmin) {
		if (req.getOid() != null && !req.getOid().isEmpty()){
			//修改站内信
			MailExtEntity mailEntity = this.findExtByOid(req.getOid());
			String mailType = req.getMailType();
			if(mailType.equals(MailExtEntity.MAIL_mailType_person)||mailType.equals(MailExtEntity.MAIL_mailType_all)||mailType.equals(MailExtEntity.MAIL_mailType_team)){
				switch(mailType){
				case MailExtEntity.MAIL_mailType_person:
					if (req.getPhone() == null || req.getPhone().isEmpty()){
						// 未选择收信人！(CODE:12002)
						throw MoneyException.getException(12002);
					}
					
					UserInfoRep user = userCenterApi.isregist(req.getPhone());
					if (user == null || !user.isRegist() || user.getInvestorOid() == null || user.getInvestorOid().isEmpty()){
						// 会员不存在！(CODE:13000)
						throw MoneyException.getException(13000);
					}
					
					updateExtMail(mailEntity, MailExtEntity.MAIL_mailType_person, user.getInvestorOid(), req.getPhone(),
							MailExtEntity.MAIL_mesType_person, req.getMesTitle(), req.getMesContent(),
							loginAdmin, req.getRemark(),req.getLabelCode());
					break;
				case MailExtEntity.MAIL_mailType_all:
					updateExtMail(mailEntity, MailExtEntity.MAIL_mailType_all, null, null,
							MailExtEntity.MAIL_mesType_all, req.getMesTitle(), req.getMesContent(),
							loginAdmin, req.getRemark(),req.getLabelCode());
					break;
				case MailExtEntity.MAIL_mailType_team:
					
					updateExtMail(mailEntity, MailExtEntity.MAIL_mailType_team, null, null,
							MailExtEntity.MAIL_mailType_team, req.getMesTitle(), req.getMesContent(),
							loginAdmin, req.getRemark(),req.getLabelCode());
					break;
				}
			}else{
				//站内信类型不存在！(CODE:12003)
				throw MoneyException.getException(12003);
			}
		}else{
			//添加站内信
			if(req.getMailType().equals(MailExtEntity.MAIL_mailType_person)
					||req.getMailType().equals(MailExtEntity.MAIL_mailType_team)
					||req.getMailType().equals(MailExtEntity.MAIL_mailType_all)){
				if (req.getMailType().equals(MailExtEntity.MAIL_mailType_person)){
					if (req.getPhone() == null || req.getPhone().isEmpty()){
						// 未选择收信人！(CODE:12002)
						throw MoneyException.getException(12002);
					}
					
					UserInfoRep user = userCenterApi.isregist(req.getPhone());
					if (user == null || !user.isRegist() || user.getInvestorOid() == null || user.getInvestorOid().isEmpty()){
						// 会员不存在！(CODE:13000)
						throw MoneyException.getException(13000);
					}
					
					createExtMail(MailExtEntity.MAIL_mailType_person, req.getPhone(), user.getInvestorOid(), MailExtEntity.MAIL_mesType_person, req.getMesTitle(), loginAdmin, req.getMesContent(), req.getRemark(),req.getLabelCode());
				}else if (req.getMailType().equals(MailExtEntity.MAIL_mailType_all)){
					createExtMail(MailExtEntity.MAIL_mailType_all, null, null, MailExtEntity.MAIL_mesType_all, req.getMesTitle(), loginAdmin, req.getMesContent(), req.getRemark(),req.getLabelCode());
				}else{
					createExtMail(MailExtEntity.MAIL_mailType_team, null, null, MailExtEntity.MAIL_mesType_person, req.getMesTitle(), loginAdmin, req.getMesContent(), req.getRemark(),req.getLabelCode());
				}
			}else{
				//站内信类型不存在！(CODE:12003)
				throw MoneyException.getException(12003);
			}
		}
	}
	
	/**变更站内信
	 * @param mailEntity
	 * @param mailtype
	 * @param userOid
	 * @param userPhone
	 * @param mestype
	 * @param mesTitle
	 * @param mesContent
	 * @param loginAdmin
	 * @param remark
	 * @param labelCode
	 */
	private void updateExtMail(MailExtEntity mailEntity, String mailtype, String userOid, String userPhone, String mestype,
			String mesTitle, String mesContent, String loginAdmin, String remark,String labelCode) {
		mailEntity.setUserOid(userOid);
		mailEntity.setPhone(userPhone);
		mailEntity.setMailType(mailtype);
		mailEntity.setMesType(mestype);
		mailEntity.setMesTitle(mesTitle);
		mailEntity.setMesContent(mesContent);
		mailEntity.setStatus(MailExtEntity.MAIL_status_toApprove);
		mailEntity.setRequester(loginAdmin);
		mailEntity.setRemark(remark);
		mailEntity.setLabelCode(labelCode);
		
		this.updateExtEntity(mailEntity);
	}
	
	/**
	 * 根据OID查询
	 * @param oid
	 * @return
	 */
	public MailExtEntity findExtByOid(String oid){
		MailExtEntity entity = this.mailExtDao.findOne(oid);
		if(null == entity){
			// 站内信不存在！
			throw MoneyException.getException(12000);
		}
		return entity;
	}
	/**
	 * 修改实体
	 * @param en
	 * @return
	 */
	@Transactional
	public MailExtEntity updateExtEntity(MailExtEntity en){
		en.setUpdateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
		return this.mailExtDao.save(en);
	}
	
	/**
	 * 手动生成站内信
	 * @param isPerson
	 * @param oid
	 * @param type
	 * @param title
	 * @param mesContent
	 */
	public void createExtMail(String mailType, String phone, String userOid, String mesType, String title, String requester, String mesContent, String remark,String labelCode){
		createExtMail(mailType, phone, userOid, mesType, title, requester, mesContent, MailEntity.MAIL_status_toApprove, remark, labelCode);
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
	public void createExtMail(String mailType, String phone, String userOid, String mesType, String title, String requester, String mesContent,
			String status, String remark,String labelCode){
		if (mesContent != null && !mesContent.isEmpty()){
			MailExtEntity mail = new MailExtEntity();
			mail.setMailType(mailType);
			mail.setPhone(phone);
			mail.setUserOid(userOid);
			mail.setMesTitle(title);	
			mail.setMesContent(mesContent);
			mail.setMesType(mesType);
			mail.setIsRead(MailExtEntity.MAIL_isRead_no);
			mail.setStatus(status);
			mail.setRequester(requester);
//			mail.setReadUserNote("");
			mail.setRemark(remark);
			mail.setLabelCode(labelCode);
			this.saveExtEntity(mail);
		}
	}
	
	/**
	 * 新增
	 * @param en
	 * @return
	 */
	@Transactional
	public MailExtEntity saveExtEntity(MailExtEntity en){
		en.setCreateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
		return this.updateExtEntity(en);
	}
	

	/**
	 * 审核
	 * @param approveResult
	 * @param aoid
	 * @param approveNote
	 * @param loginAdmin
	 */
	public void approveExt(String approveResult, String aoid, String approveNote, String loginAdmin) {
		MailExtEntity mail = this.findExtByOid(aoid);
		if (approveResult.equals(MailExtEntity.MAIL_status_pass)){
			mail.setApprover(loginAdmin);
			mail.setApproveRemark(approveNote);
			//mail.setLabelCode(labelCode);
			mail.setStatus(MailExtEntity.MAIL_status_pass);
			this.updateExtEntity(mail);
			
			createExtPush(mail);
		}else if (approveResult.equals(MailExtEntity.MAIL_status_refused)){
			mail.setApprover(loginAdmin);
			mail.setApproveRemark(approveNote);
			//mail.setLabelCode(labelCode);
			mail.setStatus(MailExtEntity.MAIL_status_refused);
			this.updateExtEntity(mail);
		}else{
			//审核有误！
			throw MoneyException.getException(12004);
		}
	}
	
	// 生成推送
	private void createExtPush(MailExtEntity mail) {
		String cont = mail.getMesContent();
		
		if (cont != null && cont.length() > 200){
			cont = cont.substring(0, 200);
		}
		pushExtService.groupAndSaveExt(mail.getMesTitle(), PushEntity.PUSH_type_mail, 
				PushEntity.PUSH_type_mail, mail.getRequester(), mail.getMesContent(), mail.getMailType(), mail.getUserOid(), mail.getPhone(),mail.getLabelCode());
	}
	
	/**
	 * 后台获取站内信详情
	 * @param mailOid
	 * @return
	 */
	public MailBTExtResp getBTExtDetail(String mailOid) {
		MailExtEntity en = this.findExtByOid(mailOid);
		String requester = AdminUtil.getAdminName(en.getRequester());
		String approver = AdminUtil.getAdminName(en.getApprover());
		
		MailBTExtResp rep = new MailBTExtRespBuilder().oid(en.getOid()).userOid(en.getUserOid())
				.phone(en.getPhone()).requester(requester).approver(approver).remark(en.getRemark())
				.mailType(en.getMailType()).mesType(en.getMesType()).mesTitle(en.getMesTitle())
				.mesContent(en.getMesContent()).isRead(en.getIsRead()).status(en.getStatus())
				.updateTime(en.getUpdateTime()).labelCode(en.getLabelCode()).createTime(en.getCreateTime()).build();
		return rep;
	}

	/**
	 * 删除站内信
	 * @param aoid
	 * @param loginUser
	 * @param remark 
	 */
	public void deleteExt(String aoid, String loginUser, String remark) {
		MailExtEntity mail = this.findExtByOid(aoid);
		
		mail.setStatus(MailExtEntity.MAIL_status_delete);
		mail.setRemark(mail.getRemark()+"  (删除原因："+remark+")");
		
		this.updateExtEntity(mail);
	}
}
