package com.dentaltechapi.model.entities.emergencycontact;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_emergency_contact")
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    public EmergencyContactModel(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }
}
