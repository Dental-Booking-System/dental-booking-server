package com.dentalclinic.bookingservices.dentalbookingservices.entity.patient;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.Appointment;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PatientResponseDTO {
    private Integer id;
    private String uid;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private List<Integer> appointmentsID;

    public PatientResponseDTO(Patient patient) {
        this.id = patient.getId();
        this.uid = patient.getUid();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.address = patient.getAddress();
        this.birthDate = patient.getBirthDate();
        this.appointmentsID = patient.getAppointments().stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());
    }
}
