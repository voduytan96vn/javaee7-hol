package org.javaee7.emr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gen_id")
public class GenId implements Serializable {
	private static final long serialVersionUID = 2983667620642499011L;

	@Id
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "value")
	private Long value;

	public GenId() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return this.value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
