package com.guohuai.basic.component.ext.hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

// JPA 基类的标识
@MappedSuperclass
public abstract class ID {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long oid;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	@Override
	public int hashCode() {
		return null == this.oid ? -1 : this.oid.hashCode();
	}

	@Override
	public String toString() {
		return null == this.oid ? "" : this.oid.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}
		if (!(obj instanceof ID)) {
			return false;

		}
		ID ref = (ID) obj;
		return null == this.oid ? null == ref.getOid() : this.oid.equals(ref.getOid());
	}
}
