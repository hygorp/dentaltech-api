package com.dentaltechapi.model.entities.specialist;

import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.model.entities.officedatetime.OfficeDateTimeModel;
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

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String cro;

    @Column(nullable = false)
    private String croState;

    @OneToOne
    private UserModel credentials;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "tb_specialist_phones",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    private Set<PhoneModel> phones;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_specialist_specialties",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<SpecialtyModel> specialties;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_specialist_office_date_time",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "office_date_time_id")
    )
    private Set<OfficeDateTimeModel> officeDateTime;
}

