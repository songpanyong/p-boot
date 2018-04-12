@TypeDefs(value = {
		@TypeDef(name = "password", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "algorithm", value = "PBEWithMD5AndTripleDES"),
				@Parameter(name = "password", value = "ghjr/7fa09592bc583a57"), @Parameter(name = "keyObtentionIterations", value = "1000") }),
		@TypeDef(name = "json", typeClass = EncryptedAsJsonType.class, defaultForType = JSONObject.class),
		@TypeDef(name = "array", typeClass = EncryptedAsArrayType.class, defaultForType = JSONArray.class) })

package com.guohuai.basic;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.ext.hibernate.datatype.EncryptedAsArrayType;
import com.guohuai.basic.component.ext.hibernate.datatype.EncryptedAsJsonType;

