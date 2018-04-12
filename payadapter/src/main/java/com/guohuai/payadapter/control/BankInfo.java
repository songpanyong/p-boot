package com.guohuai.payadapter.control;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(
    name = "t_bank_information"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankInfo extends UUID implements Serializable{
	  private static final long serialVersionUID = -3286518029485346965L;
	  private String userOid;
	  private String bankAccontClass;
	  private String bankAccount;
	  private String bankAccountName;
	  private String accountFullName;
	  private String bankAddress;
	  private String openAccountProvince;
	  private String openAccountCity;
	  private String accountType;//基本户、一般户、收入户、支出户等
	  private String accountStatus;//可用、禁用
	  private Timestamp createTime;
	  private Timestamp updateTime;
}
