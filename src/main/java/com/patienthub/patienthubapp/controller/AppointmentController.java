package com.patienthub.patienthubapp.controller;

import com.patienthub.patienthubapp.customeexceptions.AppointmentNotFoundException;
import com.patienthub.patienthubapp.customeexceptions.PatientNotFoundException;
import com.patienthub.patienthubapp.entity.Appointment;
import com.patienthub.patienthubapp.service.AppointmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    
    private final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    @PostMapping("/createAppointment")
    public Appointment createAppointment(@Valid @RequestBody Appointment appointment) throws PatientNotFoundException {
        LOGGER.info("Attempting to create an appointment for the specific patient");
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping("/appointments")
    public List<Appointment> fetchAppointmentsList() {
        LOGGER.info("Fetching appointment details list");
        return appointmentService.fetchAppointmentList();
    }

    @GetMapping("/appointment/{id}")
    public Appointment fetchAppointmentById(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        LOGGER.info("Fetching appointment info");
        return appointmentService.fetchAppointmentById(appointmentId);
    }

    @PutMapping("/appointment/{id}")
    public Appointment updateAppointmentInfo(@PathVariable("id") Long appointmentId, @RequestBody Appointment appointment) throws AppointmentNotFoundException {
        LOGGER.info("Attempting to update Appointment info in the DB");
        return appointmentService.updateAppointment(appointmentId,appointment);
    }

    @DeleteMapping("/appointment/{id}")
    public String deleteAppointmentById(@PathVariable("id") Long appointmentId) throws AppointmentNotFoundException {
        LOGGER.info("Attempting to delete Appointment info from the DB");
        appointmentService.deleteAppointmentById(appointmentId);
        return "Appointment info has been deleted";
    }

}
