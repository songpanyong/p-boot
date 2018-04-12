package com.guohuai.qams.que;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.guohuai.operate.api.objs.admin.AdminObj;
@Entity
@Table(name = "T_QAMS_QUESTIONNAIRE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsQue implements Serializable {

	private static final long serialVersionUID = -6940644532109194305L;
	public static final String ILLIQUIDASSET_STATE_INVALID = "INVALID"; // invalid 作废
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String sid;
	private String name;
	private String type;
	private String sn;
	private Timestamp create_time;
	private String status;
	private Integer number=0;
	@Column(name = "min_score")
	private String minScore;
	@Column(name = "max_score")
	private String maxScore;
	private String admin_id;



}
