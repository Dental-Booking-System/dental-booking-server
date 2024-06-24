package com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.Appointment;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DentalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer approxDuration;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dentalService")
    private List<Appointment> appointments;

}
