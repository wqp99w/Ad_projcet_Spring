package Gooroom.demo.repository;

import Gooroom.demo.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);
    List<Question> findAll();
    Optional<Question> findById(Long question_id);

}
