package com.patienthub.patienthubapp.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointmentId;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date appointmentDate;

    @NotNull
    private String hospitalName;

    @NotNull
    private String location;

    @NotNull
    private String doctorName;

    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            referencedColumnName = "id")
    @JsonBackReference
    private Patient patient;
}
