package com.steeve.ticketlive.controller;

import com.steeve.ticketlive.dto.concert.ConcertDetailResponse;
import com.steeve.ticketlive.dto.concert.ConcertRequest;
import com.steeve.ticketlive.dto.concert.ConcertResumeResponse;
import com.steeve.ticketlive.exception.ConcertNotFoundException;
import com.steeve.ticketlive.model.Concert;
import com.steeve.ticketlive.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @GetMapping
    public ResponseEntity<List<ConcertResumeResponse>> getConcerts(){
        return ResponseEntity.ok(concertService.getConcerts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ConcertDetailResponse> getConcert(@PathVariable Long id) throws ConcertNotFoundException {
        return ResponseEntity.ok(concertService.getConcert(id));
    }

    @PostMapping()
    public ResponseEntity<ConcertDetailResponse> save(@RequestBody ConcertRequest request){
        ConcertDetailResponse concertDetailResponse = concertService.saveConcert(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(concertDetailResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id){
        concertService.deleteConcert(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConcertDetailResponse> updateConcert(@PathVariable Long id, @RequestBody ConcertRequest request) {
        return ResponseEntity.ok(concertService.updateConcert(id, request));
    }


}
