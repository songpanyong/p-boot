package com.guohuai.operate.system;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SysService {

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ReadLock readLock = this.lock.readLock();
	private WriteLock writeLock = this.lock.writeLock();

	@Autowired
	private SysDao sysDao;

	private Map<String, Sys> cacheMap = new LinkedHashMap<String, Sys>();
	private List<Sys> cacheList = new ArrayList<Sys>();

	@PostConstruct
	@Scheduled(cron = "0 0/15 * * * *")
	public void load() {
		this.writeLock.lock();
		try {
			Sort sort = new Sort(Direction.ASC, "oid");
			List<Sys> list = this.sysDao.findAll(sort);
			this.cacheMap.clear();
			this.cacheList.clear();
			for (Sys s : list) {
				this.cacheMap.put(s.getOid(), s);
				this.cacheList.add(s);
			}
		} finally {
			this.writeLock.unlock();
		}
	}

	public Sys get(String oid) {
		this.readLock.lock();
		try {
			Sys s = this.cacheMap.get(oid);
			return s;
		} finally {
			this.readLock.unlock();
		}
	}

	public List<Sys> list() {
		this.readLock.lock();
		try {
			return this.cacheList;
		} finally {
			this.readLock.unlock();
		}
	}

}
