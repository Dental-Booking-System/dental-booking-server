package com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.Appointment;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DentalServiceResponseDTO {

    private Integer id;
    private String name;
    private Integer approxDuration;
    private List<Integer> appointmentsID;

    public DentalServiceResponseDTO(DentalService dentalService) {
        this.id = dentalService.getId();
        this.name = dentalService.getName();
        this.approxDuration = dentalService.getApproxDuration();
        this.appointmentsID = dentalService.getAppointments()
                .stream().map(Appointment::getId).collect(Collectors.toList());
    }
}
