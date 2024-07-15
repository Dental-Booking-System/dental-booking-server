package com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice.DentalServiceResponseDTO;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor.DoctorResponseDTO;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.patient.PatientResponseDTO;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentResponseDTO {
    private Integer id;
    private DoctorResponseDTO doctor;
    private PatientResponseDTO patient;
    private DentalServiceResponseDTO dentalService;
    private LocalDateTime start;
    private Integer duration;

    public AppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.doctor = new DoctorResponseDTO(appointment.getDoctor());
        this.patient = new PatientResponseDTO(appointment.getPatient());
        this.dentalService = new DentalServiceResponseDTO(appointment.getDentalService());
        this.start = appointment.getStart();
        this.duration = appointment.getDuration();
    }
}
