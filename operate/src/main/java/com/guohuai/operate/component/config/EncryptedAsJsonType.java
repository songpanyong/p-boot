package com.guohuai.operate.component.config;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.alibaba.fastjson.JSONObject;

public class EncryptedAsJsonType implements UserType {

	static final int sqlType = Types.VARCHAR;
	static final int[] sqlTypes = new int[] { sqlType };

	@Override
	public int[] sqlTypes() {
		return sqlTypes;
	}

	@Override
	public Class<JSONObject> returnedClass() {
		return JSONObject.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y || (x != null && y != null && x.equals(y));
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {

		if (rs.wasNull()) {
			return null;
		}
		final String message = rs.getString(names[0]);
		if (null == message) {
			return null;
		}
		if (message.trim().equals("")) {
			return null;
		}
		try {
			JSONObject json = JSONObject.parseObject(message);
			return json;
		} catch (Throwable e) {
			return null;
		}

	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, sqlType);
		} else {
			if (value instanceof JSONObject) {
				JSONObject json = (JSONObject) value;
				st.setString(index, json.toJSONString());
			} else {
				st.setNull(index, sqlType);
			}
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value instanceof JSONObject) {
			JSONObject json = (JSONObject) value;
			return json.clone();
		}
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		return (Serializable) deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		if (cached == null) {
			return null;
		}
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
