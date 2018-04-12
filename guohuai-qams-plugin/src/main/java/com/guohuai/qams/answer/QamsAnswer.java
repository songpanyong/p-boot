package com.guohuai.qams.answer;

import java.io.Serializable;

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

import com.guohuai.qams.question.QamsQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "T_QAMS_ANSWER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsAnswer implements Serializable {

	private static final long serialVersionUID = -6940644532109194305L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String sid;
	private String sn;
	private String content;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "issue_id", referencedColumnName = "sid")
	private QamsQuestion issue;
	private String score;
	private String status;
	@Column(name = "admin_id")
	private String admin;


}
