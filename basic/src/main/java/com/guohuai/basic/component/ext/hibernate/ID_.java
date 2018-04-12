package com.guohuai.basic.component.ext.hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * hotfix for compliant hibernate-5.2.0
 * 
 * @author Arthur
 *
 */
@MappedSuperclass
public abstract class ID_ {

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
		if (!(obj instanceof ID_)) {
			return false;

		}
		ID_ ref = (ID_) obj;
		return null == this.oid ? null == ref.getOid() : this.oid.equals(ref.getOid());
	}
}
