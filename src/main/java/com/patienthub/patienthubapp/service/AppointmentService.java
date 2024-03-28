package com.patienthub.patienthubapp.service;

import com.patienthub.patienthubapp.customeexceptions.AppointmentNotFoundException;
import com.patienthub.patienthubapp.customeexceptions.PatientNotFoundException;
import com.patienthub.patienthubapp.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment) throws PatientNotFoundException;

    List<Appointment> fetchAppointmentList();

    Appointment fetchAppointmentById(Long appointmentId) throws AppointmentNotFoundException;

    Appointment updateAppointment(Long appointmentId, Appointment appointment) throws AppointmentNotFoundException;

    void deleteAppointmentById(Long appointmentId) throws AppointmentNotFoundException;

    List<Appointment> fetchAppointmentsByPatientId(Long patientId) throws AppointmentNotFoundException;
}
