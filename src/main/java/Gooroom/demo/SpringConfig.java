package Gooroom.demo;

import Gooroom.demo.repository.JpaQuestionRepository;
import Gooroom.demo.repository.QuestionRepository;
import Gooroom.demo.service.QuestionService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }


    @Bean
    public QuestionRepository questionRepository() {
        return new JpaQuestionRepository(em);
    }

    @Bean
    public QuestionService questionService(){
        return new QuestionService(questionRepository());
    }

}
