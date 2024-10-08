package Gooroom.demo.repository;

import Gooroom.demo.controller.QuestionForm;
import Gooroom.demo.domain.Question;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class JpaQuestionRepository implements QuestionRepository{

    private final EntityManager em;

    public JpaQuestionRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Question save(Question question) {
        em.persist(question);
        return question;
    }

    @Override
    public List<Question> findAll() {
        return em.createQuery("select q from Question q", Question.class)
                .getResultList();
    }

    @Override
    public Optional<Question> findById(Long question_id) {
        Question question = em.find(Question.class, question_id); //조회할 타입이랑 식별자 넣어주면 알아서 찾음
        return Optional.ofNullable(question);
    }

    @Override
    public void delete(Long question_id) {
        Question question = em.find(Question.class, question_id);
        em.remove(question);
    }

    @Override
    public Question update(Long questoin_id, QuestionForm questionForm) {
        Question question = em.find(Question.class, questoin_id);
        question.setAuthor(questionForm.getAuthor());
        question.setContent(questionForm.getContent());
        question.setSubject(questionForm.getSubject());
        question.setCreate_date(LocalDateTime.now().toString());
        return em.merge(question);
    }
}
