package com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DentalServiceResource {

    DentalServiceRepository dentalServiceRepository;

    public DentalServiceResource(DentalServiceRepository dentalServiceRepository) {
        this.dentalServiceRepository = dentalServiceRepository;
    }

    @GetMapping("/api/dental-services")
    public List<DentalServiceResponseDTO> getDentalService() {
        List<DentalService> dentalServices = dentalServiceRepository.findAll();
        return dentalServices.stream().map(DentalServiceResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/api/dental-services/{id}")
    public DentalServiceResponseDTO getDentalServiceById(@PathVariable Integer id) {
        DentalService dentalService = dentalServiceRepository.findById(id)
                .orElseThrow();
        return new DentalServiceResponseDTO(dentalService);
    }

    @PostMapping("/api/dental-services")
    public ResponseEntity<DentalService> createDentalService(@RequestBody DentalService dentalService) {
        DentalService newService = dentalServiceRepository.save(dentalService);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newService.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
