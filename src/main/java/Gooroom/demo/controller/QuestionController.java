package Gooroom.demo.controller;

import Gooroom.demo.domain.Question;
import Gooroom.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class QuestionController {

    final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String question_list(Model model){
        List<Question> questions =questionService.findQuestions().stream()
                        .sorted((q1, q2) -> q2.getQuestion_id().compareTo(q1.getQuestion_id()))
                                .collect(Collectors.toList());
        model.addAttribute("questionList",questions);
        return "Gooroom/question_list";
    }

    @GetMapping("/questions/new")
    public String createQuestion(){
        return "Gooroom/question_form";
    }

    @PostMapping("/questions/new")
    public String create(QuestionForm questionForm){
        Question question = new Question();
        question.setAuthor(questionForm.getAuthor());
        question.setContent(questionForm.getContent());
        question.setSubject(questionForm.getSubject());
        question.setCreate_date(LocalDateTime.now().toString());
        questionService.question_Create(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{question_id}")
    public String question_detail(@PathVariable("question_id") Long question_id, Model model){
        Optional<Question> question = questionService.findQuestion(question_id);
        Question que = question.orElseThrow(() -> new NoSuchElementException("질문이 없습니다" + question_id));
        model.addAttribute("question",que);
        return "Gooroom/question_detail";
    }

}
