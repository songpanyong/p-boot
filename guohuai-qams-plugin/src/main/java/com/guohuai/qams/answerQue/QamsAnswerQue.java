package com.guohuai.qams.answerQue;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.guohuai.qams.que.QamsQue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答卷
 * @author William Zhang
 *
 */
@Entity
@Table(name = "T_QAMS_ANSWER_QUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsAnswerQue implements Serializable{
	
	private static final long serialVersionUID = -760186782626328831L;
	
	@Id
	private String sid;
	
	//答题者
	@Column(name = "author_id")
	private String authorId;
	
	//答题者姓名
	@Column(name = "author_name")
	private String authorName;
	
	//答题者手机号
	@Column(name = "author_phone")
	private String authorPhone;
	
	//关联问卷
	@ManyToOne
	@JoinColumn(name = "que_id", referencedColumnName = "sid")
	private QamsQue qamsQue;
	
	//答题时间
	@Column(name = "create_time")
	private Timestamp createTime;
	//分数
	private String score;
	//风险等级
	private String grade;
}
