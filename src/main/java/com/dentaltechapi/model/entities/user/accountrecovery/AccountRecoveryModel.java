package com.dentaltechapi.model.entities.user.accountrecovery;

import com.dentaltechapi.model.entities.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Random;

@Data
@Entity(name = "tb_account_recovery")
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecoveryModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant startValidity;
    private Instant endValidity;
    private Integer code;

    @ManyToOne
    private UserModel user;

    public AccountRecoveryModel(Instant startValidity, Instant endValidity, UserModel user) {
        this.code = generateCode();
        this.startValidity = startValidity;
        this.endValidity = endValidity;
        this.user = user;
    }

    private static Integer generateCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
