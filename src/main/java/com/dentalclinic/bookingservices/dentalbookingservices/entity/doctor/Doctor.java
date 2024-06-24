package com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.Appointment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birthDate;

    private String professionalStatement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<Appointment> appointments;

}
