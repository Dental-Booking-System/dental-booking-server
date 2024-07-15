package com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice.DentalService;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor.Doctor;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.patient.Patient;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne()
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne()
    @JoinColumn(name = "dental_service_id", nullable = false)
    private DentalService dentalService;

    private LocalDateTime start;

    private Integer duration;
}
