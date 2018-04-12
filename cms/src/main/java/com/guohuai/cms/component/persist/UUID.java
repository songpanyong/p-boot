package com.guohuai.cms.component.persist;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

//JPA 基类的标识
@MappedSuperclass
public abstract class UUID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8010393090467301651L;
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	protected String oid;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	@Override
	public int hashCode() {
		return null == this.oid ? "".hashCode() : this.oid.hashCode();
	}

	@Override
	public String toString() {
		return null == this.oid ? "" : this.oid;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof UUID)) {
			return false;

		}
		UUID ref = (UUID) obj;
		return null == this.oid ? null == ref.getOid() : this.oid.equals(ref.getOid());

	}

}
