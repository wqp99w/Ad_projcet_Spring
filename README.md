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

@Entity
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

+ 데이터베이스의 핵심 개념을 표현하여 여러 계층에서 재사용할 수 있도록 설계하였습니다.
  

### [View]


```java
public class QuestionController {
    @GetMapping("/")
    public String question_list(Model model){
        List<Question> questions =questionService.findQuestions().stream()
                        .sorted((q1, q2) -> q2.getQuestion_id().compareTo(q1.getQuestion_id()))
                                .collect(Collectors.toList());
        model.addAttribute("questionList",questions);
        return "Gooroom/question_list";
    }
    //중략
}
```html

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>AD project</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>

<!-- 중략 -->
            <tbody>
            <tr th:if="${questionList.empty}">
                <td colspan="4" class="text-center">질문이 없습니다.</td>
            </tr>
            <tr th:each="question, questionStat : ${questionList}">
                <td th:text="${question.getQuestion_id()}"></td>
                <td>
                    <a th:href="@{/questions/{question_id}(question_id=${question.getQuestion_id()})}"
                       th:text="${question.getSubject()}"></a>
                </td>
                <td th:text="${question.getAuthor()}"></td>
                <td th:text="${question.getCreate_date()}"></td>
            </tr>
            </tbody>
<!-- 중략 -->

```

+ Thymeleaf와 Bootstrap을 사용하여 동적으로 데이터를 바인딩하여 클라이언트와 서버 간의 데이터의 통신을 원활하게 처리하였습니다.

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




## 주요 기능

### [Create]

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%82%AD%EC%A0%9C%20%EA%B2%B0%EA%B3%BC.jpg" width="700" height="200"/>

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%A7%88%EB%AC%B8%20%EB%93%B1%EB%A1%9D2.jpg" width="400" height="400"/>

+ 질문 등록 버튼을 눌러 새로운 질문을 등록할 수 있습니다.


### [Read]

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%A7%88%EB%AC%B8%EB%AA%A9%EB%A1%9D.jpg" width="700" height="200"/>

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%A7%88%EB%AC%B8%EC%83%81%EC%84%B8.jpg" width="700" height="200"/>  

+ 등록된 질문을 선택하여 상세 정보를 볼 수 있습니다.

### [Update]

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%A7%88%EB%AC%B8%EC%88%98%EC%A0%95.jpg" width="400" height="400"/>

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%A7%88%EB%AC%B8%EC%88%98%EC%A0%95%EC%83%81%EC%84%B8.jpg" width="700" height="200"/>  

+ 수정 버튼을 눌러 질문의 정보를 변경할 수 있습니다.

### [Delete]

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%A7%88%EB%AC%B8%EC%82%AD%EC%A0%9C.jpg" width="700" height="200"/>

<img src="https://github.com/wqp99w/read-me_image/blob/main/webserver/%EC%82%AD%EC%A0%9C%20%EA%B2%B0%EA%B3%BC.jpg" width="700" height="200"/>  

+ 삭제 버튼을 눌러 질문을 삭제할 수 있습니다.

