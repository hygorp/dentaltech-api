package com.dentaltechapi.model.entities.specialist;

import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.model.entities.specialist.availability.AvailabilityModel;
import com.dentaltechapi.model.entities.specialty.SpecialtyModel;
import com.dentaltechapi.model.entities.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity(name = "tb_specialist")
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String signature;

    @Column(nullable = false)
    private String office;

    @Column(nullable = false)
    private String cpfOrCnpj;

    @Column(nullable = false)
    private String cro;

    @Column(nullable = false)
    private String croState;

    @OneToOne
    private UserModel credentials;

    @Column(nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "tb_specialist_phones", joinColumns = @JoinColumn(name = "specialist_id"), inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private Set<PhoneModel> phones;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "tb_specialist_specialties", joinColumns = @JoinColumn(name = "specialist_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<SpecialtyModel> specialties;

    @OneToOne
    private AvailabilityModel availability;

    public SpecialistModel(String name, String signature, String office, String cpfOrCnpj, String cro, String croState, UserModel credentials, String email) {
        this.name = name;
        this.signature = signature;
        this.office = office;
        this.cpfOrCnpj = cpfOrCnpj;
        this.cro = cro;
        this.croState = croState;
        this.credentials = credentials;
        this.email = email;
    }

    public SpecialistModel(String name, String signature, String office, String cpfOrCnpj, String cro, String croState, String email) {
        this.name = name;
        this.signature = signature;
        this.office = office;
        this.cpfOrCnpj = cpfOrCnpj;
        this.cro = cro;
        this.croState = croState;
        this.email = email;
    }
}
