package com.guohuai.points.task;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.SeqGenerator;


/**
 * @ClassName: JobControllerService
 * @Description: job控制器
 * @author CHENDONGHUI
 * @date 2017年4月20日 下午 19:19:22
 */
@Service
public class JobControllerService {
    private final static Logger log = LoggerFactory.getLogger(JobControllerService.class);
    
    @Autowired
    private JobControllerDao jobControllerDao;
    @Autowired
	private SeqGenerator seqGenerator;
    /**
     * 是否需要运行定时job
     * @param jobName
     * @return
     */
    public boolean isNeedRunJob(String jobName) {
    	String updateBy = seqGenerator.next("JOB");
    	Date date = new Date();
    	Timestamp stamp = new Timestamp(date.getTime());
    	JobControllerEntity job = jobControllerDao.findByJobName(jobName);
    	if(job.getUpdateTime() == null || Math.abs(stamp.getTime() -  job.getUpdateTime().getTime())  > 300 * 1000) { 
    		job.setUpdateTime(stamp);
    		job.setUpdateBy(updateBy);
    		jobControllerDao.save(job);
    	} else {
    		log.info("jobName :" + jobName +" has been executed by other server");
    	}
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.info("Thread sleep exception:{}",e);
			e.printStackTrace();
		}
    	String oldUpdateBy = jobControllerDao.findUpdateByByJobName(jobName);
    	if(updateBy.equals(oldUpdateBy)) {
    		log.info("jobName :" + jobName +"executed by this server");
    		return true;
    	}
		return false;
    }

}