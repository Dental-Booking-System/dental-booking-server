package com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDTO {
    private String patient_uid;
    private Integer doctor_id;
    private Integer dental_service_id;
    private LocalDateTime start;
}
