package com.dentalclinic.bookingservices.dentalbookingservices.exception.not_found;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalTime;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TimesNotFoundException extends RuntimeException{
    public TimesNotFoundException(LocalTime message){
        super(String.valueOf(message));
    }

}
