package com.guohuai.operate.api;

import com.guohuai.operate.api.objs.admin.log.AdminLogListObj;
import com.guohuai.operate.api.objs.admin.log.AdminLogObj;

import feign.Param;
import feign.RequestLine;

/**
 * 日志
 * @author wzx
 *
 */
public interface AdminLogOpenApi {
	@RequestLine("POST /operate/admin/log/saveAdminLog?oid={oid}&content={content}")
	public AdminLogObj saveAdminLog(@Param("oid")String oid,@Param("content")String content);

	@RequestLine("POST /operate/admin/log/listByAdmin?keyword={keyword}&type={type}&page={page}&rows={rows}")
	public AdminLogListObj listByAdmin(@Param("keyword")String keyword,@Param("type")String type,@Param("page")int page,@Param("rows")int rows);


}
