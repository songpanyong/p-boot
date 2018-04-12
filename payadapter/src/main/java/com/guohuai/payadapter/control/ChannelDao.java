package com.guohuai.payadapter.control;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ChannelDao extends JpaRepository<Channel, String>, JpaSpecificationExecutor<Channel> {
//	@Query(value="select c.channelNo,c.tradeType,c.bankAccount,c.treatmentMethod,b.bankAccontClass,c.merchantId,c.productId,b.bankAccountName,b.openAccountProvince,b.openAccountCity,b.channelbankCode from t_bank_channel c left join t_bank_information b on c.bankAccount = b.bankAccount   where  c.sourceType =?1 and c.tradeType = ?2 and c.status= '1' and ?3 between c.minAmount and c.maxAmount  order by c.rate", nativeQuery = true)
	@Query(value="select c.channelNo,c.tradeType,c.bankAccount,c.treatmentMethod,c.merchantId,c.productId,c.rateCalclationMethod,c.rate,b.dailyLimit,b.channelbankCode,b.channelbankName,b.singleQuota from t_bank_channel c left join t_bank_channel_bank b on c.channelNo = b.channelNo where c.sourceType =?1 and c.tradeType = ?2 and c.status= '1' and b.standardCode = ?3 and ?4 between c.minAmount and c.maxAmount order by c.rate", nativeQuery = true)
	public Object[] queryChannel(String sourceType,String tradeType,String standardCode,BigDecimal amonut);
	
	@Query(value="select c.channelNo,c.tradeType,c.bankAccount,c.treatmentMethod,c.merchantId,c.productId,c.rateCalclationMethod,c.rate,b.dailyLimit,b.channelbankCode,b.channelbankName,b.singleQuota from t_bank_channel c left join t_bank_channel_bank b on c.channelNo = b.channelNo where c.sourceType =?1 and c.tradeType = ?2 and c.status= '1' and b.standardCode = ?3 order by c.rate", nativeQuery = true)
	public Object[] queryBestChannel(String sourceType, String tradeType, String standardCode);
	
	@Query(value="select c.channelNo,c.tradeType,c.bankAccount,c.treatmentMethod,c.merchantId,c.productId,c.rateCalclationMethod,c.rate,b.dailyLimit,b.channelbankCode,b.channelbankName,b.singleQuota from t_bank_channel c left join t_bank_channel_bank b on c.channelNo = b.channelNo where c.status= '1' and b.standardCode = ?1 order by c.rate", nativeQuery = true)
	public Object[] queryChannels(String standardCode);
	
	@Query(value="select * from t_bank_channel as a where a.channelNo='2' and a.status='1' limit 1", nativeQuery = true)
	public Channel queryAccount();

	@Query(value="select * from t_bank_channel as a where a.channelNo=?1 and a.status='1' limit 1", nativeQuery = true)
	public Channel queryChannel(String channelNo);

	@Query(value="select c.channelNo,c.tradeType,c.bankAccount,c.treatmentMethod,b.bankAccontClass,c.merchantId,c.productId,b.bankAccountName,b.openAccountProvince,b.openAccountCity from t_bank_channel c left join t_bank_information b on c.bankAccount = b.bankAccount   where  c.tradeType = ?2 and c.status= '1' order by c.rate limit 1", nativeQuery = true)
	public Channel queryChannelByTradeType(String tradeType);
}
