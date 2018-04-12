package com.guohuai.tulip.platform.event.rule;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventRuleAddReq extends EventRuleEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2298064473899811851L;
	@NotBlank(message = "标题不能为空！")
	@Length(max = 50, message = "标题名称长度不能超过50（包含）！")
	private String title;
	@NotBlank(message = "描述不能为空！")
	@Length(max = 2000, message = "描述长度不能超过2000（包含）！")
	private String description;
	/**
	 * 获取 title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取 description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
