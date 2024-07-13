package com.dentaltechapi.resource.resources.specialty;

import com.dentaltechapi.exceptions.SpecialtyException;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.services.exceptions.specialty.SpecialtyNotFoundException;
import com.dentaltechapi.services.services.specialty.SpecialtyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/specialty")
public class SpecialtyResource {

    private final SpecialtyService specialtyService;

    public SpecialtyResource(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<SpecialtyModel>> findAll(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<SpecialtyModel> specialties = specialtyService.findAll(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(specialties);
        } catch (SpecialtyNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/find-by-filters")
    public ResponseEntity<Page<SpecialtyModel>> findByFilters(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "name", required = false) String name
    ) {
        try {
            Page<SpecialtyModel> specialties = specialtyService.findByFilters(pageable, name);
            return ResponseEntity.status(HttpStatus.OK).body(specialties);
        } catch (SpecialtyException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            SpecialtyModel specialty = specialtyService.findById(id);
            return ResponseEntity.ok().body(specialty);
        } catch (SpecialtyException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SpecialtyModel specialtyModel) {
        try {
            SpecialtyModel specialty = specialtyService.save(specialtyModel);

            return ResponseEntity.created(URI.create("/api/specialty/" + specialty.getId())).build();
        } catch (SpecialtyException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SpecialtyModel specialtyModel) {
        try {
            specialtyService.update(specialtyModel, specialtyModel.getId());

            return ResponseEntity.ok().build();
        } catch (SpecialtyException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            specialtyService.delete(id);

            return ResponseEntity.noContent().build();
        } catch (SpecialtyException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
