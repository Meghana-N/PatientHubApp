package com.patienthub.patienthubapp.service;

import com.patienthub.patienthubapp.entity.Patient;
import com.patienthub.patienthubapp.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceImplTest {
    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    public void testSavePatientWithValidData() throws Exception {
        String string = "01-02-2023";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(string);

        Patient patient = new Patient();
        patient.setFirstName("Anu");
        patient.setLastName("Rai");
        patient.setId(123L);
        patient.setDateOfBirth(date);
        patient.setContactNumber("9545455");
        patient.setCurrentAddress("address 1");
        patient.setBloodGroup("A+");
        patient.setEmergencyContactNumber("973488");

        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
        Patient savedPatient = patientService.savePatient(patient);

        assertNotNull(savedPatient, "Saved patient should not be null");
        assertEquals(patient.getFirstName(), savedPatient.getFirstName());
        assertEquals(patient.getLastName(), savedPatient.getLastName());
    }

    @Test
    public void testFetchPatientList_ReturnsListOfPatients() throws Exception {
        String dateString1 = "01-02-2023";
        String dateString2 = "02-02-2023";

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = format.parse(dateString1);
        Date date2 = format.parse(dateString2);

        Patient patient1 = new Patient();
        patient1.setFirstName("Anu");
        patient1.setLastName("Rai");
        patient1.setId(123L);
        patient1.setDateOfBirth(date1);
        patient1.setContactNumber("9545455");
        patient1.setCurrentAddress("address 1");
        patient1.setBloodGroup("A+");
        patient1.setEmergencyContactNumber("973488");

        Patient patient2 = new Patient();
        patient1.setFirstName("Ram");
        patient1.setLastName("Roy");
        patient1.setId(1123L);
        patient1.setDateOfBirth(date2);
        patient1.setContactNumber("89545455");
        patient1.setCurrentAddress("address 2");
        patient1.setBloodGroup("A-");
        patient1.setEmergencyContactNumber("8973488");

        List<Patient> mockPatients = new ArrayList<>();
        mockPatients.add(patient1);
        mockPatients.add(patient2);
        Mockito.when(patientRepository.findAll()).thenReturn(mockPatients);

        List<Patient> fetchedPatients = patientService.fetchPatientList();

        assertNotNull(fetchedPatients, "Fetched patient list should not be null");
        assertFalse(fetchedPatients.isEmpty(), "Fetched patient list should not be empty");
        assertEquals(mockPatients.size(), fetchedPatients.size(), "List size mismatch");
    }

}