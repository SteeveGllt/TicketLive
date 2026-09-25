package com.steeve.ticketlive.service;

import com.steeve.ticketlive.dto.categorieplace.CategoriePlaceRequest;
import com.steeve.ticketlive.dto.categorieplace.CategoriePlaceResponse;
import com.steeve.ticketlive.model.CategoriePlace;
import com.steeve.ticketlive.model.Concert;
import com.steeve.ticketlive.repository.CategoriePlaceRepository;
import com.steeve.ticketlive.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriePlaceService {

    private final CategoriePlaceRepository categoriePlaceRepository;
    private final ConcertRepository concertRepository;

    public CategoriePlaceService(CategoriePlaceRepository categoriePlaceRepository, ConcertRepository concertRepository){
        this.categoriePlaceRepository = categoriePlaceRepository;
        this.concertRepository = concertRepository;
    }

    public List<CategoriePlaceResponse> getCategoriesPlaces(){
        return categoriePlaceRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoriePlaceResponse getCategoriePlace(Long id){
        CategoriePlace categoriePlace = categoriePlaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoriePlace introuvable"));
        return toResponse(categoriePlace);
    }

    public CategoriePlaceResponse saveCategoriePlace(CategoriePlaceRequest request){
        Concert concert = concertRepository.findById(request.getConcertId())
                .orElseThrow(() -> new RuntimeException("Concert introuvable"));

        CategoriePlace categoriePlace = new CategoriePlace();
        categoriePlace.setConcert(concert);
        categoriePlace.setNom(request.getNom());
        categoriePlace.setPrix(request.getPrix());
        categoriePlace.setQuantiteTotale(request.getQuantiteTotale());
        categoriePlace.setQuantiteDisponible(request.getQuantiteTotale());

        CategoriePlace sauvegarde = categoriePlaceRepository.save(categoriePlace);
        return toResponse(sauvegarde);
    }

    public CategoriePlaceResponse updateCategoriePlace(final Long id, CategoriePlaceRequest request){
        CategoriePlace categoriePlace = categoriePlaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("N'exsite pas"));

        categoriePlace.setNom(request.getNom());
        categoriePlace.setPrix(request.getPrix());
        categoriePlace.setQuantiteTotale(request.getQuantiteTotale());

        CategoriePlace sauvegarde = categoriePlaceRepository.save(categoriePlace);
        return toResponse(sauvegarde);
    }

    public void deleteCategoriePlace(final Long id){
        if(!categoriePlaceRepository.existsById(id)) {
            throw new RuntimeException("CategoriePlace introuvable avec l'id " + id);
        }
        categoriePlaceRepository.deleteById(id);
    }

    private CategoriePlaceResponse toResponse(CategoriePlace categoriePlace){
        CategoriePlaceResponse response = new CategoriePlaceResponse();
        response.setId(categoriePlace.getId());
        response.setNom(categoriePlace.getNom());
        response.setPrix(categoriePlace.getPrix());
        response.setQuantiteTotale(categoriePlace.getQuantiteTotale());
        response.setQuantiteDisponible(categoriePlace.getQuantiteDisponible());
        response.setNomConcert(categoriePlace.getConcert().getTitreEvenement());
        return response;
    }
}
