package com.guohuai.operate.api;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.guohuai.operate.api.ext.SqlDateTypeAdapter;
import com.guohuai.operate.api.ext.TimestampTypeAdapter;
import com.guohuai.operate.api.objs.calendar.DateListObj;
import com.guohuai.operate.api.objs.calendar.IsTradeDateObj;
import com.guohuai.operate.api.objs.calendar.IsWorkDateObj;
import com.guohuai.operate.api.objs.calendar.LastTradeDateObj;
import com.guohuai.operate.api.objs.calendar.LastWorkDateObj;
import com.guohuai.operate.api.objs.calendar.NextTradeDateObj;
import com.guohuai.operate.api.objs.calendar.NextWorkDateObj;
import com.guohuai.operate.api.objs.calendar.TradeDateListObj;
import com.guohuai.operate.api.objs.calendar.TradeDateObj;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public class TradeCalendarSdk {

	private TradeCalendarOpenApi api;

	public TradeCalendarSdk(String apihost, Level level) {
		super();

		List<TypeAdapter<?>> adapters = new ArrayList<TypeAdapter<?>>();
		adapters.add(new TimestampTypeAdapter());
		adapters.add(new SqlDateTypeAdapter());

		this.api = Feign.builder().encoder(new GsonEncoder(adapters)).decoder(new GsonDecoder(adapters))
				.logger(new Slf4jLogger()).logLevel(level)
				.target(TradeCalendarOpenApi.class, apihost);
	}

	public List<TradeDateObj> load() {
		TradeDateListObj list = this.api.query();
		if (list.getErrorCode() == 0) {
			return list.getRows();
		} else {
			throw new RuntimeException(list.getErrorMessage());
		}
	}

	public boolean isTradeDate(Date date) {
		IsTradeDateObj obj = this.api.istd(date.getTime());
		if (obj.getErrorCode() == 0) {
			return obj.isTd();
		} else {
			throw new RuntimeException(obj.getErrorMessage());
		}
	}

	public Date nextTradeDate(Date date, int skip) {
		NextTradeDateObj obj = this.api.nexttd(date.getTime(), skip);
		if (obj.getErrorCode() == 0) {
			return obj.getNexttd();
		} else {
			throw new RuntimeException(obj.getErrorMessage());
		}
	}

	public Date nextTradeDate(Date date) {
		return this.nextTradeDate(date, 0);
	}

	public Date lastTradeDate(Date date, int skip) {
		LastTradeDateObj obj = this.api.lasttd(date.getTime(), skip);
		if (obj.getErrorCode() == 0) {
			return obj.getLasttd();
		} else {
			throw new RuntimeException(obj.getErrorMessage());
		}
	}

	public Date lastTradeDate(Date date) {
		return this.lastTradeDate(date, 0);
	}

	public boolean isWorkDate(Date date) {
		IsWorkDateObj obj = this.api.iswd(date.getTime());
		if (obj.getErrorCode() == 0) {
			return obj.isWd();
		} else {
			throw new RuntimeException(obj.getErrorMessage());
		}
	}

	public Date nextWorkDate(Date date, int skip) {
		NextWorkDateObj obj = this.api.nextwd(date.getTime(), skip);
		if (obj.getErrorCode() == 0) {
			return obj.getNextwd();
		} else {
			throw new RuntimeException(obj.getErrorMessage());
		}
	}

	public Date nextWorkDate(Date date) {
		return this.nextWorkDate(date, 0);
	}

	public Date lastWorkDate(Date date, int skip) {
		LastWorkDateObj obj = this.api.lastwd(date.getTime(), skip);
		if (obj.getErrorCode() == 0) {
			return obj.getLastwd();
		} else {
			throw new RuntimeException(obj.getErrorMessage());
		}
	}

	public Date lastWorkDate(Date date) {
		return this.lastWorkDate(date, 0);
	}

	public Date firstWorkDate(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return this.nextWorkDate(new Date(c.getTimeInMillis()), -1);
	}

	public Date lastWorkDate(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2016);
		c.set(Calendar.MONTH, 4 + 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);
		return this.lastWorkDate(new Date(c.getTimeInMillis()), -1);
	}

	public List<Date> workDates(Date begin, Date end) {
		DateListObj list = this.api.workds(begin.getTime(), end.getTime());
		if (list.getErrorCode() != 0) {
			throw new RuntimeException(list.getErrorMessage());
		}
		return list.getDates();
	}

}
