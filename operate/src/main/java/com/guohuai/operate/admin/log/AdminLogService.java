package com.guohuai.operate.admin.log;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdminLogService {

	@Autowired
	private AdminLogDao adminLogDao;

	/**
	 * 会员操作日志
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@Transactional
	public AdminLogListResp list(Specification<AdminLog>spec,int page, int rows) {
		Sort sort = new Sort(new Order(Direction.DESC, "createTime"));
		Pageable pageable = new PageRequest(page, rows, sort);
		Page<AdminLog> list = this.adminLogDao.findAll(spec, pageable);
		AdminLogListResp adminLogListResp = new AdminLogListResp(list.getContent(),list.getTotalElements());
		return adminLogListResp;
	}

	@Transactional
	public AdminLog create(AdminLog adminLog) {
		return this.adminLogDao.saveAndFlush(adminLog);
	}
}
