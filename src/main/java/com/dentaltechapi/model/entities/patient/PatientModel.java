package com.dentaltechapi.model.entities.patient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.dentaltechapi.model.entities.address.AddressModel;
import com.dentaltechapi.model.entities.allergy.AllergyModel;
import com.dentaltechapi.model.entities.anamnesis.AnamnesisModel;
import com.dentaltechapi.model.entities.emergencycontact.EmergencyContactModel;
import com.dentaltechapi.model.entities.comorbidity.ComorbidityModel;
import com.dentaltechapi.model.entities.healthinsurance.HealthInsuranceModel;
import com.dentaltechapi.model.entities.odontogram.OdontogramModel;
import com.dentaltechapi.model.entities.patient.responsible.ResponsibleModel;
import com.dentaltechapi.model.entities.phone.PhoneModel;
import com.dentaltechapi.model.entities.scheduling.SchedulingModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "tb_patient")
@NoArgsConstructor
@AllArgsConstructor
public class PatientModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String email;

    private String observation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_addresses",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private Set<AddressModel> addresses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_phones",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    private Set<PhoneModel> phones = new HashSet<>();

    @OneToOne
    @JsonIgnoreProperties(value = "patient")
    private OdontogramModel odontogram;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_responsible",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "responsible_id")
    )
    private Set<ResponsibleModel> responsibleList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_healthInsurances",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "healthInsurance_id")
    )
    private Set<HealthInsuranceModel> healthInsurances = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_comorbidities",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "comorbidity_id")
    )
    private Set<ComorbidityModel> comorbidities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_allergies",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    private Set<AllergyModel> allergies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_emergency_contacts",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "emergency_contact_id")
    )
    private Set<EmergencyContactModel> emergencyContacts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_anamnesis",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "anamnesis_id")
    )
    private Set<AnamnesisModel> anamnesisList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_patient_schedules",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "scheduling_id")
    )
    private Set<SchedulingModel> schedules;

    public PatientModel(String name, String cpf, String nationality, LocalDate birthdate, String email, String observation) {
        this.name = name;
        this.cpf = cpf;
        this.nationality = nationality;
        this.birthdate = birthdate;
        this.email = email;
        this.observation = observation;
    }
}
