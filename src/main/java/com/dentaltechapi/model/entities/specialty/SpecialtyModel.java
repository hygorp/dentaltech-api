package com.dentaltechapi.model.entities.specialty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_specialty")
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String specialty;

    private String description;

    public SpecialtyModel(String specialty) {
        this.specialty = specialty;
    }

    public SpecialtyModel(String specialty, String description) {
        this.specialty = specialty;
        this.description = description;
    }
}
