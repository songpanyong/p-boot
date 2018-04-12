package com.guohuai.tulip.platform.signin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.ext.web.PageResp;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SignInService {

	@Autowired
	private SignInDao signInDao;
	

	/**
	 * 检测用户是否签到
	 * @param signIn
	 * @return
	 */
	public Boolean checkSignIn(String userId) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
		//每天签到的开始时间
		Timestamp startTime = Timestamp.valueOf(format.format(now)+SignInEntity.HMS_START);
		//每天签到的结束时间
		Timestamp endTime = Timestamp.valueOf(format.format(now)+SignInEntity.HMS_END);
		//判断当日是否已经签到
		List<SignInEntity> sign = this.signInDao.findByUserIdAndSignInTimeBetween(userId,startTime,endTime);
		if(sign != null && sign.size() >0){
			log.info("用户已签到userId={}",userId);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 创建签到记录
	 * @param entity
	 * @return
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public SignInEntity createSignInEntity(SignInEntity entity){
		log.info("用户{}于{}时间签到！",entity.getUserId(),entity.getSignInTime());
		return this.signInDao.save(entity);
	}
	/**
	 * 查询签到记录
	 * @param userId
	 * @param signDate
	 * @return
	 */
	public SignInEntity findByUserIdAndSignDate(String userId, java.sql.Date signDate) {
		log.info("查询用户签到记录,用户:{},时间:{}",userId,signDate);
		return this.signInDao.findByUserIdAndSignDate(userId,signDate);
	}
	
	/**
	 * 查询用户签到记录
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PageResp<SignInRep> signList(Specification<SignInEntity> spec, Pageable pageable) {
		Page<SignInEntity> cas = this.signInDao.findAll(spec, pageable);
		PageResp<SignInRep> pageResp = new PageResp<SignInRep>();
		try {
			for (SignInEntity entity : cas) {
				SignInRep rep = new SignInRep();
				BeanUtils.copyProperties(entity, rep);
				pageResp.getRows().add(rep);
			}
			pageResp.setTotal(cas.getTotalElements());
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return pageResp;
	}
}
