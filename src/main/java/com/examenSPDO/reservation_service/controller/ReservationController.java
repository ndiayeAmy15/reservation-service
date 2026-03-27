package com.examenSPDO.reservation_service.controller;

import com.examenSPDO.reservation_service.dto.Reservation;
import com.examenSPDO.reservation_service.service.IReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final IReservationService iReservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(){
        return new ResponseEntity<>(iReservationService.getReservations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id){
        return new ResponseEntity<>(iReservationService.getReservation(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation){
        return new ResponseEntity<>(iReservationService.createReservation(reservation),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,@Valid @RequestBody Reservation reservation){
        return new ResponseEntity<>(iReservationService.updateReservation(id,reservation),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){
         iReservationService.deleteReservation(id);
        return new ResponseEntity<>("La chambre avec l'id : " + id + "  est supprimée avec succès",
                HttpStatus.OK);
    }


}
