package org.javaee7.emr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.javaee7.emr.client.LocalService;
import org.javaee7.emr.client.ParamQuery;
import org.javaee7.emr.client.RemoteService;
import org.javaee7.emr.dto.PatientDto;
import org.javaee7.movieplex7.entities.Patient;

@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(mappedName = "SKY", name = "patientService")
public class PatientService extends CommonService implements RemoteService, LocalService {
	public PatientService() {
		super();
	}

	public void init(PatientDto dto) throws Exception {
		Patient patient = findEntity(Patient.class, dto.getId());
		if (patient == null) {
			patient = new Patient();
		}
		dto.setPatient(patient);
	}

	public void searchAdvance(PatientDto dto) {

		ParamQuery paramQuery = new ParamQuery();

		paramQuery.setName("Patient.findAll");
		List<Patient> list = getEntities(paramQuery);
		setIndex(list, 1);
		dto.setPatientList(list);
	}
}
