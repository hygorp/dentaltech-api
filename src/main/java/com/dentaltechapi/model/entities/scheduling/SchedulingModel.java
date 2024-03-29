package com.dentaltechapi.model.entities.scheduling;

import com.dentaltechapi.model.entities.patient.PatientModel;
import com.dentaltechapi.model.entities.specialist.SpecialistModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Entity(name = "tb_scheduling")
@NoArgsConstructor
@AllArgsConstructor
public class SchedulingModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Instant time;

    @ManyToOne
    @JoinTable(name = "tb_scheduling_specialists", joinColumns = @JoinColumn(name = "scheduling_id"), inverseJoinColumns = @JoinColumn(name = "specialist_id"))
    private SpecialistModel specialist;

    @ManyToOne
    @JoinTable(name = "tb_scheduling_patients", joinColumns = @JoinColumn(name = "scheduling_id"), inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private PatientModel patient;
}
