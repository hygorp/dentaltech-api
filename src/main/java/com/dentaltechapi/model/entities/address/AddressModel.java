package com.dentaltechapi.model.entities.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_address")
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String number;

    private String complement;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    public AddressModel(String postalCode, String address, String district, String number, String complement, String city, String state) {
        this.postalCode = postalCode;
        this.address = address;
        this.district = district;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
    }
}
