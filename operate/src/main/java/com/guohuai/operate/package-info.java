@TypeDefs(value = {
		@TypeDef(name = "password", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "algorithm", value = "PBEWithMD5AndTripleDES"),
				@Parameter(name = "password", value = "ghjr/7fa09592bc583a57"), @Parameter(name = "keyObtentionIterations", value = "1000") }),
		@TypeDef(name = "json", typeClass = EncryptedAsJsonType.class, defaultForType = JSONObject.class) })
package com.guohuai.operate;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.operate.component.config.EncryptedAsJsonType;
