package org.example.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

// /thymeleaf/example GET 요청이 오면 특정 데이터를 뷰, 즉 HTML 로 넘겨주는 모델 객체에 추가하는 컨트롤러 메서드를 작성

@Controller  // 컨트롤러라는 것을 명시적으로 표시
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {  // 뷰로 데이터를 넘겨주는 모델 객체
        // Model : 뷰, 즉, HTML 쪽으로 값을 넘겨주는 객체
        // 모델 객체는 따로 생성할 필요 없이 코드처럼 인자로 선언하면 스프링이 만듬
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", examplePerson);  // Person 객체 저장
        // examplePerson 객체를 "person" 이름으로 추가하는 코드
        model.addAttribute("today", LocalDate.now());
        // LcalDate.now() 객체를 "today" 이름으로 추가하는 코드

        /*
        // addAttribute() : 모델에 값을 저장함 = Model에 데이터를 담을 때 사용
        // 2가지 방법이 있음
        // 1. Model addAttribute(String name, Object value) -> value 객체를 name 이름으로 추가.
        // 뷰 코드에서는 name으로 지정한 이름을 통해서 value를 사용
        // 2. Model addAttribute(Object value) -> value를 추가. value의 패키지 이름을 제외한 단순 클래스 이름을 모델 이름으로 사용
        // + value가 배열이거나 컬렉션인 경우 첫 번째 원소의 클래스 이름 뒤에 "List"
         */

        return "example";
        // example.html라는 뷰 조회
        // 반환하는 값 : example
        // 클래스에 붙은 애너테이션이 @Controller 이므로 뷰의 이름을 반환하는 것임
        // 'ressource/templates' 디렉터리에서 example.html을 찾아 리턴함

    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
