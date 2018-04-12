package com.guohuai.operate.api;

import com.guohuai.operate.api.objs.calendar.DateListObj;
import com.guohuai.operate.api.objs.calendar.IsTradeDateObj;
import com.guohuai.operate.api.objs.calendar.IsWorkDateObj;
import com.guohuai.operate.api.objs.calendar.LastTradeDateObj;
import com.guohuai.operate.api.objs.calendar.LastWorkDateObj;
import com.guohuai.operate.api.objs.calendar.NextTradeDateObj;
import com.guohuai.operate.api.objs.calendar.NextWorkDateObj;
import com.guohuai.operate.api.objs.calendar.TradeDateListObj;

import feign.Param;
import feign.RequestLine;

public interface TradeCalendarOpenApi {

	@RequestLine("POST /operate/calendar/query")
	public TradeDateListObj query();

	@RequestLine("POST /operate/calendar/istd?ts={ts}")
	public IsTradeDateObj istd(@Param("ts") long ts);

	@RequestLine("POST /operate/calendar/nexttd?ts={ts}&skip={skip}")
	public NextTradeDateObj nexttd(@Param("ts") long ts, @Param("skip") int skip);

	@RequestLine("POST /operate/calendar/lasttd?ts={ts}&skip={skip}")
	public LastTradeDateObj lasttd(@Param("ts") long ts, @Param("skip") int skip);

	@RequestLine("POST /operate/calendar/iswd?ts={ts}")
	public IsWorkDateObj iswd(@Param("ts") long ts);

	@RequestLine("POST /operate/calendar/nextwd?ts={ts}&skip={skip}")
	public NextWorkDateObj nextwd(@Param("ts") long ts, @Param("skip") int skip);

	@RequestLine("POST /operate/calendar/lastwd?ts={ts}&skip={skip}")
	public LastWorkDateObj lastwd(@Param("ts") long ts, @Param("skip") int skip);

	@RequestLine("POST /operate/calendar/workds?begin={begin}&end={end}")
	public DateListObj workds(@Param("begin") long begin, @Param("end") long end);

}
