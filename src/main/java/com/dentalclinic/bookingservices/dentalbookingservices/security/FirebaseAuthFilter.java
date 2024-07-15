package com.dentalclinic.bookingservices.dentalbookingservices.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
public class FirebaseAuthFilter extends GenericFilterBean {

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = ((HttpServletRequest) request).getHeader("Authorization");
        bearerToken = authorization;
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String token = getBearerToken(httpServletRequest);
        if (token == null) {
            System.out.println("No token provided");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided");
            return;
        }
        FirebaseToken decodedToken = null;
        FirebaseAuth auth = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            System.out.println(decodedToken);
        } catch (FirebaseAuthException e) {
            System.out.println(e);

            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.toString());
            return;
        }

        if (decodedToken == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
