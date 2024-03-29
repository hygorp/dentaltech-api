package com.dentaltechapi.model.entities.comorbidity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_comorbidity")
@NoArgsConstructor
@AllArgsConstructor
public class ComorbidityModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cid;

    @Column(nullable = false)
    private String cidDescription;

    public ComorbidityModel(String cid, String cidDescription) {
        this.cid = cid;
        this.cidDescription = cidDescription;
    }
}
