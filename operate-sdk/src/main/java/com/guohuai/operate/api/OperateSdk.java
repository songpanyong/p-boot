package com.guohuai.operate.api;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.guohuai.operate.api.ext.TimestampTypeAdapter;
import com.guohuai.operate.api.objs.bank.BankListObj;
import com.guohuai.operate.api.objs.bank.BankObj;
import com.guohuai.operate.api.objs.corporate.CorporateListObj;
import com.guohuai.operate.api.objs.corporate.CorporateObj;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public class OperateSdk {

	private OperateOpenApi api;

	public OperateSdk(String apihost, Level level) {
		super();

		List<TypeAdapter<?>> adapters = new ArrayList<TypeAdapter<?>>();
		adapters.add(new TimestampTypeAdapter());

		this.api = Feign.builder().encoder(new GsonEncoder(adapters)).decoder(new GsonDecoder(adapters))
				.logger(new Slf4jLogger()).logLevel(level)
				.target(OperateOpenApi.class, apihost);
	}

	public List<CorporateObj> getCorporateList() {
		CorporateListObj listObj = this.api.getCorporateList();
		return listObj.getRows();
	}

	public CorporateObj getCorporate(String oid) {
		CorporateObj obj = this.api.getCorporate(oid);
		return obj;
	}

	public List<BankObj> getUserBanks() {
		BankListObj listObj = this.api.getUserBanks();
		return listObj.getRows();
	}

	public List<BankObj> getCorporateBanks() {
		BankListObj listObj = this.api.getCorporateBanks();
		return listObj.getRows();
	}

	public BankObj getBank(String oid) {
		BankObj obj = this.api.getBank(oid);
		return obj;
	}

}
