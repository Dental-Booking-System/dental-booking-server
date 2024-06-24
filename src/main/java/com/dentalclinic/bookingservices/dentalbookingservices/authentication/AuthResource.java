package com.dentalclinic.bookingservices.dentalbookingservices.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

@RestController
public class AuthResource {

    @PostMapping ("/api/check-phone-number")
    public Boolean checkPhoneNumber(@RequestParam String phoneNumber) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByPhoneNumber("+" + phoneNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
