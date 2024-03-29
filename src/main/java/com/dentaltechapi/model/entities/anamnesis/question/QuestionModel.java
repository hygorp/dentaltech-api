package com.dentaltechapi.model.entities.anamnesis.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity(name = "tb_question")
@NoArgsConstructor
@AllArgsConstructor
public class QuestionModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    private String observation;

    public QuestionModel(String question, String answer, String observation) {
        this.question = question;
        this.answer = answer;
        this.observation = observation;
    }
}
