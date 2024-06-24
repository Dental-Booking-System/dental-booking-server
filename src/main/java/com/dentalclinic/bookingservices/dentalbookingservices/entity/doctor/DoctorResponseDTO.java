package com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.Appointment;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DoctorResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String professionalStatement;
    private List<Integer> appointmentsID;

    public DoctorResponseDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.email = doctor.getEmail();
        this.phone = doctor.getPhone();
        this.birthDate = doctor.getBirthDate();
        this.professionalStatement = doctor.getProfessionalStatement();
        this.appointmentsID = doctor.getAppointments().stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());
    }
}
