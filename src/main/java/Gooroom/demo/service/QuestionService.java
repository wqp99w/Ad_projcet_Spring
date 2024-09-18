package Gooroom.demo.service;

import Gooroom.demo.controller.QuestionForm;
import Gooroom.demo.domain.Question;
import Gooroom.demo.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Long question_Create(Question question){
        questionRepository.save(question);
        return question.getQuestion_id();
    }

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> findQuestion(Long question_id){
        return questionRepository.findById(question_id);
    }

    public void delete(Long question_id){
        questionRepository.delete(question_id);
    }

    public Long update(Long quesion_id, QuestionForm questionForm){
        questionRepository.update(quesion_id, questionForm);
        return quesion_id;
    }
}
