package com.dentalclinic.bookingservices.dentalbookingservices.entity.appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT a.* FROM appointment a " +
            "JOIN patient p ON a.patient_id = p.id " +
            "JOIN doctor d ON a.doctor_id = d.id " +
            "JOIN dental_service s ON a.dental_service_id = s.id " +
            "WHERE start_time BETWEEN ?1 AND ?2",
            nativeQuery = true)
    List<Appointment> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "SELECT a.* FROM appointment a WHERE DATE(a.start_time) = ?1",
    nativeQuery = true)
    List<Appointment> findByDate(LocalDate date);

     default List<LocalTime> findAvailableTimes(
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date,
             int duration) {
         List<Appointment> appointments = findByDate(date);
         LocalTime startOfDay = LocalTime.of(9, 0);
         LocalTime endOfDay = LocalTime.of(17,0);

         // Create a list to hold all possible times
         List<LocalTime> allTimes = new ArrayList<>();
         int slotDuration = 30;
         for (LocalTime time = startOfDay; time.isBefore(endOfDay); time = time.plusMinutes(slotDuration)) {
             allTimes.add(time);
         }

         // Remove the times that are already taken
         for (Appointment appointment : appointments) {
             LocalTime startTime = appointment.getStartTime().toLocalTime();
             LocalTime endTime = startTime.plusMinutes(appointment.getDuration());

             for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(slotDuration)) {
                 allTimes.remove(time);
             }
         }

         List<LocalTime> availableTimes = new ArrayList<>();
         int slotsNeeded = duration / slotDuration;
         for (int i = 0; i < allTimes.size(); i++) {
             boolean available = true;
             for (int j = 0; j < slotsNeeded; j++) {
                 if (i + j >= allTimes.size() || !allTimes.contains(allTimes.get(i).plusMinutes((j + 1) * slotDuration))) {
                     available = false;
                     break;
                 }
             }
             if (available) {
                 availableTimes.add(allTimes.get(i));
             }
         }

         return availableTimes;
    }
}
