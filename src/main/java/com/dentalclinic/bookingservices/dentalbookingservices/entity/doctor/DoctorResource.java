package com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor;
import com.dentalclinic.bookingservices.dentalbookingservices.exception.not_found.DoctorNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DoctorResource {

    private final DoctorRepository repository;

    public DoctorResource(DoctorRepository repository) {
        this.repository = repository;
    }

   // Get /doctors
    @GetMapping("/api/doctors")
    public List<DoctorResponseDTO> retrieveAllDoctors() {
        List<Doctor> doctors = repository.findAll();
        return doctors.stream().map(DoctorResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Get /doctors
    @GetMapping("/api/doctors/{id}")
    public DoctorResponseDTO retrieveDoctor(@PathVariable Integer id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("id: " + id));
        return new DoctorResponseDTO(doctor);
    }

    @PostMapping("/api/doctors")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor newDoctor = repository.save(doctor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDoctor.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
