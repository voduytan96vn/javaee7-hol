package org.javaee7.emr.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javaee7.movieplex7.entities.Patient;

public class PatientDto extends Dto {
	public PatientDto() {
		super();
	}

	private String account;
	private String name;
	private Patient users;
	private List<Patient> userList;

	private Date dateExpire;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Patient> getUserList() {
		if (userList == null) {
			userList = new ArrayList<Patient>();
		}
		return userList;
	}

	public void setUserList(List<Patient> userList) {
		this.userList = userList;
	}

	public Date getDateExpire() {
		return dateExpire;
	}

	public void setDateExpire(Date dateExpire) {
		this.dateExpire = dateExpire;
	}

	public Patient getUsers() {
		return users;
	}

	public void setUsers(Patient users) {
		this.users = users;
	}
}
