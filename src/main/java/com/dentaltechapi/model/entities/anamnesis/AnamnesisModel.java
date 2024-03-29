package com.dentaltechapi.model.entities.anamnesis;

import com.dentaltechapi.model.entities.anamnesis.question.QuestionModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "tb_anamnesis")
@NoArgsConstructor
@AllArgsConstructor
public class AnamnesisModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mainComplaint;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "tb_anamnesis_question",
            joinColumns = @JoinColumn(name = "anamnesis_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<QuestionModel> questions = new HashSet<>();

    public AnamnesisModel(String name, String mainComplaint) {
        this.name = name;
        this.mainComplaint = mainComplaint;
    }
}
