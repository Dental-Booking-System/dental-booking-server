package com.dentalclinic.bookingservices.dentalbookingservices.entity.patient;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.Appointment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    private String uid;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private List<Appointment> appointments;

}
