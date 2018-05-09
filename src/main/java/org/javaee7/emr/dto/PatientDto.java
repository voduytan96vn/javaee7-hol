package org.javaee7.emr.dto;

import java.util.ArrayList;
import java.util.List;

import org.javaee7.movieplex7.entities.Patient;

public class PatientDto extends Dto {
	private static final long serialVersionUID = -3120195622004936848L;

	public PatientDto() {
		super();
	}

	private String address;
	private String name;
	private Patient patient;
	private List<Patient> patientList;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Patient> getPatientList() {
		if (patientList == null) {
			patientList = new ArrayList<Patient>();
		}
		return patientList;
	}

	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
