package com.dentalclinic.bookingservices.dentalbookingservices.entity.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByUid(String uid);
}
