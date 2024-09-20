# Spring MVC 패턴을 이용하여 웹페이지 CRUD 기능 구현

2024.06

## 프로젝트 개요

웹서버컴퓨팅 강의에서 배운 Django MTV 패턴과 대응되는 Spring MVC 패턴을 이용하여 웹페이지의 기본적인 CRUD 기능을 구현하였습니다.

## 개발 환경

+ FrontEnd: Tymleaf, Bootstrap
+ BackEnd : Spring Boot
+ Database : MySQL
+ IDE : InteliJ Ultimate

## 주요 구조

### [Model]

```java

public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;
    private String author;
    private String subject;
    @Lob
    private String content;
    private String create_date;

//중략

}
```

+ 안드로이드 앱과 스프링 서버의 통신을 위해 Restful API를 사용하여 안드로이드 앱과의 호환성을 높였습니다.
  

### [View]

```html

<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>AD project</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="container">
    <h1 class="text-center">질문 상세</h1>
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <div>
                <h2 th:text="${question.getSubject()}">제목</h2>
            </div>
            <div>
                <small class="small-text" th:text="${question.getCreate_date()}">2024-06-03 12:00</small>
            </div>
        </div>
        <div class="card-body">
            <p th:text="${question.getContent()}">내용</p>
            <footer class="blockquote-footer" th:text="${question.getAuthor()}">작성자</footer>
        </div>
    </div>
<!-- 중략 -->

```

+ 레포지토리를 이용하여 데이터 접근 로직을 레포지토리에서 처리하게 하여 유지보수성과 재사용성을 향상시켰습니다.

### [Controller]

```java

@Controller
public class QuestionController {

//중략

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

//중략
}

```

+ Service 클래스를 사용하여 코드의 가독성을 높였으며, 재사용성 또한 높였습니다.



## 시연 영상

[시연영상](https://www.youtube.com/watch?v=CxD3iMuHXOE)
