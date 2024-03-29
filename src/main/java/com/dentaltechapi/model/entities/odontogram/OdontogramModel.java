package com.dentaltechapi.model.entities.odontogram;

import com.dentaltechapi.model.entities.patient.PatientModel;
import com.dentaltechapi.model.entities.tooth.ToothModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "tb_odontogram")
@NoArgsConstructor
@AllArgsConstructor
public class OdontogramModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnoreProperties(value = "patient")
    private PatientModel patient;

    @OneToMany
    private Set<ToothModel> teeth = new HashSet<>();

    public OdontogramModel(PatientModel patient, Set<ToothModel> teeth) {
        this.patient = patient;
        this.teeth = teeth;
    }
}
