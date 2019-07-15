/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 *
 *
 * 
 * @author wstevens
 */
@MappedSuperclass
abstract class DomainObject
{
	private int id;
	private Date insertTimestamp;
	private Date deleteTimestamp;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_seq")
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Generated(GenerationTime.INSERT)
	@Column(name="insert_timestamp", insertable=false)
	public Date getInsertTimestamp() {
		return insertTimestamp;
	}
	public void setInsertTimestamp(Date insertTimestamp) {
		this.insertTimestamp = insertTimestamp;
	}


	@Column(name="delete_timestamp")
	public Date getDeleteTimestamp() {
		return deleteTimestamp;
	}
	public void setDeleteTimestamp(Date deleteTimestamp) {
		this.deleteTimestamp = deleteTimestamp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainObject other = (DomainObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
