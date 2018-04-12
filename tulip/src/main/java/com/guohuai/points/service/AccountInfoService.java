package com.guohuai.points.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.basic.common.SeqGenerator;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.points.dao.AccountInfoDao;
import com.guohuai.points.entity.AccountInfoEntity;
import com.guohuai.points.component.AccountTypeEnum;
import com.guohuai.points.component.CodeConstants;
import com.guohuai.points.component.Constant;
import com.guohuai.points.request.CreateAccountRequest;
import com.guohuai.points.response.CreateAccountResponse;

/**
 * @ClassName: AccountInfoService
 * @Description: 积分账户相关
 * @author CHENDONGHUI
 * @date 2017年3月20日 下午5:19:22
 */
@Service
public class AccountInfoService {
	
	private final static Logger logger = LoggerFactory.getLogger(AccountInfoService.class);
	
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	@Autowired
	private SeqGenerator seqGenerator;
	
	/**
	 * 创建积分基本户
	 * @param userOid
	 * @param remark
	 * @return
	 */
	@Transactional
	public AccountInfoEntity addBaseAccount(CreateAccountRequest req){
		List<AccountInfoEntity> accInfoList = null;
		logger.info("查询用户是否存在");
		AccountInfoEntity account = new AccountInfoEntity();
		//判断是否已存在积分基本户
		accInfoList = accountInfoDao.findByUserOidAndAccountType(req.getUserOid(),req.getAccountType());
		if(accInfoList != null&&accInfoList.size()>0){
			logger.info("基本户oid："+accInfoList.get(0).getOid());
			return accInfoList.get(0);
		}
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		//生成账户号
		String accountNo = this.seqGenerator.next(CodeConstants.ACCOUNT_NO_PREFIX);
		account.setAccountNo(accountNo);
		account.setUserOid(req.getUserOid());
		account.setAccountType(req.getAccountType());//积分基本户
		account.setAccountName(AccountTypeEnum.getEnumName(req.getAccountType()));
		account.setBalance(BigDecimal.ZERO);
		account.setFrozenStatus("N");//默认N
		account.setRemark(req.getRemark());
		account.setCreateTime(nowTime);
		account.setUpdateTime(nowTime);
		accountInfoDao.save(account);//保存基本户
		logger.info("基本户oid："+account.getOid());
		return account;
	}
	
	/**
	 * 新增账户(创建子账户)
	 * @param req
	 * @param status
	 * @return
	 */
	@Transactional
	public CreateAccountResponse addChildAccount(CreateAccountRequest req){
		logger.info("新增账户:[" + JSONObject.toJSONString(req) + "]");
		CreateAccountResponse resp = new CreateAccountResponse();
		//基本校验
		resp = checkAccountType(req);
		if(!resp.getReturnCode().equals(Constant.SUCCESS)){
			return resp;
		}
		List<AccountInfoEntity> accInfoList = null;
		logger.info("查询用户子积分账户是否存在");
    	if(StringUtil.isEmpty(req.getRelationProduct())) {//无关联产品或卡券的积分账户
    		accInfoList = accountInfoDao.findByUserOidAndAccountType(req.getUserOid(), req.getAccountType());
    	} else {//有关联产品或卡券的积分账户
    		accInfoList = accountInfoDao.findByUserOidAndAccountTypeAndProductNo(req.getUserOid(), req.getRelationProduct(), req.getAccountType());
    	}
		if(accInfoList!=null && accInfoList.size()>0) {
			logger.info("子积分账户已存在，不需要新建 ,userOid:{},aies:{}",req.getUserOid(),accInfoList.size());
			resp.setReturnCode(Constant.SUCCESS);
			resp.setErrorMessage("成功");
			resp.setAccountNo(accInfoList.get(0).getAccountNo());
			resp.setBalance(accInfoList.get(0).getBalance());
			resp.setOid(accInfoList.get(0).getOid());
			resp.setAccountType(req.getAccountType());
			resp.setRelationProduct(req.getRelationProduct());
			resp.setUserOid(req.getUserOid());
			resp.setRelationProductName(req.getRelationProductName());
			return resp;
		}
		logger.info("查询用户是否存在完成，需要新建 ,userOid{}",req.getUserOid());
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		
		AccountInfoEntity account = new AccountInfoEntity();
//		account.setOid(StringUtil.uuid());
		account.setUserOid(req.getUserOid());
		account.setAccountType(req.getAccountType());
		account.setRelationTicketCode(req.getRelationProduct());
		account.setRelationTicketName(req.getRelationProductName());
		account.setAccountName(AccountTypeEnum.getEnumName(req.getAccountType()));
		String accountNo = this.seqGenerator.next(CodeConstants.ACCOUNT_NO_PREFIX);
		account.setAccountNo(accountNo);
		account.setBalance(BigDecimal.ZERO);
		account.setFrozenStatus("N");//默认N
		account.setRemark(req.getRemark());
		account.setOverdueTime(req.getOverdueTime());
		account.setCreateTime(nowTime);
		account.setUpdateTime(nowTime);
		logger.info("保存用户子账户信息{}",JSONObject.toJSONString(account));
		Object result = accountInfoDao.save(account);
		//返回参数
		if(result != null){
			resp.setOid(account.getOid());
			logger.info("子账户oid："+account.getOid());
			resp.setReturnCode(Constant.SUCCESS);
			resp.setErrorMessage("成功");
			resp.setUserOid(account.getUserOid());
			resp.setAccountType(account.getAccountType());
			resp.setRelationProduct(account.getRelationTicketCode());
			resp.setRelationProductName(account.getRelationTicketName());
			resp.setAccountNo(accountNo);
			resp.setBalance(account.getBalance());
			resp.setCreateTime(DateUtil.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
		}else{
			resp.setReturnCode(Constant.FAIL);
			resp.setErrorMessage("创建用户子积分账户失败");
		}
		logger.info("保存用户子账户信息完成  createUserResp:{}",JSONObject.toJSON(resp));
		return resp;
	}
	
	/**
	 * 检查类型是否存在
	 * @param req
	 * @return
	 */
	public CreateAccountResponse checkAccountType(CreateAccountRequest req){
		CreateAccountResponse resp = new CreateAccountResponse();
		resp.setReturnCode(Constant.SUCCESS);
		
		if(StringUtil.isEmpty(req.getUserOid())){
			resp.setReturnCode(Constant.FAIL);
			resp.setErrorMessage("userOid不能为空!");
			return resp;
		}
		if(StringUtil.isEmpty(AccountTypeEnum.getEnumName(req.getAccountType()))){
			//账户类型不存在
			resp.setReturnCode(Constant.FAIL);
			resp.setErrorMessage("账户类型不存在！");
			logger.error("账户类型不存在，[accountType=" + req.getAccountType() + "]");
			return resp;
		}
		return resp;
	} 
	
	/**
	 * 根据账户号获取账户信息
	 * @param accountNo
	 * @return
	 */
	public AccountInfoEntity getAccountByNo(String accountNo){
		AccountInfoEntity account = accountInfoDao.findByAccountNo(accountNo);
		return account;
	}
	
	/**
	 * 更新积分账户余额
	 * @param form
	 * @return
	 */
    @Transactional
    public BaseResp update(String oid, BigDecimal balance) {
    	BaseResp response = new BaseResp();
    	logger.info("update");
    	logger.info("{}积分账户余额变动,balance:{}", oid, balance);
    	
    	//验证参数
 		if(StringUtil.isEmpty(oid)) {
 			response.setErrorCode(-1);
 			response.setErrorMessage("OID不能为空");
 			return response;
 		}
//      AccountInfoEntity accEntity = this.accountInfoDao.findOne(oid);
        AccountInfoEntity accEntity = this.accountInfoDao.findByOidForUpdate(oid);
        
        if(accEntity!=null) {
        	accEntity.setBalance(balance);
        	accEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        	accEntity = this.accountInfoDao.saveAndFlush(accEntity);
            
            logger.info("更新AccountInfoEntity,AccountInfoEntity:{}", JSON.toJSONString(accEntity));
            response.setErrorCode(0);
        } else {
        	response.setErrorCode(-1);
        	response.setErrorMessage("找不到账户");
        }
		return response;
    }

    /**
     * 批量更新积分账户余额
     * @param list
     * @return
     */
    @Transactional
	public BaseResp update(List<Map<String, Object>> list) {
		BaseResp response = new BaseResp();
		for(Map<String, Object> map :list){
			logger.info("{}积分账户余额变动,balance:{}", map.get("oid"), map.get("balance"));
			//验证参数
	 		if(StringUtil.isEmpty((String) map.get("oid"))) {
	 			response.setErrorCode(-1);
	 			response.setErrorMessage("OID不能为空");
	 			return response;
	 		}
	        AccountInfoEntity accEntity = this.accountInfoDao.findOne((String) map.get("oid"));
	        
	        if(accEntity!=null) {
	        	accEntity.setBalance((BigDecimal) map.get("balance"));
	        	accEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
	        	accEntity = this.accountInfoDao.saveAndFlush(accEntity);
	            
	            logger.info("更新AccountInfoEntity,AccountInfoEntity:{}", JSON.toJSONString(accEntity));
	            response.setErrorCode(0);
	        } else {
	        	response.setErrorCode(-1);
	        	response.setErrorMessage("找不到账户");
	        	return response;
	        }
		}
		return response;
	}

    /**
     * 根据账户类型及用户id查询用户积分账户信息
     * @param accountType
     * @param userOid
     * @return
     */
	public AccountInfoEntity getAccountByTypeAndUser(String accountType, String userOid) {
		AccountInfoEntity account = accountInfoDao.findByTypeAndUser(accountType, userOid);
		if(account != null){
			account = accountInfoDao.findByOidForUpdate(account.getOid());
		}
		return account;
	}

	/**
	 * 根基用户查询用户名下所有有积分的子账户
	 * @param userOid
	 * @return
	 */
	public List<AccountInfoEntity> getChildAccountListByUser(String userOid) {
		List<AccountInfoEntity> childAccountListByUser = accountInfoDao.findChildAccountListByUser(userOid);
		return childAccountListByUser;
	}
	
	/**
	 * 获取过期账户List
	 * @return
	 */
	public List<AccountInfoEntity> getOverdueAccount(){
		List<AccountInfoEntity> overdueAccountList = accountInfoDao.findOverdueAccount();
		return overdueAccountList;
	}

}