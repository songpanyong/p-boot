package com.guohuai.cms.platform.advice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.cms.platform.advice.tab.TabEntity;

public interface AdviceDao extends JpaRepository<AdviceEntity, String>, JpaSpecificationExecutor<AdviceEntity>{

	/**
	 * 根据意见标签获取意见列表
	 * @param tab
	 * @return
	 */
	public List<AdviceEntity> getAdvicesByTab(TabEntity tab);
	
}
