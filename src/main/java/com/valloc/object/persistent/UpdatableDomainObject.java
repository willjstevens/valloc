/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 *
 *
 * 
 * @author wstevens
 */
@MappedSuperclass
abstract class UpdatableDomainObject extends DomainObject
{
	private Date updateTimestamp;
	
	/* (non-Javadoc)
	 * @see com.valloc.domain.DomainObject#getupdateTimestamp()
	 */
	@Column(name="update_timestamp")
	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	/* (non-Javadoc)
	 * @see com.valloc.domain.DomainObject#setupdateTimestamp(java.util.Date)
	 */
	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
}
