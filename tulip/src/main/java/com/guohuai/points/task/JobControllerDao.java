package com.guohuai.points.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface JobControllerDao extends JpaRepository<JobControllerEntity, String>, JpaSpecificationExecutor<JobControllerEntity> {
	
	@Query(value = "SELECT updateBy FROM T_TULIP_job_controller WHERE jobName = ?1", nativeQuery = true)
	public String findUpdateByByJobName(String jobName);
	
	@Query(value = "SELECT * FROM T_TULIP_job_controller WHERE jobName = ?1", nativeQuery = true)
	public JobControllerEntity findByJobName(String jobName);
	
}
