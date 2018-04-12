package com.guohuai.cms.component.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.operate.api.AdminSdk;
import com.guohuai.operate.api.objs.admin.AdminObj;

@Service
public class AdminUtil {
	@Autowired
	private AdminSdk adminSdk;
	
	public String getAdminName(String adminOid){
		if (adminOid!=null&&!adminOid.isEmpty()){
			AdminObj admin = adminSdk.getAdmin(adminOid);
			if (admin!=null){
				return admin.getName();
			}
		}
		
		return adminOid;
	}
}
