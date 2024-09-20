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

```java

public class JpaFoodRepository implements FoodRepository{
    private final EntityManager em;

    public JpaFoodRepository(EntityManager em) {this.em = em; }

    @Override
    public List<Food> findByCate1AndCate2(String cate1, String cate2) {
         return em.createQuery("select f from Food f where f.cate1 = :cate1 AND f.cate2 = :cate2", Food.class)
                 .getResultList();
    }

    //중략
}
```

+ JPA를 사용하여 코드의 생산성과 유지보수성을 향상 시켰습니다.
  
### [Controller]

```java

@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    ///중략

    public List<Food> getAllFoods() {
        List<Food> foods = foodRepository.findALL();
        foods.forEach(food -> System.out.println("Food: " + food.getFood() + ", Category1: " + food.getCate1() + ", Category2: " + food.getCate2()));
        return foodRepository.findALL();
    }

    public List<Food> findRandomFoodByCategories(String cate1, String cate2) {
        return foodRepository.RandomfindBycate1Andcate2(cate1, cate2);
    }
}

```

+ Service 클래스를 사용하여 코드의 가독성을 높였으며, 재사용성 또한 높였습니다.



## 시연 영상

[시연영상](https://www.youtube.com/watch?v=CxD3iMuHXOE)
