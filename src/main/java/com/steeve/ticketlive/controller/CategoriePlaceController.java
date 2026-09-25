package com.steeve.ticketlive.controller;

import com.steeve.ticketlive.dto.categorieplace.CategoriePlaceRequest;
import com.steeve.ticketlive.dto.categorieplace.CategoriePlaceResponse;
import com.steeve.ticketlive.model.CategoriePlace;
import com.steeve.ticketlive.service.CategoriePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories-places")
@RequiredArgsConstructor
public class CategoriePlaceController {

    private final CategoriePlaceService categoriePlaceService;

    @GetMapping
    public ResponseEntity<List<CategoriePlaceResponse>> getCategorieServices(){
        return ResponseEntity.ok(categoriePlaceService.getCategoriesPlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriePlaceResponse> getCategorieService(@PathVariable Long id){
        return ResponseEntity.ok(categoriePlaceService.getCategoriePlace(id));
    }

    @PostMapping
    public ResponseEntity<CategoriePlaceResponse> saveCategoriePlace(@RequestBody CategoriePlaceRequest request){
        CategoriePlaceResponse response = categoriePlaceService.saveCategoriePlace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriePlaceResponse> updateCategoriePlace(@PathVariable Long id, @RequestBody CategoriePlaceRequest request){
        return ResponseEntity.ok(categoriePlaceService.updateCategoriePlace(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriePlace(@PathVariable Long id){
        categoriePlaceService.deleteCategoriePlace(id);
        return ResponseEntity.noContent().build();
    }
}
