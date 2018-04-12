package com.guohuai.tulip.platform.facade;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2018/1/1
 */
@Data
public class BatchIssueCouponRedisRecord implements Serializable{

    private String eventOid;

    private String operateOid;

    private Date startDate;

    private Date endDate;

    private int issueCount;

    private String status;

}
