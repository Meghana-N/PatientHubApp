package com.patienthub.patienthubapp.controller;

import com.patienthub.patienthubapp.customeexceptions.AppointmentNotFoundException;
import com.patienthub.patienthubapp.customeexceptions.PatientNotFoundException;
import com.patienthub.patienthubapp.entity.Appointment;
import com.patienthub.patienthubapp.entity.Patient;
import com.patienthub.patienthubapp.service.AppointmentService;
import com.patienthub.patienthubapp.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @PostMapping("/addPatient")
    public Patient savePatient(@Valid @RequestBody Patient patient) {
        LOGGER.info("Attempting to add Patient info into DB");
        return patientService.savePatient(patient);
    }

    @GetMapping("/patients")
    public List<Patient> fetchPatientsList() {
        LOGGER.info("Fetching patient details list");
        return patientService.fetchPatientList();
    }

    @GetMapping("/patient/{id}")
    public Patient fetchPatientById(@PathVariable("id") Long patientId) throws PatientNotFoundException {
        LOGGER.info("Fetching patient's info");
        return patientService.fetchPatientById(patientId);
    }

    @PutMapping("/patient/{id}")
    public Patient updatePatientInfo(@PathVariable("id") Long patientId, @RequestBody Patient patient) throws PatientNotFoundException {
        LOGGER.info("Attempting to update Patient info in the DB");
        return patientService.updatePatient(patientId,patient);
    }

    @DeleteMapping("/patient/{id}")
    public String deletePatientById(@PathVariable("id") Long patientId) throws PatientNotFoundException {
        LOGGER.info("Attempting to delete Patient info from the DB");
        patientService.deletePatientById(patientId);
        return "Patient info has been deleted";
    }

    // Get appointments with respect to particular Patient's ID
    @GetMapping("/patients/{id}/appointments")
    public List<Appointment> fetchAppointmentsList(@PathVariable("id") Long patientId) throws AppointmentNotFoundException {
        LOGGER.info("Fetching appointment details of particular patient");
        return appointmentService.fetchAppointmentsByPatientId(patientId);
    }
}
