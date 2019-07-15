/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.Category;
import com.valloc.Exposure;
import com.valloc.SpecConstants;
import com.valloc.object.domain.Config;

import javax.persistence.*;

/**
 * 
 *
 * 
 * @author wstevens
 */
@Entity
@Table(name="config")
@SequenceGenerator(
	    name="id_seq",
	    allocationSize=1,
	    sequenceName="config_id_seq"
	)
public class PersistentConfig extends UpdatableDomainObject implements Config 
{
	private Category category;
	private String key;
	private String value;
	private String description;
	private Exposure exposure;

	@Column(name="category", nullable=false)
	@Enumerated(EnumType.STRING)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name="key", nullable=false)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	@Column(name="value", nullable=false, length=SpecConstants.CONFIG_VAL_MAX_SIZE_VALUE)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="description", nullable=false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="exposure", nullable=false)
	@Enumerated(EnumType.STRING)
	public Exposure getExposure() {
		return exposure;
	}
	public void setExposure(Exposure exposure) {
		this.exposure = exposure;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		PersistentConfig other = (PersistentConfig) obj;
		if (category != other.getCategory())
			return false;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Config [category=" + category + ", key=" + key + ", value="
				+ value + ", ID=" + getId() + "]";
	}

	
}
