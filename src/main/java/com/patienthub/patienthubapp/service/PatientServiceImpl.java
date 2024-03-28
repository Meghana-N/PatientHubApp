package com.patienthub.patienthubapp.service;

import com.patienthub.patienthubapp.customeexceptions.PatientNotFoundException;
import com.patienthub.patienthubapp.entity.Patient;
import com.patienthub.patienthubapp.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    public static final String MY_KEY = "mykey";

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @CachePut(key = "#patient.id", value="patient")
    @CacheEvict(key="#root.target.MY_KEY", value = "patients")
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    @Cacheable(value="patients", key = "#root.target.MY_KEY")
    public List<Patient> fetchPatientList() {
        log.info("making DB call inside fetchPatientList()");
        return patientRepository.findAll();
    }

    @Override
    @Cacheable(value = "patient", key = "#patientId")
    public Patient fetchPatientById(Long patientId) throws PatientNotFoundException {
        log.info("making DB call inside fetchPatientById()");

        Optional<Patient> patient = patientRepository.findById(patientId);
        if(!patient.isPresent()) {
            throw new PatientNotFoundException("Patient data is not available");
        }
        return  patient.get();
    }

    @Override
    @CachePut(key = "#patientId", value="patient")
    @CacheEvict(key="#root.target.MY_KEY", value = "patients")
    public Patient updatePatient(Long patientId, Patient patient) throws PatientNotFoundException {
        log.info("making DB call inside updatePatient()");

        Patient existingPatientInfo = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient info not found"));

        existingPatientInfo.setFirstName(patient.getFirstName());
        existingPatientInfo.setMiddleName(patient.getMiddleName());
        existingPatientInfo.setLastName(patient.getLastName());
        existingPatientInfo.setDateOfBirth(patient.getDateOfBirth());
        existingPatientInfo.setContactNumber(patient.getContactNumber());
        existingPatientInfo.setEmailId(patient.getEmailId());
        existingPatientInfo.setCurrentAddress(patient.getCurrentAddress());
        existingPatientInfo.setBloodGroup(patient.getBloodGroup());
        existingPatientInfo.setEmergencyContactNumber((patient.getEmergencyContactNumber()));

        return patientRepository.save(existingPatientInfo);
    }

    @Override
    @Caching( evict = {
            @CacheEvict(key = "#patientId", value="patient"),
            @CacheEvict(key="#root.target.MY_KEY", value = "patients")})
    public void deletePatientById(Long patientId) throws PatientNotFoundException {
        log.info("making DB call inside deletePatientById()");
        Patient existingPatientInfo = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient info not found"));
        patientRepository.deleteById(patientId);
    }

}