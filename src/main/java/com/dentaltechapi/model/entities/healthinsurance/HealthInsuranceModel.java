package com.dentaltechapi.model.entities.healthinsurance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_health_insurance")
@NoArgsConstructor
@AllArgsConstructor
public class HealthInsuranceModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operator;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String holder;

    @Column(nullable = false)
    private String holderCpf;

    public HealthInsuranceModel(String operator, String number, String holder, String holderCpf) {
        this.operator = operator;
        this.number = number;
        this.holder = holder;
        this.holderCpf = holderCpf;
    }
}
