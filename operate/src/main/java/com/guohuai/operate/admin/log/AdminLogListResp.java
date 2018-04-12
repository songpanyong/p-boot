package com.guohuai.operate.admin.log;

import java.util.List;

import com.guohuai.operate.component.web.view.PageResp;

public class AdminLogListResp extends PageResp<AdminLogResp>{

	public AdminLogListResp(){
		super.total = 0;
	}
	
	public AdminLogListResp(long total){
		super.total = total;
	}
	
	public AdminLogListResp(List<AdminLog> adminLogs,long total){
		super.total = total;
		if(null != adminLogs && adminLogs.size()>0){
			for(AdminLog adminLog:adminLogs){
				super.rows.add(new AdminLogResp(adminLog));
			}
		}
	}
}
