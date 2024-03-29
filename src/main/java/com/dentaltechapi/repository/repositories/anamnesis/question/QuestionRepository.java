package com.dentaltechapi.repository.repositories.anamnesis.question;

import com.dentaltechapi.model.entities.anamnesis.question.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {

}
