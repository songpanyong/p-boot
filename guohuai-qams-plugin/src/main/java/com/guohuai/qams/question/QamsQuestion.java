
package com.guohuai.qams.question;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.que.QamsQue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "T_QAMS_ISSUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsQuestion implements Serializable {

	private static final long serialVersionUID = -6940644532109194305L;

	//问题状态
	public static final String ISSUE_STATE_HIDE = "HIDE"; // 隐藏；
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String sid;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "que_SID", referencedColumnName = "sid")
	private QamsQue que;
	private String ch_type;
	private Timestamp create_time;
	private Timestamp edit_time;
	private String status;
	private String content;
	private String admin_id;
	private int que_sort;



}


