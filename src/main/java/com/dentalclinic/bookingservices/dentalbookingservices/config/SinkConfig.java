package com.dentalclinic.bookingservices.dentalbookingservices.config;

import com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment.AppointmentResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {
    @Bean
    public Sinks.Many<AppointmentResponseDTO> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<AppointmentResponseDTO> appointmentBroadcast(Sinks.Many<AppointmentResponseDTO> sink) {
        return sink.asFlux();
    }
}
