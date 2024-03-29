package com.dentaltechapi.model.entities.tooth;

import com.dentaltechapi.model.entities.treatment.TreatmentModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Entity(name = "tb_tooth")
@NoArgsConstructor
@AllArgsConstructor
public class ToothModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String name;

    private String health;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String side;

    @Column(nullable = false)
    private Boolean present;

    private String observation;

    @OneToMany
    @JoinTable(
            name = "tb_tooth_treatment",
            joinColumns = @JoinColumn(name = "tooth_id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
    private List<TreatmentModel> treatments;

    public ToothModel(Integer number, String name, String location, String side, Boolean present) {
        this.number = number;
        this.name = name;
        this.location = location;
        this.side = side;
        this.present = present;
    }
}
