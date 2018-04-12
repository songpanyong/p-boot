package com.guohuai.qams.score;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guohuai.qams.que.QamsQue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 评分准则
 * @author wzx
 *
 */

@Entity
@Table(name = "T_QAMS_SCORE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsScore implements Serializable {

	private static final long serialVersionUID = -6940644532109194305L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@Column(name = "sid")
	private String sid;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "que_SID", referencedColumnName = "sid")
	private QamsQue qamsQue;
	@Column(name = "score_grade")
	private String scoreGrade;
	@Column(name = "min_score")
	private String minScore;
	@Column(name = "max_score")
	private String maxScore;
	@Temporal(TemporalType.DATE)
	 @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd",locale ="zh",timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "admin_id")
	private String adminId;

}
