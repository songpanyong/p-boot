package com.guohuai.payadapter.bankutil;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 对账Service
 * @author chendonghui
 * 
 */
@Service
@Transactional
public class BankUtilService {

	@Autowired
	private BankUtilDao passDao;

	/**
	 * 新增
	 * @param req
	 * @param operator
	 * @return
	 */
    public void add(BankUtilReq req) {
    	BankUtilEntity bank = new BankUtilEntity();
    	bank.setBankName(req.getBankName());
    	bank.setBankCode(req.getBankCode());
    	bank.setCardName(req.getCardName());
    	bank.setBINLength(req.getBINLength());
    	bank.setCardLength(req.getCardLength());
    	bank.setBankBin(req.getBankBin());
    	bank.setKindOfCard(req.getKindOfCard());
    	this.passDao.save(bank);
    }
    
    public void addList(List<BankUtilEntity> list){
    	this.passDao.save(list);
    }
    
    public List<BankUtilEntity> findAll(){
		return this.passDao.findAll();
    }
    
    /**
     * 根据银行卡号获取银行名称
     * @param idCard
     * @return
     */
    public String getNameByCard(String idCard){
    	if(idCard==null || idCard.length()<16 || idCard.length()>25){
			return "无法识别银行卡所属银行名称"; 
		}
		// 6位Bin号
		String cardbin_6 = idCard.substring(0, 6);
		BankUtilEntity bank6 = this.passDao.getNameByBin(cardbin_6);
		if(bank6 != null){
			return bank6.getBankName();
		}
		// 8位Bin号
		String cardbin_8 = idCard.substring(0, 8);
		BankUtilEntity bank8 = this.passDao.getNameByBin(cardbin_8);
		if(bank8 != null){
			return bank8.getBankName();
		}
		// 3位Bin号
		String cardbin_3 = idCard.substring(0, 3);
		BankUtilEntity bank3 = this.passDao.getNameByBin(cardbin_3);
		if(bank3 != null){
			return bank3.getBankName();
		}
		// 4位Bin号
		String cardbin_4 = idCard.substring(0, 4);
		BankUtilEntity bank4 = this.passDao.getNameByBin(cardbin_4);
		if(bank4 != null){
			return bank4.getBankName();
		}
		// 5位Bin号
		String cardbin_5 = idCard.substring(0, 5);
		BankUtilEntity bank5 = this.passDao.getNameByBin(cardbin_5);
		if(bank5 != null){
			return bank5.getBankName();
		}
		// 7位Bin号
		String cardbin_7 = idCard.substring(0, 7);
		BankUtilEntity bank7 = this.passDao.getNameByBin(cardbin_7);
		if(bank7 != null){
			return bank7.getBankName();
		}
		// 9位Bin号
		String cardbin_9 = idCard.substring(0, 9);
		BankUtilEntity bank9 = this.passDao.getNameByBin(cardbin_9);
		if(bank9 != null){
			return bank9.getBankName();
		}
		// 10位Bin号
		String cardbin_10 = idCard.substring(0, 10);
		BankUtilEntity bank10 = this.passDao.getNameByBin(cardbin_10);
		if(bank10 != null){
			return bank10.getBankName();
		}else{
			return "无法识别银行卡所属银行名称";
		}
		
    }
    
    /**
     * 根据银行卡号获取银行信息
     * @param idCard
     * @return
     */
    public BankUtilEntity getBankByCard(String idCard){
    	BankUtilEntity bank = new BankUtilEntity();
    	if(idCard==null || idCard.length()<16 || idCard.length()>25){
			return bank;
		}
    	
    	// 6位Bin号
		String cardbin_6 = idCard.substring(0, 6);
		BankUtilEntity bank6 = passDao.getNameByBin(cardbin_6);
		if(bank6 != null){
			return bank6;
		}
		// 8位Bin号
		String cardbin_8 = idCard.substring(0, 8);
		BankUtilEntity bank8 = passDao.getNameByBin(cardbin_8);
		if(bank8 != null){
			return bank8;
		}
				
    	// 3位Bin号
		String cardbin_3 = idCard.substring(0, 3);
		BankUtilEntity bank3 = passDao.getNameByBin(cardbin_3);
		if(bank3 != null){
			return bank3;
		}
		// 4位Bin号
		String cardbin_4 = idCard.substring(0, 4);
		BankUtilEntity bank4 = passDao.getNameByBin(cardbin_4);
		if(bank4 != null){
			return bank4;
		}
		// 5位Bin号
		String cardbin_5 = idCard.substring(0, 5);
		BankUtilEntity bank5 = passDao.getNameByBin(cardbin_5);
		if(bank5 != null){
			return bank5;
		}
		
		// 7位Bin号
		String cardbin_7 = idCard.substring(0, 7);
		BankUtilEntity bank7 = passDao.getNameByBin(cardbin_7);
		if(bank7 != null){
			return bank7;
		}
		
		// 9位Bin号
		String cardbin_9 = idCard.substring(0, 9);
		BankUtilEntity bank9 = passDao.getNameByBin(cardbin_9);
		if(bank9 != null){
			return bank9;
		}
		// 10位Bin号
		String cardbin_10 = idCard.substring(0, 10);
		BankUtilEntity bank10 = passDao.getNameByBin(cardbin_10);
		if(bank10 != null){
			return bank10;
		}
		return bank;
    }
    
    public String getBankNoByBin(String idCard, String channelNo){
    	
    	String channelBankCode = "";
    	if(idCard==null || idCard.length()<16 || idCard.length()>19){
			return channelBankCode;
		}
    	// 6位Bin号
		String cardbin_6 = idCard.substring(0, 6);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_6, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		// 8位Bin号
		String cardbin_8 = idCard.substring(0, 8);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_8, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		
    	// 3位Bin号
		String cardbin_3 = idCard.substring(0, 3);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_3, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		// 4位Bin号
		String cardbin_4 = idCard.substring(0, 4);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_4, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		// 5位Bin号
		String cardbin_5 = idCard.substring(0, 5);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_5, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		
		// 7位Bin号
		String cardbin_7 = idCard.substring(0, 7);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_7, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		
		// 9位Bin号
		String cardbin_9 = idCard.substring(0, 9);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_9, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		// 10位Bin号
		String cardbin_10 = idCard.substring(0, 10);
		channelBankCode = this.passDao.getBankNoByBin(cardbin_10, channelNo);
		if(channelBankCode != null){
			return channelBankCode;
		}
		return channelBankCode;
    }
    
//    /**
//     * 从缓存取所有卡BIN的信息，如果没有从库里查
//     * @return
//     */
//    public List<BankUtilEntity> getAllBank() {
//        String key = PayadapterCacheManager.findAllBankBIN;
//        List<BankUtilEntity> listBank;
//        PayadapterCache cache= PayadapterCacheManager.getCacheInfo(key);
//        if (null == cache){
//        	listBank = passDao.findAll();
//            cache = new PayadapterCache();
//            cache.setKey(key);
//            cache.setValue( listBank);
//            PayadapterCacheManager.putCache(key, cache);
//        }else {
//            listBank=(List<BankUtilEntity>) cache.getValue();
//        }
//        return listBank;
//    }
//    
//    /**
//     * 根据卡BIN获取银行信息
//     * @param cardbin
//     * @return
//     */
//    public BankUtilEntity getBankFromCache(String cardbin){
//    	List<BankUtilEntity> bankList = getAllBank();
//    	for(BankUtilEntity bank : bankList){
//    		if(cardbin.equals(bank.getBankBin())){
//    			return bank;
//    		}
//    	}
//    	return null;
//    }
    public static void main(String[] args) {
    	BankUtilService ser=new BankUtilService();
		System.out.println(ser.getBankByCard("6217001210074931500"));
	}
}
