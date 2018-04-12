package com.guohuai.cms.platform.notice;

import com.guohuai.cms.component.web.BaseRep;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class NoticeH5InfoRep extends BaseRep{

	private String oid, title, linkUrl, linkHtml;
}
