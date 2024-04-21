package com.dentaltechapi.resource.resources.specialist;

import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import com.dentaltechapi.model.entities.specialist.dto.SpecialistDTO;
import com.dentaltechapi.service.exceptions.specialist.SpecialistCreationException;
import com.dentaltechapi.service.exceptions.specialist.SpecialistNotFoundException;
import com.dentaltechapi.service.services.specialist.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
public class SpecialistResource {

    private final SpecialistService specialistService;

    public SpecialistResource(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpecialistDTO>> findAllSpecialists() {
        try {
            return ResponseEntity.ok().body(specialistService.findAllSpecialists());
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

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createSpecialist(@RequestBody SpecialistModel specialistModel) {
        try {
            specialistService.createNewSpecialist(specialistModel);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SpecialistCreationException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
