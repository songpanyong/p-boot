package com.guohuai.payadapter.bankutil;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
/**
 * 对账DAO
 * @author chendonghui
 *
 */
public interface BankUtilDao extends JpaRepository<BankUtilEntity, String>, JpaSpecificationExecutor<BankUtilEntity> {

	@Query("from BankUtilEntity a where a.bankBin = ?1 and a.bankName = ?2")
	public List<BankUtilEntity> getListByName(String bankBin, String bankName);
	
	@Query("from BankUtilEntity a where a.bankBin = ?1")
	public BankUtilEntity getNameByBin(String bankBin);
	
	@Query("from BankUtilEntity a where a.bankBin = ?1")
	public BankUtilEntity getCodeByBin(String bankBin);
	
	@Query(value="select bankCode from t_bank_card_bin_basic where bankName like CONCAT('%',?1) limit 1",nativeQuery=true)
	public String getCodeByName(String bankName);
	
	@Query(value="SELECT k.channelbankCode FROM t_bank_card_bin_basic c LEFT JOIN t_bank_channel_bank k ON k.standardCode = c.bankCode WHERE c.bankBin = ?1 AND k.channelNo = ?2",nativeQuery=true)
	public String getBankNoByBin(String bankBin, String channelNo);
	
	@Query(value="SELECT channelbankCode FROM t_bank_channel_bank WHERE standardCode =?1 AND channelNo in ('10','11') limit 1",nativeQuery=true)
	public String getChannelbankCodeByBin(String bankBin);
	
	@Query(value="SELECT bankName FROM t_bank_card_bin_basic WHERE bankCode=?1 limit 1",nativeQuery=true)
	public String  getNameByBankCode(String bankCode);
	
}
