package com.steeve.ticketlive.service;

import com.steeve.ticketlive.dto.concert.ConcertDetailResponse;
import com.steeve.ticketlive.dto.concert.ConcertRequest;
import com.steeve.ticketlive.dto.concert.ConcertResumeResponse;
import com.steeve.ticketlive.exception.ConcertNotFoundException;
import com.steeve.ticketlive.model.Concert;
import com.steeve.ticketlive.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // génère le constructeur automatiquement pour tous les champs "final"
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertResumeResponse> getConcerts(){
        return concertRepository.findAll()
                .stream()
                .map(this::toResumeResponse)
                .toList();
    }

    public ConcertDetailResponse getConcert(final Long id) throws ConcertNotFoundException {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new ConcertNotFoundException(ConcertNotFoundException.ConcertNotFoundError.CONCERT_NOT_FOUND, String.format("Le concert %d n'existe pas", id)));

        return toDetailResponse(concert);
    }

    public ConcertDetailResponse saveConcert(ConcertRequest request){
        Concert concert = new Concert();
        concert.setNomArtiste(request.getNomArtiste());
        concert.setTitreEvenement(request.getTitreEvenement());
        concert.setDateConcert(request.getDateConcert());
        concert.setLieu(request.getLieu());
        concert.setVille(request.getVille());
        concert.setDescription(request.getDescription());
        concert.setImageUrl(request.getImageUrl());

        Concert sauvegarde = concertRepository.save(concert);

        return toDetailResponse(sauvegarde);
    }

    public void deleteConcert(final Long id){
        if (!concertRepository.existsById(id)){
            throw new RuntimeException("Le concert n'existe pas");
        }
        concertRepository.deleteById(id);
    }

    public ConcertDetailResponse updateConcert(final Long id, ConcertRequest request){
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new RuntimeException("Le concert n'existe pas"));

        concert.setNomArtiste(request.getNomArtiste());
        concert.setTitreEvenement(request.getTitreEvenement());
        concert.setDateConcert(request.getDateConcert());
        concert.setLieu(request.getLieu());
        concert.setVille(request.getVille());
        concert.setDescription(request.getDescription());
        concert.setImageUrl(request.getImageUrl());

        Concert sauvegarde = concertRepository.save(concert);

        return toDetailResponse(sauvegarde);
    }

    private ConcertResumeResponse toResumeResponse(Concert concert){
        ConcertResumeResponse response = new ConcertResumeResponse();
        response.setId(concert.getId());
        response.setNomArtiste(concert.getNomArtiste());
        response.setTitreEvenement(concert.getTitreEvenement());
        response.setDateConcert(concert.getDateConcert());
        response.setVille(concert.getVille());
        response.setImageUrl(concert.getImageUrl());

        return response;
    }

    private ConcertDetailResponse toDetailResponse(Concert concert){
        ConcertDetailResponse response = new ConcertDetailResponse();
        response.setId(concert.getId());
        response.setNomArtiste(concert.getNomArtiste());
        response.setTitreEvenement(concert.getTitreEvenement());
        response.setDateConcert(concert.getDateConcert());
        response.setLieu(concert.getLieu());
        response.setVille(concert.getVille());
        response.setDescription(concert.getDescription());
        response.setImageUrl(concert.getImageUrl());

        return response;
    }
}
