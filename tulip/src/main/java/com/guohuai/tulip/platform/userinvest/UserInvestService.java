package com.guohuai.tulip.platform.userinvest;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.commissionorder.CommissionOrderEntity;
import com.guohuai.tulip.platform.commissionorder.CommissionOrderService;
import com.guohuai.tulip.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserInvestService {

	@Autowired
	private UserInvestDao userInvestDao;
	@Autowired
	private CommissionOrderService commissionOrderService;

	/**
	 * 根据用户ID查询用户投资记录表
	 * @param userId
	 * @return
	 */
	public UserInvestEntity findUserInvestByUserId(String userId) {
		UserInvestEntity entity = userInvestDao.findUserInvestByUserId(userId);
		if(entity==null){
			throw new GHException("用户的投资信息不存在!");
		}
		return  entity;
	}
	/**
	 * 根据用户Oid查询用户投资记录
	 * @param oid
	 * @return
	 */
	public UserInvestEntity findUserInvestByOid(String oid) {
		UserInvestEntity entity = userInvestDao.findOne(oid);
		if(entity==null){
			throw new GHException("用户的投资信息不存在!");
		}
		return  entity;
	}
	/**
	 * 查询生日的用户
	 * @return
	 */
	public List<UserInvestEntity> findUserByBirthDay() {
		List<UserInvestEntity> userlist = userInvestDao.findUserByBirthDay();
		return userlist;
	}
	/**
	 * 根据手机集合查询用户投资记录
	 * @param phones
	 * @return
	 */
	public List<UserInvestEntity> findUserInvestByPhones(List<String> phones) {
		List<UserInvestEntity> userlist = userInvestDao.findUserInvestByPhones(phones);
		return userlist;
	}
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<UserInvestEntity> findAllUser() {
		List<UserInvestEntity> userlist = userInvestDao.findAll();
		return userlist;
	}

    /**
     * 查询所有用户
     * @return
     */
    public List<UserInvestEntity> batchFindUser(String lastOid) {
        return userInvestDao.batchFindUser(lastOid);
    }

    /**
     * 查询所有用户条数
     * @return
     */
    public int count() {
        return (int)userInvestDao.count();
    }

	/**
	 * 根据oid查询用户详情
	 * @param oid
	 * @return
	 */
	public UserInvestRep findUserDetail(String oid) {
		UserInvestRep userInvestRep = new UserInvestRep();
		UserInvestEntity userInvestEntity = userInvestDao.findOne(oid);
		BeanUtils.copyProperties(userInvestEntity, userInvestRep);
		return userInvestRep;
	}
	
	/**
	 * 用户分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PageResp<UserInvestRep> userList(Specification<UserInvestEntity> spec, Pageable pageable) {
		Page<UserInvestEntity> cas = this.userInvestDao.findAll(spec, pageable);
		PageResp<UserInvestRep> pageRep = new PageResp<UserInvestRep>();
		for (UserInvestEntity entity : cas) {
			UserInvestRep rep = new UserInvestRep();
			BeanUtils.copyProperties(entity, rep);
			if(StringUtils.isNotBlank(entity.getFriendId())){
				UserInvestEntity friend=this.userInvestDao.findUserInvestByUserId(entity.getFriendId());
				rep.setFriendPhone(friend.getPhone());
			}
			pageRep.getRows().add(rep);
		}
		pageRep.setTotal(cas.getTotalElements());
			
		return pageRep;
	}


	/**
	 * 推广人升级为渠道
	 * @param req
	 * @return
	 */
	public UserInvestRep refereeToChannel(UserInvestReq req) {
		UserInvestRep rep = new UserInvestRep();
		UserInvestEntity userInvestEntity = this.userInvestDao.findOne(req.getOid());
		userInvestEntity.setChannelAmount(req.getChannelAmount());
		userInvestEntity.setType(UserInvestEntity.TYPE_CHANNEL);
		userInvestEntity.setAuditor(req.getOperateName());
		userInvestEntity.setAuditorTime(DateUtil.getSqlCurrentDate());
		BeanUtils.copyProperties(userInvestEntity, rep);
		return rep;
	}


	/**
	 * 说明：渠道降为推广人
	 *
	 * @param oid
	 * @author ddyin
	 * @time：2017年3月28日 下午7:32:00
	 */
	public void channelToReferee(String oid,String operateName) {
		UserInvestEntity userInvestEntity = this.userInvestDao.findOne(oid);
		userInvestEntity.setChannelAmount(BigDecimal.ZERO);
		userInvestEntity.setType(UserInvestEntity.TYPE_REFEREE);
		userInvestEntity.setAuditor(operateName);
		userInvestEntity.setAuditorTime(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 用户邀请的佣金明细
	 *
	 * @param oid 用户ID
	 * @return PageResp<RefereeRep>
	 */
	public PageResp<UserInvestRep> getCommissionDetail(String oid) {
		PageResp<UserInvestRep> resp = new PageResp<>();

		UserInvestEntity userInvestEntity = this.userInvestDao.findOne(oid);
		if (userInvestEntity == null) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("未查询到该用户！");
			log.info("查询用户失败：userOid={}", oid);
			return resp;
		}
		String userId = userInvestEntity.getUserId();
		List<CommissionOrderEntity> coList = commissionOrderService.queryByUserId(userId);
		if (coList == null) {
			return resp;
		}
		resp.setTotal(coList.size());
		return resp;
	}

	/**
	 * 用户邀请的注册记录
	 *
	 * @param oid 用户id
	 * @return PageResp<RefereeRep>
	 */
	public PageResp<UserInvestRep> getInviteRecords(String oid) {
		log.info("查询用户邀请的注册记录：userOid={}", oid);

		PageResp<UserInvestRep> resp = new PageResp<>();
		List<UserInvestEntity> list = this.userInvestDao.findUserInvestByFriendId(oid);
		log.info("查询用户邀请的注册记录：userOid={}，返回条数：{}", oid, list.size());

		for (UserInvestEntity entity : list) {
			UserInvestRep rep = new UserInvestRep();
			rep.setRegisterTime(entity.getRegisterTime());
			rep.setName(entity.getName());
			rep.setPhone(entity.getPhone());
			resp.getRows().add(rep);
		}
		return resp;
	}

	/**
	 * 查询用户佣金订单
	 * @param oid
	 * @param pageable
	 * @return
	 */
	public PageResp<UserInvestRep> getCommissionDetailPage(String oid,Pageable pageable) {
		PageResp<UserInvestRep> resp=new PageResp<UserInvestRep>();
		UserInvestEntity userInvestEntity = this.userInvestDao.findOne(oid);
		if (userInvestEntity == null) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("未查询到该用户！");
			log.info("查询用户失败：userOid={}", oid);
			return resp;
		}
		String userId = userInvestEntity.getUserId();
		Specification<CommissionOrderEntity> spec = new Specification<CommissionOrderEntity>() {
			@Override
			public Predicate toPredicate(Root<CommissionOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = null;
				p = cb.and(cb.equal(root.get("userId").as(String.class), userId),cb.equal(root.get("orderStatus").as(String.class), CommissionOrderEntity.ORDERTYPE_PASS));
				return p;
			}
		};
		Page<CommissionOrderEntity> coList = commissionOrderService.findAll(spec, pageable);
		long total=coList.getTotalElements();
		resp.setTotal(total);
		return resp;
	}
	
	/**
	 * 修改推荐人信息
	 * @param userId
	 * @param name
	 * @param birthday
	 */
	public void updateReferee(String userId, String name, Date birthday) {
		int num = this.userInvestDao.updateReferee(userId, name, birthday);
		if(num < 1){
			throw new GHException("修改推荐人信息异常!");
		}
	}
	/**
	 * 创建用户投资记录
	 * 
	 * update：2017年7月20日21:14:46 
	 * <p>注册事件逻辑变成异步之后：新增userInvestor和asynchEvent需要处于同一个事务中
	 * 
	 * @param entity
	 */
//	@Transactional(value=TxType.REQUIRES_NEW)
	public void createUserInvestEntity(UserInvestEntity entity) {
		this.userInvestDao.save(entity);
	}
	/**
	 * 修改推荐人数
	 * @param userId
	 */
	public void updateFriends(String userId) {
		int num = this.userInvestDao.updateFriends(userId);
		if(num < 1){
			throw new GHException("修改推荐人数异常!");
		}
	}
	/**
	 * 修改用户累计投资额度
	 * @param orderAmount
	 * @param userId
	 */
	public void updateInvestAmount(BigDecimal orderAmount, String userId) {
		int num = this.userInvestDao.updateInvestAmount(orderAmount,userId);
		if(num < 1){
			throw new GHException("修改用户累计投资额度异常!");
		}
	}
	
	/**
	 * 同步修改用户手机号
	 * @param req
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void updatePhoneByOid(UserInvestReq req) {
		int num = this.userInvestDao.updatePhone(req.getUserId(), req.getUserPhone());
		log.info("修改用户手机号返回结果：{}", num);
	}
}
