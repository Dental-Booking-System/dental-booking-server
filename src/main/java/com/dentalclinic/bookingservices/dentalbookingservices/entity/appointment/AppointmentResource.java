package com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice.DentalService;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.dentalservice.DentalServiceRepository;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor.Doctor;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.doctor.DoctorRepository;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.patient.Patient;
import com.dentalclinic.bookingservices.dentalbookingservices.entity.patient.PatientRepository;
import com.dentalclinic.bookingservices.dentalbookingservices.exception.not_found.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
public class AppointmentResource {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DentalServiceRepository dentalServiceRepository;

    public AppointmentResource(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, DentalServiceRepository dentalServiceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.dentalServiceRepository = dentalServiceRepository;
    }

    @Autowired
    private Sinks.Many<AppointmentResponseDTO> appointmentSink;
    @Autowired
    private Flux<AppointmentResponseDTO> appointmentFlux;

    @CrossOrigin
    @GetMapping(value = "/api/appointments/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AppointmentResponseDTO> streamAppointments() {
        return this.appointmentFlux;
    }

    @GetMapping("/api/appointments")
    public List<AppointmentResponseDTO> getAppointmentsBetweenDates(
            @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Appointment> appointments;
        if (start != null && end != null) {
            appointments = appointmentRepository.findByStartTimeBetween(start, end);
        } else {
            appointments = appointmentRepository.findAll();
        }
        return appointments.stream().map(AppointmentResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/api/appointments/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Integer id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("id: " + id));
        return new AppointmentResponseDTO(appointment);
    }

    @GetMapping("/api/appointments/available-times")
    public List<LocalTime> getAvailableTimes(
            @RequestParam(value = "date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date,
            @RequestParam(value = "duration", required = true) Integer duration
    ) {
        return appointmentRepository.findAvailableTimes(date, duration);
    }

    @DeleteMapping("/api/appointments")
    public void deleteAppointment() {
        appointmentRepository.deleteAll();
    }

    @DeleteMapping("/api/appointments/{id}")
    public void deleteAppointmentById(@PathVariable Integer id) {
        appointmentRepository.deleteById(id);
    }

    @PostMapping("/api/appointments")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        // check if appointment time is available
        LocalTime localTime = appointmentRequestDTO.getStart_time().toLocalTime();
        LocalDate localDate = appointmentRequestDTO.getStart_time().toLocalDate();
        DentalService dentalService = dentalServiceRepository.findById(appointmentRequestDTO.getDental_service_id())
                .orElseThrow(() -> new DentalServiceNotFoundException("id: " + appointmentRequestDTO.getDental_service_id()));
        int duration = dentalService.getApproxDuration();
        List<LocalTime> availableTimes = appointmentRepository.findAvailableTimes(localDate, duration);
        if (!availableTimes.contains(localTime)) {
            throw new TimesNotFoundException(localTime);
        }

        Appointment appointment = new Appointment();
        appointment.setStartTime(appointmentRequestDTO.getStart_time());
        Patient patient = patientRepository.findByUid(appointmentRequestDTO.getPatient_uid())
                .orElseThrow(() -> new PatientNotFoundException("uid: " + appointmentRequestDTO.getPatient_uid()));
        Doctor doctor = doctorRepository.findById(appointmentRequestDTO.getDoctor_id())
                .orElseThrow(() -> new DoctorNotFoundException("id: " + appointmentRequestDTO.getDoctor_id()));
        appointment.setDuration(duration);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDentalService(dentalService);

        Appointment savedAppointment =  appointmentRepository.save(appointment);
        AppointmentResponseDTO savedAppointmentResponseDTO = new AppointmentResponseDTO(savedAppointment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAppointment.getId())
                .toUri();
        appointmentSink.tryEmitNext(savedAppointmentResponseDTO);
        return ResponseEntity.created(location).build();
    }

}
