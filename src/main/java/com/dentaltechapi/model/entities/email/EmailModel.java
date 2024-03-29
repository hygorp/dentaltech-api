package com.dentaltechapi.model.entities.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String mailTo;
    private String mailCc;
    private String mailSubject;
    private String mailBody;
}
