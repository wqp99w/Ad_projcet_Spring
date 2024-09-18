package Gooroom.demo.repository;

import Gooroom.demo.controller.QuestionForm;
import Gooroom.demo.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);
    List<Question> findAll();
    Optional<Question> findById(Long question_id);

    void delete(Long question_id);

    Question update(Long question_id, QuestionForm questionForm);
}
