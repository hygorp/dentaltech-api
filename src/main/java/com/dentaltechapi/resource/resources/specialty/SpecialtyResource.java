package com.dentaltechapi.resource.resources.specialty;

import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.service.exceptions.specialty.SpecialtyNotFoundException;
import com.dentaltechapi.service.services.specialty.SpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specialty")
public class SpecialtyResource {

    private final SpecialtyService specialtyService;

    public SpecialtyResource(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpecialtyModel>> findAllSpecialties() {
        try {
            List<SpecialtyModel> specialties = specialtyService.findAllSpecialties();
            return ResponseEntity.status(HttpStatus.OK).body(specialties);
        } catch (SpecialtyNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
