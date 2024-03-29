package com.dentaltechapi.model.entities.procedure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_procedure")
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(nullable = false)
    private String procedure;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private Boolean status;

    public ProcedureModel(String procedure, Double cost, Boolean status) {
        this.procedure = procedure;
        this.cost = cost;
        this.status = status;
    }
}
