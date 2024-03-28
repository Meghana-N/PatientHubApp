package com.patienthub.patienthubapp.service;

import com.patienthub.patienthubapp.customeexceptions.AppointmentNotFoundException;
import com.patienthub.patienthubapp.customeexceptions.PatientNotFoundException;
import com.patienthub.patienthubapp.entity.Appointment;
import com.patienthub.patienthubapp.repository.AppointmentRepository;
import com.patienthub.patienthubapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) throws PatientNotFoundException {
        patientRepository.findById(appointment.getPatient().getId()).orElseThrow(() -> new PatientNotFoundException("Patient record does not exist"));
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> fetchAppointmentList() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment fetchAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(!appointment.isPresent()) {
            throw new AppointmentNotFoundException("No appointments are available with ID:" + appointmentId);
        }
        return  appointment.get();
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, Appointment appointment) throws AppointmentNotFoundException {
        Appointment existingAppointmentInfo = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        existingAppointmentInfo.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointmentInfo.setDoctorName(appointment.getDoctorName());
        existingAppointmentInfo.setHospitalName(appointment.getHospitalName());
        existingAppointmentInfo.setPatient(appointment.getPatient());
        existingAppointmentInfo.setLocation(appointment.getLocation());

        return appointmentRepository.save(existingAppointmentInfo);
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
        Appointment existingAppointmentInfo = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public List<Appointment> fetchAppointmentsByPatientId(Long patientId) throws AppointmentNotFoundException {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        if(appointments.size() <= 0) {
            throw new AppointmentNotFoundException("No appointments are available for the Patient: " + patientId);
        }
        return appointments;
    }

}
