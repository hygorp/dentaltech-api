package com.dentaltechapi.model.entities.treatment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.dentaltechapi.model.entities.patient.PatientModel;
import com.dentaltechapi.model.entities.procedure.ProcedureModel;
import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity(name = "tb_treatment")
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne
    @JsonIgnore
    private PatientModel patient;

    @OneToOne
    private ProcedureModel procedure;

    @OneToOne
    private SpecialistModel specialist;

    @Column(nullable = false)
    private String faces;

    private String observation;

    public TreatmentModel(LocalDate date, PatientModel patient, ProcedureModel procedure, SpecialistModel specialist, String faces) {
        this.date = date;
        this.patient = patient;
        this.procedure = procedure;
        this.specialist = specialist;
        this.faces = faces;
    }
}
