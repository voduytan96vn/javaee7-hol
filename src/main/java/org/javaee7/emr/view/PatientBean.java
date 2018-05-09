package org.javaee7.emr.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.javaee7.emr.dto.PatientDto;

@ManagedBean(name = "patientBean")
@ViewScoped
public class PatientBean extends BaseBean {
	private PatientDto dto;

	public PatientBean() {
		super();
	}

	@Override
	public void init() throws Exception {
		dto = getDto();
		Long id = getParam(ID);
		dto.setId(id);
		doService("init", dto);
	}

	@Override
	public String getScreen() {
		return "";
	}

	public void searchAdvance() throws Exception {
		doService("searchAdvance", dto);
	}
}
