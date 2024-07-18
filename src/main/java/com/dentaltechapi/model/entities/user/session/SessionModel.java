package com.dentaltechapi.model.entities.user.session;

import com.dentaltechapi.model.entities.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity(name = "tb_session")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SessionModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Boolean isValid;
    private Instant startValidity;
    private Instant endValidity;
    private String token;

    @ManyToOne
    private UserModel user;

    public SessionModel(Boolean isValid, Instant startValidity, Instant endValidity, String token, UserModel user) {
        this.isValid = isValid;
        this.startValidity = startValidity;
        this.endValidity = endValidity;
        this.token = token;
        this.user = user;
    }
}
