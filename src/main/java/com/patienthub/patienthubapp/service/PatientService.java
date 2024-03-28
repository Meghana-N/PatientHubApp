package com.patienthub.patienthubapp.service;

import com.patienthub.patienthubapp.customeexceptions.PatientNotFoundException;
import com.patienthub.patienthubapp.entity.Patient;

import java.util.List;

public interface PatientService {
    public Patient savePatient(Patient patient);

    public List<Patient> fetchPatientList();

    Patient fetchPatientById(Long patientId) throws PatientNotFoundException;

    Patient updatePatient(Long patientId, Patient patient) throws PatientNotFoundException;

    void deletePatientById(Long patientId) throws PatientNotFoundException;
}
