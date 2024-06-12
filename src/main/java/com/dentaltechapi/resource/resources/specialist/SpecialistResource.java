package com.dentaltechapi.resource.resources.specialist;

import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import com.dentaltechapi.model.entities.specialist.dto.SpecialistDTO;
import com.dentaltechapi.service.exceptions.specialist.SpecialistCreationException;
import com.dentaltechapi.service.exceptions.specialist.SpecialistNotFoundException;
import com.dentaltechapi.service.services.specialist.SpecialistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specialist")
public class SpecialistResource {

    private final SpecialistService specialistService;

    public SpecialistResource(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SpecialistDTO>> findAllSpecialists(Pageable pageable) {
        try {
            Page<SpecialistDTO> specialistsPage = specialistService.findAllSpecialists(pageable);
            return ResponseEntity.ok().body(specialistsPage);
        } catch (SpecialistNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistDTO> findSpecialistById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(specialistService.findSpecialistById(id));
        } catch (SpecialistNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/filter-specialists")
    public ResponseEntity<Page<SpecialistDTO>> filterSpecialistsByNameAndSpecialty(
            Pageable pageable,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "specialties", required = false) Long[] specialties
            ) {
        try {
            Page<SpecialistDTO> specialistsPage = specialistService.filterSpecialistsByNameAndSpecialty(pageable, name, specialties);
            return ResponseEntity.ok().body(specialistsPage);
        } catch (SpecialistNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createSpecialist(@RequestBody SpecialistModel specialistModel) {
        try {
            specialistService.createNewSpecialist(specialistModel);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SpecialistCreationException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/verify-existing-cpf/{cpf}")
    public ResponseEntity<?> verifyExistingCpf(@PathVariable String cpf) {
        try {
            if (specialistService.verifyExistingCpf(cpf)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } catch (SpecialistNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
