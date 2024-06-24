package com.dentalclinic.bookingservices.dentalbookingservices.entity.patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientRequestDTO {
    private String uid;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;

}
