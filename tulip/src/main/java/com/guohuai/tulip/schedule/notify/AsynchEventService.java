package com.guohuai.tulip.schedule.notify;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.guohuai.rules.config.DroolsContainerHolder;

@Service
@Transactional
public class AsynchEventService {

	@Value("${async.pool.size:10}")
	int asyncPoolSize;
	
	@Value("${event.async:false}")
	boolean eventAsync;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	DroolsContainerHolder containerHolder;
	
	@Autowired
	private AsynchEventDao asynchEventDao;
	
	public List<AsynchEventEntity> getEventList(String eventType){
		if(eventAsync){
			//异步挡板
			return asynchEventDao.getUnsentEventList(eventType);			
		}
		return null;
	}
	
	public int updateAsynchEvent(String oid){
		return asynchEventDao.updateEventTosendSuccess(oid);
	}
	
	public void saveEvent(AsynchEventEntity entity){
		asynchEventDao.save(entity);
	}
	
}
