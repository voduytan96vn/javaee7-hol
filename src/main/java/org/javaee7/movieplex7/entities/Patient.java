package org.javaee7.movieplex7.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Arun Gupta
 */
@Entity
@Table(name = "PATIENT")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Patient.findAll", query = "SELECT m FROM Patient m"), })
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@NotNull
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	@NotNull
	@Size(min = 1, max = 200)
	private String address;

	public Patient() {
	}

	public Patient(Long id) {
		this.id = id;
	}

	public Patient(Long id, String address) {
		this.id = id;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Patient)) {
			return false;
		}
		Patient other = (Patient) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return address;
	}

}
