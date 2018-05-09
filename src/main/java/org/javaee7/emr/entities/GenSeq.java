package org.javaee7.emr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gen_seq")
public class GenSeq implements Serializable {
	private static final long serialVersionUID = 2406784919686998570L;

	@Id
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "value")
	private Long value;

	@Column(name = "seq_date")
	private String seqDate;

	public GenSeq() {
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

	public String getSeqDate() {
		return this.seqDate;
	}

	public void setSeqDate(String seqDate) {
		this.seqDate = seqDate;
	}

}