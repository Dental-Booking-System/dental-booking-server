package com.dentalclinic.bookingservices.dentalbookingservices.entity.patient;

import com.dentalclinic.bookingservices.dentalbookingservices.exception.not_found.PatientNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PatientResource {

    PatientRepository repository;

    public PatientResource(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/patients")
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = repository.findAll();
        return patients.stream()
                .map(PatientResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/patients/{uid}")
    public PatientResponseDTO getPatientsByUid(@PathVariable String uid) {
        Patient patient = repository.findByUid(uid)
                .orElseThrow(() -> new PatientNotFoundException("uid: " + uid));
        return new PatientResponseDTO(patient);
    }

    @PostMapping("/api/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequestDTO patient) {
        Patient checkPatient = repository.findByUid(patient.getUid())
                .orElseThrow(() -> new PatientNotFoundException("uid: " + patient.getUid()));
        if (checkPatient != null) {
            if (patient.getName() != null) checkPatient.setName(patient.getName());
            if (patient.getEmail() != null) checkPatient.setEmail(patient.getEmail());
            if (patient.getPhone() != null) checkPatient.setPhone(patient.getPhone());
            repository.save(checkPatient);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(checkPatient.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
        Patient newPatient = new Patient();
        newPatient.setUid(patient.getUid());
        newPatient.setName(patient.getName());
        newPatient.setEmail(patient.getEmail());
        newPatient.setPhone(patient.getPhone());
        newPatient.setAddress(patient.getAddress());
        newPatient.setBirthDate(patient.getBirthDate());
        repository.save(newPatient);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPatient.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/api/patients/{id}")
    public void deletePatientById(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
