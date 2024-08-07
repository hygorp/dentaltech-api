package com.dentaltechapi.model.entities.user.accountrecovery;

import com.dentaltechapi.model.entities.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Random;

@Data
@Entity(name = "tb_account_recovery")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "code")
public class AccountRecoveryModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false, unique = true)
    private Integer code;

    @Column(nullable = false)
    private Instant startValidity;

    @Column(nullable = false)
    private Instant endValidity;

    @Column(nullable = false)
    private Boolean isValid;


    @ManyToOne
    private UserModel user;

    public AccountRecoveryModel(Instant startValidity, Instant endValidity, Boolean isValid, UserModel user) {
        this.code = generateCode();
        this.startValidity = startValidity;
        this.endValidity = endValidity;
        this.isValid = isValid;
        this.user = user;
    }

    private static Integer generateCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
